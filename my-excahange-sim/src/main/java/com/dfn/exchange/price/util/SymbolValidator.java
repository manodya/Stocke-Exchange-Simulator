package com.dfn.exchange.price.util;



import com.dfn.exchange.price.res.bo.PXSymbol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manodyas on 5/14/2018.
 */
public class SymbolValidator {

    /**
     * * @param symbolCode
     *
     * @param exchange
     * @return
     * @throws IOException
     */

    public static List<PXSymbol> validateSymbol(String symbolCode, String exchange) throws IOException {
        String url = PriceConstants.PX_URL + composePriceRequest(symbolCode, exchange);
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        return processHTTPResponse(response.toString());
    }


    private static String composePriceRequest(String symbolCode, String exchange) {
        String request = PriceConstants.REQUEST_TEMPLATE;
        request = request.replace("$symbol", symbolCode);
        request = request.replace("$exchange", exchange);
        return request;
    }

    private static List<PXSymbol> processHTTPResponse(String response) {
        List<PXSymbol> symbols = null;
        if (response.contains("200|OK")) {
            int start = response.indexOf("\"DAT\":{\"SYMS\":");
            int end = response.indexOf(",\"STAT\"");
            System.out.println("##Start :" + start);
            System.out.println("##End :" + end);
            String res = response.substring(start, end).replace("\"DAT\":{\"SYMS\":", "").replaceAll("[\"}\\]\\[]", "");
            String[] symbolStrings = res.split(",");
            if (symbolStrings.length > 0) {
                symbols = new ArrayList<>();
                for (int i = 0; i < symbolStrings.length; i++) {
                    String sym = symbolStrings[i];
                    String[] symbolData = sym.split("\\|");
                    if (symbolData.length == 8) {
                        PXSymbol symbol = new PXSymbol();
                        symbol.setExchange(symbolData[0]);
                        symbol.setCode(symbolData[1]);
                        symbol.setInstrumentType(symbolData[2]);
                        symbol.setSectorCode(symbolData[3]);
                        symbol.setShortDescription(symbolData[4]);
                        symbol.setDescription(symbolData[5]);
                        symbol.setCompanyName(symbolData[6]);
                        symbol.setCompanyId(symbolData[7]);
                        symbols.add(symbol);

                    }
                }
            }

        }
        return symbols;

    }
}
