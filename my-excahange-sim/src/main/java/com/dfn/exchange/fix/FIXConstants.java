package com.dfn.exchange.fix;

public class FIXConstants {
    public static String connectionMode = FIXConnectionMode.ACCEPTOR.toString();

    public static void setConnectionMode(String mode){
            connectionMode = mode;
    }

}
