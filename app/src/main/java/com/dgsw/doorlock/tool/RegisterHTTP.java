package com.dgsw.doorlock.tool;

import android.util.Log;

import com.dgsw.doorlock.data.UserInfo;

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
 * Created by baeju on 2017-11-25.
 */

public class RegisterHTTP {
    private UserInfo Info;
    private boolean success=true;
    public RegisterHTTP(UserInfo get) {
        Info = get;
    }

    public boolean connect() {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jo = new JSONObject();
                        try {
                            jo.put("name", Info.getName());
                            jo.put("id", Info.getId());
                            jo.put("pw", Info.getPw());
                            jo.put("belong", Info.getBelong());
                            jo.put("position", Info.getPosition());
                            jo.put("phonenum", Info.getPhoneNum());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(jo.toString());
                        String Json = jo.toString();
                        try {
                            URL Url = new URL("http://192.168.0.105:8080/ENT_SYSTEM/webresources/com.dgsw.usertable");//보낼 주소
                            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();//연결해줄 Connection
                            conn.setRequestMethod("POST");//POST 형식
                            conn.setRequestProperty("Content-Type", "application/json");
                            conn.setDoInput(true);//입력 가능
                            conn.setDoOutput(true);//받기 가능

                            OutputStream os = conn.getOutputStream();//OutputStream을 Connection을 통해 생성
                            os.write(Json.getBytes("windows-949"));//json을 Byte로 변환 및 HOST가 한글 윈도우이므로 인코딩을 windows-949로 설정

                            os.flush();//보내기
                            os.close();//닫기

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
