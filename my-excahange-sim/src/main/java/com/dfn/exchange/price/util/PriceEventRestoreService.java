package com.dfn.exchange.price.util;


import com.dfn.exchange.price.res.PriceEvent;
import com.dfn.exchange.price.res.bo.DepthByOrderEntry;
import com.dfn.exchange.price.res.bo.DepthByPriceEntry;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by manodyas on 4/11/2018.
 */
public class PriceEventRestoreService {
    private static Gson gson = new Gson();

    public static void restorePriceData() {
        restoreL1Data();
        restoreL2Data();
    }

    private static void restoreL1Data() {
        List<String> priceEntries = PriceLogReader.readEntries(1);
        if (priceEntries.size() > 0) {
            for (int i = 0; i <= priceEntries.size() - 1; i++) {
                String entry = priceEntries.get(i);
                String[] x = entry.split("\\|");
                if (x.length == 3) {
                    PriceEvent req = gson.fromJson(x[2], PriceEvent.class);
                    L1DataStore.addPriceEventToEquityCache(x[0], req);
                    L1DataStore.addPriceEventToEquitySnapshot(x[0], req);

                }
            }

        }
    }

    private static void restoreL2Data() {
        List<String> priceEntries = PriceLogReader.readEntries(2);
        if (priceEntries.size() > 0) {
            for (String entry : priceEntries) {

                String[] x = entry.split("\\|");
                if (x.length == 4) {
                    final String mode = x[2];
                    final String symbol = x[0];
                    Map<String, Object> result = new Gson().fromJson(x[3], Map.class);
                    Map<Integer, DepthByOrderEntry> depthByOrderMap = new HashMap<>();
                    Map<Integer, DepthByPriceEntry> depthByPriceMap = new HashMap<>();
                    result.forEach((k, v) -> {

                        String value = gson.toJson(v);
                        if (mode.equalsIgnoreCase("DBO")) {
                            DepthByOrderEntry depthByOrder = gson.fromJson(value, DepthByOrderEntry.class);
                            depthByOrderMap.put(Integer.valueOf(k), depthByOrder);
                        } else if (mode.equalsIgnoreCase("DBP")) {
                            DepthByPriceEntry depthByPrice = gson.fromJson(value, DepthByPriceEntry.class);
                            depthByPriceMap.put(Integer.valueOf(k), depthByPrice);
                        }
                    });

                    if(mode.equalsIgnoreCase("DBO")){
                        L2DataStore.updateDepthByOrderOrdBook(symbol,depthByOrderMap); /*-Add DBO entries finally last entry will remain-*/
                    }else if(mode.equalsIgnoreCase("DBP")){
                        L2DataStore.updateDepthByPriceOrdBook(symbol,depthByPriceMap);/*-Add DBP entries finally last entry will remain-*/

                    }
                }

            }
        }


    }
}
