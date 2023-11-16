package com.goddessbot;

import com.goddessbot.websocket.WebSocketServer;

import net.dv8tion.jda.api.JDA;

public class App {

    private static JDA jda;

    public static void main(String[] args) {
        try {
            Thread webSocketThread = new Thread(() -> {
                WebSocketServer.startWebSocketServer();
            });
            webSocketThread.start();

            // Build and start the JDA Discord bot in the main thread
            jda = Startup.buildJda();
            jda.awaitReady();

            // Optionally, you can join the WebSocket thread if needed
            webSocketThread.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
