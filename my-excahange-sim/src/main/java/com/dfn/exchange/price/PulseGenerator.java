package com.dfn.exchange.price;


import com.dfn.exchange.price.req.PulseRequest;
import com.dfn.exchange.price.util.RequestPreProcessor;
import com.dfn.exchange.price.util.WSSessionStore;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;


/**
 * Created by manodyas on 3/21/2018.
 */
public class PulseGenerator implements Runnable {
    private static final Gson gson = new Gson();
    private static RequestPreProcessor requestPreProcessor = new RequestPreProcessor();
    private Session session = null;

    @Override
    public void run() {

        while (WSSessionStore.isConnected()) {
            if (session == null) {
                session = WSSessionStore.getSession();
            }
            try {
               Thread.sleep(2000);
                // session.getBasicRemote().sendText("8{\"0\":0}");
                session.getRemote().sendString(requestPreProcessor.composeRequest(gson.toJson(new PulseRequest())));
                // System.out.println("Sending Pulse.");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Pulse Generator Interrupted");
            }

        }
    }
}
