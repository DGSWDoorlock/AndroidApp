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

public class Login extends AppCompatActivity {
    private boolean save_check;
    private String id = null;
    private EditText InputID=null;
    private EditText InputPW=null;
    private Button LoginBtn=null;
    private TextView RegisterBtn=null;
    private CheckBox save_id=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InputID = findViewById(R.id.InputID);
        InputPW = findViewById(R.id.InputPW);
        LoginBtn = findViewById(R.id.LoginBtn);
        save_id = findViewById(R.id.save_id);
        RegisterBtn = findViewById(R.id.RegisterBtn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(new Preference(getApplicationContext()).getBoolean("SAVE_ID",false))
            InputID.setText(new Preference(getApplicationContext()).getString("ID",null));

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                Login.this.startActivity(registerIntent);
            }
        });

        save_id.setChecked(new Preference(getApplicationContext()).getBoolean("SAVE_ID", false));

        save_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_check = save_id.isChecked();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        new Preference(getApplicationContext()).putBoolean("SAVE_ID", save_check);
        if (save_check) {
            id=InputID.getText().toString();
            new Preference(getApplicationContext()).putString("ID", id);
        }
    }
}
