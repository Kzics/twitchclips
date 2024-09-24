package com.tc.events;

import com.tc.manager.StreamerManager;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.tc.obj.Streamer;
import org.jetbrains.annotations.NotNull;

public class CommandInteractionEvents extends ListenerAdapter {

    private final StreamerManager streamerManager;
    public CommandInteractionEvents(StreamerManager streamerManager){
        this.streamerManager = streamerManager;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        if(event.getName().equals("addstreamer")){
            TextChannel channel = event.getOption("channel").getAsChannel().asTextChannel();

            String streamerName = event.getOption("streamer").getAsString();

            Streamer streamer = new Streamer(streamerName);

            System.out.println(channel.getId());

            streamerManager.addStreamerToChannel(channel.getId(), streamer);
            event.getHook().sendMessage("Streamer ajouté").queue();
        }
    }
}
