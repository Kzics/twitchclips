package com.tc;

import javax.security.auth.login.LoginException;

public class Main  {

    public static void main(String[] args) throws LoginException {
        BotConfig config = new BotConfig(
                "MTA5MDI2MDY4OTgyNzkzNDIyMA.GkPTOJ.2NP4UvO_gcYVWCLds1jK1joN4-fktwOauFRBnU",
                "h0gl6lc9n4djn8e1yvox388v68cy01",
                "tjlkw1wwu7v9iz6c0jztyo432lcsac",
                "thebausffs",
                "1110557740448886906"
        );

        DiscordBot bot = new DiscordBot(config);
        bot.start();
    }

}
