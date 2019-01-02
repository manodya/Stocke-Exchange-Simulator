package com.dfn.exchange.price.util;



import com.dfn.exchange.price.res.bo.DepthByPriceEntry;

import java.util.Map;

/**
 * Created by manodyas on 4/4/2018.
 */
public class OrderBookNotification {
    private final Map<Integer, DepthByPriceEntry> orderBook;

    public OrderBookNotification(Map<Integer, DepthByPriceEntry> orderBook) {
        this.orderBook = orderBook;
    }

    public Map<Integer, DepthByPriceEntry> getOrderBook() {
        return orderBook;
    }

}
