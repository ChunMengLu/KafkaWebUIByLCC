package com.lcc.kafkaUI.config;

import com.lcc.kafkaUI.common.utils.JsonUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfig {

    private static String getBootstrapServers(ZooKeeper zooKeeper) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        // 获取所有Broker ID
        List<String> brokerIds = zooKeeper.getChildren("/brokers/ids", false);
        if (brokerIds.isEmpty()) {
            throw new RuntimeException("当前zookeeper没有kafka连接");
        }
        String brokerId = brokerIds.get(0);
        byte[] data = zooKeeper.getData("/brokers/ids/" + brokerId, false, new Stat());
        String brokerInfoStr = new String(data, StandardCharsets.UTF_8);
        Map<String, Object> map = JsonUtils.readMap(brokerInfoStr);
        String host = (String) map.get("host");
        Integer port = (Integer) map.get("port");
        return host + ':' + port;
    }

    @Bean
    public AdminClient adminClient(ObjectProvider<ZooKeeper> zooKeeperObjectProvider,
                                   KafkaProperties kafkaProperties) throws Exception {
        Properties properties = new Properties();
        // 禁止自动提交偏移量
        properties.put("enable.auto.commit", "false");
        ZooKeeper zooKeeper = zooKeeperObjectProvider.getIfAvailable();
        String bootstrapServers;
        if (zooKeeper == null) {
            bootstrapServers = kafkaProperties.getBootstrapServers();
        } else {
            bootstrapServers = getBootstrapServers(zooKeeper);
            // 将地址设置到 kafkaProperties
            kafkaProperties.setBootstrapServers(bootstrapServers);
        }
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return AdminClient.create(properties);
    }

}
