package com.flink.example.datasetapi;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.Arrays;

public class DatasetWordCount {
    public static void main(String[] args) throws  Exception {
        final ExecutionEnvironment env=ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> text=env.fromElements("who is this","it is right for u ,who knows that !!!");
        DataSet<Tuple2<String,Integer>> wordCounts=text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                Arrays.stream(s.split(" ")).forEach(key->collector.collect(new Tuple2<String,Integer>(key,1)));
            }
        }).groupBy(0).sum(1);
        wordCounts.print();
    }
}
