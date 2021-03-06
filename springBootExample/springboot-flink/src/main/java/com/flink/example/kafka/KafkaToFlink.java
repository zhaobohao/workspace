package com.flink.example.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.StreamQueryConfig;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Objects;
import java.util.Properties;

import static org.apache.flink.api.common.typeinfo.BasicTypeInfo.STRING_TYPE_INFO;

public class KafkaToFlink {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);
        String planner = params.has("planner") ? params.get("planner") : "blink";
        //初始化流式处理环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //初始化table环境
        StreamTableEnvironment tEnv;
        if (Objects.equals(planner, "blink")) {	// use blink planner in streaming mode
            EnvironmentSettings settings = EnvironmentSettings.newInstance()
                    .inStreamingMode()
                    .useBlinkPlanner()
                    .build();
            tEnv = StreamTableEnvironment.create(env, settings);
        } else if (Objects.equals(planner, "flink")) {	// use flink planner in streaming mode
            EnvironmentSettings settings = EnvironmentSettings.newInstance()
                    .inStreamingMode()
                .useOldPlanner()

                    .build();
            tEnv = StreamTableEnvironment.create(env, settings);
        } else {
            System.err.println("The planner is incorrect. Please run 'StreamSQLExample --planner <planner>', " +
                    "where planner (it is either flink or blink, and the default is blink) indicates whether the " +
                    "example uses flink planner or blink planner.");
            return;
        }
        //设置此可以屏蔽掉日记打印情况，运行环境配置
//      env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        env.enableCheckpointing(500);      //启动检查点
        //toAppendStream方法的查询配置
       // StreamQueryConfig qConfig = tEnv.queryConfig();
        //空闲状态保持时间，最小最大时间
       // qConfig.withIdleStateRetentionTime(Time.milliseconds(5), Time.minutes(6));

        //kafka配置信息
        Properties properties = new Properties();
//        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.201.82.55:9092");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test");

        //创建flink kafka消费者，不用版本功能介绍
        // https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/connectors/kafka.html
        FlinkKafkaConsumer010<String> myConsumer = new FlinkKafkaConsumer010<>(
                "ReportedData",
                new SimpleStringSchema(),
                properties);

        DataStream<String> sourceStream = env.addSource(myConsumer);
        //将来源的数据转为DataStream
        DataStream<Tuple3<String,String,String>> stream = sourceStream.map(new JobMapFun());
        //注册临时表
        tEnv.registerDataStream("myTable2", stream, "din,type,version");
        //写sql语句
        String sql = "SELECT din,type,version FROM myTable2";
        //执行sql查询
        Table table = tEnv.sqlQuery(sql);
        //打印查看数据
        DataStream<Tuple2<Boolean, Row>> wcDataStream = tEnv.toRetractStream(table, Row.class);
        wcDataStream.print();

        //sql中的字段的类型
        RowTypeInfo typeInformations = new RowTypeInfo(STRING_TYPE_INFO);
        //将数据追加到Table中
//        DataStream<Row> retractStream = tableEnv.toAppendStream(table, typeInformations, qConfig);
        //输出查询结果，value.f1就是Row
//        desStream.print();
//        retractStream.print();

        //将获取的数据直接输出
//        sourceStream.map(new MapFunction<String, String>() {
//            @Override
//            public String map(String value) throws Exception {
//                return "Stream Value ========== " + value;
//            }
//        }).print();
        env.execute("flink kafka test");
    }

    public static class JobMapFun implements MapFunction<String, Tuple3<String,String,String>> {
        @Override
        public Tuple3<String,String,String> map(String json) {
            JSONObject outJson = JSON.parseObject(json);
            String din = outJson.get("din").toString();
            System.out.println("-----------Din:"+ din);
            String type = outJson.get("type").toString();
            String version = outJson.get("version").toString();
            Long timestamp = Long.parseLong(outJson.get("timestamp").toString());
            return new Tuple3<String, String, String>(din,type,version);
        }
    }

}
