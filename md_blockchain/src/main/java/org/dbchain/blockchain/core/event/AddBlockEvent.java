package org.dbchain.blockchain.core.event;

import org.dbchain.blockchain.block.Block;
import org.springframework.context.ApplicationEvent;

/**
 * 确定生成block的Event（添加到rocksDB，执行sqlite语句，发布给其他节点）
 * @author zhaobo create on 2020/3/15.
 */
public class AddBlockEvent extends ApplicationEvent {
    public AddBlockEvent(Block block) {
        super(block);
    }
}
