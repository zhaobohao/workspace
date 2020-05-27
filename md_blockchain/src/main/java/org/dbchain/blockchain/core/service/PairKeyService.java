package org.dbchain.blockchain.core.service;

import org.dbchain.blockchain.block.PairKey;
import org.dbchain.blockchain.common.TrustSDK;
import org.dbchain.blockchain.common.exception.TrustSDKException;
import org.springframework.stereotype.Service;

/**
 * @author zhaobo create on 2020/3/7.
 */
@Service
public class PairKeyService {

    /**
     * 生成公私钥对
     * @return PairKey
     * @throws TrustSDKException TrustSDKException
     */
    public PairKey generate() throws TrustSDKException {
        return TrustSDK.generatePairKey(true);
    }
}
