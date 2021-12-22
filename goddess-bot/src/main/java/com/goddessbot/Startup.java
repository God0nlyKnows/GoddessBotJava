package com.goddessbot;

import javax.security.auth.login.LoginException;

import com.goddessbot.modules.CommandsHandler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.entities.*;

public class Startup {

    public static JDA buildJda() throws LoginException {

        JDABuilder builder = JDABuilder.createDefault(System.getenv("GODDESS_BOT_TOKEN"));

        configureMemoryUsage(builder);

        builder.setActivity(Activity.watching("You"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);

        builder.addEventListeners(new CommandsHandler());

        return builder.build();
    }

    private static void configureMemoryUsage(JDABuilder builder) {
        // Disable cache for member activities (streaming/games/spotify)
        builder.disableCache(CacheFlag.ACTIVITY);

        // Only cache members who are either in a voice channel or owner of the guild
        builder.setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER));

        // Disable member chunking on startup
        builder.setChunkingFilter(ChunkingFilter.NONE);

        builder.setAutoReconnect(true);

        // Consider guilds with more than 50 members as "large".
        // Large guilds will only provide online members in their setup and thus reduce
        // bandwidth if chunking is disabled.
        builder.setLargeThreshold(60);
    }
}
