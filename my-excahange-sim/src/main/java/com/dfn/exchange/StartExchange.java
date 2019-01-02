package com.dfn.exchange;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.dfn.exchange.fix.FIXConnectionMode;
import com.dfn.exchange.fix.FIXConstants;
import com.dfn.exchange.fix.TradeAppAcceptor;
import com.dfn.exchange.fix.TradeAppInitiator;
import com.dfn.exchange.price.PriceWSConnector;
import com.dfn.exchange.utils.PropertyLoader;

/**
 * Created by manodyas on 7/30/2018.
 */
public class StartExchange {
    public static void main(String[] args) {
        System.out.println("###Starting Exchange.");


        PropertyLoader.loadSystemProperties();

        if(FIXConstants.connectionMode.equalsIgnoreCase(FIXConnectionMode.INITIATOR.toString())){
            TradeAppInitiator.getInstance().startInitiator();
        }else if(FIXConstants.connectionMode.equalsIgnoreCase(FIXConnectionMode.ACCEPTOR.toString())){
            TradeAppAcceptor.getInstance().startAcceptor();
        }

        ActorSystem actorSystem = ActorSystem.create("exchange");
        actorSystem.actorOf(Props.create(ExchangeActor.class), "exchangeActor");
        /*-----*/
        PriceWSConnector priceWSConnector = new PriceWSConnector();
        Thread priceWSThread = new Thread(priceWSConnector);
        priceWSThread.start();

        /*-----*/
    }

}
