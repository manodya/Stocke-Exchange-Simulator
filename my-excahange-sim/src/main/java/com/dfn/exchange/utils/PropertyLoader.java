package com.dfn.exchange.utils;

import com.dfn.exchange.fix.FIXConstants;
import com.dfn.exchange.price.util.PriceConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by manodyas on 8/8/2018.
 */
public class PropertyLoader {
    private static final Logger logger = LogManager.getLogger(PropertyLoader.class);
    protected static Properties properties = null;
    public static String EXECUTION_MODE = "";

    public static void loadSystemProperties(){
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("./Settings/Exchange.properties");
            properties = new Properties();
            properties.load(inputStream);
            EXECUTION_MODE = properties.getProperty("executionMode");
            PriceConstants.setWSUrl(properties.getProperty("WebSocketUrl"));
            PriceConstants.setPriceUser(properties.getProperty("PriceUser"));
            PriceConstants.setSymbolList(properties.getProperty("Symbols"));
            PriceConstants.setExchangeCode(properties.getProperty("exchangeCode"));

            FIXConstants.setConnectionMode(properties.getProperty("connectionMode"));

        } catch (IOException e) {
            logger.error("Error Reading Property File.", e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("Error Closing Input Stream.", e.getMessage());
            }
        }

    }
}
