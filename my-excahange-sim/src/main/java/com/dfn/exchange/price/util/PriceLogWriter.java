package com.dfn.exchange.price.util;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manodyas on 3/19/2018.
 */
public class PriceLogWriter {
    private static Map<String, BufferedWriter> l1WriterMap = new HashMap<>();
    private static Map<String, BufferedWriter> l2WriterMap = new HashMap<>();


    public static boolean writeToFile(String res, int level) throws IOException {
        BufferedWriter bwrtr = getBufferedWriter(level);
        bwrtr.write(res + "\n");
        bwrtr.flush();

        return true;
    }

    private static BufferedWriter getBufferedWriter(int level) throws IOException {
        BufferedWriter bw = null;
        String fileName = PriceUtils.getLogFileName(level, PriceUtils.getDateString(new Date()));
        if (level == 1) {
            if (l1WriterMap.keySet().size() > 0) {
                String tempName = (String) l1WriterMap.keySet().toArray()[0];
                if (tempName.equalsIgnoreCase(fileName)) {
                    bw = l1WriterMap.get(tempName);
                } else {
                    l1WriterMap.get(tempName).close();
                    l1WriterMap.remove(tempName);
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
                    l1WriterMap.put(fileName, bufferedWriter);
                    bw = bufferedWriter;
                }
            } else {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
                l1WriterMap.put(fileName, bufferedWriter);
                bw = bufferedWriter;
            }


        } else if (level == 2) {
            if (l2WriterMap.keySet().size() > 0) {
                String tempName = (String) l2WriterMap.keySet().toArray()[0];
                if (tempName.equalsIgnoreCase(fileName)) {
                    bw = l2WriterMap.get(tempName);
                } else {
                    l2WriterMap.get(tempName).close();
                    l2WriterMap.remove(tempName);
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
                    l2WriterMap.put(fileName, bufferedWriter);
                    bw = bufferedWriter;
                }

            } else {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
                l2WriterMap.put(fileName, bufferedWriter);
                bw = bufferedWriter;
            }

        }
        return bw;
    }

    private static String getFileName(int level, String currentDate) {
        return "./priceLogStore/" + "L" + level + "_" + currentDate;
    }
}
