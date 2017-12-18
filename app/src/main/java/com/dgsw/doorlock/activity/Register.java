package com.dgsw.doorlock.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.data.UserInfo;
import com.dgsw.doorlock.tool.RegisterHTTP;

public class Register extends AppCompatActivity {
    UserInfo info;
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

        TelephonyManager mgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //FIXME !!
        final String phonenum = mgr.getLine1Number();
        register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nametext.getText().toString();
                final String id = idtext.getText().toString();
                final String pw = pwtext.getText().toString();
                final String com = comtext.getText().toString();
                final String job = jobtext.getText().toString();

                if(!(name.isEmpty() && id.isEmpty() && pw.isEmpty() && com.isEmpty() && job.isEmpty())) {
                    info = new UserInfo(name, id, pw, com, job, phonenum);
                    RegisterHTTP example = new RegisterHTTP(info);
                    boolean success = example.connect();
                }


            }
        });
    }

}
