package com.dgsw.doorlock.data;

import java.util.Date;
import java.util.Map;

/**
 * Created by Jin on 2017-11-25.
 */

public class EntryInfo {
    private String id;
    private String date;
    private String clockStart;
    private String clockEnd;
    private String state;
    public EntryInfo(String id, String date, String clockStart, String clockEnd) {
        this.id = id;
        this.date = date;
        this.clockStart = clockStart;
        this.clockEnd = clockEnd;
    }
    public EntryInfo(String id,String date,String clockStart, String clockEnd, String state){
        this.id = id;
        this.date = date;
        this.clockStart = clockStart;
        this.clockEnd = clockEnd;
        this.state = state;
    }

    public String getId() {
        return this.id;
    }

    public String getDate() {
        return this.date;
    }

    public String getClockStart() {
        return clockStart;
    }

    public String getClockEnd() {
        return clockEnd;
    }

    public String getState() { return state; }
}
