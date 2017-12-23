package com.dgsw.doorlock.tool.task;

import android.os.AsyncTask;
import android.util.Log;

import com.dgsw.doorlock.data.EntryInfo;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.dgsw.doorlock.activity.Main.ID;
import static com.dgsw.doorlock.activity.Main.IP_ADDRESS;

/**
 * Created by Jin on 2017-12-22.
 */

public class LootOperateTask extends AsyncTask<EntryInfo, Integer, Boolean> {
        Boolean Operate;

        @Override
        protected void onPreExecute() {
        super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(EntryInfo[] infos) {
            publishProgress(20);
            try {
                URL Url = new URL("http://" + IP_ADDRESS + ":8080/ENT_SYSTEM/webresources/com.dgsw.entinfo/"+ID);
                HttpURLConnection conn = (HttpURLConnection) Url.openConnection();//연결해줄 Connection
                publishProgress(25);
                conn.setRequestMethod("GET");//POST 형식
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);//입력 가능

                publishProgress(50);

                int Res = conn.getResponseCode();
                if (Res != HttpURLConnection.HTTP_NO_CONTENT)
                    Log.e("RESPONSE_CODE", Res + "");

                BufferedReader br;
                publishProgress(60);
                try {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } catch (Exception e) {
                    e.printStackTrace();
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
                publishProgress(70);
                String line = br.readLine();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
                if(jsonObject.get("admin").toString().equals("1")){
                    Operate = true;
                }else{
                    Operate = false;
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

            return Operate;
        }

        @Override
        protected void onProgressUpdate(Integer... params) {

        }

    @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

        }
}
