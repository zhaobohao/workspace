package com.guava.collection;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Table;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class TableTest {
    @Test
    public void test1()
    {
        Table<Integer, Integer, String> weightedGraph = HashBasedTable.create();
        weightedGraph.put(1, 1, "男");
        weightedGraph.put(2, 1, "女");
        weightedGraph.put(3, 1, "它");

        weightedGraph.put(1, 2, "女");
        weightedGraph.put(2, 2, "男");
        weightedGraph.put(3, 2, "它");

        weightedGraph.put(1, 3, "男");
        weightedGraph.put(2, 3, "女");
        weightedGraph.put(3, 3, "它");



        log.info(weightedGraph.row(1)); // returns a Map mapping v2 to 4, v3 to 20
        log.info(weightedGraph.column(3)); // returns a Map mapping v1 to 20, v2 to 5

        BiMap  bm= HashBiMap.create(weightedGraph.column(2));

        log.info(bm.inverse());
    }
}
