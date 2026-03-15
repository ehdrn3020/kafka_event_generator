package com.gernerator;

public class Main {
    public static void main(String[] args) throws Exception {
        String bootstrapServers = System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", "kafka:9092");

        LiveEventProducer producer = new LiveEventProducer(bootstrapServers);
        producer.start();
    }
}