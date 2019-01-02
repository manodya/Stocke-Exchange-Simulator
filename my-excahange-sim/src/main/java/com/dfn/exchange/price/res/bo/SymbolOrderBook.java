package com.dfn.exchange.price.res.bo;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by manodyas on 8/13/2018.
 */
public class SymbolOrderBook {
    private String symbol;
    private List<DepthByOrder> bidList;
    private List<DepthByOrder> askList;
    private Gson gson;

    public SymbolOrderBook(String symbol) {
        this.symbol = symbol;
        this.bidList = new ArrayList<>();
        this.askList = new ArrayList<>();
        this.gson = new Gson();
    }

    public List<DepthByOrder> getBidList() {
        return bidList;
    }

    public void setBidList(List<DepthByOrder> bidList) {
        this.bidList = bidList;
    }

    public List<DepthByOrder> getAskList() {
        return askList;
    }

    public void setAskList(List<DepthByOrder> askList) {
        this.askList = askList;
    }

    public void sortOrderBook() {
        askList.sort(Comparator.comparing(DepthByOrder::getPrice).thenComparing(DepthByOrder::getTimeStamp));

        bidList.sort(Comparator.comparing(DepthByOrder::getPrice).reversed().thenComparing(DepthByOrder::getTimeStamp));

        /*System.out.println("###Order Book Reorganized, Symbol:" + this.symbol );
        System.out.println("OB --> BID:" + gson.toJson(bidList));
        System.out.println("OB --> ASK:" + gson.toJson(askList));
*/    }
}
