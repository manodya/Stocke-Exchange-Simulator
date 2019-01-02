package com.dfn.exchange.cache;

import com.dfn.exchange.fix.FIXConnectionMode;
import com.dfn.exchange.fix.FIXConstants;
import com.dfn.exchange.fix.TradeAppAcceptor;
import com.dfn.exchange.fix.TradeAppInitiator;
import com.dfn.exchange.price.res.bo.DepthByOrder;
import com.dfn.exchange.price.res.bo.SymbolOrderBook;
import com.dfn.exchange.price.util.L2DataStoreM;
import quickfix.FieldNotFound;
import quickfix.SessionNotFound;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.NewOrderSingle;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by manodyas on 8/8/2018.
 */
public class LocalOrderCache {

    private static Map<String, NewOrderSingle> orderCache = new HashMap<>();
    private static Map<String, ExecutionReport> executionReportMap = new HashMap<>();

    public static void addOrder(NewOrderSingle newOrderSingle) throws FieldNotFound {
        if (newOrderSingle.isSet(new ClOrdID())) {
            orderCache.put(newOrderSingle.getClOrdID().getValue(), newOrderSingle);
        }
    }

    public static NewOrderSingle getOrder(String clOrdID) {
        return orderCache.get(clOrdID);
    }

 /*   public static ExecutionReport getOrderExecutionReport(String clOrdID) {
        ExecutionReport executionReport = new ExecutionReport();
        return executionReport;
    }*/

    public static void updateOrderExecution(String clOrdID, double qty, double px) throws FieldNotFound, SessionNotFound {
        NewOrderSingle order = orderCache.get(clOrdID);
        if(order != null){
            ExecutionReport newReport = new ExecutionReport();
            double leavesQty = 0;


            if (executionReportMap.containsKey(clOrdID)) {
                ExecutionReport oldReport = executionReportMap.get(clOrdID);

                double newCumQty = qty + oldReport.getCumQty().getValue();
                newReport.set(new CumQty(newCumQty));
                double newAvgPx = 0;
                if (newCumQty > 0) {
                    newAvgPx = (qty * px + oldReport.getCumQty().getValue() * oldReport.getAvgPx().getValue()) / newCumQty;
                }

                newReport.set(new AvgPx(newAvgPx));


                leavesQty = oldReport.getLeavesQty().getValue() - qty;

            } else { // first execution for the order
                newReport.set(order.getClOrdID());
                newReport.set(new CumQty(qty));
                newReport.set(new AvgPx(px));

                leavesQty = order.getOrderQty().getValue() - qty;

            }

            newReport.set(order.getClOrdID());
            newReport.set(new LastPx(px));
            newReport.set(new LastQty(qty));
            newReport.set(new LeavesQty(leavesQty));
            if (leavesQty > 0) {
                newReport.set(new OrdStatus(OrdStatus.PARTIALLY_FILLED));
               // newReport.set(new ExecTransType(ExecTransType.NEW));
                newReport.set(new ExecType(ExecType.PARTIAL_FILL));
            } else {
                newReport.set(new OrdStatus(OrdStatus.FILLED));
              //  newReport.set(new ExecTransType(ExecTransType.NEW));
                newReport.set(new ExecType(ExecType.FILL));
            }
            newReport.set(new OrderID(Long.toString(System.currentTimeMillis())));
            newReport.set(new ExecID(Long.toString(System.currentTimeMillis())));
            newReport.set(new Symbol(order.getSymbol().getValue()));
            newReport.set(new Side(order.getSide().getValue()));
            newReport.set(new TransactTime());

            executionReportMap.put(clOrdID, newReport);
            if(FIXConstants.connectionMode.equalsIgnoreCase(FIXConnectionMode.INITIATOR.toString())){
                TradeAppInitiator.pushMessage(newReport);
            }else{
                TradeAppAcceptor.pushMessage(newReport);
            }

        }


    }

    public static void processIOCOrder(String clOrdId) throws FieldNotFound, SessionNotFound {
        NewOrderSingle order = orderCache.get(clOrdId);
        ExecutionReport executionReport = executionReportMap.get(clOrdId);
        if(order != null && executionReport !=null && (order.getTimeInForce().getValue() == TimeInForce.IMMEDIATE_OR_CANCEL)){
            char lastExeStatus = executionReport.getOrdStatus().getValue();
            if(executionReport.getLeavesQty().getValue() > 0 && (lastExeStatus == OrdStatus.NEW || lastExeStatus == OrdStatus.PARTIALLY_FILLED)){
                executionReport.set(new TransactTime(new Date()));
                executionReport.set(new OrdStatus(OrdStatus.CANCELED));
                //executionReport.set(new ExecTransType(ExecTransType.CANCEL));
                executionReport.set(new ExecType(ExecType.CANCELED));
                executionReportMap.put(clOrdId, executionReport);
                removeEntryFromOrderBook(order.getSymbol().getValue(), clOrdId, order.getSide().getValue());

                if(FIXConstants.connectionMode.equalsIgnoreCase(FIXConnectionMode.INITIATOR.toString())){
                    TradeAppInitiator.pushMessage(executionReport);
                }else{
                    TradeAppAcceptor.pushMessage(executionReport);
                }
            }

        }
    }

    private static void removeEntryFromOrderBook(String symbol, String clOrdId, char side){
        SymbolOrderBook symbolOrderBook = L2DataStoreM.getLocalOrderBook(symbol);
        List<DepthByOrder> entries = null;
        if(symbolOrderBook != null ){
            if(side == Side.BUY){
                entries = symbolOrderBook.getBidList();
            }else if(side == Side.SELL){
                entries = symbolOrderBook.getAskList();
            }
            DepthByOrder removeEntry = null;
            for(DepthByOrder entry : entries){
                if(entry.getClOrdId().equalsIgnoreCase(clOrdId)){
                    removeEntry = entry;
                    break;
                }
            }

            if(removeEntry != null){
                entries.remove(removeEntry);
            }
        }

    }
}
