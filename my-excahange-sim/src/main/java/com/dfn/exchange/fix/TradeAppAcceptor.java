package com.dfn.exchange.fix;

import com.dfn.exchange.registry.SymbolRegistry;
import quickfix.*;
import quickfix.field.TradSesStatus;
import quickfix.field.TradingSessionID;
import quickfix.fix44.MessageCracker;
import quickfix.fix44.NewOrderSingle;
import quickfix.fix44.TradingSessionStatus;
import quickfix.fix44.TradingSessionStatusRequest;

public class TradeAppAcceptor extends MessageCracker implements Application {
    private static TradeAppAcceptor tradeAppAcceptor = null;
    private static SocketAcceptor socketAcceptor = null;

    public static TradeAppAcceptor getInstance(){
        if(tradeAppAcceptor == null){
            tradeAppAcceptor = new TradeAppAcceptor();
        }
        return tradeAppAcceptor;
    }

    public void startAcceptor(){

        try {
            SessionSettings sessionSettings = new SessionSettings("./Settings/acceptorSettings.txt");
            Application application = new TradeAppAcceptor();
            FileStoreFactory fileStoreFactory = new FileStoreFactory(sessionSettings);

            MessageFactory messageFactory = new DefaultMessageFactory();
            FileLogFactory fileLogFactory = new FileLogFactory(sessionSettings);

            socketAcceptor = new SocketAcceptor(application, fileStoreFactory, sessionSettings, fileLogFactory, messageFactory);
            socketAcceptor.start();

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
        System.out.println("####Admin Message Received:" + message.toString());
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        System.out.println("####Application Message Sent:" + message.toString());
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println("####Application Message Received: " + message.toString());

        if(message.toString().contains("35=D")){
            crack44((quickfix.fix44.Message) message, sessionID);
           /* NewOrderSingle order = (NewOrderSingle) message;
            System.out.println("###NewOrder Received:" + order.toString());
            System.out.println("###Symbol" + order.getSymbol().toString());
            System.out.println("###Side" + order.getSide().toString());
            System.out.println("###Type" + order.getOrdType().toString());
            System.out.println("###Price" + order.getPrice().getValue());
            SymbolRegistry.getExecutionRelatedSymbolActorRef(order.getSymbol().getValue()).tell(order,null);
       */ }
       // crack42((quickfix.fix42.Message) message, sessionID);
    }

    public static void pushMessage(quickfix.fix44.Message message) throws SessionNotFound {
        System.out.println("#####Sending FIX Message:" + message.toString());
        Session.sendToTarget(message, socketAcceptor.getSessions().get(0));
    }

    public void onMessage(NewOrderSingle order, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("###NewOrder Received:" + order.toString());
        System.out.println("###Symbol" + order.getSymbol().toString());
        System.out.println("###Side" + order.getSide().toString());
        System.out.println("###Type" + order.getOrdType().toString());
     //   System.out.println("###Price" + order.getPrice().getValue());
        SymbolRegistry.getExecutionRelatedSymbolActorRef(order.getSymbol().getValue()).tell(order,null);
    }

    public void onMessage(TradingSessionStatusRequest statusRequest, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        TradingSessionStatus tradingSessionStatus = new TradingSessionStatus();
        tradingSessionStatus.set(new TradingSessionID("TDWL"));
        tradingSessionStatus.set(new TradSesStatus(TradSesStatus.OPEN));
        try {
            //Session.sendToTarget(tradingSessionStatus, socketAcceptor.getSessions().get(0));
            pushMessage(tradingSessionStatus);
            System.out.println("###Sent Exchange Status Response :" + tradingSessionStatus.toString());
        } catch (SessionNotFound sessionNotFound) {
            sessionNotFound.printStackTrace();
        }
    }
}
