package com.dfn.exchange.price.req;


import com.dfn.exchange.price.util.PriceConstants;
import com.google.gson.annotations.SerializedName;


/**
 * Created by manodyas on 3/19/2018.
 */
public class RequestJSONEquity {
    @SerializedName("80")
    private String requestType = PriceConstants.REQ_EQUITY;
    @SerializedName("E")
    private String exchange;
    @SerializedName("S")
    private String symbol;

    public RequestJSONEquity(String exchange, String symbol) {
        this.exchange = exchange;
        this.symbol = symbol;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getExchange() {
        return exchange;
    }

    public String getSymbol() {
        return symbol;
    }

}
