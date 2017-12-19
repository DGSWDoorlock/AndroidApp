package com.dgsw.doorlock.data;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by Jin on 2017-11-25.
 */
public class UserInfo {
    //이름 아이디 비번 소속 직책 전화번호
    private String name;
    private String id;
    private String pw;
    private String belong;
    private String position;
    private String phoneNum;

    public UserInfo(String name, String id, String pw, String belong, String position, String phoneNum) {
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.belong = belong;
        this.position = position;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getPosition() {
        return position;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

}
