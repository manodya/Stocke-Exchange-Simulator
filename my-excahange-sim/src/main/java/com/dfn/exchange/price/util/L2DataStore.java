package com.dfn.exchange.price.util;


import com.dfn.exchange.cache.LocalOrderCache;
import com.dfn.exchange.price.res.ResWSJSONMarketDepthByOrder;
import com.dfn.exchange.price.res.ResWSJSONMarketDepthByPrice;
import com.dfn.exchange.price.res.bo.*;
import com.google.gson.Gson;
import quickfix.FieldNotFound;
import quickfix.SessionNotFound;
import quickfix.field.Side;
import quickfix.fix42.NewOrderSingle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manodyas on 3/16/2018.
 */
public class L2DataStore {
    public static Map<String, Map<Integer, DepthByOrderEntry>> depthByOrderOrdBookProd = new HashMap<>(); /*-Last Updated Symbol vise DBO book-*/
    public static Map<String, Map<Integer, DepthByPriceEntry>> depthByPriceOrdBookProd = new HashMap<>(); /*-Last Updated Symbol vise DBP book-*/

    public static Map<String, Map<Integer, LocalDepthByOrderEntry>> depthByOrderOrdBookLocal = new HashMap<>(); /*-Local Order Book-*/

    public static Map<String, Map<Integer, DepthByPrice>> summedDepthByOrderOrdBook = new HashMap<>(); /*-Summed Order Book-*/

    private static final Gson gson = new Gson();

    public synchronized static void updateDepthByOrderOrderBook(ResWSJSONMarketDepthByOrder res) throws IOException {
    //    System.out.println("####Updating Production Order Book, Symbol :" + res.getSymbol() + ", Current OB:" + gson.toJson(depthByOrderOrdBookProd.get(res.getSymbol())));
        Map<Integer, DepthByOrderEntry> depthByOrderEntryMap = null;
        if (depthByOrderOrdBookProd.get(res.getSymbol()) != null) {
            depthByOrderEntryMap = depthByOrderOrdBookProd.get(PriceUtils.getSymbolReference(res.getSymbol()));

        } else {
            depthByOrderEntryMap = new HashMap<>();
            depthByOrderOrdBookProd.put(PriceUtils.getSymbolReference(res.getSymbol()), depthByOrderEntryMap);
        }

        for (DepthByOrder depthByOrder : res.getDepthByOrderList()) {
            if (depthByOrderEntryMap.get(depthByOrder.getLevel()) != null) {
                DepthByOrderEntry depthByOrderEntry = depthByOrderEntryMap.get(depthByOrder.getLevel());
                if (depthByOrder.getType() == 0) {
                    depthByOrderEntry.setBid(depthByOrder);
                } else if (depthByOrder.getType() == 1) {
                    depthByOrderEntry.setOffer(depthByOrder);
                }
            } else {
                DepthByOrderEntry depthByOrderEntry = new DepthByOrderEntry();
                if (depthByOrder.getType() == 0) {
                    depthByOrderEntry.setBid(depthByOrder);
                } else if (depthByOrder.getType() == 1) {
                    depthByOrderEntry.setOffer(depthByOrder);
                }
                depthByOrderEntryMap.put(depthByOrder.getLevel(), depthByOrderEntry);

            }


        }
        String resString = PriceUtils.getSymbolReference(res.getSymbol()) + "|" + PriceUtils.getCurrentTimeStrForToday() + "|DBO|" + gson.toJson(depthByOrderOrdBookProd.get(PriceUtils.getSymbolReference(res.getSymbol())));
        PriceLogWriter.writeToFile(resString, 2);
      //  System.out.println("####Updated Production Order Book, Symbol :" + res.getSymbol() + ", Updated OB:" + gson.toJson(depthByOrderOrdBookProd.get(res.getSymbol())));

    }

    public synchronized static void updateDepthByPriceOrderBook(ResWSJSONMarketDepthByPrice res) throws IOException {
        Map<Integer, DepthByPriceEntry> depthByPriceEntryMap = null;
        if (depthByPriceOrdBookProd.get(PriceUtils.getSymbolReference(res.getSymbol())) != null) {
            depthByPriceEntryMap = depthByPriceOrdBookProd.get(res.getSymbol());

        } else {
            depthByPriceEntryMap = new HashMap<>();
            depthByPriceOrdBookProd.put(PriceUtils.getSymbolReference(res.getSymbol()), depthByPriceEntryMap);
        }

        for (DepthByPrice depthByPrice : res.getDepthByPriceList()) {
            if (depthByPriceEntryMap.get(depthByPrice.getLevel()) != null) {
                DepthByPriceEntry depthByPriceEntry = depthByPriceEntryMap.get(depthByPrice.getLevel());
                if (depthByPrice.getType() == 0) {
                    DepthByPrice bid = depthByPriceEntry.getBid();
                    if (!(depthByPrice.getPrice() > 0)) {
                        depthByPrice.setPrice(bid.getPrice());
                    }
                    if (!(depthByPrice.getQty() > 0)) {
                        depthByPrice.setQty(bid.getQty());
                    }
                    if (!(depthByPrice.getSplits() > 0)) {
                        depthByPrice.setSplits(bid.getSplits());
                    }
                    depthByPriceEntry.setBid(depthByPrice);
                } else if (depthByPrice.getType() == 1) {
                    DepthByPrice offer = depthByPriceEntry.getOffer();
                    if (!(depthByPrice.getPrice() > 0)) {
                        depthByPrice.setPrice(offer.getPrice());
                    }
                    if (!(depthByPrice.getQty() > 0)) {
                        depthByPrice.setQty(offer.getQty());
                    }
                    if (!(depthByPrice.getSplits() > 0)) {
                        depthByPrice.setSplits(offer.getSplits());
                    }
                    depthByPriceEntry.setOffer(depthByPrice);
                }
            } else {
                DepthByPriceEntry depthByPriceEntry = new DepthByPriceEntry();
                if (depthByPrice.getType() == 0) {
                    depthByPriceEntry.setBid(depthByPrice);
                } else if (depthByPrice.getType() == 1) {
                    depthByPriceEntry.setOffer(depthByPrice);
                }
                depthByPriceEntryMap.put(depthByPrice.getLevel(), depthByPriceEntry);

            }

        }

        String resString = PriceUtils.getSymbolReference(res.getSymbol()) + "|" + PriceUtils.getCurrentTimeStrForToday() + "|DBP|" + gson.toJson(depthByPriceOrdBookProd.get(PriceUtils.getSymbolReference(res.getSymbol())));
        PriceLogWriter.writeToFile(resString, 2);

    }

    public static Map<Integer, DepthByOrderEntry> getDepthByOrderOrdBookProd(String symbol) {
        return depthByOrderOrdBookProd.get(symbol);
    }
    public static Map<Integer, LocalDepthByOrderEntry> getDepthByOrderOrdBookLocal(String symbol) {
        return depthByOrderOrdBookLocal.get(symbol);
    }

    public static void updateDepthByOrderOrdBook(String symbol, Map<Integer, DepthByOrderEntry> depthByOrderEntryMap) {
        depthByOrderOrdBookProd.put(symbol, depthByOrderEntryMap);
    }

    public static void updateDepthByPriceOrdBook(String symbol, Map<Integer, DepthByPriceEntry> depthByPriceEntryMap) {
        depthByPriceOrdBookProd.put(symbol, depthByPriceEntryMap);
    }

    public static void reOrganizeOrderBook(String symbol, char side, int modifiedRowCount) {
        Map<Integer, DepthByPriceEntry> temp = depthByPriceOrdBookProd.get(symbol);

        for (int i = 0; i < modifiedRowCount; i++) {
            DepthByPriceEntry entry = temp.get(i);

            if (side == Side.BUY) {
                entry.setOffer(temp.get(i + modifiedRowCount).getOffer());
                temp.get(i + modifiedRowCount).setOffer(null);

            } else if (side == Side.SELL) {
                entry.setBid(temp.get(i + modifiedRowCount).getBid());
                temp.get(i + modifiedRowCount).setBid(null);
            }
        }

        depthByPriceOrdBookProd.put(symbol, temp);


    }


    public static void addNewOrderToLocalOrderBook(NewOrderSingle order) throws FieldNotFound {
       // System.out.println("####Current OB:" + gson.toJson(depthByOrderOrdBookLocal));
        if (depthByOrderOrdBookLocal.get(order.getSymbol().getValue()) != null) {
            Map<Integer, LocalDepthByOrderEntry> map = depthByOrderOrdBookLocal.get(order.getSymbol().getValue());
            Map<Integer, LocalDepthByOrderEntry> tempMap = new HashMap<>();

            int depth = map.size();
            double ordPx = order.getPrice().getValue();
            LocalDepthByOrder depthByOrder = new LocalDepthByOrder();
            depthByOrder.setType(order.getOrdType().getValue());
            depthByOrder.setQty((int) order.getOrderQty().getValue());
            depthByOrder.setPrice(order.getPrice().getValue());
            depthByOrder.setClOrdId(order.getClOrdID().getValue());
            depthByOrder.setTimeStamp(System.currentTimeMillis());

            for (int i = 0; i < depth; i++) {

                LocalDepthByOrderEntry entry = map.get(i);
                if (order.getSide().getValue() == Side.BUY) {
                    if (entry.getLocalBid() != null && ordPx > entry.getLocalBid().getPrice()) {
                        LocalDepthByOrderEntry newEntry = new LocalDepthByOrderEntry();
                        depthByOrder.setLevel(i);
                        newEntry.setLocalBid(depthByOrder);
                        newEntry.setLocalOffer(entry.getLocalOffer());

                        tempMap.put(i, newEntry);

                        for (int j = i; j < depth; j++) {
                            LocalDepthByOrderEntry tempEntry = map.get(j);
                            LocalDepthByOrderEntry tempEntry1 = null;

                            if (j != depth - 1) {
                                tempEntry1 = map.get(j + 1);
                            }
                            LocalDepthByOrderEntry newLevel = new LocalDepthByOrderEntry();
                            newLevel.setLocalBid(tempEntry.getLocalBid());
                            if (tempEntry1 != null) {
                                newLevel.setLocalOffer(tempEntry1.getLocalOffer());
                            }
                            tempMap.put(j + 1, newLevel);
                        }
                        if (ordPx == entry.getLocalBid().getPrice()) {
                            LocalDepthByOrderEntry l1 = tempMap.get(i);
                            LocalDepthByOrderEntry l2 = tempMap.get(i + 1);

                            tempMap.get(i).setLocalBid(l2.getLocalBid());
                            tempMap.get(i + 1).setLocalBid(l1.getLocalBid());
                        }
                        break;

                    } else {
                        if (entry.getLocalBid() == null) {
                            map.get(i).setLocalBid(depthByOrder);
                            depthByOrder = null;
                            tempMap.put(i, map.get(i));
                            if (i == depth - 1) {
                                break;
                            }
                        } else {
                            tempMap.put(i, map.get(i));
                        }
                    }
                    if (i == depth - 1) {
                        LocalDepthByOrderEntry localDepthByOrderEntry = new LocalDepthByOrderEntry();
                        depthByOrder.setType(order.getOrdType().getValue());
                        depthByOrder.setQty((int) order.getOrderQty().getValue());
                        depthByOrder.setPrice(order.getPrice().getValue());
                        depthByOrder.setClOrdId(order.getClOrdID().getValue());
                        depthByOrder.setTimeStamp(System.currentTimeMillis());
                        depthByOrder.setLevel(depth);

                        localDepthByOrderEntry.setLocalBid(depthByOrder);

                        tempMap.put(depth, localDepthByOrderEntry);

                    }

                } else if (order.getSide().getValue() == Side.SELL) {

                    if (entry.getLocalOffer() != null && ordPx < entry.getLocalOffer().getPrice()) {
                        LocalDepthByOrderEntry newEntry = new LocalDepthByOrderEntry();
                        depthByOrder.setLevel(i);
                        newEntry.setLocalOffer(depthByOrder);
                        newEntry.setLocalBid(entry.getLocalBid());

                        tempMap.put(i, newEntry);

                        for (int j = i; j < depth; j++) {
                            LocalDepthByOrderEntry tempEntry = map.get(j);
                            LocalDepthByOrderEntry tempEntry1 = null;

                            if (j != depth - 1) {
                                tempEntry1 = map.get(j + 1);
                            }
                            LocalDepthByOrderEntry newLevel = new LocalDepthByOrderEntry();
                            newLevel.setLocalOffer(tempEntry.getLocalOffer());
                            if (tempEntry1 != null) {
                                newLevel.setLocalBid(tempEntry1.getLocalBid());
                            }
                            tempMap.put(j + 1, newLevel);
                        }

                        if (ordPx == entry.getLocalOffer().getPrice()) {
                            LocalDepthByOrderEntry l1 = tempMap.get(i);
                            LocalDepthByOrderEntry l2 = tempMap.get(i + 1);

                            tempMap.get(i).setLocalOffer(l2.getLocalOffer());
                            tempMap.get(i + 1).setLocalOffer(l1.getLocalOffer());
                        }

                        break;

                    } else {
                        if (map.get(i).getLocalOffer() == null) {
                            map.get(i).setLocalOffer(depthByOrder);
                            depthByOrder = null;
                            tempMap.put(i, map.get(i));
                            if (i == depth - 1) {
                                break;
                            }

                        } else {
                            tempMap.put(i, map.get(i));
                        }


                    }
                    if (i == depth - 1) {
                        LocalDepthByOrderEntry localDepthByOrderEntry = new LocalDepthByOrderEntry();
                        depthByOrder.setLevel(depth);
                        localDepthByOrderEntry.setLocalOffer(depthByOrder);
                        tempMap.put(depth, localDepthByOrderEntry);
                    }
                }
            }
            depthByOrderOrdBookLocal.put(order.getSymbol().getValue(), tempMap);
        } else { /*-First Entry to the depthByOrderOrdBookLocal -*/
            LocalDepthByOrderEntry localDepthByOrderEntry = new LocalDepthByOrderEntry();
            LocalDepthByOrder depthByOrder = new LocalDepthByOrder();
            depthByOrder.setType(order.getOrdType().getValue());
            depthByOrder.setQty((int) order.getOrderQty().getValue());
            depthByOrder.setPrice(order.getPrice().getValue());
            depthByOrder.setClOrdId(order.getClOrdID().getValue());
            depthByOrder.setTimeStamp(System.currentTimeMillis());
            depthByOrder.setLevel(0);
            if (order.getSide().getValue() == Side.BUY) {
                localDepthByOrderEntry.setLocalBid(depthByOrder);
            } else if (order.getSide().getValue() == Side.SELL) {
                localDepthByOrderEntry.setLocalOffer(depthByOrder);
            }
            Map<Integer, LocalDepthByOrderEntry> map = new HashMap<>();
            map.put(0, localDepthByOrderEntry);
            depthByOrderOrdBookLocal.put(order.getSymbol().getValue(), map);
        }
        System.out.println("####Modified OB:" + gson.toJson(depthByOrderOrdBookLocal));
    }

    public static void modifyLocalOrderBookEntryQty(String symbol, char side, int level, double executedQty) throws FieldNotFound, SessionNotFound {
        LocalDepthByOrder localEntry = null;
        if(side == Side.BUY){
            localEntry = depthByOrderOrdBookLocal.get(symbol).get(level).getLocalOffer();
        }else if(side == Side.SELL){
            localEntry = depthByOrderOrdBookLocal.get(symbol).get(level).getLocalBid();
        }
        if(localEntry != null){
            localEntry.setQty((int) (localEntry.getQty() - executedQty));
            LocalOrderCache.updateOrderExecution(localEntry.getClOrdId(), executedQty, localEntry.getPrice());
        }
   //     System.out.println("####Modified OB:" + gson.toJson(depthByOrderOrdBookLocal));

    }

    public static void adjustOrderBook(String symbol, char side, int numOfLevels){
        Map<Integer, LocalDepthByOrderEntry> temp = depthByOrderOrdBookLocal.get(symbol);
        int depth = temp.size();
        for (int i = 0; i < depth; i++) {
            LocalDepthByOrderEntry entry = temp.get(i);

            if(i + numOfLevels + 1 < depth){
                if (side == Side.BUY) {
                    entry.setLocalOffer(temp.get(i + numOfLevels + 1).getLocalOffer());
                    temp.get(i + numOfLevels + 1).setLocalOffer(null);
                } else if (side == Side.SELL) {
                    entry.setLocalBid(temp.get(i + numOfLevels + 1).getLocalBid());
                    temp.get(i + numOfLevels + 1).setLocalBid(null);
                }
            }

        }
        depthByOrderOrdBookLocal.put(symbol, temp);
        System.out.printf("Adjusted Order Book :" + gson.toJson(depthByOrderOrdBookLocal));

    }


}
