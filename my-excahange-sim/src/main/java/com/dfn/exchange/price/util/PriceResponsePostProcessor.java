package com.dfn.exchange.price.util;

import akka.actor.ActorRef;
import com.dfn.exchange.price.PriceWSConnector;
import com.dfn.exchange.price.res.PriceEvent;
import com.dfn.exchange.price.res.ResWSJSONExchangeUpdates;
import com.dfn.exchange.price.res.ResWSJSONMarketDepthByOrder;
import com.dfn.exchange.price.res.ResWSJSONMarketDepthByPrice;
import com.dfn.exchange.price.res.bo.*;
import com.dfn.exchange.price.util.factory.GsonBuilderFactory;
import com.dfn.exchange.registry.SymbolRegistry;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by manodyas on 3/14/2018.
 */
public class PriceResponsePostProcessor {
    private static final Gson gson = new Gson();
    private GsonBuilderFactory gsonBuilderFactory;


    public PriceResponsePostProcessor() {
        gsonBuilderFactory = new GsonBuilderFactory();


    }

    public Object parsePriceResponse(String res) throws IOException {
        String jsonRes = PriceUtils.getJSONString(res);
        Map<String, Object> tempMap = new Gson().fromJson(jsonRes, Map.class);
        Double d = new Double((Double) tempMap.get("1"));
        int messageType = d.intValue();
        Object resObject = null;
        switch (messageType) {
            case PriceConstants.RES_EXCHANGE_STATUS_UPDATE: {
                resObject = gsonBuilderFactory.getGsonBulider(PriceConstants.RES_EXCHANGE_STATUS_UPDATE).fromJson(jsonRes, ResWSJSONExchangeUpdates.class);
                break;
            }
            case PriceConstants.RES_DEPTH_BY_ORDER: {
             //   System.out.println("##Depth By Order Res:" + res);
                resObject = gsonBuilderFactory.getGsonBulider(PriceConstants.RES_DEPTH_BY_ORDER).fromJson(jsonRes, ResWSJSONMarketDepthByOrder.class);
                break;
            }
            case PriceConstants.RES_DEPTH_BY_PRICE: {
                resObject = gsonBuilderFactory.getGsonBulider(PriceConstants.RES_DEPTH_BY_PRICE).fromJson(jsonRes, ResWSJSONMarketDepthByPrice.class);
                break;
            }
            case PriceConstants.RES_EQUITY_UPDATE: {
                resObject = gsonBuilderFactory.getGsonBulider(PriceConstants.RES_EQUITY_UPDATE).fromJson(jsonRes, PriceEvent.class);
                break;
            }
            default:
                break;
        }
        if (resObject instanceof ResWSJSONExchangeUpdates) {
            ResWSJSONExchangeUpdates exchangeUpdates = (ResWSJSONExchangeUpdates) resObject;
            L1DataStore.updateExchangeStatus(exchangeUpdates);

            if (exchangeUpdates.getMktStatus() != 0) { /*-Notify Mkt Status Changes to All the symbol actors-*/
                Object mktStatus = null;
                switch (exchangeUpdates.getMktStatus()) {
                    case MktStatusConstants.PRE_OPEN:
                        mktStatus = new MktPreOpen();
                        break;
                    case MktStatusConstants.OPEN:
                        mktStatus = new MktOpen();
                        break;
                    case MktStatusConstants.CLOSED:
                        mktStatus = new MktClose();
                        break;
                    case MktStatusConstants.PRE_CLOSED:
                        mktStatus = new MktPreClose();
                        break;
                    case MktStatusConstants.OPEN_SUSPENDED:
                        mktStatus = new MktOpenSuspended();
                        break;
                    case MktStatusConstants.CLOSING_AUCTION:
                        mktStatus = new MktClosingAuction();
                        break;
                    default:
                        break;
                }

             }


        } else if (resObject instanceof ResWSJSONMarketDepthByPrice) {
            L2DataStore.updateDepthByPriceOrderBook((ResWSJSONMarketDepthByPrice) resObject);

        } else if (resObject instanceof ResWSJSONMarketDepthByOrder) {
            ResWSJSONMarketDepthByOrder depthByOrder = (ResWSJSONMarketDepthByOrder) resObject;
            SymbolRegistry.getPriceRelatedSymbolActorRef( depthByOrder.getSymbol()).tell(depthByOrder, null);

        } else if (resObject instanceof PriceEvent) {
            PriceEvent priceEvent = (PriceEvent) resObject;
            PriceEvent updated = null;
           /* if(!L1DataStore.isSymbolAvailableInCache(priceEvent.getSymbol())){
                priceWSConnector.sendDepthByOrderRequest(PriceConstants.EXCHANGE_CODE, priceEvent.getSymbol());
                priceWSConnector.sendDepthByPriceRequest(PriceConstants.EXCHANGE_CODE, priceEvent.getSymbol());
               // System.out.println("##Price Event Received , Sending OB Subscription , Symbol :" + priceEvent.getSymbol());
            }*/
            L1DataStore.updateEquity(priceEvent);
           // SymbolRegistry.getPriceRelatedSymbolActorRef( priceEvent.getSymbol()).tell(priceEvent, null);
        }


        return resObject;
    }
}
