package com.dgsw.doorlock.tool;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by kimji on 2017-12-17.
 */

public class ApproveHTTP {
    public boolean success = false;
    public boolean connect() {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        try {
                            URL Url = new URL("http://192.168.0.6:8080/ENT_SYSTEM/webresources/com.dgsw.entinfo/" );//보낼 주소
                            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();//연결해줄 Connection
                            conn.setRequestMethod("GET");//POST 형식
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setDoInput(true);//입력 가능
                            conn.setDoOutput(true);//받기 가능

                            Log.e("RESPONSE_CODE",conn.getResponseCode()+"");

                            BufferedReader br;
                            try {
                                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            } catch (Exception e) {
                                e.printStackTrace();
                                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                            }
                            String buf;
                            StringBuilder Text = new StringBuilder();
                            while ((buf = br.readLine()) != null) {
                                Text.append(buf);
                            }
                            br.close();
                            success = true;
                        } catch (MalformedURLException | ProtocolException exception) {
                            exception.printStackTrace();
                            success = false;
                        } catch (IOException io) {
                            io.printStackTrace();
                            success = false;
                        }
                    }
                }
        ).start();
        return success;
    }
}
