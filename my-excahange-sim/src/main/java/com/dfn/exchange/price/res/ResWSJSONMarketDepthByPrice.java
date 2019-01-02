package com.dfn.exchange.price.res;




import com.dfn.exchange.price.res.bo.DepthByPrice;

import java.util.List;

/**
 * Created by manodyas on 3/16/2018.
 */
public class ResWSJSONMarketDepthByPrice {
    private String symbol;
    private String exchange;
    private int decimalCorrFactor;
    private int instmntType;
    private List<DepthByPrice> depthByPriceList;

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

    public List<DepthByPrice> getDepthByPriceList() {
        return depthByPriceList;
    }

    public void setDepthByPriceList(List<DepthByPrice> depthByPriceList) {
        this.depthByPriceList = depthByPriceList;
    }
}
