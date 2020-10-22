package com.github.s7connector.impl.serializer.parser;

import java.util.Vector;

public final class BeanParseResult {

    /**
     * The needed blocksize
     */
    public int blockSize;

    /**
     * The Bean entries
     */
    public Vector<BeanEntry> entries = new Vector<BeanEntry>();

}
