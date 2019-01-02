package com.dfn.exchange.price.req;


import com.dfn.exchange.price.util.PriceConstants;
import com.google.gson.annotations.SerializedName;

/**
 * Created by manodyas on 4/5/2018.
 */
public class RequestJSONFullMarketQuote {
    @SerializedName("40")
    private String requestType = PriceConstants.REQ_FULL_MKT;
    @SerializedName("E")
    private String exchange;

    public RequestJSONFullMarketQuote(String exchange) {
        this.exchange = exchange;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getExchange() {
        return exchange;
    }
}
