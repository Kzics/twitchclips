package com.tc.events;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class ButtonClickEvents extends ListenerAdapter {

    public ButtonClickEvents(){
    }
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        final String componentId = event.getComponentId();

        if(componentId.startsWith("sl_")){
            String videoUrl = componentId.split("_")[1];
            System.out.println(videoUrl);
            event.deferReply(true).queue();

        }
    }
}
