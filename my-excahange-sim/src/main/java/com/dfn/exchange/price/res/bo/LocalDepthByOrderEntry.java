package com.dfn.exchange.price.res.bo;

/**
 * Created by manodyas on 8/7/2018.
 */
public class LocalDepthByOrderEntry {
    private LocalDepthByOrder localBid;
    private LocalDepthByOrder localOffer;

    public LocalDepthByOrder getLocalBid() {
        return localBid;
    }

    public void setLocalBid(LocalDepthByOrder localBid) {
        this.localBid = localBid;
    }

    public LocalDepthByOrder getLocalOffer() {
        return localOffer;
    }

    public void setLocalOffer(LocalDepthByOrder localOffer) {
        this.localOffer = localOffer;
    }
}
