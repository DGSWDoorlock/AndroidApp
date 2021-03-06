package com.dgsw.doorlock.tool.task;

import android.os.AsyncTask;
import android.util.Log;

import com.dgsw.doorlock.data.EntryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.dgsw.doorlock.activity.Main.ID;
import static com.dgsw.doorlock.activity.Main.IP_ADDRESS;

/**
 * Created by kimji on 2017-12-17.
 */

public class GetEntryHTTPTask extends AsyncTask<EntryInfo, Integer, ArrayList<EntryInfo>> {

    private ArrayList<EntryInfo> entryInfos = new ArrayList<>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<EntryInfo> doInBackground(EntryInfo[] infos) {
        publishProgress(20);
        try {
            URL Url = new URL("http://" + IP_ADDRESS + ":8080/ENT_SYSTEM/webresources/com.dgsw.entinfo");//보낼 주소
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
            String buf = br.readLine();
            JSONObject jo;
            JSONArray ja = new JSONArray(buf);
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                if(jo.get("userId").toString().equals(ID)) {
                    StringBuilder date = new StringBuilder(jo.get("date").toString());
                    date.delete(10, date.length());

                    StringBuilder in_Time = new StringBuilder(jo.get("inTime").toString());
                    in_Time.delete(0, 11);
                    in_Time.delete(8, in_Time.length());

                    StringBuilder out_Time = new StringBuilder(jo.get("outTime").toString());
                    out_Time.delete(0, 11);
                    out_Time.delete(8, out_Time.length());

                    entryInfos.add(new EntryInfo(jo.get("userId").toString(), jo.get("name").toString(), date.toString(), in_Time.toString(), out_Time.toString(), jo.get("state").toString()));
                    Log.d("ATAGA", entryInfos.get(entryInfos.size()-1).toString() + " AABB");//TODO
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        Log.d("TAG_size", entryInfos.size()+" AACC");
        return entryInfos;
    }

    @Override
    protected void onProgressUpdate(Integer... params) {
    }

    @Override
    protected void onPostExecute(ArrayList<EntryInfo> result) {
        super.onPostExecute(result);
    }
}
