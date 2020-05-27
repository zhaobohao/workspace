package org.dbchain.blockchain.core.sqlparser;

import javax.annotation.Resource;

import org.dbchain.blockchain.block.Operation;
import org.dbchain.blockchain.common.CommonUtil;
import org.dbchain.blockchain.core.model.MessageEntity;
import org.dbchain.blockchain.core.repository.MessageRepository;
import org.springframework.stereotype.Service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * 解析语句入库的具体实现，Message表的
 * @author zhaobo create on 2020/3/21.
 */
@Service
public class MessageSqlParser extends AbstractSqlParser<MessageEntity> {
    @Resource
    private MessageRepository messageRepository;

    @Override
    public void parse(byte operation, String messageId, MessageEntity entity) {
         if (Operation.ADD == operation) {
        	 entity.setCreateTime(CommonUtil.getNow());
             entity.setMessageId(messageId);
             messageRepository.save(entity);
         } else if (Operation.DELETE == operation) {
             messageRepository.deleteByMessageId(messageId);
         } else if (Operation.UPDATE == operation) {
             MessageEntity messageEntity = messageRepository.findByMessageId(messageId);
             BeanUtil.copyProperties(entity, messageEntity, CopyOptions.create().setIgnoreNullValue(true).setIgnoreProperties("id", "createTime"));
             messageRepository.save(messageEntity);
         }
    }

    @Override
    public Class getEntityClass() {
        return MessageEntity.class;
    }

}
