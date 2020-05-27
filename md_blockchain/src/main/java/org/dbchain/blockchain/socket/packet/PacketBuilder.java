package org.dbchain.blockchain.socket.packet;

import org.dbchain.blockchain.socket.body.BaseBody;
import org.tio.utils.json.Json;

/**
 * @author zhaobo create on 2020/3/12.
 */
public class PacketBuilder<T extends BaseBody> {
    /**
     * 消息类型，其值在Type中定义
     */
    private byte type;

    private T body;

    public PacketBuilder<T> setType(byte type) {
        this.type = type;
        return this;
    }

    public PacketBuilder<T> setBody(T body) {
        this.body = body;
        return this;
    }

    public BlockPacket build() {
        return new BlockPacket(type, Json.toJson(body));
    }
}
