package com.dfn.exchange.price.util;


import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manodyas on 3/14/2018.
 */
public class WSSessionStore {
    private static List<Session> sessionList = new ArrayList<>();
    private static boolean connected = false;

    public static void addSessionToStore(Session session){
        sessionList.add(session);
    }

    public static Session getSession(){
        return  sessionList.get(0);
    }
    public static void setConnectionStatus(boolean status){
        connected = status;
    }

    public static boolean isConnected() {
        return connected;
    }
}
