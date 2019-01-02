package com.dfn.exchange.price;


import akka.actor.UntypedActorContext;
import com.dfn.exchange.price.req.*;
import com.dfn.exchange.price.util.PriceConstants;
import com.dfn.exchange.price.util.RequestPreProcessor;
import com.dfn.exchange.price.util.WSSessionStore;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manodyas on 3/21/2018.
 */

public class PriceWSConnector implements Runnable {
    private static RequestPreProcessor requestPreProcessor = new RequestPreProcessor();
    private SslContextFactory sslContextFactory;
    private Session session = null;


    public PriceWSConnector() {
    }

    @Override
    public void run() {
         start();
    }

    private void start() {
            reconnect();
    }

    public void reconnect(){
        sslContextFactory = new SslContextFactory();
        sslContextFactory.setTrustAll(true);
        WebSocketClient client = new WebSocketClient(sslContextFactory);

        try {
            client.start();
            QSPriceWSEndPoint socket = new QSPriceWSEndPoint(this);
            System.out.println("#####Price Connection Details:");
            System.out.println("#####URL:" + PriceConstants.WSUrl);
            client.connect(socket, URI.create(PriceConstants.WSUrl));
            while (!WSSessionStore.isConnected()){
                Thread.sleep(250);
            }
            this.session = WSSessionStore.getSession();
            sendLogin(); /*-Price Login-*/
            Thread.sleep(5000);
            sendFullMktSub(PriceConstants.EXCHANGE_CODE); /*-Full Mkt Subscription-*/

           /* List<String> symbolList = new ArrayList<>();
            symbolList.add("1010");
            symbolList.add("1020");
            symbolList.add("1050");
            symbolList.add("1080");
            symbolList.add("2010");

*/
            for(String s: PriceConstants.symbolList){
                sendDepthByOrderRequest(PriceConstants.EXCHANGE_CODE,s);
            }


           /* PulseGenerator pulseGenerator = new PulseGenerator();
            Thread thread = new Thread(pulseGenerator);
            thread.start();
            socket.setPulseGenerator(thread);*/

            while (WSSessionStore.isConnected()){

            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("###Error Connecting WS");
        }
    }


    private void sendLogin() throws IOException {
        LoginRequest loginRequest =
                new LoginRequest(PriceConstants.PRICE_USER,
                        "*",
                        "56",
                        "EN",
                        "1");
        String req = requestPreProcessor.composeRequest(loginRequest);
        System.out.println("#####Authenticating , Req:" + req);
        session.getRemote().sendString(req);
    }

    private void sendFullMktSub(String exchange) throws IOException {
        RequestJSONFullMarketQuote fullMarketQuote =
                new RequestJSONFullMarketQuote(exchange);
        String req = requestPreProcessor.composeRequest(fullMarketQuote);
       // System.out.println("#####Full Mkt Subscription , Req:" + req);
        session.getRemote().sendString(req);
    }


    public void sendDepthByOrderRequest(String exchange, String symbol) throws IOException {
        RequestJSONDepthByOrder depthByOrder =
                new RequestJSONDepthByOrder(exchange,
                        symbol);
        String req = requestPreProcessor.composeRequest(depthByOrder);
        System.out.println("####Depth By Order Request:" + req);

        session.getRemote().sendString(req);

    }

    public void sendDepthByPriceRequest(String exchange, String symbol) throws IOException {
        RequestJSONDepthByPrice depthByPrice =
                new RequestJSONDepthByPrice(exchange,
                        symbol);
        String req = requestPreProcessor.composeRequest(depthByPrice);

        session.getRemote().sendString(req);

    }




}
