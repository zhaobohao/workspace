package org.dbchain.blockchain.block;

/**
 * @author zhaobo create on 2020/3/20.
 */
public interface Operation {
    byte ADD = 1;
    byte DELETE = -1;
    byte UPDATE = 2;

}
