package com.goddessbot.websocket;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventEndpoint extends WebSocketAdapter
{
    private static final Logger LOG = LoggerFactory.getLogger(EventEndpoint.class);
    private final CountDownLatch closureLatch = new CountDownLatch(1);

    @Override
    public void onWebSocketConnect(Session sess)
    {
        super.onWebSocketConnect(sess);
        LOG.debug("Endpoint connected: {}", sess);
    }

    @Override
    public void onWebSocketText(String message)
    {
        super.onWebSocketText(message);
        LOG.debug("Received TEXT message: {}", message);

        if (message.toLowerCase(Locale.US).contains("bye"))
        {
            getSession().close(StatusCode.NORMAL, "Thanks");
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        super.onWebSocketClose(statusCode, reason);
        LOG.debug("Socket Closed: [{}] {}", statusCode, reason);
        closureLatch.countDown();
    }

    @Override
    public void onWebSocketError(Throwable cause)
    {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }

    public void awaitClosure() throws InterruptedException
    {
        LOG.debug("Awaiting closure from remote");
        closureLatch.await();
    }
}