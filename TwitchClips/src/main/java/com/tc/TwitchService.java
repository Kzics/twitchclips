package com.tc;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.tc.obj.ClipInfo;
import com.tc.obj.Streamer;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;


public abstract class TwitchService {

    private final TwitchClient twitchClient;
    private final Streamer streamer;

    public TwitchService(String clientId, String clientSecret, String streamerName) {
        this.twitchClient = TwitchClientBuilder.builder()
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .withEnableHelix(true)
                .build();
        this.streamer = new Streamer(streamerName);
    }

    public TwitchClient getTwitchClient() {
        return twitchClient;
    }

    public TwitchService getInstance() {
        return this;
    }

    public abstract void onNewClip(ClipInfo clipInfo, TextChannel channel);

    public void startMonitoring() {
        System.out.println("STARTED MONITORING");
    }

    public static String convertToMp4(String url) {
        if (url == null || !url.contains("-preview-")) {
            throw new IllegalArgumentException("URL invalide ou ne contient pas le format attendu.");
        }

        int previewIndex = url.indexOf("-preview-");

        String baseUrl = url.substring(0, previewIndex);

        return baseUrl + ".mp4";
    }
}