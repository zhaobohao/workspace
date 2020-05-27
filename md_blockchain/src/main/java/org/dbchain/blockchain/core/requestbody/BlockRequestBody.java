package org.dbchain.blockchain.core.requestbody;

import org.dbchain.blockchain.block.BlockBody;

/**
 * 生成Block时传参
 * @author zhaobo create on 2020/3/8.
 */
public class BlockRequestBody {
    private String publicKey;
    private BlockBody blockBody;

    @Override
    public String toString() {
        return "BlockRequestBody{" +
                "publicKey='" + publicKey + '\'' +
                ", blockBody=" + blockBody +
                '}';
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public BlockBody getBlockBody() {
        return blockBody;
    }

    public void setBlockBody(BlockBody blockBody) {
        this.blockBody = blockBody;
    }
}
