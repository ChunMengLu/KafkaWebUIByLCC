package com.lcc.kafkaUI.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "kafka.type", havingValue = "zookeeper")
public class ZookeeperConfig {

    @Bean
    public ZooKeeper zooKeeper(KafkaProperties properties) throws Exception {
        KafkaProperties.Zookeeper zookeeper = properties.getZookeeper();
        String host = zookeeper.getHost();
        int port = zookeeper.getPort();
        Duration sessionTimeout = zookeeper.getSessionTimeout();
        // 连接到 ZooKeeper
        return new ZooKeeper(host + ":" + port, (int) sessionTimeout.toMillis(), event -> {
            log.info("zookeeper连接成功");
        });
    }
}
