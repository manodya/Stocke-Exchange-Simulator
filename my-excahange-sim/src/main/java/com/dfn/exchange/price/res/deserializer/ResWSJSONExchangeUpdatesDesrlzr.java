package com.dfn.exchange.price.res.deserializer;

import com.dfn.exchange.price.res.ResWSJSONExchangeUpdates;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by manodyas on 3/14/2018.
 */
public class ResWSJSONExchangeUpdatesDesrlzr implements JsonDeserializer<ResWSJSONExchangeUpdates> {
    @Override
    public ResWSJSONExchangeUpdates deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) jsonElement;
        ResWSJSONExchangeUpdates exchangeUpdate = new ResWSJSONExchangeUpdates();
        if (jsonObject.get("exg") != null)
            exchangeUpdate.setExchange(jsonObject.get("exg").getAsString());

        if (jsonObject.get("stat") != null)
            exchangeUpdate.setMktStatus(jsonObject.get("stat").getAsInt());

        if (jsonObject.get("date") != null)
            exchangeUpdate.setMktDate(jsonObject.get("date").getAsString());

        if (jsonObject.get("time") != null)
            exchangeUpdate.setMktTime(jsonObject.get("time").getAsString());

        if (jsonObject.get("led") != null)
            exchangeUpdate.setLastEODDate(jsonObject.get("led").getAsString());

        if (jsonObject.get("ups") != null)
            exchangeUpdate.setNmbrOfUps(jsonObject.get("ups").getAsInt());

        if (jsonObject.get("dwns") != null)
            exchangeUpdate.setNmbrOfDowns(jsonObject.get("dwns").getAsInt());

        if (jsonObject.get("nChg") != null)
            exchangeUpdate.setNmbrOfNoChange(jsonObject.get("nChg").getAsInt());

        if (jsonObject.get("symt") != null)
            exchangeUpdate.setSymbolsTraded(jsonObject.get("symt").getAsInt());

        if (jsonObject.get("vol") != null)
            exchangeUpdate.setVolume(jsonObject.get("vol").getAsLong());

        if (jsonObject.get("tovr") != null)
            exchangeUpdate.setTurnOver(jsonObject.get("tovr").getAsDouble());

        if (jsonObject.get("trades") != null)
            exchangeUpdate.setNmbrOfTrades(jsonObject.get("trades").getAsInt());

        if (jsonObject.get("mktCap") != null)
            exchangeUpdate.setMktCap(jsonObject.get("mktCap").getAsDouble());

        if (jsonObject.get("mboae") != null)
            exchangeUpdate.setIsAdvancedMboEnabled(jsonObject.get("mboae").getAsInt());

        if (jsonObject.get("mbpae") != null)
            exchangeUpdate.setIsAdvancedMbpEnabled(jsonObject.get("mbpae").getAsInt());

        if (jsonObject.get("cit") != null)
            exchangeUpdate.setCashInTurnOver(jsonObject.get("cit").getAsDouble());

        if (jsonObject.get("cot") != null)
            exchangeUpdate.setCashOutTurnOver(jsonObject.get("cot").getAsDouble());


        return exchangeUpdate;
    }
}
