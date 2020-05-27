package org.dbchain.blockchain.core.sqlparser;

import org.dbchain.blockchain.block.InstructionBase;

/**
 * @author zhaobo create on 2020/3/21.
 */
public interface InstructionParser {
    boolean parse(InstructionBase instructionBase);
}
