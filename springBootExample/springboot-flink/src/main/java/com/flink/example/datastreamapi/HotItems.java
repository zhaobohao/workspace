package com.flink.example.datastreamapi;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.streaming.api.functions.source.TimestampedFileInputSplit;
import java.io.File;
import java.net.URL;


public class HotItems {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        URL fileUrl = HotItems.class.getClassLoader().getResource("UserBehavior.csv");
        Path FilePath = Path.fromLocalFile(new File(fileUrl.toURI()));

        PojoTypeInfo<UserBehavior> pojoTypeInfo = (PojoTypeInfo<UserBehavior>) TypeExtractor.createTypeInfo(UserBehavior.class);
        String[] fileOrder = new String[]{"userId", "itemId", "categoryId", "behavior", "timestamp"};

        PojoCsvInputFormat<UserBehavior> csvInput = new PojoCsvInputFormat<UserBehavior>(FilePath, pojoTypeInfo, fileOrder);

        DataStream<UserBehavior> datasource = env.createInput(csvInput, pojoTypeInfo);

        DataStream<UserBehavior> timeData = datasource.assignTimestampsAndWatermarks(
                new AscendingTimestampExtractor<UserBehavior>() {
                    @Override
                    public long extractAscendingTimestamp(UserBehavior userBehavior) {
                        return userBehavior.timestamp * 1000;
                    }
                }
        );

        DataStream<UserBehavior> pvData = timeData.filter(new FilterFunction<UserBehavior>() {
            @Override
            public boolean filter(UserBehavior userBehavior) throws Exception {
                return "pv".equals(userBehavior.behavior);
            }
        });
        pvData.print();
        DataStream<ItemViewCount> windowedData = pvData.keyBy("itemId")
                .timeWindow(Time.seconds(10), Time.seconds(5)).aggregate(new CountAgg(), new WindowResultFunction());
        windowedData.print().setParallelism(1);
        env.execute("streaming word count");
    }

    private static class CountAgg implements AggregateFunction<UserBehavior, Long, Long> {
        @Override
        public Long createAccumulator() {
            return 0L;
        }

        @Override
        public Long add(UserBehavior userBehavior, Long aLong) {
            return aLong + 1;
        }

        @Override
        public Long getResult(Long aLong) {
            return aLong;
        }

        @Override
        public Long merge(Long aLong, Long acc1) {
            return aLong + acc1;
        }
    }

    private static class WindowResultFunction implements WindowFunction<Long, ItemViewCount, Tuple, TimeWindow> {

        @Override
        public void apply(Tuple tuple, TimeWindow timeWindow, Iterable<Long> iterable, Collector<ItemViewCount> collector) throws Exception {
            Long itemid = ((Tuple1<Long>) tuple).f0;
            Long count = iterable.iterator().next();
            collector.collect(ItemViewCount.of(itemid, timeWindow.getEnd(), count));
        }

    }


}

