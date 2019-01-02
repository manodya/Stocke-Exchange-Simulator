package com.dfn.exchange.price.res;

/**
 * Created by manodyas on 3/19/2018.
 */
public class PriceEvent {
    private String symbol;
    private String exchange;
    private int decimalCorrFactor;
    private int instmntType;
    private double high;
    private double low;
    private double todaysClose;
    private double todaysOpen;
    private double netChange;
    private double perChange;
    private double prevClose;
    private double turnOver;
    private long volume;
    private int numOfTrades;
    private String currency;
    private double lstTrdPx;
    private int lstTrdQty;
    private String lstTrdDate;
    private String trdTime;
    private double bstAskPx;
    private int bstAskQty;
    private double bstBidPx;
    private int bstBidQty;
    private int totalAskQty;
    private int totalBidQty;
    private double minPx;
    private double maxPx;
    private double avgTrdPx;
    private double strikePx;
    private String date;
    private String time;
    private long timeStamp;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public int getDecimalCorrFactor() {
        return decimalCorrFactor;
    }

    public void setDecimalCorrFactor(int decimalCorrFactor) {
        this.decimalCorrFactor = decimalCorrFactor;
    }

    public int getInstmntType() {
        return instmntType;
    }

    public void setInstmntType(int instmntType) {
        this.instmntType = instmntType;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getTodaysClose() {
        return todaysClose;
    }

    public void setTodaysClose(double todaysClose) {
        this.todaysClose = todaysClose;
    }

    public double getTodaysOpen() {
        return todaysOpen;
    }

    public void setTodaysOpen(double todaysOpen) {
        this.todaysOpen = todaysOpen;
    }

    public double getNetChange() {
        return netChange;
    }

    public void setNetChange(double netChange) {
        this.netChange = netChange;
    }

    public double getPerChange() {
        return perChange;
    }

    public void setPerChange(double perChange) {
        this.perChange = perChange;
    }

    public double getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(double prevClose) {
        this.prevClose = prevClose;
    }

    public double getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(double turnOver) {
        this.turnOver = turnOver;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public int getNumOfTrades() {
        return numOfTrades;
    }

    public void setNumOfTrades(int numOfTrades) {
        this.numOfTrades = numOfTrades;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getLstTrdPx() {
        return lstTrdPx;
    }

    public void setLstTrdPx(double lstTrdPx) {
        this.lstTrdPx = lstTrdPx;
    }

    public int getLstTrdQty() {
        return lstTrdQty;
    }

    public void setLstTrdQty(int lstTrdQty) {
        this.lstTrdQty = lstTrdQty;
    }

    public String getLstTrdDate() {
        return lstTrdDate;
    }

    public void setLstTrdDate(String lstTrdDate) {
        this.lstTrdDate = lstTrdDate;
    }

    public String getTrdTime() {
        return trdTime;
    }

    public void setTrdTime(String trdTime) {
        this.trdTime = trdTime;
    }

    public double getBstAskPx() {
        return bstAskPx;
    }

    public void setBstAskPx(double bstAskPx) {
        this.bstAskPx = bstAskPx;
    }

    public int getBstAskQty() {
        return bstAskQty;
    }

    public void setBstAskQty(int bstAskQty) {
        this.bstAskQty = bstAskQty;
    }

    public double getBstBidPx() {
        return bstBidPx;
    }

    public void setBstBidPx(double bstBidPx) {
        this.bstBidPx = bstBidPx;
    }

    public int getBstBidQty() {
        return bstBidQty;
    }

    public void setBstBidQty(int bstBidQty) {
        this.bstBidQty = bstBidQty;
    }

    public int getTotalAskQty() {
        return totalAskQty;
    }

    public void setTotalAskQty(int totalAskQty) {
        this.totalAskQty = totalAskQty;
    }

    public int getTotalBidQty() {
        return totalBidQty;
    }

    public void setTotalBidQty(int totalBidQty) {
        this.totalBidQty = totalBidQty;
    }

    public double getMinPx() {
        return minPx;
    }

    public void setMinPx(double minPx) {
        this.minPx = minPx;
    }

    public double getMaxPx() {
        return maxPx;
    }

    public void setMaxPx(double maxPx) {
        this.maxPx = maxPx;
    }

    public double getAvgTrdPx() {
        return avgTrdPx;
    }

    public void setAvgTrdPx(double avgTrdPx) {
        this.avgTrdPx = avgTrdPx;
    }

    public double getStrikePx() {
        return strikePx;
    }

    public void setStrikePx(double strikePx) {
        this.strikePx = strikePx;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
