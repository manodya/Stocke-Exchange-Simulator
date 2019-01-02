package com.dfn.exchange.price.req;

/**
 * Created by manodyas on 3/14/2018.
 */
public class LoginRequest {
    private String AUTHVER = "10";
    private String UNM;
    private String SID;
    private String PDM;
    private String LAN;
    private String METAVER;

    public LoginRequest(String UNM, String SID, String PDM, String LAN, String METAVER) {
        this.UNM = UNM;
        this.SID = SID;
        this.PDM = PDM;
        this.LAN = LAN;
        this.METAVER = METAVER;
    }

    public void setAUTHVER(String AUTHVER) {
        this.AUTHVER = AUTHVER;
    }

    public void setUNM(String UNM) {
        this.UNM = UNM;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public void setPDM(String PDM) {
        this.PDM = PDM;
    }

    public void setLAN(String LAN) {
        this.LAN = LAN;
    }

    public void setMETAVER(String METAVER) {
        this.METAVER = METAVER;
    }
}
