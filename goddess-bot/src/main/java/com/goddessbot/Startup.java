package com.goddessbot;

import java.util.EnumSet;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Startup {

    public static JDA buildJda() throws LoginException {

        JDABuilder builder = JDABuilder.createDefault(Config.get("token"),configureIntents());

        configureMemoryUsage(builder);

        builder.setActivity(Activity.watching("You"));

        builder.setStatus(OnlineStatus.ONLINE);

        builder.addEventListeners(new Listener());

        return builder.build();
    }



    private static void configureMemoryUsage(JDABuilder builder) {

        builder.disableCache(CacheFlag.ACTIVITY);

        builder.setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER));

        builder.setChunkingFilter(ChunkingFilter.NONE);

        builder.setAutoReconnect(true);

        builder.setLargeThreshold(60);
    }


    private static EnumSet<GatewayIntent> configureIntents(){
        return EnumSet.of(
            GatewayIntent.DIRECT_MESSAGES,
            GatewayIntent.DIRECT_MESSAGE_REACTIONS,
            GatewayIntent.DIRECT_MESSAGE_TYPING,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS,
            GatewayIntent.GUILD_MESSAGE_TYPING,
            GatewayIntent.GUILD_VOICE_STATES,
            GatewayIntent.GUILD_EMOJIS,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_PRESENCES,
            GatewayIntent.GUILD_WEBHOOKS
        );
    }
}
