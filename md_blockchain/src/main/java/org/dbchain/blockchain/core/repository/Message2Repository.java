package org.dbchain.blockchain.core.repository;

import org.dbchain.blockchain.core.model.Message2Entity;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface Message2Repository extends BaseRepository<Message2Entity> {
    /**
     * 删除一条记录
     * @param messageId  messageId
     */
    void deleteByMessageId(String messageId);

    /**
     * 查询一个
     * @param messageId messageId
     * @return MessageEntity
     */
    Message2Entity findByMessageId(String messageId);
}
