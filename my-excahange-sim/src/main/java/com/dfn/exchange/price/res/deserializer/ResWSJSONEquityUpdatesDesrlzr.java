package com.dfn.exchange.price.res.deserializer;


import com.dfn.exchange.price.res.PriceEvent;
import com.dfn.exchange.price.util.PriceUtils;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by manodyas on 3/19/2018.
 */
public class ResWSJSONEquityUpdatesDesrlzr implements JsonDeserializer<PriceEvent> {
    private static DecimalFormat formatter = new DecimalFormat(".##");

    @Override
    public PriceEvent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) jsonElement;
        PriceEvent equityUpdates = new PriceEvent();
        float temp = 0;
        if (jsonObject.get("sym") != null) {
            equityUpdates.setSymbol(jsonObject.get("sym").getAsString());
        }
        if (jsonObject.get("exg") != null) {
            equityUpdates.setExchange(jsonObject.get("exg").getAsString());
        }
        if (jsonObject.get("dcf") != null) {
            equityUpdates.setDecimalCorrFactor(jsonObject.get("dcf").getAsInt());
        }
        if (jsonObject.get("inst") != null) {
            equityUpdates.setInstmntType(jsonObject.get("inst").getAsInt());
        }
        if (jsonObject.get("high") != null) {
            equityUpdates.setHigh(jsonObject.get("high").getAsDouble());
        }
        if (jsonObject.get("low") != null) {
            equityUpdates.setLow(jsonObject.get("low").getAsDouble());
        }
        if (jsonObject.get("cls") != null) {
            equityUpdates.setTodaysClose(jsonObject.get("cls").getAsDouble());
        }
        if (jsonObject.get("open") != null) {
            equityUpdates.setTodaysOpen(jsonObject.get("open").getAsDouble());
        }
        if (jsonObject.get("chg") != null) {
            equityUpdates.setNetChange(jsonObject.get("chg").getAsDouble());
        }
        if (jsonObject.get("pctChg") != null) {
            equityUpdates.setPerChange(jsonObject.get("pctChg").getAsDouble());
        }
        if (jsonObject.get("prvCls") != null) {
            equityUpdates.setPrevClose(jsonObject.get("prvCls").getAsDouble());
        }
        if (jsonObject.get("tovr") != null) {
            equityUpdates.setTurnOver(jsonObject.get("tovr").getAsDouble());
        }
        if (jsonObject.get("vol") != null) {
            equityUpdates.setVolume(jsonObject.get("vol").getAsLong());
        }
        if (jsonObject.get("trades") != null) {
            equityUpdates.setNumOfTrades(jsonObject.get("trades").getAsInt());
        }
        if (jsonObject.get("cur") != null) {
            equityUpdates.setCurrency(jsonObject.get("cur").getAsString());
        }
        if (jsonObject.get("ltp") != null) {
            temp = jsonObject.get("ltp").getAsFloat();
            equityUpdates.setLstTrdPx(Double.parseDouble(formatter.format(temp)));
        }
        if (jsonObject.get("ltq") != null) {
            equityUpdates.setLstTrdQty(jsonObject.get("ltq").getAsInt());
        }
        if (jsonObject.get("ltd") != null) {
            equityUpdates.setLstTrdDate(jsonObject.get("ltd").getAsString());
        }
        if (jsonObject.get("ltt") != null) {
            equityUpdates.setTrdTime(jsonObject.get("ltt").getAsString());
        }
        if (jsonObject.get("bap") != null) {
            temp = jsonObject.get("bap").getAsFloat();
            equityUpdates.setBstAskPx(Double.parseDouble(formatter.format(temp)));
        }
        if (jsonObject.get("baq") != null) {
            equityUpdates.setBstAskQty(jsonObject.get("baq").getAsInt());
        }
        if (jsonObject.get("bbp") != null) {
            temp = jsonObject.get("bbp").getAsFloat();
            equityUpdates.setBstBidPx(Double.parseDouble(formatter.format(temp)));
        }
        if (jsonObject.get("bbq") != null) {
            equityUpdates.setBstBidQty(jsonObject.get("bbq").getAsInt());
        }
        if (jsonObject.get("taq") != null) {
            equityUpdates.setTotalAskQty(jsonObject.get("taq").getAsInt());
        }
        if (jsonObject.get("tbq") != null) {
            equityUpdates.setTotalBidQty(jsonObject.get("tbq").getAsInt());
        }
        if (jsonObject.get("min") != null) {
            temp = jsonObject.get("min").getAsFloat();
            equityUpdates.setMinPx(Double.parseDouble(formatter.format(temp)));
        }
        if (jsonObject.get("max") != null) {
            temp = jsonObject.get("max").getAsFloat();
            equityUpdates.setMaxPx(Double.parseDouble(formatter.format(temp)));
        }
        if (jsonObject.get("vwap") != null) {
            equityUpdates.setAvgTrdPx(jsonObject.get("vwap").getAsDouble());
        }
        if (jsonObject.get("stkP") != null) {
            equityUpdates.setStrikePx(jsonObject.get("stkP").getAsDouble());
        }


        equityUpdates.setDate(PriceUtils.getDateString(new Date()));
        equityUpdates.setTimeStamp(PriceUtils.getCurrentMills());

        return equityUpdates;
    }
}
