package com.dgsw.doorlock.tool;

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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.dgsw.doorlock.activity.Main.IP_ADDRESS;

/**
 * Created by kimji on 2017-12-17.
 */

public class GetRFIDTask extends AsyncTask<EntryInfo, Integer, String> {

    private final String id;

    private String RFID;

    public GetRFIDTask(String id) {
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(EntryInfo[] infos) {
        try {
            URL Url = new URL(" http://"+ IP_ADDRESS +":8080/ENT_SYSTEM/webresources/com.dgsw.entinfo/"+ URLEncoder.encode(id, "UTF-8"));//받을 주소 + ID
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();//연결해줄 Connection
            conn.setRequestMethod("GET");//POST 형식
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);//입력 가능

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
            String line=br.readLine();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
            RFID = jsonObject.get("rfid").toString();
            br.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return RFID;
    }

    @Override
    protected void onProgressUpdate(Integer... params) {

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }
}
