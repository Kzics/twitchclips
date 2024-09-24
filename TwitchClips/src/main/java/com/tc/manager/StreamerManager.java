package com.tc.manager;

import com.github.philippheuer.events4j.api.domain.IDisposable;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.events.ChannelClipCreatedEvent;
import com.github.twitch4j.helix.domain.StreamList;
import com.github.twitch4j.helix.domain.User;
import com.tc.DiscordBot;
import com.tc.TwitchService;
import com.tc.obj.TrackerChannel;
import com.tc.obj.ClipInfo;
import com.tc.obj.Streamer;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamerManager {
    private final TwitchService twitchService;
    private final Map<String, TrackerChannel> channels;
    private final HashMap<String, IDisposable> disposables = new HashMap<>();

    public StreamerManager(TwitchService twitchService) {
        this.channels = new HashMap<>();
        this.twitchService = twitchService;
    }


    public List<String> getStreamerNames() {
        return channels.values()
                .stream()
                .flatMap(channel -> channel.getStreamers().stream())
                .map(Streamer::name)
                .toList();
    }
    public void addStreamerToChannel(String channelId, Streamer streamer) {
        TrackerChannel channel = channels.getOrDefault(channelId, new TrackerChannel(channelId));
        channel.addStreamer(streamer);
        channels.put(channelId, channel);

        //final List<String> streamerNames = getStreamerNames();

        twitchService.getTwitchClient().getClientHelper().enableClipEventListener(getStreamerNames());

        twitchService.getTwitchClient().getEventManager().onEvent(ChannelClipCreatedEvent.class, event -> {
            System.out.println(event.getClip().getBroadcasterName());
            System.out.println(streamer.name());
            if (event.getClip().getBroadcasterName().equalsIgnoreCase(streamer.name())) {
                System.out.println(event.getClip().getVideoId());
                String title = getStreamTitle(twitchService.getTwitchClient(), streamer.name());
                System.out.println(title);

                ClipInfo clipInfo = new ClipInfo(
                        event.getClip().getTitle(),
                        event.getClip().getUrl(),
                        event.getClip().getCreatorName(),
                        event.getClip().getThumbnailUrl(),
                        TwitchService.convertToMp4(event.getClip().getThumbnailUrl()),
                        event.getClip().getEmbedUrl(),
                        event.getClip().getDuration(),
                        event.getClip().getBroadcasterName()
                );
                System.out.println("JE SUIS ICI");
                DiscordBot discordBot = (DiscordBot) twitchService;
                System.out.println("JE SUIS ICI");

                TextChannel textChannel = discordBot.getJda().getTextChannelById(channelId);
                System.out.println("JE SUIS ICI");

                twitchService.onNewClip(clipInfo,textChannel);
            }

            System.out.println("new CLIP FOUND");
        });
        //disposables.put("clip", disposable);

    }

    public static String getStreamTitle(TwitchClient twitchClient, String streamerName) {
        StreamList streams = twitchClient.getHelix()
                .getStreams(null, null , null, 1, null, null, null, List.of(streamerName)).execute();

        if (streams.getStreams().isEmpty()) {
            return "Le streamer n'est pas en ligne.";
        } else {
            // Retournez le titre du stream
            return streams.getStreams().get(0).getTitle();
        }
    }

    public TrackerChannel getChannel(String channelId) {
        return channels.get(channelId);
    }
}