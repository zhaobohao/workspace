package com.flink.example.datastreamapi;

import lombok.Data;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;

import java.util.Properties;

public class StreamingWindowWordCountReadFromKafka {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");

        //    args[0] = "test-0921";  //传入的是kafka中的topic
        FlinkKafkaConsumer010<String> consumer =
                new FlinkKafkaConsumer010<String>("test", new SimpleStringSchema(), props);

        DataStreamSource<String> text = env.addSource(consumer);
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

        windowCount.print();
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




