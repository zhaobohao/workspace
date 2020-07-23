package org.dbchain.blockchain.core.manager;

import org.dbchain.blockchain.core.model.Message2Entity;
import org.dbchain.blockchain.core.model.MessageEntity;
import org.dbchain.blockchain.core.repository.Message2Repository;
import org.dbchain.blockchain.core.repository.MessageRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaobo create on 2020/3/28.
 */
@Component
public class Message2Manager {
    @Resource
    private Message2Repository message2Repository;

    public List<Message2Entity> findAll() {
        return message2Repository.findAll();
    }

    public List<String> findAllContent() {
        return findAll().stream().map(Message2Entity::getContent).collect(Collectors.toList());
    }

    public Message2Entity findById(String id) {
        return message2Repository.findByMessageId(id);
    }
}
