package com.example.check;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AdminSettingsActivity extends AppCompatActivity {

    private EditText mEt_add_edit_location;
    private Spinner mSpinner_location_admin_settings;
    private Button mbtn_edit_location;
    private Button mbtn_add_location;
    private Button mbtn_delete_location;

    private EditText mEt_add_edit_thought;
    private Spinner mSpinner_thought_admin_settings;
    private Button mbtn_edit_thought;
    private Button mbtn_add_thought;
    private Button mbtn_delete_thought;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);
        mEt_add_edit_location=(EditText)findViewById(R.id.et_add_edit_location);
        mbtn_edit_location=(Button) findViewById(R.id.btn_edit_location);
        mbtn_add_location=(Button) findViewById(R.id.btn_add_location);
        mbtn_delete_location=(Button) findViewById(R.id.btn_delete_location);

        mEt_add_edit_thought=(EditText)findViewById(R.id.et_add_edit_thought);
        mbtn_edit_thought=(Button) findViewById(R.id.btn_edit_thought);
        mbtn_add_thought=(Button) findViewById(R.id.btn_add_thought);
        mbtn_delete_location=(Button) findViewById(R.id.btn_delete_thought);
    }
}
