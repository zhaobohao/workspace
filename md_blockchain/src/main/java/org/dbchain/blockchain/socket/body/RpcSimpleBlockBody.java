package org.dbchain.blockchain.socket.body;

/**
 * @author zhaobo create on 2020/4/25.
 */
public class RpcSimpleBlockBody extends BaseBody {
    /**
     * blockHash
     */
    private String hash;

    public RpcSimpleBlockBody() {
        super();
    }

    public RpcSimpleBlockBody(String hash) {
        super();
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "RpcSimpleBlockBody{" +
                "hash='" + hash + '\'' +
                '}';
    }
}
