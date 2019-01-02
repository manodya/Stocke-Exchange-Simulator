package com.dfn.exchange.price.res.bo;

/**
 * Created by manodyas on 3/16/2018.
 */
public class DepthByPriceEntry {
    private DepthByPrice bid;
    private DepthByPrice offer;

    public DepthByPrice getBid() {
        return bid;
    }

    public void setBid(DepthByPrice bid) {
        this.bid = bid;
    }

    public DepthByPrice getOffer() {
        return offer;
    }

    public void setOffer(DepthByPrice offer) {
        this.offer = offer;
    }
}
