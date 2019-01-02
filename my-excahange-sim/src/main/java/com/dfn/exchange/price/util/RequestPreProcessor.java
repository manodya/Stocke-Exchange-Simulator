package com.dfn.exchange.price.util;

import com.google.gson.Gson;

/**
 * Created by manodyas on 3/14/2018.
 */
public class RequestPreProcessor {
    private static final Gson gson = new Gson();

    public  String composeRequest(Object o){
        String res = gson.toJson(o);
        return  res.length() + res;
    }
}
