package com.dfn.exchange.price.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by manodyas on 3/14/2018.
 */
public class PriceConstants {

    /*---Connection Mode-----*/
    public static final String WEB_SOCKET = "ws";
    public static final String TCP_SOCKET = "socket";


    /*---Price Connectivity Constants---*/
 //   public static final String WSUrl = "ws://192.168.13.123:9018/"; //
    public static  String WSUrl = "wss://data-sa9.mubasher.net/html5-Retail"; //
//    public static final String PRICE_USER = "TDWL.REALTIME";
    public static  String PRICE_USER = "anbtest1";
    public static  String EXCHANGE_CODE = "TDWL"; // default exchange will be overwritten properties file configuration

    public static void setWSUrl(String wsUrl){
        WSUrl = wsUrl;
    }

    public static void setPriceUser(String priceUser){
        PRICE_USER =  priceUser;
    }

    public static void setExchangeCode(String exchangeCode) {
        EXCHANGE_CODE = exchangeCode;
    }

    /*---Request Constants---*/
    public static final String REQ_EXCHANGE_STATUS = "32";
    public static final String REQ_DEPTH_BY_PRICE = "30";
    public static final String REQ_DEPTH_BY_ORDER = "29";
    public static final String REQ_EQUITY = "0";
    public static final String REQ_FULL_MKT = "0";


    /*--Response Constants---*/
    public static final int RES_EQUITY_UPDATE = 3;
    public static final int RES_EXCHANGE_STATUS_UPDATE = 5;
    public static final int RES_DEPTH_BY_ORDER = 7;
    public static final int RES_DEPTH_BY_PRICE = 9;

    /*---Symbol Validation-----*/
    public static final String PX_URL = "http://data-sa9.mubasher.net/mix2?";
    public static final String REQUEST_TEMPLATE = "SID=sid&UID=123&RT=53&SK=$symbol&E=$exchange&AE=0&ST=S&IFLD=DES,COM,CFID&PGI=0&PGS=10&L=EN&H=1&M=1";

    /*-Order Book Entry Type-*/
    private static final int ENTRY_TYPE_LOCAL = 1;
    private static final int ENTRY_TYPE_PROD = 2;


    /*--- Symbol Related --*/
    public static List<String>  symbolList = new ArrayList<>();

    public static  void setSymbolList(String symbolStr){
            symbolList = Arrays.asList(symbolStr.split(","));
    }




}
