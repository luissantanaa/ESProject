package pt.ua.deti.es.p21.BodyTrackingAnalysis.KafkaP;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {
    //@Value(value = "192.168.160.103:9093")
    @Value(value = "localhost:9092")
    private String bootstrapAddress;
 
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
     
    @Bean
    public NewTopic topic1() {
         return new NewTopic("esp21_alarms", 1, (short) 1);
    }
    
    
    @Bean
    public NewTopic topic2() {
         return new NewTopic("esp21_joints", 1, (short) 1);
    }
    
    @Bean
    public NewTopic topic3() {
         return new NewTopic("esp21_logs", 1, (short) 1);
    }

    
    
}