package com.flink.example.datastreamapi;

import lombok.Data;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class StreamingWindowWordCount {
    public static void main(String[] args) throws Exception{
        int port = 9000;
        try {
            ParameterTool parameterTool = ParameterTool.fromArgs(args);
            port = parameterTool.getInt("port");
        } catch (Exception e) {
            System.err.println("if not ports, default is 9000");
            port = 9000;
        }
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> text = env.socketTextStream("172.17.196.178", port, "\n");
        DataStream<WordWithCount> windowCount = text.flatMap(new FlatMapFunction<String, WordWithCount>() {
                                                                 @Override
                                                                 public void flatMap(String s, Collector<WordWithCount> collector) throws Exception {
                                                                     String[] splits = s.split("\\s");
                                                                     for (String word : splits) {
                                                                         collector.collect(new WordWithCount(word, 1L));

                                                                     }
                                                                 }
                                                             }

        )
        .keyBy("word")
                .timeWindow(Time.seconds(2), Time.seconds(1))
                .sum("count");

        windowCount.print().setParallelism(1);
        env.execute("streaming word count");

    }

    @Data
    public static class WordWithCount {
        public String word;
        public long count;

        public WordWithCount() {
        }

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }
    }
}




