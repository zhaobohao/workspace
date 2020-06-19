package org.springclouddev.log.core.kafka;

import org.springclouddev.log.core.util.Pool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * className：KafkaProducerPool
 * description：kafka Producer Pool
 * time：2020-05-11.16:17
 *
 * @author Frank.chen
 * @version 1.0.0
 */
public class KafkaProducerPool extends Pool<KafkaProducer> {

    public KafkaProducerPool(final GenericObjectPoolConfig poolConfig, final String hosts) {
        super(poolConfig,new KafkaProducerFactory(hosts));
    }
    public KafkaProducerPool( final String hosts) {
        super(new GenericObjectPoolConfig(),new KafkaProducerFactory(hosts));
    }
    @Override
    public KafkaProducer getResource() {
        KafkaProducer connection = super.getResource();
        return connection;
    }
    @Override
    public void returnBrokenResource(final KafkaProducer resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }
    @Override
    public void returnResource(final KafkaProducer resource) {
        if (resource != null) {
            try {
                returnResourceObject(resource);
            } catch (Exception e) {
                returnBrokenResource(resource);
            }
        }
    }
    
}
