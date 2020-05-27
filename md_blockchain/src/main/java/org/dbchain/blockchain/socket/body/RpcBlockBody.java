package org.dbchain.blockchain.socket.body;

import org.dbchain.blockchain.block.Block;

/**
 * body里是一个block信息
 * @author zhaobo create on 2020/3/12.
 */
public class RpcBlockBody extends BaseBody {
    /**
     * blockJson
     */
    private Block block;

    public RpcBlockBody() {
        super();
    }

    public RpcBlockBody(Block block) {
        super();
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "BlockBody{" +
                "block=" + block +
                '}';
    }
}
