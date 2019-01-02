package com.dfn.exchange.price.res.deserializer;

import com.dfn.exchange.price.res.ResWSJSONMarketDepthByPrice;
import com.dfn.exchange.price.res.bo.DepthByPrice;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manodyas on 3/16/2018.
 */
public class ResWSJSONMarketDepthByPriceDesrlzr implements JsonDeserializer<ResWSJSONMarketDepthByPrice> {
    @Override
    public ResWSJSONMarketDepthByPrice deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ResWSJSONMarketDepthByPrice resDepthByPx = new ResWSJSONMarketDepthByPrice();
        JsonObject jsonObject = (JsonObject) jsonElement;
        if (jsonObject.get("sym") != null)
            resDepthByPx.setSymbol(jsonObject.get("sym").getAsString());

        if (jsonObject.get("exg") != null)
            resDepthByPx.setExchange(jsonObject.get("exg").getAsString());

        if (jsonObject.get("inst") != null)
            resDepthByPx.setInstmntType(jsonObject.get("inst").getAsInt());

        if(jsonObject.get("D") != null){
            List<DepthByPrice> list = new ArrayList<>();
            JsonArray  jsonArray = jsonObject.get("D").getAsJsonArray();
            for(int i = 0; i < jsonArray.size(); i++){
                DepthByPrice depthByPrice = new DepthByPrice();
                JsonObject jsonObj = (JsonObject) jsonArray.get(i);
                if(jsonObj.get("type") != null)
                    depthByPrice.setType(jsonObj.get("type").getAsInt());

                if(jsonObj.get("lvl") != null)
                    depthByPrice.setLevel(jsonObj.get("lvl").getAsInt());

                if(jsonObj.get("prc") != null)
                    depthByPrice.setPrice(jsonObj.get("prc").getAsDouble());

                if(jsonObj.get("qty") != null)
                    depthByPrice.setQty(jsonObj.get("qty").getAsInt());

                if(jsonObj.get("splt") != null)
                    depthByPrice.setSplits(jsonObj.get("splt").getAsInt());

                list.add(depthByPrice);
            }
            resDepthByPx.setDepthByPriceList(list);
        }



        return resDepthByPx;
    }
}
