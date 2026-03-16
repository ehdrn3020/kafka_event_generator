## Kafka Event Generator

- Kafka Event Generator continuously produces streaming event data to Kafka topics.
- It simulates live viewing data (e.g., YouTube live streams) by randomly generating viewer join and viewer leave events and sending them to Kafka. 

This project is used to generate test data in the following scenarios
- Testing Kafka streaming pipelines
- Testing aggregation workflows with ClickHouse / Spark / Flink
- Experimenting with real-time event processing
---

## Tech Stack
- Java 17
- Gradle
- Apache Kafka
- Docker Compose
---

## Architecture
### Topic
```declarative
Kafka
├── livein
└── liveout
```
### Data Schema
```declarative
{
    "eventType": "livein",
    "userId": "user-7095",
    "channelId": "youtube-c",
    "eventTime": 1773668485841
}
```
---

### Run
```declarative
// 실행 & 중지 
docker compose up -d --build
docker compose down -v

// 토픽 확인
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic livein

// 로그 확인
docker logs -f kafka-event-generator

// Kafbat UI 접속
http://localhost:8080/
```