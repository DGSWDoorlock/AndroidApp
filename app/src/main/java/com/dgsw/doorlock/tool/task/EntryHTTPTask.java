package com.dgsw.doorlock.tool.task;

import android.os.AsyncTask;
import android.util.Log;

import com.dgsw.doorlock.data.EntryInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.dgsw.doorlock.activity.Main.IP_ADDRESS;

public class EntryHTTPTask extends AsyncTask<EntryInfo, Integer, Boolean> {

    private int state;

    public EntryHTTPTask(int state) {
        this.state = state;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(EntryInfo[] infos) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("userId", infos[0].getId());
            jo.put("name", infos[0].getName());
            jo.put("date", infos[0].getDate() + "T00:00:00+09:00");
            jo.put("inTime", "1970-01-01T" + infos[0].getClockStart() + "+09:00");
            jo.put("outTime", "1970-01-01T" + infos[0].getClockEnd() + "+09:00");
            jo.put("state", Integer.toString(state));
            publishProgress(10);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println(jo.toString());
        String Json = jo.toString();
        publishProgress(20);
        boolean success = true;
        try {
            URL Url = new URL(" http://" + IP_ADDRESS + ":8080/ENT_SYSTEM/webresources/com.dgsw.entinfo");//보낼 주소
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();//연결해줄 Connection
            publishProgress(25);
            conn.setRequestMethod("POST");//POST 형식
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);//입력 가능
            conn.setDoOutput(true);//받기 가능

            publishProgress(35);

            OutputStream os = conn.getOutputStream();//OutputStream을 Connection을 통해 생성
            os.write(Json.getBytes("windows-949"));//json을 Byte로 변환 및 HOST가 한글 윈도우이므로 인코딩을 windows-949로 설정 DESKTOP-P5BVGSS
            publishProgress(45);
            os.flush();//보내기
            os.close();//닫기

            publishProgress(50);

            int Res = conn.getResponseCode();
            if (Res != HttpURLConnection.HTTP_NO_CONTENT)
                Log.e("RESPONSE_CODE", Res + "");

            BufferedReader br;
            publishProgress(60);
            try {
                publishProgress(70);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } catch (Exception e) {
                publishProgress(70);
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            publishProgress(90);
            br.close();
            success = true;
            publishProgress(100);
        } catch (IOException io) {
            io.printStackTrace();
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

