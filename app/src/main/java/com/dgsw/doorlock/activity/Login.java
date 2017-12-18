package com.dgsw.doorlock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.tool.Preference;
import com.dgsw.doorlock.tool.SecurityXor;

public class Login extends AppCompatActivity {
    private String id;
    private EditText InputID;
    private EditText InputPW;
    private Button LoginBtn;
    private TextView RegisterBtn;
    private CheckBox isSaveID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InputID = findViewById(R.id.InputID);
        InputPW = findViewById(R.id.InputPW);
        LoginBtn = findViewById(R.id.LoginBtn);
        isSaveID = findViewById(R.id.isSaveID);
        RegisterBtn = findViewById(R.id.RegisterBtn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent toMain = new Intent(Login.this, Main.class);
                Login.this.startActivity(toMain);
                finish();
            }
        });

        isSaveID.setChecked(new Preference(getApplicationContext()).getBoolean("isSaveID", false));

        if(new Preference(getApplicationContext()).getBoolean("isSaveID",false)) {
            SecurityXor securityXor = new SecurityXor();
            InputID.setText(securityXor.getSecurityXor(new Preference(getApplicationContext()).getString("ID", null), 777));
        }

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
            }
        });

        isSaveID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Preference(getApplicationContext()).putBoolean("isSaveID", isSaveID.isChecked());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (new Preference(getApplicationContext()).getBoolean("isSaveID", false)) {
            id=InputID.getText().toString();
            SecurityXor securityXor = new SecurityXor();
            new Preference(getApplicationContext()).putString("ID", securityXor.getSecurityXor(id, 777));
        }
    }
}
