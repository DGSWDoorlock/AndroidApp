package com.dgsw.doorlock.tool.task;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.dgsw.doorlock.data.EntryInfo;
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

import static com.dgsw.doorlock.activity.Main.IP_ADDRESS;

public class RegisterHTTPTask extends AsyncTask<EntryInfo, Integer, Boolean> {
    private UserInfo Info;

    public RegisterHTTPTask(UserInfo get) {
        Info = get;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(EntryInfo[] infos) {
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
        boolean success;
        try {
            URL Url = new URL("http://" + IP_ADDRESS + ":8080/ENT_SYSTEM/webresources/com.dgsw.usertable");//보낼 주소
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();//연결해줄 Connection
            conn.setRequestMethod("POST");//POST 형식
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);//입력 가능
            conn.setDoOutput(true);//받기 가능

            OutputStream os = conn.getOutputStream();//OutputStream을 Connection을 통해 생성
            os.write(Json.getBytes("windows-949"));//json을 Byte로 변환 및 HOST가 한글 윈도우이므로 인코딩을 windows-949로 설정

            os.flush();//보내기
            os.close();//닫기

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
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    @Override
    protected void onProgressUpdate(Integer... params) {

    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

    }
}
