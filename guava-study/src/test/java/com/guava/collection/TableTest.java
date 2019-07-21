package com.guava.collection;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class TableTest {
    @Test
    public void test1()
    {
        Table<Integer, Integer, Integer> weightedGraph = HashBasedTable.create();
        weightedGraph.put(1, 2, 4);
        weightedGraph.put(1, 3, 20);
        weightedGraph.put(2, 3, 5);

        log.info(weightedGraph.row(1)); // returns a Map mapping v2 to 4, v3 to 20
        log.info(weightedGraph.column(3)); // returns a Map mapping v1 to 20, v2 to 5


    }
}
