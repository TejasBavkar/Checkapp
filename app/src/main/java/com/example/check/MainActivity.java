package com.example.check;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,AdapterView.OnItemSelectedListener {

    private DatabaseReference mDatabase;

    private TextView mTv_thought;
    private Button mBtn_nextsession;
    private Button mBtn_date_user;
    private TextView mTv_team1;
    private TextView mTv_timing1;
    private TextView mTv_instructions1;
    private ImageButton mImgbtn_settings;
    private TextView mTv_team2;
    private TextView mTv_timing2;
    private TextView mTv_instructions2;

    private String location="";

    private Spinner location_spinner;

    private String LOCATION = "Location";
    private String DATE = "Date";

    private String TIMING1 = "Timing1";
    private String TEAM1 = "Team1";
    private String INSTRUCTIONS1 = "Instructions1";

    private String TIMING2 = "Timing2";
    private String TEAM2 = "Team2";
    private String INSTRUCTIONS2 = "Instructions2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv_thought=(TextView)findViewById(R.id.tv_thought);

        mBtn_nextsession=(Button) findViewById(R.id.btn_nextsession);

        mBtn_date_user=(Button) findViewById(R.id.btn_date_user);

        mTv_team1=(TextView)findViewById(R.id.tv_team1);
        mTv_timing1=(TextView)findViewById(R.id.tv_timing1);
        mTv_instructions1=(TextView)findViewById(R.id.tv_instructions1);
        mTv_team2=(TextView)findViewById(R.id.tv_team2);
        mTv_timing2=(TextView)findViewById(R.id.tv_timing2);
        mTv_instructions2=(TextView)findViewById(R.id.tv_instructions2);

        mImgbtn_settings=(ImageButton)findViewById(R.id.imgbtn_settings);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mImgbtn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings_intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settings_intent);
            }
        });

        final DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this,MainActivity.this,2010,01,01);
        mBtn_date_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        location_spinner = (Spinner)findViewById(R.id.spinner_location_user);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.location_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        location_spinner.setAdapter(adapter);

        location_spinner.setOnItemSelectedListener(this);

        location_spinner.setSelection(0,true);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthindex, int day) {
        mBtn_date_user.setText(day+"-"+(monthindex+1)+"-"+year);
        retrieveData();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String locationArr[] = getResources().getStringArray(R.array.location_array);
        location = String.valueOf(locationArr[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void retrieveData(){

        final String loc = location_spinner.getSelectedItem().toString();
        String date = mBtn_date_user.getText().toString();

        final String t[] = new String[2];

        try{
            final DatabaseReference databs = mDatabase.child(loc).child(date);
            DatabaseReference db = databs.getRef();

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    t[0]= dataSnapshot.child(TEAM1).getValue(String.class);

                    t[1] = dataSnapshot.child(TEAM2).getValue(String.class);

                    mTv_team1.setText(t[0]);
                    mTv_team2.setText(t[1]);

                    DatabaseReference db_team1 = databs.child(t[0]).getRef();

                    db_team1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String timing = dataSnapshot.child(TIMING1).getValue(String.class);
                            String instructions = dataSnapshot.child(INSTRUCTIONS1).getValue(String.class);

                            mTv_timing1.setText(timing);
                            mTv_instructions1.setText(instructions);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("abc", databaseError.getMessage().toString());
                            //System.out.println(databaseError.getMessage().toString());
                        }
                    });

                    DatabaseReference db_team2 = databs.child(t[1]).getRef();

                    db_team2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String timing = dataSnapshot.child(TIMING2).getValue(String.class);
                            String instructions = dataSnapshot.child(INSTRUCTIONS2).getValue(String.class);

                            mTv_timing2.setText(timing);
                            mTv_instructions2.setText(instructions);



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("abc", databaseError.getMessage().toString());
                            // System.out.println(databaseError.getMessage().toString());

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Toast.makeText(getApplicationContext(), "Successfully retrieved", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
