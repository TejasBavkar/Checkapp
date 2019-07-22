package com.example.check;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    private Button mBtn_date_admin;
    private String location = "Thane";

    private EditText mEt_team1;
    private Button mBtn_timing1;
    private EditText mEt_instructions1;

    private EditText mEt_team2;
    private Button mBtn_timing2;
    private EditText mEt_instructions2;

    private Spinner location_spinner;

    private ImageButton mImgbtn_settings_admin;

    private Button mBtn_save;
    private DatabaseReference mDatabase;
    private String LOCATION = "Location";
    private String DATE = "Date";

    private String TIMING1 = "Timing1";
    private String TEAM1 = "Team1";
    private String INSTRUCTIONS1 = "Instructions1";

    private String TIMING2 = "Timing2";
    private String TEAM2 = "Team2";
    private String INSTRUCTIONS2 = "Instructions2";

    private Boolean btn_timing1 = false;
    private Boolean btn_timing2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mBtn_date_admin = (Button) findViewById(R.id.btn_date_admin);
        mEt_team1 = (EditText) findViewById(R.id.et_team1);
        mBtn_timing1 = (Button) findViewById(R.id.btn_timing1);
        mEt_instructions1 = (EditText) findViewById(R.id.et_instructions1);
        mEt_team2 = (EditText) findViewById(R.id.et_team2);
        mBtn_timing2 = (Button) findViewById(R.id.btn_timing2);
        mEt_instructions2 = (EditText) findViewById(R.id.et_instructions2);

        mBtn_save = (Button) findViewById(R.id.btn_save);

        mImgbtn_settings_admin=(ImageButton)findViewById(R.id.imgbtn_settings_admin);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mImgbtn_settings_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent admin_settings_intent = new Intent(AdminActivity.this, AdminSettingsActivity.class);
                startActivity(admin_settings_intent);
            }
        });

        Date newdate = new Date();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("MM");
        SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy");
        String newday=formatter1.format(newdate);
        String newmonth=formatter2.format(newdate);
        String newyear=formatter3.format(newdate);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(AdminActivity.this, AdminActivity.this, Integer.parseInt(newyear), Integer.parseInt(newmonth)-1, Integer.parseInt(newday));

        mBtn_date_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        final TimePickerDialog timePickerDialog1 = new TimePickerDialog(AdminActivity.this, AdminActivity.this, 12, 0, false);
        mBtn_timing1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog1.show();
                btn_timing1 = true;
                btn_timing2 = false;
            }
        });
        final TimePickerDialog timePickerDialog2 = new TimePickerDialog(AdminActivity.this, AdminActivity.this, 12, 0, false);
        mBtn_timing2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog2.show();
                btn_timing2 = true;
                btn_timing1 = false;
            }
        });
        mBtn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
                //addquote
                //addThought();
            }
        });


        location_spinner = (Spinner) findViewById(R.id.spinner_location_admin);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.location_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        location_spinner.setAdapter(adapter);

        location_spinner.setOnItemSelectedListener(this);

        location_spinner.setSelection(0, true);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month_index, int day) {
        mBtn_date_admin.setText(String.format("%02d", day) + "-" + String.format("%02d", (month_index+1)) + "-" + year);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {

        if(btn_timing1 == true){
            mBtn_timing1.setText(String.format("%02d", hour) + ":" + String.format("%02d", minutes));
        }else if(btn_timing2 == true){
            mBtn_timing2.setText(String.format("%02d", hour) + ":" + String.format("%02d", minutes));
        }else{

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String locationArr[] = getResources().getStringArray(R.array.location_array);
        location = String.valueOf(locationArr[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
//    public  void addThought(){
//        String thoughtdata= mEt_thought.getText().toString();
//        try{
//            DatabaseReference db_thought = mDatabase.child("Thoughts");
//            db_thought.push().setValue(thoughtdata);
//        }
//        catch (Exception e){
//            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
//        }
//
//    }
    public void addData() {

        String loc = location_spinner.getSelectedItem().toString();
        String date = mBtn_date_admin.getText().toString();
        String timing1 = mBtn_timing1.getText().toString();
        String timing2 = mBtn_timing2.getText().toString();
        String team1=mEt_team1.getText().toString();
        String team2=mEt_team2.getText().toString();
        String instructions1=mEt_instructions1.getText().toString();
        String instructions2=mEt_instructions2.getText().toString();



        try{
            DatabaseReference db_location = mDatabase.child(loc);
            DatabaseReference db_date = db_location.child(date);

            db_date.child(TEAM1).setValue(team1);

            DatabaseReference db_team1 = db_date.child(team1);
            db_team1.child(TIMING1).setValue(timing1);
            db_team1.child(INSTRUCTIONS1).setValue(instructions1);

            db_date.child(TEAM2).setValue(team2);

            DatabaseReference db_team2 = db_date.child(team2);
            db_team2.child(TIMING2).setValue(timing2);
            db_team2.child(INSTRUCTIONS2).setValue(instructions2);

            Toast.makeText(getApplicationContext(), "Successfully saved", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }
}

