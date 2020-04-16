/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.RestP;

import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 *
 * @author santananas
 */

@ServerEndpoint(value = "/{url}")
public class Endpoint {
    
    private Session session;
    private static Set<Endpoint> Endpoints = new CopyOnWriteArraySet<>();
    private List<String> MessageList = new ArrayList<>();
    
    @OnOpen
    public void onOpen(Session session,@PathParam("url") final String url) throws IOException {
        this.session = session;
        Endpoints.add(this);
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        MessageList.add(message);
    }
 
    @OnClose
    public void onClose(Session session) throws IOException {
        this.session = session;
        Endpoints.remove(this);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}
