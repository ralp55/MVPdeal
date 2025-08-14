package neo.project.task.deal;

import java.util.List;

public class KafkaTopicsProperties {
    private List<Topic> topics;

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public static class Topic {
        private String name;
        private int numPartitions;
        private int replicationFactor;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getNumPartitions() { return numPartitions; }
        public void setNumPartitions(int numPartitions) { this.numPartitions = numPartitions; }

        public int getReplicationFactor() { return replicationFactor; }
        public void setReplicationFactor(int replicationFactor) { this.replicationFactor = replicationFactor; }
    }
}