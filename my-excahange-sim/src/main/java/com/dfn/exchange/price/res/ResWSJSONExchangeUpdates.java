package com.dfn.exchange.price.res;

/**
 * Created by manodyas on 3/14/2018.
 */
public class ResWSJSONExchangeUpdates {
    private String exchange;
    private int decimalCorrFactor;
    private int mktStatus;
    private String mktDate;
    private String mktTime;
    private String lastEODDate;
    private int nmbrOfUps;
    private int nmbrOfDowns;
    private int nmbrOfNoChange;
    private int symbolsTraded;
    private long volume;
    private double turnOver;
    private int nmbrOfTrades;
    private double mktCap;
    private int IsAdvancedMboEnabled;
    private int IsAdvancedMbpEnabled;
    private double cashInTurnOver;
    private double cashOutTurnOver;

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

    public int getMktStatus() {
        return mktStatus;
    }

    public void setMktStatus(int mktStatus) {
        this.mktStatus = mktStatus;
    }

    public String getMktDate() {
        return mktDate;
    }

    public void setMktDate(String mktDate) {
        this.mktDate = mktDate;
    }

    public String getMktTime() {
        return mktTime;
    }

    public void setMktTime(String mktTime) {
        this.mktTime = mktTime;
    }

    public String getLastEODDate() {
        return lastEODDate;
    }

    public void setLastEODDate(String lastEODDate) {
        this.lastEODDate = lastEODDate;
    }

    public int getNmbrOfUps() {
        return nmbrOfUps;
    }

    public void setNmbrOfUps(int nmbrOfUps) {
        this.nmbrOfUps = nmbrOfUps;
    }

    public int getNmbrOfDowns() {
        return nmbrOfDowns;
    }

    public void setNmbrOfDowns(int nmbrOfDowns) {
        this.nmbrOfDowns = nmbrOfDowns;
    }

    public int getNmbrOfNoChange() {
        return nmbrOfNoChange;
    }

    public void setNmbrOfNoChange(int nmbrOfNoChange) {
        this.nmbrOfNoChange = nmbrOfNoChange;
    }

    public int getSymbolsTraded() {
        return symbolsTraded;
    }

    public void setSymbolsTraded(int symbolsTraded) {
        this.symbolsTraded = symbolsTraded;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(double turnOver) {
        this.turnOver = turnOver;
    }

    public double getMktCap() {
        return mktCap;
    }

    public void setMktCap(double mktCap) {
        this.mktCap = mktCap;
    }

    public int getNmbrOfTrades() {
        return nmbrOfTrades;
    }

    public void setNmbrOfTrades(int nmbrOfTrades) {
        this.nmbrOfTrades = nmbrOfTrades;
    }

    public int getIsAdvancedMboEnabled() {
        return IsAdvancedMboEnabled;
    }

    public void setIsAdvancedMboEnabled(int isAdvancedMboEnabled) {
        IsAdvancedMboEnabled = isAdvancedMboEnabled;
    }

    public int getIsAdvancedMbpEnabled() {
        return IsAdvancedMbpEnabled;
    }

    public void setIsAdvancedMbpEnabled(int isAdvancedMbpEnabled) {
        IsAdvancedMbpEnabled = isAdvancedMbpEnabled;
    }

    public double getCashInTurnOver() {
        return cashInTurnOver;
    }

    public void setCashInTurnOver(double cashInTurnOver) {
        this.cashInTurnOver = cashInTurnOver;
    }

    public double getCashOutTurnOver() {
        return cashOutTurnOver;
    }

    public void setCashOutTurnOver(double cashOutTurnOver) {
        this.cashOutTurnOver = cashOutTurnOver;
    }
}
