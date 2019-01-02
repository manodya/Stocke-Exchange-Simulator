package com.dfn.exchange.price.util;


import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by manodyas on 4/11/2018.
 */
public class PriceLogReader {
    /**
     * @param level
     * @return
     */

    public static List<String> readEntries(int level) {
        List<String> priceEntries = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        String fileName = PriceUtils.getLogFileName(level, PriceUtils.getDateString(new Date())); /*-Get today's Log file name based on the level-*/
        if (new File(fileName).exists()) {
            try {
                fileReader = new FileReader(fileName);
                bufferedReader = new BufferedReader(fileReader);
                String currentLine;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    priceEntries.add(currentLine);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();

                    if (fileReader != null)
                        fileReader.close();

                } catch (IOException ex) {

                    ex.printStackTrace();
                }
            }
        }

        return priceEntries;
    }
}
