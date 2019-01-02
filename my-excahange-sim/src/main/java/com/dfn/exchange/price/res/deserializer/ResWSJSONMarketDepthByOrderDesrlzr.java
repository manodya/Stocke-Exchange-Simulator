package com.dfn.exchange.price.res.deserializer;

import com.dfn.exchange.price.res.ResWSJSONMarketDepthByOrder;
import com.dfn.exchange.price.res.bo.DepthByOrder;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manodyas on 3/16/2018.
 */
public class ResWSJSONMarketDepthByOrderDesrlzr implements JsonDeserializer<ResWSJSONMarketDepthByOrder> {
    @Override
    public ResWSJSONMarketDepthByOrder deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ResWSJSONMarketDepthByOrder resDepthByOrd = new ResWSJSONMarketDepthByOrder();
        JsonObject jsonObject = (JsonObject) jsonElement;
        if (jsonObject.get("sym") != null)
            resDepthByOrd.setSymbol(jsonObject.get("sym").getAsString());

        if (jsonObject.get("exg") != null)
            resDepthByOrd.setExchange(jsonObject.get("exg").getAsString());

        if (jsonObject.get("inst") != null)
            resDepthByOrd.setInstmntType(jsonObject.get("inst").getAsInt());

        if(jsonObject.get("D") != null){
            List<DepthByOrder> list = new ArrayList<>();
            JsonArray  jsonArray = jsonObject.get("D").getAsJsonArray();
            for(int i = 0; i < jsonArray.size(); i++){
                DepthByOrder depthByOrder = new DepthByOrder();
                JsonObject jsonObj = (JsonObject) jsonArray.get(i);
                if(jsonObj.get("type") != null)
                    depthByOrder.setType(jsonObj.get("type").getAsInt());

                if(jsonObj.get("lvl") != null)
                    depthByOrder.setLevel(jsonObj.get("lvl").getAsInt());

                if(jsonObj.get("prc") != null)
                    depthByOrder.setPrice(jsonObj.get("prc").getAsDouble());

                if(jsonObj.get("qty") != null)
                    depthByOrder.setQty(jsonObj.get("qty").getAsInt());

                if(jsonObj.get("ordno") != null)
                    depthByOrder.setOrdNo(jsonObj.get("ordno").getAsString());

                list.add(depthByOrder);
            }
            resDepthByOrd.setDepthByOrderList(list);
        }

        return resDepthByOrd;
    }
}
