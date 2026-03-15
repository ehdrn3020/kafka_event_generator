package com.generator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveViewerEvent {
    private String eventType; // livein or liveout
    private String userId;
    private String streamId;
    private String channelId;
    private long eventTime;
}