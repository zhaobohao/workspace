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

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.paboo.leaf.SnowflakeImpl;
import org.paboo.leaf.config.LeafConfiguration;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Leonard Woo
 */

@Slf4j
public class LeafTest {

    @Test
    public void genTime() {
        log.info( ZonedDateTime.now(ZoneId.of("UTC")).toEpochSecond() + "" );
    }

    @Test
    public void genId() {
        SnowflakeImpl idGenerator = new SnowflakeImpl(2, 5);
        for (int i = 0; i < 10; i++) {
            log.info("ID -> " + idGenerator.nextId());
        }
    }

    @Test
    public void formatId() {
        SnowflakeImpl sf = new SnowflakeImpl(3,5);
        log.info("Time offset: " +
            ZonedDateTime.ofInstant( Instant.ofEpochSecond( sf.getEpoch()), ZoneId.of("UTC") ) );
        log.info(sf.toString());
        long id = sf.nextId();
        log.info("ID -> " + id);
        log.info(sf.formatId(id));
    }

    @Test
    public void loadConfig() throws Exception {
        LeafConfiguration.getInstance().loadConfig();
    }

    @Test
    public void getMillSecond()
    {
        System.out.println(System.currentTimeMillis());
    }
}
