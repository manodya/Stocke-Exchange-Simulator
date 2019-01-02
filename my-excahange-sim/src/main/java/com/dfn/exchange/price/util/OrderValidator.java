package com.dfn.exchange.price.util;

import com.dfn.exchange.price.res.bo.DepthByOrder;
import com.dfn.exchange.price.res.bo.SymbolOrderBook;
import com.dfn.exchange.utils.ExchangeConstants;
import com.dfn.exchange.utils.PropertyLoader;
import quickfix.FieldNotFound;
import quickfix.SessionNotFound;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.NewOrderSingle;

import javax.enterprise.inject.New;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by manodyas on 8/13/2018.
 */
public class OrderValidator {
    private  static  final DecimalFormat formatter = new DecimalFormat(".##");
    public static boolean validateNewOrder(NewOrderSingle newOrder) throws SessionNotFound, FieldNotFound {
        boolean response = true;

        ExecutionReport executionReport = sendAcceptance(newOrder, response);
        if (newOrder.getTimeInForce().getValue() == TimeInForce.FILL_OR_KILL) {// validate FOK order
            response = validateFillOrKillOrder(newOrder, executionReport);
        }
        return response;
    }

    private static ExecutionReport sendAcceptance(NewOrderSingle newOrderSingle, boolean status) throws FieldNotFound, SessionNotFound {
        ExecutionReport executionReport = new ExecutionReport();
        executionReport.set(newOrderSingle.getClOrdID());
        executionReport.set(newOrderSingle.getSymbol());
        executionReport.set(newOrderSingle.getSide());
        executionReport.set(newOrderSingle.getOrdType());
        executionReport.set(new LeavesQty(newOrderSingle.getOrderQty().getValue()));
        executionReport.set(new CumQty(0));
        executionReport.set(new AvgPx(0));
        executionReport.set(new OrderID(Long.toString(System.currentTimeMillis())));
        executionReport.set(new ExecID(Long.toString(System.currentTimeMillis())));
        executionReport.set(new TransactTime(new Date()));
        if (status) {
           // executionReport.set(new ExecTransType(ExecTransType.NEW));
            executionReport.set(new OrdStatus(OrdStatus.NEW));
            executionReport.set(new ExecType(ExecType.NEW));
        } else {
            //executionReport.set(new ExecTransType(ExecTransType.NEW));
            executionReport.set(new OrdStatus(OrdStatus.REJECTED));
            executionReport.set(new ExecType(ExecType.REJECTED));
        }
        OrderExecutionSender.sendExecution(executionReport);
        return executionReport;
    }

    public static boolean validateFillOrKillOrder(NewOrderSingle newOrderSingle, ExecutionReport executionReport) throws FieldNotFound, SessionNotFound { //FOK - if depth is not enough to fill the qty then cancel the order
        boolean res = false;
        String symbol = newOrderSingle.getSymbol().getValue();
        char side = newOrderSingle.getSide().getValue();
        SymbolOrderBook orderBook = null;
        if (PropertyLoader.EXECUTION_MODE.equalsIgnoreCase(ExchangeConstants.EXECUTION_MODE_LOCAL)) {
            orderBook = L2DataStoreM.getLocalOrderBook(symbol);
        } else if (PropertyLoader.EXECUTION_MODE.equalsIgnoreCase(ExchangeConstants.EXECUTION_MODE_PROD)) {
            orderBook = L2DataStoreM.getProdOrderBook(symbol);
        }

        if (orderBook != null) {
            List<DepthByOrder> entryList = null;
            if (side == Side.BUY) {
                entryList = orderBook.getAskList();
            } else if (side == Side.SELL) {
                entryList = orderBook.getBidList();
            }
            double summedQty = 0;

            for (DepthByOrder order : entryList) {
                if(newOrderSingle.getOrdType().getValue() == OrdType.LIMIT){
                    if(Double.compare(Double.valueOf(formatter.format(newOrderSingle.getPrice().getValue())), order.getPrice()) == 0){
                        summedQty = summedQty + order.getQty();
                    }
                }else{
                    summedQty = summedQty + order.getQty();
                }

            }

            if (summedQty >= newOrderSingle.getOrderQty().getValue()) {
                res = true;
            }
        }

        if (!res) {
            executionReport.set(new TransactTime(new Date()));
            executionReport.set(new ExecID(Long.toString(System.currentTimeMillis())));
          //  executionReport.set(new ExecTransType(ExecTransType.CANCEL));
            executionReport.set(new OrdStatus(OrdStatus.CANCELED));
            executionReport.set(new ExecType(ExecType.CANCELED));
            OrderExecutionSender.sendExecution(executionReport);
        }

        return res;
    }
}
