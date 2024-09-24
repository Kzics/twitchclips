package com.tc.embed;

import net.dv8tion.jda.api.EmbedBuilder;

public interface EmbedAction {
    void execute(EmbedBuilder builder);
}