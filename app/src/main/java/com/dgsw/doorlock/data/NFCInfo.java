package com.dgsw.doorlock.data;

/**
 * Created by kimji on 2017-12-21.
 */

public class NFCInfo {
    private String date;
    private String clockStart;
    private String clockEnd;
    private String RFID;

    public NFCInfo(String date, String clockStart, String clockEnd, String RFID) {
        this.date = date;
        this.clockStart = clockStart;
        this.clockEnd = clockEnd;
        this.RFID = RFID;
    }

    public String getDate() {
        return date;
    }

    public String getClockStart() {
        return clockStart;
    }

    public String getClockEnd() {
        return clockEnd;
    }

    public String getRFID() {
        return RFID;
    }
}
