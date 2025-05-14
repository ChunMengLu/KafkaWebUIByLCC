package com.lcc.kafkaUI.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 验证码配置
 *
 * @author L.cm
 */
@Getter
@Setter
@ConfigurationProperties("kafka")
public class KafkaProperties {
    /**
     * kafka 类型，支持 BootstrapServers 和 ZooKeeper
     */
    private KafkaType type = KafkaType.BOOTSTRAP_SERVERS;
    /**
     * kafka bootstrap-servers 地址，默认：localhost:9092
     */
    private String bootstrapServers = "localhost:9092";
    /**
     * zk 配置
     */
    private Zookeeper zookeeper = new Zookeeper();

    /**
     * kafka 类型
     */
    public enum KafkaType {
        BOOTSTRAP_SERVERS,
        ZOOKEEPER
    }

    /**
     * zk 配置
     */
    @Data
    public static class Zookeeper {
        private String host = "localhost";
        private int port = 2181;
        private Duration sessionTimeout = Duration.ofSeconds(30);
    }
}
