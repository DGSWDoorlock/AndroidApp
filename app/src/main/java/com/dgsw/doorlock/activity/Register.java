package com.dgsw.doorlock.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.data.UserInfo;
import com.dgsw.doorlock.tool.RegisterHTTPTask;

import java.util.concurrent.ExecutionException;

public class Register extends AppCompatActivity {

    public static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    private UserInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idtext = findViewById(R.id.idText);
        final EditText pwtext = findViewById(R.id.pwtext);
        final EditText nametext = findViewById(R.id.nametext);
        final EditText comtext = findViewById(R.id.comtext);
        final EditText jobtext = findViewById(R.id.jobtext);

        Button register = findViewById(R.id.register);

        //FIXME !!
        final String phonenum = getPhoneNumber();
        register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nametext.getText().toString();
                final String id = idtext.getText().toString();
                final String pw = pwtext.getText().toString();
                final String com = comtext.getText().toString();
                final String job = jobtext.getText().toString();

                if (!(name.isEmpty() && id.isEmpty() && pw.isEmpty() && com.isEmpty() && job.isEmpty())) {
                    info = new UserInfo(name, id, pw, com, job, phonenum);
                    RegisterHTTPTask task = new RegisterHTTPTask(info);
                    task.execute();
                    try {
                        boolean success = task.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //TODO
                    //Toast.makeText(getApplicationContext(), "Granted", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO
                    //Toast.makeText(getApplicationContext(), "Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @SuppressLint("HardwareIds")
    public String getPhoneNumber() {
        TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                //필요한 이유
                Toast.makeText(this, "전화 번호를 가저오기 위한 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);
            return null;
        } else {
            //이미 허용 될 경우
            return mgr != null ? mgr.getLine1Number() : null;
        }
    }
}
