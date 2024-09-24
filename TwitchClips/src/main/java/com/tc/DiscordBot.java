package com.tc;

import com.tc.embed.EmbedMessageBuilder;
import com.tc.events.ButtonClickEvents;
import com.tc.events.CommandInteractionEvents;
import com.tc.manager.StreamerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.GatewayIntent;
import com.tc.obj.ClipInfo;


public class DiscordBot extends TwitchService {

    private final JDA jda;
    private final String channelId;

    public DiscordBot(BotConfig config) {
        super(config.twitchClientId(), config.twitchClientSecret(), config.twitchStreamerName());
        this.jda = JDABuilder.createDefault(config.discordToken())
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.of(Activity.ActivityType.PLAYING,"RIEN")).build();
        this.channelId = config.discordChannelId();

        StreamerManager streamerManager = new StreamerManager(super.getInstance());

        jda.addEventListener(new ButtonClickEvents(),
                new CommandInteractionEvents(streamerManager));

        registerCommands();

    }

    @Override
    public void onNewClip(ClipInfo clipInfo, TextChannel channel) {
        EmbedMessageBuilder builder = new EmbedMessageBuilder();
        System.out.println(clipInfo.thumbnailUrl());
        builder.setTitle(clipInfo.title(), clipInfo.url())
                .setImage(clipInfo.thumbnailUrl())
                .addField("Titre", clipInfo.title(), true)
                .addField("Créateur", clipInfo.creatorName(), true)
                .addField("Durée", String.valueOf(clipInfo.duration()), true)
                .addField("Chaîne", clipInfo.broadcasterName(), true)
                .build();

        if (channel != null) {
            System.out.println("ENVOI DU MESSAGE");

            MessageEmbed messageEmbed = builder.build();

            channel.sendMessage(String.format("%s", clipInfo.videoUrl())).queue();

            String clipId = clipInfo.url().split("tv/")[1].split("/")[0];

            channel.sendMessageEmbeds(messageEmbed)
                    .addActionRow(Button.link(clipInfo.videoUrl(), "Télécharger le clip"))
                    .addActionRow(Button.link("https://app.streamladder.com/twitch-clip/" + clipId + "/layouts/Split16x9", "Split Screen"))
                    .addActionRow(Button.link("https://app.streamladder.com/twitch-clip/" + clipId + "/layouts/BlurredBackground", "Blurred Screen"))
                    .addActionRow(Button.link("https://app.streamladder.com/twitch-clip/" + clipId + "/layouts/FullScreen", "Full Screen"))
                    .queue();
        }
    }

    public void start() {
        startMonitoring();
    }

    public JDA getJda() {
        return jda;
    }

    public void registerCommands(){
        try {
            jda.awaitReady().getGuilds().forEach(g->{
                g.updateCommands().addCommands(Commands.slash("addstreamer","Ajoute un streamer")
                        .addOption(OptionType.STRING,"streamer", "Streamer")
                        .addOption(OptionType.CHANNEL, "channel", "channel"))
                        .queue();
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}