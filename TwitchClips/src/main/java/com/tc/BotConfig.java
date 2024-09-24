package com.tc;

public record BotConfig(String discordToken, String twitchClientId, String twitchClientSecret, String twitchStreamerName, String discordChannelId) {}