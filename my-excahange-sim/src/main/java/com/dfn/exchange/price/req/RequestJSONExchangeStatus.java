package com.dfn.exchange.price.req;


import com.dfn.exchange.price.util.PriceConstants;
import com.google.gson.annotations.SerializedName;

/**
 * Created by manodyas on 3/14/2018.
 */
public class RequestJSONExchangeStatus {

    @SerializedName("40")
    private String requestType = PriceConstants.REQ_EXCHANGE_STATUS;
    @SerializedName("E")
    private String exchange;

    public RequestJSONExchangeStatus() {
    }

    public RequestJSONExchangeStatus(String exchange) {
        this.exchange = exchange;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
