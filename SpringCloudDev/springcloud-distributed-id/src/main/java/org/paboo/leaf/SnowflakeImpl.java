/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.paboo.leaf;

import lombok.extern.slf4j.Slf4j;
import org.paboo.leaf.config.LeafConfiguration;
import org.paboo.leaf.exceptions.MaxIdException;
import org.paboo.leaf.utils.DatetimeUtil;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Another <b>Snowflake</b> algorithm implement.a network service
 * for generating unique ID numbers at high scale with some simple guarantees.
 *
 * https://github.com/twitter/snowflake
 *
 * +-------------------------------------------------------------------------------------------------+
 * | UNUSED(1BIT) |         TIMESTAMP(41BIT)           |  WORKER-ID(10BIT)  |    SERIAL-NO(12BIT)    |
 * +-------------------------------------------------------------------------------------------------+
 *
 * @author Leonard Woo
 */
@Slf4j
public class SnowflakeImpl {

    private final long unusedBits = 1L;

    private final  long epoch = LeafConfiguration.getInstance().loadConfig().getTimeOffset();

    // Datetime length
    private final long timestampBits = 41L;
    // Data identifier id length
    private final long datacenterIdBits = 5L;
    // Worker id length
    private final long workerIdBits = 5L;
    // Sequence length
    private final long sequenceBits = 12L;

    // max values of timeStamp, workerId, datacenterId and sequence
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); // 2^5-1
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits); // 2^5-1
    private final long maxSequence = -1L ^ (-1L << sequenceBits); // 2^12-1

    // left shift bits of timeStamp, workerId and datacenterId
    private final long timestampShift = sequenceBits + datacenterIdBits + workerIdBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long workerIdShift = sequenceBits;


    /**
     * data center number the process running on, its value can't be modified
     * after initialization.
     */
    private final long datacenterId;

    /**
     * machine or process number, its value can't be modified after
     * initialization.
     */
    private final long workerId;

    /**
     * the unique and incrementing sequence number scoped in only one
     * period/unit (here is ONE millisecond). its value will be increased by 1
     * in the same specified period and then reset to 0 for next period.
     */
    private long sequence = 0L;

    /**
     * the time stamp last snowflake ID generated
     */
    private long lastTimestamp = -1L;

    /**
     *
     * @param datacenterId data center number the process running on, value range: [0,31]
     * @param workerId machine or process number, value range: [0,31]
     */
    public SnowflakeImpl(long datacenterId, long workerId) {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new MaxIdException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new MaxIdException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }

        log.debug("%n Datecenter ID: " + datacenterId + "%n Worker ID:" + workerId);
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    /**
     * generate an unique and incrementing id
     *
     * @return id
     */
    public synchronized long nextId() {
        long currTimestamp = timestampNow();
        if (currTimestamp < lastTimestamp) {
            throw new IllegalStateException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                            lastTimestamp - currTimestamp));
        }
        if (currTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) { // overflow: greater than max sequence
                currTimestamp = waitNextMillis(currTimestamp);
            }
        } else { // reset to 0 for next period/millisecond
            sequence = 0L;
        }
        lastTimestamp = currTimestamp;

        long snowflake = ((currTimestamp - epoch) << timestampShift) | // timestamp
                (datacenterId << datacenterIdShift) | // datacenter
                (workerId << workerIdShift) | // worker
                sequence; // sequence
        return snowflake;
    }

    /**
     * track the amount of calling {@link #waitNextMillis(long)} method
     */
    private final AtomicLong waitCount = new AtomicLong(0);

    /**
     * @return the amount of calling {@link #waitNextMillis(long)} method
     */
    public long getWaitCount() {
        return this.waitCount.get();
    }

    public long getEpoch(){
        return this.epoch;
    }

    /**
     * running loop blocking until next millisecond
     *
     * @param	currTimestamp	current time stamp
     * @return current time stamp in millisecond
     */
    private long waitNextMillis(long currTimestamp) {
        waitCount.incrementAndGet();
        while (currTimestamp <= lastTimestamp) {
            currTimestamp = timestampNow();
        }
        return currTimestamp;
    }

    private long timestampNow(){
        return DatetimeUtil.getEpoch();
    }

    public long[] parseId(long id) {
        long[] arr = new long[5];
        arr[4] = ((id & diode(unusedBits, timestampBits)) >> timestampShift);
        arr[0] = arr[4] + epoch;
        arr[1] = (id & diode(unusedBits + timestampBits, datacenterIdBits)) >> datacenterIdShift;
        arr[2] = (id & diode(unusedBits + timestampBits + datacenterIdBits, workerIdBits)) >> workerIdShift;
        arr[3] = (id & diode(unusedBits + timestampBits + datacenterIdBits + workerIdBits, sequenceBits));
        return arr;
    }

    public String formatId(long id) {
        long[] arr = parseId(id);
        String tmf = DatetimeUtil.format("yyyy-MM-dd HH:mm:ss.SSS", DatetimeUtil.parse(arr[0]*1000));
        return String.format("%s, #%d, @(%d,%d)", tmf, arr[3], arr[1], arr[2]);
    }


    /**
     * a diode is a long value whose left and right margin are ZERO, while
     * middle bits are ONE in binary string layout. it looks like a diode in
     * shape.
     *
     * @param offset
     *            left margin position
     * @param length
     *            offset+length is right margin position
     * @return a long value
     */
    private long diode(long offset, long length) {
        int lb = (int) (64 - offset);
        int rb = (int) (64 - (offset + length));
        return (-1L << lb) ^ (-1L << rb);
    }

    @Override
    public String toString() {
        return "Snowflake Settings [timestampBits=" + timestampBits + ", datacenterIdBits=" + datacenterIdBits
                + ", workerIdBits=" + workerIdBits + ", sequenceBits=" + sequenceBits + ", epoch=" + epoch
                + ", datacenterId=" + datacenterId + ", workerId=" + workerId + "]";
    }
}
