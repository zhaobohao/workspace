package org.rocketmq.test.simple;

import org.apache.rocketmq.common.message.MessageExt;

import java.util.TreeMap;

public class CachedQueue {
    private final TreeMap<Long, MessageExt> msgCachedTable = new TreeMap<Long, MessageExt>();

    public TreeMap<Long, MessageExt> getMsgCachedTable() {
        return msgCachedTable;
    }
}
