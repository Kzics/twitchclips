package com.tc.embed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;

public class EmbedMessageBuilder {
    private final EmbedBuilder embedBuilder;
    private final List<Button> buttons;

    public EmbedMessageBuilder() {
        this.embedBuilder = new EmbedBuilder();
        this.buttons = new ArrayList<>();
    }

    public EmbedMessageBuilder setTitle(String title, String url) {
        embedBuilder.setTitle(title, url);
        return this;
    }
    public EmbedMessageBuilder setDescription(String description) {
        embedBuilder.setDescription(description);
        return this;
    }

    public EmbedMessageBuilder setImage(String imageUrl) {
        embedBuilder.setImage(imageUrl);
        return this;
    }

    public EmbedMessageBuilder addField(String name, String value, boolean inline) {
        embedBuilder.addField(name, value, inline);
        return this;
    }

    public EmbedMessageBuilder addButton(EmbedButton button, ButtonType type) {
        Button discordButton;
        switch (type) {
            case ButtonType.LINK -> discordButton = Button.link(button.getLabel(), ((LinkButton) button).getUrl());
            case ButtonType.ACTION -> discordButton = Button.primary(button.getId(), button.getLabel());
            default -> throw new IllegalArgumentException("Unknown button type: " + type);
        }
        buttons.add(discordButton);
        return this;
    }
    public EmbedMessageBuilder addActionButton(ActionButton button){
        buttons.add(Button.primary(button.getId(),button.getLabel()));
        return this;
    }

    public EmbedMessageBuilder addLinkButton(LinkButton button){
        buttons.add(Button.link(button.getUrl(),button.getLabel()));
        return this;
    }

    public MessageEmbed build() {
        return embedBuilder.build();
    }

    public List<Button> getButtons() {
        return buttons;
    }
    // Example method to build a clip embed
    /*public static EmbedBuilder buildClipEmbed(obj.ClipInfo clipInfo) {
        embed.EmbedMessageBuilder builder = new embed.EmbedMessageBuilder();
        return builder.setTitle(clipInfo.title(), clipInfo.url())
                .setImage(clipInfo.thumbnailUrl())
                .addField("Titre", clipInfo.title(), true)
                .addField("Créateur", clipInfo.creatorName(), true)
                .addField("Durée", String.valueOf(clipInfo.duration()), true)
                .addField("Chaîne", clipInfo.broadcasterName(), true)
                .addButton(new LinkButton("link_id", "Watch", clipInfo.url()), ButtonType.LINK)
                .addButton(new embed.ActionButton("action_id","Click",(v)-> System.out.println("Clicked")), ButtonType.ACTION)
                .build();
    }*/
}