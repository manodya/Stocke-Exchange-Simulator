package com.dfn.exchange.price.util;


import com.dfn.exchange.price.res.PriceEvent;
import com.dfn.exchange.price.res.ResWSJSONExchangeUpdates;
import com.google.gson.Gson;
import edu.emory.mathcs.backport.java.util.Collections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by manodyas on 3/19/2018.
 */
public class L1DataStore {
    private static Map<String, List<PriceEvent>> equityCache = new HashMap(); /*-EquityUpdate Sequence for a symbol-*/
    private static String lastCachedDate = null;
    private static Map<String, PriceEvent> equitySnapShot = new HashMap<>(); /*-Last Equity Update for a Symbol-*/
    private static ResWSJSONExchangeUpdates exchangeStatus = null;
    private static final Gson gson = new Gson();


    /**
     *
     * @param update
     */
    public static void updateExchangeStatus(ResWSJSONExchangeUpdates update) {
        if (exchangeStatus == null) {
            exchangeStatus = update;
        } else {

            if (update.getMktStatus() != 0)
                exchangeStatus.setMktStatus(update.getMktStatus());

            if (update.getMktDate() != null)
                exchangeStatus.setMktDate(update.getMktDate());

            if (update.getMktTime() != null)
                exchangeStatus.setMktTime(update.getMktTime());

            if (update.getLastEODDate() != null)
                exchangeStatus.setLastEODDate(update.getLastEODDate());

            if (update.getNmbrOfUps() != 0)
                exchangeStatus.setNmbrOfUps(update.getNmbrOfUps());

            if (update.getNmbrOfDowns() != 0)
                exchangeStatus.setNmbrOfDowns(update.getNmbrOfDowns());

            if (update.getNmbrOfNoChange() != 0)
                exchangeStatus.setNmbrOfNoChange(update.getNmbrOfNoChange());

            if (update.getSymbolsTraded() != 0)
                exchangeStatus.setSymbolsTraded(update.getSymbolsTraded());

            if (update.getVolume() != 0)
                exchangeStatus.setVolume(update.getVolume());

            if (update.getTurnOver() != 0)
                exchangeStatus.setTurnOver(update.getTurnOver());

            if (update.getNmbrOfTrades() != 0)
                exchangeStatus.setNmbrOfTrades(update.getNmbrOfTrades());

            if (update.getMktCap() != 0)
                exchangeStatus.setMktCap(update.getMktCap());

            if (update.getIsAdvancedMboEnabled() != 0)
                exchangeStatus.setIsAdvancedMboEnabled(update.getIsAdvancedMboEnabled());

            if (update.getIsAdvancedMbpEnabled() != 0)
                exchangeStatus.setIsAdvancedMbpEnabled(update.getIsAdvancedMbpEnabled());

            if (update.getCashInTurnOver() != 0)
                exchangeStatus.setCashInTurnOver(update.getCashInTurnOver());

            if (update.getCashOutTurnOver() != 0)
                exchangeStatus.setCashOutTurnOver(update.getCashOutTurnOver());

        }


    }

    /**
     *
     * @param update
     * @return
     * @throws IOException
     */
    public static PriceEvent updateEquity(PriceEvent update) throws IOException {
        if (equitySnapShot.get(PriceUtils.getSymbolReference(update.getSymbol())) != null) {
            PriceEvent temp = equitySnapShot.get(PriceUtils.getSymbolReference(update.getSymbol()));
            if ((update.getLstTrdPx() != 0) && (temp.getLstTrdPx() != update.getLstTrdPx()) ||
                    ((update.getVolume() != 0) && (temp.getVolume() != update.getVolume()))) {
                if (update.getHigh() == 0) {
                    update.setHigh(temp.getHigh());
                }
                if (update.getLow() == 0) {
                    update.setLow(temp.getLow());
                }
                if (update.getTodaysClose() == 0) {
                    update.setTodaysClose(temp.getTodaysClose());
                }
                if (update.getTodaysOpen() == 0) {
                    update.setTodaysOpen(temp.getTodaysOpen());
                }
                if (update.getNetChange() == 0) {
                    update.setNetChange(temp.getNetChange());
                }
                if (update.getPerChange() == 0) {
                    update.setPerChange(temp.getPerChange());
                }
                if (update.getPrevClose() == 0) {
                    update.setPrevClose(temp.getPrevClose());
                }
                if (update.getTurnOver() == 0) {
                    update.setTurnOver(temp.getTurnOver());
                }
                if (update.getVolume() == 0) {
                    update.setVolume(temp.getVolume());
                }
                if (update.getNumOfTrades() == 0) {
                    update.setNumOfTrades(temp.getNumOfTrades());
                }
                if (update.getLstTrdPx() == 0) {
                    update.setLstTrdPx(temp.getLstTrdPx());
                }
                if (update.getLstTrdQty() == 0) {
                    update.setLstTrdQty(temp.getLstTrdQty());
                }
                if (update.getLstTrdDate() == null) {
                    update.setLstTrdDate(temp.getLstTrdDate());
                }
                if (update.getTrdTime() == null) {
                    update.setTrdTime(temp.getTrdTime());
                }
                if (update.getBstAskPx() == 0) {
                    update.setBstAskPx(temp.getBstAskPx());
                }
                if (update.getBstAskQty() == 0) {
                    update.setBstAskQty(temp.getBstAskQty());
                }
                if (update.getBstBidPx() == 0) {
                    update.setBstBidPx(temp.getBstBidPx());
                }
                if (update.getBstBidQty() != 0) {
                    update.setBstBidQty(temp.getBstBidQty());
                }
                if (update.getTotalAskQty() != 0) {
                    update.setTotalAskQty(temp.getTotalAskQty());
                }
                if (update.getTotalBidQty() != 0) {
                    update.setTotalBidQty(temp.getTotalBidQty());
                }
                if (update.getMinPx() != 0) {
                    update.setMinPx(temp.getMinPx());
                }
                if (update.getMaxPx() != 0) {
                    update.setMaxPx(temp.getMaxPx());
                }
                if (update.getAvgTrdPx() != 0) {
                    update.setAvgTrdPx(temp.getAvgTrdPx());
                }
                if (update.getStrikePx() != 0) {
                    update.setStrikePx(temp.getStrikePx());
                }

                if (!update.getDate().equalsIgnoreCase(lastCachedDate)) {
                    lastCachedDate = update.getDate();
                    equityCache = new HashMap<>();

                    if(equityCache.containsKey(PriceUtils.getSymbolReference(update.getSymbol()))){
                        equityCache.get(PriceUtils.getSymbolReference(update.getSymbol())).add(update);

                    }else{
                        List<PriceEvent> list = new ArrayList<>();
                        list.add(update);
                        equityCache.put(PriceUtils.getSymbolReference(update.getSymbol()), list);

                    }

                } else {
                    lastCachedDate = update.getDate();
                    try{
                        if(equityCache.get(PriceUtils.getSymbolReference(update.getSymbol())) == null){
                            List<PriceEvent> list = new ArrayList<>();
                            list.add(update);
                            equityCache.put(PriceUtils.getSymbolReference(update.getSymbol()), list);
                        }else{
                            equityCache.get(PriceUtils.getSymbolReference(update.getSymbol())).add(update);

                        }
                    }catch (Exception e){
                        //System.out.println("######## Symbol :" + update.getSymbol());
                    }


                }
                equitySnapShot.put(PriceUtils.getSymbolReference(update.getSymbol()), update);
                String resString = PriceUtils.getSymbolReference(update.getSymbol()) + "|" + PriceUtils.getCurrentTimeStrForToday() + "|" + gson.toJson(equitySnapShot.get(update.getSymbol()));
                PriceLogWriter.writeToFile(resString, 1);
            } else {
                update = null;
            }


        } else {
            equitySnapShot.put(PriceUtils.getSymbolReference(update.getSymbol()), update);
            lastCachedDate = update.getDate();
            List<PriceEvent> list = new ArrayList<>();
            list.add(update);
            equityCache.put(PriceUtils.getSymbolReference(update.getSymbol()), list);
            String resString = update.getSymbol() + "|" + PriceUtils.getCurrentTimeStrForToday() + "|" + gson.toJson(equitySnapShot.get(update.getSymbol()));
            PriceLogWriter.writeToFile(resString, 1);
        }
        return update;
    }

    /**
     *
     * @param symbol
     * @return
     */
    public static PriceEvent getL1SnapShot(String symbol) {
        symbol = PriceUtils.getSymbolReference(symbol);
        return equitySnapShot.get(symbol);

    }

    /**
     *
     * @param symbol
     * @param count
     * @return
     */
    public static List<PriceEvent> getL1PriceEvents(String symbol, int count) {
        symbol = PriceUtils.getSymbolReference(symbol);
        List<PriceEvent> tempEvents = equityCache.get(symbol);
        List<PriceEvent> events = new ArrayList<>();
        if (tempEvents != null) {
            if (count >= tempEvents.size()) {
                events = tempEvents;
            } else {
                int counter = 0;
                for (int i = tempEvents.size() - 1; counter < count; i--) {
                    events.add(tempEvents.get(i));
                    counter++;
                }
            }
        }
        Collections.reverse(events);
        return events;
    }

    /**
     *
     * @param symbol
     * @param fromTime
     * @param toTime
     * @return
     */
    public static List<PriceEvent> getL1PriceEvents(String symbol, long fromTime, long toTime) {
        symbol = PriceUtils.getSymbolReference(symbol);
        List<PriceEvent> tempEvents = equityCache.get(symbol);
        List<PriceEvent> events = new ArrayList<>();
        if (tempEvents != null) {
            for (int i = tempEvents.size() - 1; i >= 0; i--) {
                if ((fromTime <= tempEvents.get(i).getTimeStamp()) && (tempEvents.get(i).getTimeStamp() <= toTime)) {
                    events.add(tempEvents.get(i));
                }
            }
        }
        Collections.reverse(events);
        return events;

    }

    /**
     *
     * @param symbol
     * @param fromTime
     * @param toTime
     * @param count
     * @return
     */
    public static List<PriceEvent> getL1PriceEvents(String symbol, long fromTime, long toTime, int count) {
        symbol = PriceUtils.getSymbolReference(symbol);
        List<PriceEvent> tempEvents = equityCache.get(symbol);
        List<PriceEvent> events = new ArrayList<>();
        if (tempEvents != null) {
            int counter = 0;
          /*  for (int i = tempEvents.size() - 1; i >= 0; i--) {
                if ((fromTime <= tempEvents.get(i).getTimeStamp()) && (tempEvents.get(i).getTimeStamp() <= toTime)) {
                    if (counter < count) {
                        events.add(tempEvents.get(i));
                        counter++;
                    }
                }
            }
            if(tempEvents.size() >= (counter + 1)){
                events.add(tempEvents.get(counter));
                events.add(tempEvents.get(counter + 1));
            }*/

          for(int i = 0; i < tempEvents.size() -1; i++){
              if ((fromTime <= tempEvents.get(i).getTimeStamp()) && (tempEvents.get(i).getTimeStamp() <= toTime)) {
                  if (counter < count) {
                      events.add(tempEvents.get(i));
                      counter++;
                  }
              }
          }
        }
        events.sort((e1,e2) -> new Long(e1.getTimeStamp()).compareTo(new Long(e2.getTimeStamp())));
        //Collections.reverse(events);
        return events;
    }

    /**
     *
     * @param symbol
     * @param priceEvent
     * add the equityupdate list
     */
    public static void addPriceEventToEquityCache(String symbol, PriceEvent priceEvent){
        if(equityCache.keySet().contains(symbol)){
            equityCache.get(symbol).add(priceEvent);
        }else {
            List<PriceEvent> priceEvents = new ArrayList<>();
            priceEvents.add(priceEvent);
            equityCache.put(symbol, priceEvents);
        }
    }

    /**
     *
     * @param symbol
     * @param priceEvent
     * add last EquityUpdate
     */
    public static void addPriceEventToEquitySnapshot(String symbol, PriceEvent priceEvent){
        equitySnapShot.put(symbol, priceEvent);
    }

    public static int getEventCount(String s){
        return  equityCache.get(s).size();
    }

    public static boolean isSymbolAvailableInCache(String symbol){
        return equitySnapShot.containsKey(symbol);
    }

}
