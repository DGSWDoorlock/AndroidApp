package com.dgsw.doorlock.tool.task;

import android.os.AsyncTask;
import android.util.Log;

import com.dgsw.doorlock.data.EntryInfo;
import com.dgsw.doorlock.data.NFCInfo;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.dgsw.doorlock.activity.Main.IP_ADDRESS;

/**
 * Created by kimji on 2017-12-17.
 */

public class NFCListHTTPTask extends AsyncTask<EntryInfo, Integer, ArrayList<NFCInfo>> {

    private String id;

    public NFCListHTTPTask(String id) {
        this.id = id;
    }

    private ArrayList<NFCInfo> nfcInfos = new ArrayList<>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<NFCInfo> doInBackground(EntryInfo[] infos) {
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
            org.json.JSONObject jo;
            org.json.JSONArray ja = new org.json.JSONArray(buf);
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                if("1".equals(jo.get("state").toString())&&id.equals(jo.get("userId").toString())) {
                    StringBuilder date = new StringBuilder(jo.get("date").toString());
                    date.delete(10, date.length());

                    StringBuilder in_Time = new StringBuilder(jo.get("inTime").toString());
                    in_Time.delete(0, 11);
                    in_Time.delete(8, in_Time.length());

                    StringBuilder out_Time = new StringBuilder(jo.get("outTime").toString());
                    out_Time.delete(0, 11);
                    out_Time.delete(8, out_Time.length());

                    nfcInfos.add(new NFCInfo(date.toString(), in_Time.toString(), out_Time.toString(), jo.get("rfid").toString()));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return nfcInfos;
    }

    @Override
    protected void onProgressUpdate(Integer... params) {

    }

    @Override
    protected void onPostExecute(ArrayList<NFCInfo> result) {
        super.onPostExecute(result);

    }
}
