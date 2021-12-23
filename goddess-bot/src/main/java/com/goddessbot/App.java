package com.goddessbot;

import net.dv8tion.jda.api.JDA;

/**
 * Hello world!
 *
 */
public class App {
    
    private static JDA jda;

    public static void main(String[] args) {
        try {
            jda = Startup.buildJda();

            jda.awaitReady();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
