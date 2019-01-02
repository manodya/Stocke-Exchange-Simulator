package com.dfn.exchange.price.res.bo;

/**
 * Created by manodyas on 3/16/2018.
 */
public class DepthByOrderEntry {
    private DepthByOrder bid;
    private DepthByOrder offer;

    public DepthByOrder getBid() {
        return bid;
    }

    public void setBid(DepthByOrder bid) {
        this.bid = bid;
    }

    public DepthByOrder getOffer() {
        return offer;
    }

    public void setOffer(DepthByOrder offer) {
        this.offer = offer;
    }
}
