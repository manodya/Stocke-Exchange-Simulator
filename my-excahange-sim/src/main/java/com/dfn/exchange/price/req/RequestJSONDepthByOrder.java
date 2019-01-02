package com.dfn.exchange.price.req;


import com.dfn.exchange.price.util.PriceConstants;
import com.google.gson.annotations.SerializedName;


/**
 * Created by manodyas on 3/14/2018.
 */
public class RequestJSONDepthByOrder {
    @SerializedName("80")
    private String requestType = PriceConstants.REQ_DEPTH_BY_ORDER;
    @SerializedName("E")
    private String exchange;
    @SerializedName("S")
    private String symbol;

    public RequestJSONDepthByOrder(String exchange, String symbol) {
        this.exchange = exchange;
        this.symbol = symbol;
    }
}
