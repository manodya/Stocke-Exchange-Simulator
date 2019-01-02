package com.dfn.exchange.price.util;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by manodyas on 4/24/2018.
 */
public class SocketStatus {
    private static boolean connected = false;
    private static final int timeWindow = 10; // 10 mins
    private static final int priceDisconnectionThreshold = 200; // 200 disconnection per 10  mins

    private static long athenaStartTime;
    private static long lastConnectedTime;
    private static long lastDisconnectedTime;
    private static int disconnectCount = 0;
    private static double disconnectFrequency;
    private static List<Long> disconnectionTimeList = new ArrayList();



    /**
     *
     * @param states
     */
    public static synchronized void setSocketStates(boolean states) {
        boolean lastConnectionStatus = connected;
        connected = states;
        if (states) {
            lastConnectedTime = System.currentTimeMillis();
        } else {
            lastDisconnectedTime = System.currentTimeMillis();
            if(lastConnectionStatus && !states){
                disconnectionTimeList.add(lastConnectedTime);
            }

        }


    }

    public static boolean isConnected() {
        return connected;
    }


    /**
     * If Price is disconnected or experience frequent disconnections not allowed to accept orders
     * @return
     */
    public static boolean isReadyToAcceptOrders() {
        boolean result = true;
        if (!connected) {
            result = false;
        } else {
            long now = System.currentTimeMillis();
            if (disconnectionTimeList.size() > 0) {
                int count = 0;
                for (int i = disconnectionTimeList.size() - 1; i >= 0; i--) {
                    if ((now - disconnectionTimeList.get(i)) < timeWindow * (60000)) {
                        count++;
                    }
                }
                if (count >= priceDisconnectionThreshold) {
                    result = false;
                }
            }
        }
        return result;
    }


}
