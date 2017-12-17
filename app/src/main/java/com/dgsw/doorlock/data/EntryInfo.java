package com.dgsw.doorlock.data;

import java.util.Date;

/**
 * Created by Jin on 2017-11-25.
 */

public class EntryInfo {
    private String id;
    private String date;
    private String clockStart;
    private String clockEnd;

    public EntryInfo(String id, String date, String clockStart, String clockEnd){
        this.id = id;
        this.date = date;
        this.clockStart = clockStart;
        this.clockEnd = clockEnd;
    }

    public String getId(){
        return this.id;
    }
    public String getDate(){
        return this.date;
    }

    public String getClockStart() {
        return clockStart;
    }

    public String getClockEnd() {
        return clockEnd;
    }
}
