package com.example.check;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private String password="admin123";

    private EditText mEt_password;
    private Button mBtn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mEt_password = (EditText) findViewById(R.id.et_password);

        mBtn_login = (Button)findViewById(R.id.btn_login);

        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(mEt_password)){
                    Toast.makeText(getApplicationContext(), "Enter password plz", Toast.LENGTH_SHORT).show();
                }else{
                    if(mEt_password.getText().toString().equals(password)){
                        Intent adminintent = new Intent(SettingsActivity.this, AdminActivity.class);
                        startActivity(adminintent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
