package com.springclouddev.loges.server.collect;

import com.springclouddev.loges.core.constant.LogMessageConstant;
import com.springclouddev.loges.core.kafka.KafkaConsumerClient;
import com.springclouddev.loges.server.InitConfig;
import com.springclouddev.loges.server.es.ElasticLowerClient;
import com.springclouddev.loges.server.util.DateUtil;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.*;

/**
* @Author Frank.chen
* @Description kafka collect
* @Date 14:15 2020/5/12
* @Param 
* @return 
**/
public class KafkaLogCollect extends BaseLogCollect{

    private  org.slf4j.Logger logger= LoggerFactory.getLogger(KafkaLogCollect.class);
    private KafkaConsumer<String, String> kafkaConsumer;

    public KafkaLogCollect(String kafkaHosts,String esHosts,String userName,String passWord){
        super.elasticLowerClient= ElasticLowerClient.getInstance(esHosts,userName,passWord);
        this.kafkaConsumer=KafkaConsumerClient.getInstance(kafkaHosts,InitConfig.KAFKA_GROUP_NAME,InitConfig.MAX_SEND_SIZE).getKafkaConsumer();
        this.kafkaConsumer.subscribe(Arrays.asList(LogMessageConstant.LOG_KEY,LogMessageConstant.LOG_KEY+"_"+ LogMessageConstant.LOG_TYPE_TRACE));
        logger.info("sending log ready!");
    }
    public  void kafkaStart(){
        logger.info("getting log ready!");
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
            List<String> logList=new ArrayList();
            List<String> sendlogList=new ArrayList();
            records.forEach(record->{
                logger.debug("get log:" + record.value()+"  logType:"+record.topic());
                if(record.topic().equals(LogMessageConstant.LOG_KEY)){
                    logList.add(record.value());
                }
                if(record.topic().equals(LogMessageConstant.LOG_KEY_TRACE)){
                    sendlogList.add(record.value());
                }
            });
            if(logList.size()>0) {
                super.sendLog(LogMessageConstant.ES_INDEX + DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD),logList);
            }
            if(sendlogList.size()>0){
                super.sendTraceLogList(LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_TRACE + "_" + DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD),sendlogList);
            }
        }
    }
}
