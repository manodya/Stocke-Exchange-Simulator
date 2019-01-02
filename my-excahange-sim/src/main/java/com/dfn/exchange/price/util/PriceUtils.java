package com.dfn.exchange.price.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by manodyas on 3/15/2018.
 */
public class PriceUtils {
    private static  String TIME_ZONE = "Asia/Riyadh";
    private static final String DATE_FORMAT_DATE = "yyyyMMdd";

    public static String getJSONString(String res){
        return res.substring(res.indexOf("{"), res.length());
    }


    public static String getLogFileName(int level, String currentDate) {
        return "./priceLogStore/" + "L" + level + "_" + currentDate + "_" + PriceConstants.EXCHANGE_CODE;
    }

    public static String getSymbolReference(String symbolCode){

        if(symbolCode != null){
            return symbolCode.replaceAll("[.,`]","_");
        }

        return null;

    }

    public static String getCurrentTimeStrForToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
        format.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        String dateTimeString = format.format(new Date());
        if (dateTimeString != null) {
            return dateTimeString.split("-")[1];
        }
        return null;
    }

    public static String getDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DATE);
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        return sdf.format(date);
    }

    public static long getCurrentMills() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
        format.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        Date date = new Date();
        return date.getTime() /*+ getOffset("Asia/Riyadh")*/;
    }




}
