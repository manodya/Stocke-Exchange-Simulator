package com.dfn.exchange.utils;

import com.dfn.exchange.cache.LocalOrderCache;
import com.dfn.exchange.fix.TradeAppInitiator;
import com.dfn.exchange.price.res.bo.*;
import com.dfn.exchange.price.util.L2DataStore;
import com.dfn.exchange.price.util.L2DataStoreM;
import com.dfn.exchange.price.util.OrderExecutionSender;
import quickfix.FieldNotFound;
import quickfix.SessionNotFound;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.NewOrderSingle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by manodyas on 8/8/2018.
 */
public class OrderMatchingHelper {
    /*

    public void matchLimitOrders(String symbol) throws SessionNotFound, FieldNotFound {
        if (PropertyLoader.EXECUTION_MODE.equalsIgnoreCase(ExchangeConstants.EXECUTION_MODE_LOCAL)) {
            Map<Integer, LocalDepthByOrderEntry> localMap = L2DataStore.getDepthByOrderOrdBookLocal(symbol);
            if (localMap != null) {
                int depth = localMap.size();
                if (depth > 0) {
                    LocalDepthByOrderEntry level1 = localMap.get(0);
                    for (int i = 0; i < depth; i++) {
                        LocalDepthByOrder bid = localMap.get(i).getLocalBid();
                        boolean isMatched = false;
                        for (int j = 0; j < depth; j++) {
                            LocalDepthByOrder ask = localMap.get(i).getLocalOffer();
                            if (ask != null && bid != null && ask.getQty() > 0 && bid.getQty() > 0 && ask.getPrice() <= bid.getPrice()) {
                                if (bid.getQty() >= ask.getQty()) {
                                    int matchedQty = ask.getQty();
                                    L2DataStore.modifyLocalOrderBookEntryQty(symbol, Side.BUY, i, matchedQty); // counter order partially filled
                                    L2DataStore.modifyLocalOrderBookEntryQty(symbol, Side.SELL, i, matchedQty);
                                    L2DataStore.adjustOrderBook(symbol, Side.BUY, 0);
                                } else if (bid.getQty() < ask.getQty()) {
                                    int matchedQty = bid.getQty();
                                    L2DataStore.modifyLocalOrderBookEntryQty(symbol, Side.BUY, i, matchedQty); // counter order partially filled
                                    L2DataStore.modifyLocalOrderBookEntryQty(symbol, Side.SELL, i, matchedQty);
                                    L2DataStore.adjustOrderBook(symbol, Side.SELL, 0);
                                }
                                isMatched = true;
                            } else {
                                continue;
                            }
                        }
                        if (!isMatched) {
                            continue;
                        }
                    }

                }
            }

        }
    }

    public void matchMktOrder(NewOrderSingle newOrderSingle) throws FieldNotFound, SessionNotFound {
        double totalQty = newOrderSingle.getOrderQty().getValue();
        double cumQty = 0;
        double leavesQty = totalQty;
        double avgPx = 0;
        boolean isCompleted = false;
        double ordValue = 0;
        double px = 0;
        int modifiedOrdLevel = 0;
        boolean isRowModified = false;
        String symbol = newOrderSingle.getSymbol().getValue();
        char side = newOrderSingle.getSide().getValue();
        ExecutionReport executionReport = new ExecutionReport();
        executionReport.set(newOrderSingle.getClOrdID());
        executionReport.set(newOrderSingle.getSymbol());
        executionReport.set(newOrderSingle.getSide());

        if (PropertyLoader.EXECUTION_MODE.equalsIgnoreCase(ExchangeConstants.EXECUTION_MODE_LOCAL)) { //match with local Order Book (Depth By Order)
            Map<Integer, LocalDepthByOrderEntry> localMap = L2DataStore.getDepthByOrderOrdBookLocal(symbol);
            int depth = localMap.size();
            if (depth > 0) {
                for (int i = 0; i < depth; i++) {
                    LocalDepthByOrderEntry localDepthByOrderEntry = localMap.get(i);
                    LocalDepthByOrder counterOrder = null;
                    if (side == Side.BUY) {
                        counterOrder = localDepthByOrderEntry.getLocalOffer();
                    } else if (side == Side.SELL) {
                        counterOrder = localDepthByOrderEntry.getLocalBid();
                    }
                    if (leavesQty <= counterOrder.getQty()) {
                        ordValue = ordValue + leavesQty * counterOrder.getPrice();
                        cumQty = cumQty + leavesQty;
                        L2DataStore.modifyLocalOrderBookEntryQty(symbol, side, i, leavesQty); // counter order partially filled
                        leavesQty = 0;
                        break;
                    } else if (leavesQty > counterOrder.getQty()) {
                        ordValue = ordValue + counterOrder.getQty() * counterOrder.getPrice();
                        cumQty = cumQty + counterOrder.getQty();
                        leavesQty = leavesQty - counterOrder.getQty();
                        L2DataStore.modifyLocalOrderBookEntryQty(symbol, side, i, counterOrder.getQty()); // counter order filled
                        modifiedOrdLevel = i;
                        isRowModified = true;
                    }
                }
                if (cumQty > 0) {
                    avgPx = ordValue / cumQty;
                }

                if (isRowModified) {
                    L2DataStore.adjustOrderBook(symbol, side, modifiedOrdLevel);
                }


                executionReport.set(new LeavesQty(leavesQty));
                executionReport.set(new CumQty(cumQty));
                executionReport.set(new AvgPx(avgPx));
                executionReport.set(new OrderID(Long.toString(System.currentTimeMillis())));
                executionReport.set(new ExecID(Long.toString(System.currentTimeMillis())));

                if (leavesQty > 0) {
                    executionReport.set(new ExecTransType(ExecTransType.NEW));
                    executionReport.set(new OrdStatus(OrdStatus.PARTIALLY_FILLED));
                    executionReport.set(new ExecType(ExecType.PARTIAL_FILL));
                } else {
                    executionReport.set(new OrdStatus(OrdStatus.FILLED));
                    executionReport.set(new ExecTransType(ExecTransType.NEW));
                    executionReport.set(new ExecType(ExecType.FILL));
                }

                executionReport.set(new TransactTime());
                TradeAppInitiator.pushMessage(executionReport);
            }

        } else if (PropertyLoader.EXECUTION_MODE.equalsIgnoreCase(ExchangeConstants.EXECUTION_MODE_PROD)) { // match with Prod Order Book (Depth By Order)
            Map<Integer, DepthByOrderEntry> prodMap = L2DataStore.getDepthByOrderOrdBookProd(symbol);

        }

    }

*/
    public static void matchMarketOrder(NewOrderSingle newOrder) throws FieldNotFound, SessionNotFound {
        double totalQty = newOrder.getOrderQty().getValue();
        double cumQty = 0;
        double leavesQty = totalQty;
        double avgPx = 0;
        double ordValue = 0;

        String symbol = newOrder.getSymbol().getValue();
        char side = newOrder.getSide().getValue();
        ExecutionReport executionReport = new ExecutionReport();
        executionReport.set(newOrder.getClOrdID());
        executionReport.set(newOrder.getSymbol());
        executionReport.set(newOrder.getSide());
        SymbolOrderBook orderBook = null;

        if (PropertyLoader.EXECUTION_MODE.equalsIgnoreCase(ExchangeConstants.EXECUTION_MODE_LOCAL)) { //match with local Order Book (Depth By Order)
            orderBook = L2DataStoreM.getLocalOrderBook(symbol);
        } else if (PropertyLoader.EXECUTION_MODE.equalsIgnoreCase(ExchangeConstants.EXECUTION_MODE_PROD)) {
            orderBook = L2DataStoreM.getProdOrderBook(symbol);
        }

        List<DepthByOrder> obEntries = null;

        if (side == Side.BUY) {
            obEntries = orderBook.getAskList();
        } else if (side == Side.SELL) {
            obEntries = orderBook.getBidList();
        }
        List<DepthByOrder> listToBeRemoved = new ArrayList<>();

        for (DepthByOrder entry : obEntries) {
            double matchedQty = 0;
            if (leavesQty < entry.getQty()) {
                matchedQty = leavesQty;
                ordValue = ordValue + leavesQty * entry.getPrice();
                entry.setQty((int) (entry.getQty() - leavesQty));
                cumQty = cumQty + leavesQty;
                leavesQty = 0;
                LocalOrderCache.updateOrderExecution(entry.getClOrdId(), matchedQty, entry.getPrice());
                break;
            } else if (leavesQty >= entry.getQty()) {
                matchedQty = entry.getQty();
                ordValue = ordValue + entry.getQty() * entry.getPrice();
                cumQty = cumQty + entry.getQty();
                leavesQty = leavesQty - entry.getQty();
                listToBeRemoved.add(entry);
                LocalOrderCache.updateOrderExecution(entry.getClOrdId(), matchedQty, entry.getPrice());
            }
        }


        for (DepthByOrder order : listToBeRemoved) {
            obEntries.remove(order);
        }
        orderBook.sortOrderBook();
        if (cumQty > 0) {
            avgPx = ordValue / cumQty;
        }

        executionReport.set(new LeavesQty(leavesQty));
        executionReport.set(new CumQty(cumQty));
        executionReport.set(new AvgPx(avgPx));
        executionReport.set(new OrderID(Long.toString(System.currentTimeMillis())));
        executionReport.set(new ExecID(Long.toString(System.currentTimeMillis())));

        if (leavesQty > 0) {
          //  executionReport.set(new ExecTransType(ExecTransType.NEW));
            executionReport.set(new OrdStatus(OrdStatus.PARTIALLY_FILLED));
            executionReport.set(new ExecType(ExecType.PARTIAL_FILL));
        } else {
            executionReport.set(new OrdStatus(OrdStatus.FILLED));
           // executionReport.set(new ExecTransType(ExecTransType.NEW));
            executionReport.set(new ExecType(ExecType.FILL));
        }

        executionReport.set(new TransactTime());
        OrderExecutionSender.sendExecution(executionReport);

        // IOC - for Immediate Or Cancel // if there is a leaveQty send a cancel for the with leavesQty

        if (newOrder.getTimeInForce().getValue() == TimeInForce.IMMEDIATE_OR_CANCEL && leavesQty > 0) {
            executionReport.set(new TransactTime(new Date()));
            executionReport.set(new OrdStatus(OrdStatus.CANCELED));
         //   executionReport.set(new ExecTransType(ExecTransType.CANCEL));
            executionReport.set(new ExecType(ExecType.CANCELED));

            OrderExecutionSender.sendExecution(executionReport);
        }

    }

    public static void matchLimitOrderss(String symbol) throws SessionNotFound, FieldNotFound {
        SymbolOrderBook orderBook = L2DataStoreM.getLocalOrderBook(symbol);
        SymbolOrderBook counterOrderBook = null;
        if (PropertyLoader.EXECUTION_MODE.equalsIgnoreCase(ExchangeConstants.EXECUTION_MODE_LOCAL)) {
            counterOrderBook = L2DataStoreM.getLocalOrderBook(symbol);
        } else if (PropertyLoader.EXECUTION_MODE.equalsIgnoreCase(ExchangeConstants.EXECUTION_MODE_PROD)) {
            counterOrderBook = L2DataStoreM.getProdOrderBook(symbol);
        }


        /*-----------------------------Local Buy Side---------------------------*/

        List<DepthByOrder> bidEntries = orderBook.getBidList();
        List<DepthByOrder> offerEntries = counterOrderBook.getAskList();

        List<DepthByOrder> bidsToBeRemoved = new ArrayList<>();
        List<DepthByOrder> offersToBeRemoved = new ArrayList<>();

        for (DepthByOrder bid : bidEntries) {
            boolean matched = true;
            for (DepthByOrder offer : offerEntries) {
                double matchedQty = 0;
                if (bid != null && offer != null && bid.getQty() > 0 && offer.getQty() > 0 && offer.getPrice() == bid.getPrice()) {
                    if (bid.getQty() >= offer.getQty()) {
                        matchedQty = offer.getQty();
                        bid.setQty(bid.getQty() - offer.getQty());
                        if(bid.getQty() == 0){
                            bidsToBeRemoved.add(bid);
                        }
                        offer.setQty(0);
                        offersToBeRemoved.add(offer);
                        LocalOrderCache.updateOrderExecution(bid.getClOrdId(), matchedQty, bid.getPrice());
                        LocalOrderCache.updateOrderExecution(offer.getClOrdId(), matchedQty, bid.getPrice());

                    } else if (bid.getQty() < offer.getQty()) {
                        matchedQty = bid.getQty();
                        offer.setQty(offer.getQty() - bid.getQty());
                        bid.setQty(0);
                        bidsToBeRemoved.add(bid);
                        LocalOrderCache.updateOrderExecution(bid.getClOrdId(), matchedQty, bid.getPrice());
                        LocalOrderCache.updateOrderExecution(offer.getClOrdId(), matchedQty, bid.getPrice());
                    }

                } else {
                    matched = false;
                    break;
                }
            }
            LocalOrderCache.processIOCOrder(bid.getClOrdId());
            if (!matched) {
                break;
            }

        }

        for (DepthByOrder bid : bidsToBeRemoved) {
            bidEntries.remove(bid);
        }
        for (DepthByOrder offer : offersToBeRemoved) {
            offerEntries.remove(offer);
        }

        orderBook.sortOrderBook();
        counterOrderBook.sortOrderBook();

        /*---------------------------------------------------------------------*/
        /*-----------------------------Local Sell Side-------------------------*/
        offerEntries = orderBook.getAskList();
        bidEntries = counterOrderBook.getBidList();
        bidsToBeRemoved = new ArrayList<>();
        offersToBeRemoved = new ArrayList<>();

        for (DepthByOrder offer : offerEntries) {
            boolean matched = true;
            for (DepthByOrder bid : bidEntries) {
                double matchedQty = 0;
                if (bid != null && offer != null && bid.getQty() > 0 && offer.getQty() > 0 && offer.getPrice() == bid.getPrice()) {
                    if (bid.getQty() >= offer.getQty()) {
                        matchedQty = offer.getQty();
                        bid.setQty(bid.getQty() - offer.getQty());
                        offer.setQty(0);
                        if(bid.getQty() == 0){
                            bidsToBeRemoved.add(bid);
                        }
                        offersToBeRemoved.add(offer);
                        LocalOrderCache.updateOrderExecution(bid.getClOrdId(), matchedQty, bid.getPrice());
                        LocalOrderCache.updateOrderExecution(offer.getClOrdId(), matchedQty, bid.getPrice());

                    } else if (bid.getQty() < offer.getQty()) {
                        matchedQty = bid.getQty();
                        offer.setQty(offer.getQty() - bid.getQty());
                        bid.setQty(0);
                        bidsToBeRemoved.add(bid);
                        LocalOrderCache.updateOrderExecution(bid.getClOrdId(), matchedQty, bid.getPrice());
                        LocalOrderCache.updateOrderExecution(offer.getClOrdId(), matchedQty, bid.getPrice());
                    }
                } else {
                    matched = false;
                    break;
                }
            }

            LocalOrderCache.processIOCOrder(offer.getClOrdId());
            if (!matched) {
                break;
            }

        }
        for (DepthByOrder bid : bidsToBeRemoved) {
            bidEntries.remove(bid);
        }
        for (DepthByOrder offer : offersToBeRemoved) {
            offerEntries.remove(offer);
        }

        orderBook.sortOrderBook();
        counterOrderBook.sortOrderBook();

        /*---------------------------------------------------------------------*/
    }
}
