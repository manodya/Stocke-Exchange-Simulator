package com.dfn.exchange.price.util;

import quickfix.Session;
import quickfix.SessionID;
import quickfix.fix42.NewOrderSingle;

/**
 * Created by manodyas on 7/30/2018.
 */
public class InternalNewOrder {
    private final NewOrderSingle newOrderSingle;
    private final Session session;

    public InternalNewOrder(NewOrderSingle newOrderSingle, Session session) {
        this.newOrderSingle = newOrderSingle;
        this.session = session;
    }

    public NewOrderSingle getNewOrderSingle() {
        return newOrderSingle;
    }

    public Session getSession() {
        return session;
    }
}
