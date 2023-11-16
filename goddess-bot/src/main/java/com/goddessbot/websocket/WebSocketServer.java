package com.goddessbot.websocket;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.*;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;


public class WebSocketServer {

    public static void startWebSocketServer() {
       int serverPort = Integer.getInteger("server.port", 8080);

    Server server = new Server(serverPort);
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Configure specific websocket behavior
        JettyWebSocketServletContainerInitializer.configure(context, (servletContext, wsContainer) ->
        {
            // Configure default max size
            wsContainer.setMaxTextMessageSize(65535);

            // Add websockets
            wsContainer.addMapping("/events/*", EventEndpoint.class);
        });

try {
    server.start();
    server.join();
    
} catch (Exception e) {
    
}
    }
}