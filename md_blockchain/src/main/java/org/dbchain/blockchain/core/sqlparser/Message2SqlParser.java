package org.dbchain.blockchain.core.sqlparser;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.dbchain.blockchain.block.Operation;
import org.dbchain.blockchain.common.CommonUtil;
import org.dbchain.blockchain.core.model.Message2Entity;
import org.dbchain.blockchain.core.model.MessageEntity;
import org.dbchain.blockchain.core.repository.Message2Repository;
import org.dbchain.blockchain.core.repository.MessageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 解析语句入库的具体实现，Message2表的
 * @author zhaobo create on 2020/3/21.
 */
@Service
public class Message2SqlParser extends AbstractSqlParser<Message2Entity> {
    @Resource
    private Message2Repository message2Repository;

    @Override
    public void parse(byte operation, String messageId, Message2Entity entity) {
         if (Operation.ADD == operation) {
        	 entity.setCreateTime(CommonUtil.getNow());
             entity.setMessageId(messageId);
             message2Repository.save(entity);
         } else if (Operation.DELETE == operation) {
             message2Repository.deleteByMessageId(messageId);
         } else if (Operation.UPDATE == operation) {
             Message2Entity messageEntity = message2Repository.findByMessageId(messageId);
             BeanUtil.copyProperties(entity, messageEntity, CopyOptions.create().setIgnoreNullValue(true).setIgnoreProperties("id", "createTime"));
             message2Repository.save(messageEntity);
         }
    }

    @Override
    public Class getEntityClass() {
        return Message2Entity.class;
    }

}
