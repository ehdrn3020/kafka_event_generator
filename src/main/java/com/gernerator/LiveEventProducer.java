package com.gernerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class LiveEventProducer {
    private static final String IN_TOPIC = "live-in";
    private static final String OUT_TOPIC = "live-out";

    private final KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    private final List<String> streamIds = List.of("stream-1001", "stream-1002", "stream-1003");
    private final List<String> channelIds = List.of("youtube-a", "youtube-b", "youtube-c");

    public LiveEventProducer(String bootstrapServers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "1");

        this.producer = new KafkaProducer<>(props);
    }

    public void start() throws Exception {
        while (true) {
            boolean isEnter = random.nextBoolean();

            int idx = random.nextInt(streamIds.size());
            String streamId = streamIds.get(idx);
            String channelId = channelIds.get(idx);
            String userId = "user-" + (random.nextInt(10000) + 1);

            LiveEventProducer event = new LiveEventProducer(
                    isEnter ? "livein" : "liveout",
                    userId,
                    streamId,
                    channelId,
                    System.currentTimeMillis()
            );

            String topic = isEnter ? IN_TOPIC : OUT_TOPIC;
            String json = objectMapper.writeValueAsString(event);

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, userId, json);
            producer.send(record);

            System.out.println("Sent to topic=" + topic + ", message=" + json);

            Thread.sleep(1000);
        }
    }
}
