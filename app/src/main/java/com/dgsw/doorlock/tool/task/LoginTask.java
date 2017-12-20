package com.dgsw.doorlock.tool.task;

import android.os.AsyncTask;
import android.util.Log;

import com.dgsw.doorlock.data.EntryInfo;
import com.dgsw.doorlock.tool.LoginData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.dgsw.doorlock.activity.Main.IP_ADDRESS;

/**
 * Created by kimji on 2017-12-20.
 */

public class LoginTask extends AsyncTask<LoginData, Integer, Object[]> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object[] doInBackground(LoginData[] data) {
        Object[] results = new Object[3];
        Boolean result = false;
        String name = null;
        String id = null;
        try {
            URL Url = new URL(" http://" + IP_ADDRESS + ":8080/ENT_SYSTEM/webresources/com.dgsw.usertable/" + URLEncoder.encode(data[0].getID(), "UTF-8"));//받을 주소 + ID
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();//연결해줄 Connection
            conn.setConnectTimeout(2500); // 2.5초 까지만 대기
            conn.setRequestMethod("GET");//POST 형식
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);//입력 가능

            int Res = conn.getResponseCode();
            if (Res != HttpURLConnection.HTTP_OK)
                Log.e("RESPONSE_CODE", Res + "");

            BufferedReader br;
            try {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } catch (Exception e) {
                e.printStackTrace();
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line = br.readLine();
            if (line == null) {
                results[0] = result;
                return results;
            }
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
            result = data[0].getID().equals(jsonObject.get("id").toString()) && data[0].getPW().equals(jsonObject.get("pw").toString());
            if (result) {
                name = jsonObject.get("name").toString();
                id = jsonObject.get("id").toString();
            }
            br.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        results[0] = result;
        results[1] = name;
        results[2] = id;
        return results;
    }

    @Override
    protected void onProgressUpdate(Integer... params) {

    }

    @Override
    protected void onPostExecute(Object[] result) {
        super.onPostExecute(result);

    }
}
