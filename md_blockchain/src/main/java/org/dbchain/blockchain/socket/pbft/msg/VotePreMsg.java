package org.dbchain.blockchain.socket.pbft.msg;

import org.dbchain.blockchain.block.Block;

/**
 * @author zhaobo create on 2020/4/25.
 */
public class VotePreMsg extends VoteMsg {
    private Block block;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
