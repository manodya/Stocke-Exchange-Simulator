package com.dfn.exchange.price;


import com.dfn.exchange.price.util.PriceResponsePostProcessor;
import com.dfn.exchange.price.util.WSSessionStore;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;

/**
 * Created by manodyas on 3/21/2018.
 */
@WebSocket
public class QSPriceWSEndPoint {


    private static PriceResponsePostProcessor postProcessor = null;
    private static Gson gson = new Gson();
   private Thread pulseGenerator = null;
    private PriceWSConnector priceWSConnector =  null;

    public QSPriceWSEndPoint(PriceWSConnector connector){

        postProcessor = new PriceResponsePostProcessor();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("###Connected to endpoint:" + session.getRemote());
        WSSessionStore.setConnectionStatus(true);
        WSSessionStore.addSessionToStore(session);

    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("##Close");
        WSSessionStore.setConnectionStatus(false);
        if(pulseGenerator != null){
           pulseGenerator.interrupt();
            priceWSConnector.reconnect();
        }
        //priceWSConnector.reconnect();


    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        System.out.println("##Warn");
        System.out.printf(cause.getMessage());
        WSSessionStore.setConnectionStatus(false);
        if(pulseGenerator != null){
            pulseGenerator.interrupt();
            priceWSConnector.reconnect();
        }
        //priceWSConnector.reconnect();


    }

    @OnWebSocketMessage
    public void onMessage(String message) throws IOException {
        long start = System.currentTimeMillis();
        // System.out.println("###Received message in client: " + message);
        Object resObject = postProcessor.parsePriceResponse(message);
           }


    public void setPulseGenerator(Thread pulseGenerator) {
        this.pulseGenerator = pulseGenerator;
    }

    public void setPriceWSConnector(PriceWSConnector priceWSConnector) {
        this.priceWSConnector = priceWSConnector;
    }
}
