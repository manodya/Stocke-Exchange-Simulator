package com.dfn.exchange.price.res.bo;

/**
 * Created by manodyas on 3/16/2018.
 */
public class DepthByOrder {
    private int type;
    private int level;
    private double price;
    private int qty;
    private String ordNo;
    private long timeStamp;
    private String clOrdId;

    public DepthByOrder() {
    }

    public DepthByOrder(int type, int level, double price, int qty, String clOrdId) {
        this.type = type;
        this.level = level;
        this.price = price;
        this.qty = qty;
        this.timeStamp = System.currentTimeMillis();
        this.clOrdId = clOrdId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getClOrdId() {
        return clOrdId;
    }

    public void setClOrdId(String clOrdId) {
        this.clOrdId = clOrdId;
    }

    @Override
    public boolean equals(Object obj) {
        DepthByOrder depthByOrder = (DepthByOrder) obj;
        return this.clOrdId == depthByOrder.getClOrdId();
    }
}
