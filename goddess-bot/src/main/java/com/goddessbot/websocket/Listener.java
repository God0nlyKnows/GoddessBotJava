package com.goddessbot.websocket;

public class Listener implements org.eclipse.jetty.websocket.api.WebSocketListener {
        @Override
        public void onWebSocketBinary(byte[] payload, int offset, int len) {
            // Handle binary messages
        }

        @Override
        public void onWebSocketClose(int statusCode, String reason) {
            // Handle WebSocket close
        }

        @Override
        public void onWebSocketConnect(org.eclipse.jetty.websocket.api.Session session) {
            System.out.println("w");
        }

        @Override
        public void onWebSocketError(Throwable cause) {
            // Handle WebSocket errors
        }

        @Override
        public void onWebSocketText(String message) {
            // Handle text messages
        }
}