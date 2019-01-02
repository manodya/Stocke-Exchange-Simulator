package com.dfn.exchange.fix;

import com.dfn.exchange.price.util.InternalNewOrder;
import com.dfn.exchange.registry.SymbolRegistry;
import org.eclipse.jetty.websocket.api.*;
import quickfix.*;
import quickfix.Message;
import quickfix.MessageFactory;
import quickfix.Session;
import quickfix.fix44.*;
import quickfix.fix44.MessageCracker;

/**
 * Created by manodyas on 7/30/2018.
 */
public class TradeAppInitiator extends MessageCracker implements Application {
 private static TradeAppInitiator tradeAppInitiator = null;
    private static SocketInitiator socketInitiator = null;
     public static TradeAppInitiator getInstance(){
         if(tradeAppInitiator == null){
             tradeAppInitiator = new TradeAppInitiator();
         }
         return tradeAppInitiator;
     }


    public void startInitiator(){

        try {
            SessionSettings sessionSettings = new SessionSettings("./Settings/initiatorSettings.txt");
            Application application = new TradeAppInitiator();
            FileStoreFactory fileStoreFactory = new FileStoreFactory(sessionSettings);

            MessageFactory messageFactory = new DefaultMessageFactory();
            FileLogFactory fileLogFactory = new FileLogFactory(sessionSettings);

            socketInitiator = new SocketInitiator(application, fileStoreFactory, sessionSettings, fileLogFactory, messageFactory);
            socketInitiator.start();

        } catch (ConfigError configError) {
            configError.printStackTrace();
        }
    }

    @Override
    public void onCreate(SessionID sessionID) {

    }

    @Override
    public void onLogon(SessionID sessionID) {

    }

    @Override
    public void onLogout(SessionID sessionID) {

    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {

    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
       // System.out.println("###Admin Msg:" + message.toString());
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {

    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        crack44((quickfix.fix44.Message) message, sessionID);
    }

    public void onMessage(NewOrderSingle order, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("###NewOrder Received:" + order.toString());
        System.out.println("###Symbol" + order.getSymbol().toString());
        System.out.println("###Side" + order.getSide().toString());
        System.out.println("###Type" + order.getOrdType().toString());
        System.out.println("###Price" + order.getPrice().getValue());
        SymbolRegistry.getExecutionRelatedSymbolActorRef(order.getSymbol().getValue()).tell(order,null);
    }

    public static void pushMessage(quickfix.fix44.Message message) throws SessionNotFound {
        System.out.println("#####Sending FIX Message:" + message.toString());
        Session.sendToTarget(message, socketInitiator.getSessions().get(0));
    }

}
