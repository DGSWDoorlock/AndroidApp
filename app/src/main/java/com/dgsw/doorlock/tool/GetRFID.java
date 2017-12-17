package com.dgsw.doorlock.tool;

import android.util.Log;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.dgsw.doorlock.activity.Main.IP_ADDRESS;

/**
 * Created by kimji on 2017-12-17.
 */

public class GetRFID {
    private final String id;

    public String RFID;

    public GetRFID(String id) {
        this.id = id;
    }
    public String connect() {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL Url = new URL(" http://"+ IP_ADDRESS +":8080/ENT_SYSTEM/webresources/com.dgsw.entinfo/"+id);//보낼 주소
                            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();//연결해줄 Connection
                            conn.setRequestMethod("GET");//POST 형식
                            conn.setRequestProperty("ACCEPT", "application/json");
                            conn.setDoInput(true);//입력 가능
                            conn.setDoOutput(true);//출력 가능

                            int Res=conn.getResponseCode();
                            if(Res!=HttpURLConnection.HTTP_OK)
                                Log.e("RESPONSE_CODE",Res+"");

                            BufferedReader br;
                            try {
                                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            } catch (Exception e) {
                                e.printStackTrace();
                                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                            }
                            JSONParser jsonParser = new JSONParser();
                            JSONObject jsonObject = (JSONObject) jsonParser.parse(br.readLine());
                            RFID = jsonObject.get("rfid").toString();
                            br.close();
                        } catch (IOException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
        return RFID;
    }
}
