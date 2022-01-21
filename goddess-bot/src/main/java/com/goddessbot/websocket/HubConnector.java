package com.goddessbot.websocket;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

public class HubConnector {

    private HubConnection hub;


    public HubConnector(){
        this.hub = HubConnectionBuilder.create("").build();
    }

    public HubConnection getHub(){
        return hub;
    }

}
