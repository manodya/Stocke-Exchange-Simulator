package com.dfn.exchange.registry;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorContext;
import com.dfn.exchange.SymbolActor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by manodyas on 7/30/2018.
 */
public class SymbolRegistry {
    private static Map<String, ActorRef> priceRelatedSymbolActorMap = new HashMap<>();
    private static Map<String, ActorRef> executionRelatedSymbolActorMap = new HashMap<>();
    public static UntypedActorContext context;

    public static ActorRef getPriceRelatedSymbolActorRef( String symbol){
        if(!priceRelatedSymbolActorMap.containsKey(symbol)){
            priceRelatedSymbolActorMap.put(symbol, context.actorOf(Props.create(SymbolActor.class, symbol), symbol));
        }
        return priceRelatedSymbolActorMap.get(symbol);
    }

    public static ActorRef getExecutionRelatedSymbolActorRef( String symbol){
        if(!priceRelatedSymbolActorMap.containsKey(symbol)){
            priceRelatedSymbolActorMap.put(symbol, context.actorOf(Props.create(SymbolActor.class, symbol), symbol));
        }
        return priceRelatedSymbolActorMap.get(symbol);
    }


}
