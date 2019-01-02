package com.dfn.exchange.price.util;

import com.dfn.exchange.fix.FIXConnectionMode;
import com.dfn.exchange.fix.FIXConstants;
import com.dfn.exchange.fix.TradeAppAcceptor;
import com.dfn.exchange.fix.TradeAppInitiator;
import quickfix.SessionNotFound;
import quickfix.fix44.ExecutionReport;

/**
 * Created by manodyas on 8/13/2018.
 */
public class OrderExecutionSender {
    public static void sendExecution(ExecutionReport executionReport) throws SessionNotFound {
        if(FIXConstants.connectionMode.equalsIgnoreCase(FIXConnectionMode.INITIATOR.toString())){
            TradeAppInitiator.pushMessage(executionReport);
        }else{
            TradeAppAcceptor.pushMessage(executionReport);
        }

    }
}
