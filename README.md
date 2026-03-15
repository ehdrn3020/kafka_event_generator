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
├── live-in
└── live-out
```
### Data Schema
```declarative
{
  "eventType": "livein",
  "userId": "user-123",
  "streamId": 202603152233489,
  "channeId": "youtube-a",
  "eventTime": 1710000005000
}
```
---

### Run
```declarative

```