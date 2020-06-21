package com.flink.example.datastreamapi;

import lombok.Data;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;
import org.apache.flink.util.Collector;

import java.util.Properties;

public class StreamingWindowWordCount2Kafka {
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
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        windowCount.map(new MapFunction<WordWithCount, String>() {

            @Override
            public String map(WordWithCount wordWithCount) throws Exception {
                return wordWithCount.toString();
            }
        }).addSink(new FlinkKafkaProducer010<String>("test", new SimpleStringSchema(),props));
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




