package org.dbchain.blockchain.core.manager;

import org.dbchain.blockchain.core.model.MessageEntity;
import org.dbchain.blockchain.core.repository.MessageRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaobo create on 2020/3/28.
 */
@Component
public class MessageManager {
    @Resource
    private MessageRepository messageRepository;

    public List<MessageEntity> findAll() {
        return messageRepository.findAll();
    }

    public List<String> findAllContent() {
        return findAll().stream().map(MessageEntity::getContent).collect(Collectors.toList());
    }

    public MessageEntity findById(String id) {
        return messageRepository.findByMessageId(id);
    }
}
