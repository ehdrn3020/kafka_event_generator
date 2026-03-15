package com.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.Random;

public class LiveEventProducer {
    private static final Logger log = LoggerFactory.getLogger(LiveEventProducer.class);

    private static final String IN_TOPIC = "livein";
    private static final String OUT_TOPIC = "liveout";

    private final KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();
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
            int idx = random.nextInt(channelIds.size());
            String channelId = channelIds.get(idx);
            String userId = "user-" + (random.nextInt(10000) + 1);

            LiveViewerEvent event = new LiveViewerEvent(
                isEnter ? "livein" : "liveout",
                userId,
                channelId,
                System.currentTimeMillis()
            );

            String topic = isEnter ? IN_TOPIC : OUT_TOPIC;
            String json = objectMapper.writeValueAsString(event);

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, userId, json);

            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    log.error(
                        "[Fail]. topic={}, key={}, payload={}",
                        topic, userId, json, exception
                    );
                } else {
                    log.info(
                        "[SUCCESS]. topic={}, partition={}, offset={}, key={}",
                        metadata.topic(), metadata.partition(), metadata.offset(), userId
                    );
                }
            });

            log.info("Generated event. topic={}, payload={}", topic, json);

            Thread.sleep(1000);
        }
    }
}