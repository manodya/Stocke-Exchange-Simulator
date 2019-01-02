package com.dfn.exchange.price.util.factory;


import com.dfn.exchange.price.res.PriceEvent;
import com.dfn.exchange.price.res.ResWSJSONExchangeUpdates;
import com.dfn.exchange.price.res.ResWSJSONMarketDepthByOrder;
import com.dfn.exchange.price.res.ResWSJSONMarketDepthByPrice;
import com.dfn.exchange.price.res.deserializer.ResWSJSONEquityUpdatesDesrlzr;
import com.dfn.exchange.price.res.deserializer.ResWSJSONExchangeUpdatesDesrlzr;
import com.dfn.exchange.price.res.deserializer.ResWSJSONMarketDepthByOrderDesrlzr;
import com.dfn.exchange.price.res.deserializer.ResWSJSONMarketDepthByPriceDesrlzr;
import com.dfn.exchange.price.util.PriceConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manodyas on 3/14/2018.
 */
public class GsonBuilderFactory {
    private Map<Integer, Gson> gsonObjectMap = new HashMap<>();

    public Gson getGsonBulider(int resType) {
        Gson gson = null;
        switch (resType) {
            case PriceConstants.RES_EXCHANGE_STATUS_UPDATE: {
                if (gsonObjectMap.get(PriceConstants.RES_EXCHANGE_STATUS_UPDATE) != null) {
                    gson = gsonObjectMap.get(PriceConstants.RES_EXCHANGE_STATUS_UPDATE);
                } else {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(ResWSJSONExchangeUpdates.class, new ResWSJSONExchangeUpdatesDesrlzr());
                    gson = gsonBuilder.create();
                    gsonObjectMap.put(PriceConstants.RES_EXCHANGE_STATUS_UPDATE, gson);


                }
                break;
            }
            case PriceConstants.RES_DEPTH_BY_ORDER: {
                if (gsonObjectMap.get(PriceConstants.RES_DEPTH_BY_ORDER) != null) {
                    gson = gsonObjectMap.get(PriceConstants.RES_DEPTH_BY_ORDER);
                } else {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(ResWSJSONMarketDepthByOrder.class, new ResWSJSONMarketDepthByOrderDesrlzr());
                    gson = gsonBuilder.create();
                    gsonObjectMap.put(PriceConstants.RES_DEPTH_BY_ORDER, gson);
                }
                break;
            }
            case PriceConstants.RES_DEPTH_BY_PRICE: {
                if (gsonObjectMap.get(PriceConstants.RES_DEPTH_BY_PRICE) != null) {
                    gson = gsonObjectMap.get(PriceConstants.RES_DEPTH_BY_PRICE);
                } else {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(ResWSJSONMarketDepthByPrice.class, new ResWSJSONMarketDepthByPriceDesrlzr());
                    gson = gsonBuilder.create();
                    gsonObjectMap.put(PriceConstants.RES_DEPTH_BY_PRICE, gson);
                }
                break;
            }
            case PriceConstants.RES_EQUITY_UPDATE: {
                if (gsonObjectMap.get(PriceConstants.RES_EQUITY_UPDATE) != null) {
                    gson = gsonObjectMap.get(PriceConstants.RES_EQUITY_UPDATE);
                } else {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(PriceEvent.class, new ResWSJSONEquityUpdatesDesrlzr());
                    gson = gsonBuilder.create();
                    gsonObjectMap.put(PriceConstants.RES_EQUITY_UPDATE, gson);
                }
                break;
            }
            default:
                break;
        }
        return gson;
    }
}
