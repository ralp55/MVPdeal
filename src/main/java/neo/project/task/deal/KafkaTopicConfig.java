package neo.project.task.deal;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class KafkaTopicConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka")
    public KafkaTopicsProperties kafkaTopicsProperties() {
        return new KafkaTopicsProperties();
    }

    @Bean
    public List<NewTopic> createTopics(KafkaTopicsProperties props) {
        return props.getTopics().stream()
                .map(t -> new NewTopic(t.getName(), t.getNumPartitions(), (short) t.getReplicationFactor()))
                .collect(Collectors.toList());
    }
}
