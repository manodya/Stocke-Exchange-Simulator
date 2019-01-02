package com.dfn.exchange;

import akka.actor.UntypedActor;
import com.dfn.exchange.cache.LocalOrderCache;
import com.dfn.exchange.fix.TradeAppInitiator;
import com.dfn.exchange.price.res.PriceEvent;
import com.dfn.exchange.price.res.ResWSJSONMarketDepthByOrder;
import com.dfn.exchange.price.res.ResWSJSONMarketDepthByPrice;
import com.dfn.exchange.price.res.bo.DepthByPrice;
import com.dfn.exchange.price.res.bo.DepthByPriceEntry;
import com.dfn.exchange.price.util.L1DataStore;
import com.dfn.exchange.price.util.L2DataStore;
import com.dfn.exchange.price.util.L2DataStoreM;
import com.dfn.exchange.price.util.OrderValidator;
import com.dfn.exchange.utils.OrderMatchingHelper;
import com.google.gson.Gson;
import quickfix.FieldNotFound;
import quickfix.SessionNotFound;
import quickfix.field.*;
import quickfix.fix44.NewOrderSingle;

import java.util.Map;

/**
 * Created by manodyas on 7/30/2018.
 */
public class SymbolActor  extends UntypedActor{
    private   String symbolCode;
    private Gson gson;
    private OrderMatchingHelper orderMatchingHelper;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        orderMatchingHelper = new OrderMatchingHelper();
    }

    public SymbolActor(String symbolCode) {
        this.symbolCode = symbolCode;
        gson = new Gson();
    }
    @Override
    public void onReceive(Object message) throws Exception {
       /* if(message instanceof ResWSJSONMarketDepthByOrder){
            ResWSJSONMarketDepthByOrder depthByOrder = (ResWSJSONMarketDepthByOrder) message;
            L2DataStore.updateDepthByOrderOrderBook(depthByOrder);
           //  orderMatchingHelper.matchLimitOrders(depthByOrder.getSymbol());
        }else if(message instanceof NewOrderSingle){
            System.out.println("####New Order Received.");
            processNewOrder((NewOrderSingle) message);
        }*/


       if(message instanceof NewOrderSingle){
           System.out.println("### New Order Received:" + message.toString());
           NewOrderSingle newOrderSingle = (NewOrderSingle) message;
           if(OrderValidator.validateNewOrder((newOrderSingle))){
               processNewOrder((NewOrderSingle) message);
           }
       }else if(message instanceof  ResWSJSONMarketDepthByOrder){
           L2DataStoreM.updateProdOrderBook((ResWSJSONMarketDepthByOrder) message);
       }
    }

    private void processNewOrder(NewOrderSingle newOrderSingle) throws FieldNotFound, SessionNotFound {
        if(newOrderSingle.getOrdType().getValue() == OrdType.MARKET){
              OrderMatchingHelper.matchMarketOrder(newOrderSingle);
        }else if(newOrderSingle.getOrdType().getValue() == OrdType.LIMIT){
            L2DataStoreM.addNewOrderToLocalOrderBook(newOrderSingle);
            LocalOrderCache.addOrder(newOrderSingle);
            OrderMatchingHelper.matchLimitOrderss(newOrderSingle.getSymbol().getValue());
        }
    }


 /*   private void processNewOrder(NewOrderSingle newOrderSingle) throws FieldNotFound, SessionNotFound {

        if(newOrderSingle.getOrdType().getValue() == OrdType.MARKET){
                orderMatchingHelper.matchMktOrder(newOrderSingle);
        }else if (newOrderSingle.getOrdType().getValue() == OrdType.LIMIT){ *//*-Limit Order Add to Local Depth By Order Book-*//*
            L2DataStore.addNewOrderToLocalOrderBook(newOrderSingle);
            LocalOrderCache.addOrder(newOrderSingle); *//*-Add to Local Order Cache-*//*
            orderMatchingHelper.matchLimitOrders(newOrderSingle.getSymbol().getValue());
        }



    }*/
   /* private void matchOrder(NewOrderSingle newOrderSingle) throws FieldNotFound, SessionNotFound {

        double totalQty = newOrderSingle.getOrderQty().getValue();
        double cumQty = 0;
        double leavesQty = newOrderSingle.getOrderQty().getValue();
        double avgPx = 0;
        boolean isCompleted = false;
        double  ordValue = 0;
        double px = 0;
        int modifiedEntryCount = 0;
        Map<Integer, DepthByPriceEntry> orderBook =  L2DataStore.getDepthByOrderOrdBookProd(newOrderSingle.getSymbol().getValue());
        System.out.println("#####Matching Order, Order Book :" + gson.toJson(orderBook));
        if(orderBook.size() > 0){
            if(newOrderSingle.isSet(new Side())){
                for(int i = 0; i < orderBook.size(); i++){

                    DepthByPrice entry = null;
                    if(newOrderSingle.getSide().getValue() == Side.BUY){
                        entry = orderBook.get(i).getOffer();
                    }else{
                        entry = orderBook.get(i).getBid();
                    }

                    if(leavesQty <= entry.getQty()){
                        ordValue = ordValue + leavesQty*entry.getPrice();
                        entry.setQty((int) (entry.getQty() - leavesQty));
                        cumQty = cumQty + leavesQty;
                        leavesQty = 0;
                        isCompleted = true;
                    }else if(leavesQty > entry.getQty() ){
                        ordValue = ordValue + entry.getQty()*entry.getPrice();
                        cumQty = cumQty + entry.getQty();
                        leavesQty = leavesQty - entry.getQty();
                        modifiedEntryCount++;
                    }
                    if(cumQty > 0){
                        avgPx = ordValue/cumQty;
                    }
                    ExecutionReport executionReport = new ExecutionReport();
                    executionReport.set(new LeavesQty(leavesQty));
                    executionReport.set(new CumQty(cumQty));
                    executionReport.set(new AvgPx(avgPx));

                    executionReport.set(new LastPx(entry.getPrice()));
                    executionReport.set(new LastShares(entry.getQty()));

                    if(isCompleted){
                        executionReport.set(new OrdStatus(OrdStatus.FILLED));
                        executionReport.set(new ExecTransType(ExecTransType.NEW));
                        executionReport.set(new ExecType(ExecType.FILL));
                    }else {
                        executionReport.set(new OrdStatus(OrdStatus.PARTIALLY_FILLED));
                        executionReport.set(new ExecTransType(ExecTransType.NEW));
                        executionReport.set(new ExecType(ExecType.PARTIAL_FILL));
                    }
                    sendExecutionReport(newOrderSingle, executionReport);

                    if(isCompleted){
                        break;
                    }
                }
            }

            L2DataStore.reOrganizeOrderBook(newOrderSingle.getSymbol().getValue(),newOrderSingle.getSide().getValue(), modifiedEntryCount);
            System.out.println("#####Order Book After Match :" + gson.toJson(L2DataStore.getDepthByPriceOrdBook(newOrderSingle.getSymbol().getValue())));

        }else{
            ExecutionReport report = new ExecutionReport();
            report.set(new LeavesQty(totalQty));
            report.set(new CumQty(0));
            report.set(new AvgPx(0));
            report.set(new LastPx(0));
            report.set(new LastShares(0));

            report.set(new OrdStatus(OrdStatus.REJECTED));
            report.set(new ExecTransType(ExecTransType.CANCEL));
            report.set(new ExecType(ExecType.REJECTED));
            sendExecutionReport(newOrderSingle, report);

        }
    }

    private void sendExecutionReport(NewOrderSingle newOrderSingle, ExecutionReport executionReport) throws FieldNotFound, SessionNotFound {
        executionReport.set(new ClOrdID(newOrderSingle.getClOrdID().getValue()));
        executionReport.set(new OrderID(Long.toString(System.currentTimeMillis())));
        executionReport.set(new ExecID(Long.toString(System.currentTimeMillis())));
        executionReport.set(new Symbol(newOrderSingle.getSymbol().getValue()));
        executionReport.set(new Side(newOrderSingle.getSide().getValue()));
        executionReport.set(new TransactTime());
        System.out.println("#####Execution Report :" + executionReport.toString());
        TradeAppInitiator.pushMessage(executionReport);


    }*/
}
