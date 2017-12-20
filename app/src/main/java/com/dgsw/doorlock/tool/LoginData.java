package com.dgsw.doorlock.tool;

/**
 * Created by kimji on 2017-12-20.
 */

public class LoginData {
    private String ID, PW;

    public LoginData(String ID, String PW) {
        this.ID = ID;
        this.PW = PW;
    }

    public String getID() {
        return ID;
    }

    public String getPW() {
        return PW;
    }

}
