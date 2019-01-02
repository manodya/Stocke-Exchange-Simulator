package com.dfn.exchange;

import akka.actor.UntypedActor;
import com.dfn.exchange.registry.SymbolRegistry;

/**
 * Created by manodyas on 7/30/2018.
 */
public class ExchangeActor extends UntypedActor {
    @Override
    public void preStart() throws Exception {
        SymbolRegistry.context = getContext();
        super.preStart();
    }

    @Override
    public void onReceive(Object o) throws Exception {

    }
}
