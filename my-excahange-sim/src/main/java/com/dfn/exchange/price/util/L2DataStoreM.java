package com.dfn.exchange.price.util;

import com.dfn.exchange.price.res.ResWSJSONMarketDepthByOrder;
import com.dfn.exchange.price.res.bo.DepthByOrder;
import com.dfn.exchange.price.res.bo.SymbolOrderBook;
import com.google.gson.Gson;
import quickfix.FieldNotFound;
import quickfix.field.Side;
import quickfix.fix44.NewOrderSingle;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by manodyas on 8/13/2018.
 */
public class L2DataStoreM {
    private static Map<String, SymbolOrderBook> localOrderBook = new HashMap();
    private static Map<String, SymbolOrderBook> prodOrderBook = new HashMap();
    private  static  final DecimalFormat formatter = new DecimalFormat(".##");
    private static Gson gson = new Gson();

    public static SymbolOrderBook getLocalOrderBook(String symbol) {
        return localOrderBook.get(symbol);
    }

    public static SymbolOrderBook getProdOrderBook(String symbol) {
        return prodOrderBook.get(symbol);
    }

    public static void addNewOrderToLocalOrderBook(NewOrderSingle order) throws FieldNotFound {
        String symbol = order.getSymbol().getValue();
        System.out.println("###Modifying Local OB, Symbol:" + symbol);
        System.out.println("###Current OB:" + gson.toJson(localOrderBook.get(symbol)));

        DepthByOrder depthByOrder = new DepthByOrder((int) order.getOrdType().getValue(),
                0,
                Double.valueOf(formatter.format(order.getPrice().getValue())),
                (int) order.getOrderQty().getValue(),
                order.getClOrdID().getValue());

        SymbolOrderBook symbolOrderBook = null;
        if (localOrderBook.get(symbol) != null) {
            symbolOrderBook = localOrderBook.get(symbol);
        } else {
            symbolOrderBook = new SymbolOrderBook(symbol);
            localOrderBook.put(symbol, symbolOrderBook);
        }

        if (order.getSide().getValue() == Side.BUY) {
            symbolOrderBook.getBidList().add(depthByOrder);
        } else if (order.getSide().getValue() == Side.SELL) {
            symbolOrderBook.getAskList().add(depthByOrder);
        }
        symbolOrderBook.sortOrderBook();
        System.out.println("###Modified Local OB:" + gson.toJson(symbolOrderBook));
    }

    public static void updateProdOrderBook(ResWSJSONMarketDepthByOrder depthByOrderRes) {
        String symbol = depthByOrderRes.getSymbol();
       // System.out.println("##Updating Prod Order Book, Symbol: " + symbol);

        SymbolOrderBook symbolOrderBook = prodOrderBook.get(symbol);
        if (symbolOrderBook == null) {
           // System.out.println("First Entry to OB");
            symbolOrderBook = new SymbolOrderBook(symbol);
            prodOrderBook.put(symbol, symbolOrderBook);
        } else {
           /* System.out.println("OB --> BID:" + gson.toJson(prodOrderBook.get(symbol).getBidList()));
            System.out.println("OB --> ASK:" + gson.toJson(prodOrderBook.get(symbol).getAskList()));
*/
        }

        for(DepthByOrder depthByOrder: depthByOrderRes.getDepthByOrderList()){
            DepthByOrder oldEntry = getDepthByOrderEntry(symbolOrderBook, depthByOrder.getLevel(), depthByOrder.getType());
            oldEntry.setLevel(depthByOrder.getLevel());

            if (depthByOrder.getPrice() > 0) {
                oldEntry.setPrice(depthByOrder.getPrice());
            }
            if (depthByOrder.getQty() > 0) {
                oldEntry.setQty(depthByOrder.getQty());
            }
            if(oldEntry.getClOrdId() ==null){
                oldEntry.setClOrdId(String.valueOf(System.currentTimeMillis()));
            }
        }
       /* System.out.println("##Updated Prod Order Book , Symbol" + symbol );
        System.out.println("OB --> BID:" + gson.toJson(prodOrderBook.get(symbol).getBidList()));
        System.out.println("OB --> ASK:" + gson.toJson(prodOrderBook.get(symbol).getAskList()));*/

    }

    public static DepthByOrder getDepthByOrderEntry(SymbolOrderBook symbolOrderBook, int level, int type ){
        DepthByOrder depthByOrder = null;
        List<DepthByOrder> depthByOrders = null;
        if(type == 0){
            depthByOrders = symbolOrderBook.getBidList();
        }else if(type == 1){
            depthByOrders = symbolOrderBook.getAskList();
        }

        for(DepthByOrder entry: depthByOrders){
            if(entry.getLevel() == level){
                depthByOrder = entry;
                break;
            }
        }
        if(depthByOrder == null){
            depthByOrder = new DepthByOrder();
            if(type == 0){
                symbolOrderBook.getBidList().add(depthByOrder);
            }else if(type == 1){
                symbolOrderBook.getAskList().add(depthByOrder);
            }
        }

        return depthByOrder;
    }
}
