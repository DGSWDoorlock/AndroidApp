package com.dgsw.doorlock.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dgsw.doorlock.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText InputID = findViewById(R.id.InputID);
        EditText InputPW = findViewById(R.id.InputPW);

        Button LoginBtn = findViewById(R.id.LoginBtn);
        TextView RegisterBtn = findViewById(R.id.RegisterBtn);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
            }
        });
    }
}
