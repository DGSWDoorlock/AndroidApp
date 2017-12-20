package com.dgsw.doorlock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.tool.LoginData;
import com.dgsw.doorlock.tool.Preference;
import com.dgsw.doorlock.tool.SecurityXor;
import com.dgsw.doorlock.tool.task.LoginTask;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {
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
                boolean isCorrectIDPW = false;
                String name = "", id = "";
                    LoginTask loginTask = new LoginTask();
                loginTask.execute(new LoginData(InputID.getText().toString(), InputPW.getText().toString()));
                try {
                    Object[] result=loginTask.get();
                    isCorrectIDPW = (Boolean) result[0];
                    name = (String) result[1];
                    id = (String) result[2];
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }


                if (isCorrectIDPW) {
                    Intent intent = new Intent(Login.this, Main.class);
                    intent.putExtra("name", name);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
                } else {
                    //TODO
                    Snackbar.make(findViewById(R.id.layout), "실패", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        isSaveID.setChecked(new Preference(getApplicationContext()).getBoolean("isSaveID", false));

        if (new Preference(getApplicationContext()).getBoolean("isSaveID", false)) {
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
            SecurityXor securityXor = new SecurityXor();
            new Preference(getApplicationContext()).putString("ID", securityXor.getSecurityXor(InputID.getText().toString(), 777));
        } else {
            new Preference(getApplicationContext()).remove("ID");
        }
    }
}
