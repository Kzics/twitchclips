package com.tc.obj;

import java.util.ArrayList;
import java.util.List;

public class TrackerChannel {
    private final String channelId;
    private final List<Streamer> streamers;

    public TrackerChannel(String channelId) {
        this.channelId = channelId;
        this.streamers = new ArrayList<>();
    }

    public String getChannelId() {
        return channelId;
    }

    public List<Streamer> getStreamers() {
        return new ArrayList<>(streamers);
    }

    public void addStreamer(Streamer streamer) {
        this.streamers.add(streamer);
    }
}
