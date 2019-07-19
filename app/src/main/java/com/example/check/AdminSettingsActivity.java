package com.example.check;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    private DatabaseReference mDatabase;
    private Spinner mDefaultday_spinner;

    public static String DEFAULT_DAY = "default_day";

    public static String LOCATION = "Location";

    public static String THOUGHTS = "Thoughts";

    private ArrayList<String> location_array;

    private ArrayAdapter<String> adapter2;

    private ArrayList<String> thoughts_array;

    private ArrayAdapter<String> adapter3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);
        mEt_add_edit_location = (EditText) findViewById(R.id.et_add_edit_location);
        mbtn_edit_location = (Button) findViewById(R.id.btn_edit_location);
        mbtn_add_location = (Button) findViewById(R.id.btn_add_location);
        mbtn_delete_location = (Button) findViewById(R.id.btn_delete_location);

        mEt_add_edit_thought = (EditText) findViewById(R.id.et_add_edit_thought);
        mbtn_edit_thought = (Button) findViewById(R.id.btn_edit_thought);
        mbtn_add_thought = (Button) findViewById(R.id.btn_add_thought);
        mbtn_delete_location = (Button) findViewById(R.id.btn_delete_thought);

        mDefaultday_spinner = (Spinner) findViewById(R.id.spinner_default_day);

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(new String[]{"Sunday", "Monday"}));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mDefaultday_spinner.setAdapter(adapter);

        mDefaultday_spinner.setOnItemSelectedListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSpinner_location_admin_settings = (Spinner) findViewById(R.id.spinner_location_admin_settings);

        location_array = new ArrayList<>();

        updateLocationArray();

        //location_array = new ArrayList<String>(Arrays.asList(new String[]{"Thane", "Pune", "Andheri", "Bangalore", "Amsterdam"}));

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, location_array);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner_location_admin_settings.setAdapter(adapter2);

        mSpinner_location_admin_settings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("devang"+i);
                String old_loc = location_array.get(i);
                mEt_add_edit_location.setText(old_loc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //thought spinner
        mSpinner_thought_admin_settings = (Spinner) findViewById(R.id.spinner_thought_admin_settings);

        thoughts_array = new ArrayList<>();

        updateThoughtArray();

        //location_array = new ArrayList<String>(Arrays.asList(new String[]{"Thane", "Pune", "Andheri", "Bangalore", "Amsterdam"}));

        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, thoughts_array);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner_thought_admin_settings.setAdapter(adapter3);

        mSpinner_thought_admin_settings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setDefaultDay();

        mbtn_add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocation();
            }
        });

        mbtn_add_thought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addThought();
            }
        });

//        mbtn_delete_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleteLocation();
//            }
//        });

   }
    private void addLocation(){



        String locationdata= mEt_add_edit_location.getText().toString();
        try{
            DatabaseReference db_loc = mDatabase.child(LOCATION);
            locationdata = capitalize(locationdata);
            db_loc.child(locationdata).setValue(locationdata);
            Toast.makeText(getApplicationContext(), "Location added successfully", Toast.LENGTH_SHORT).show();
            updateLocationArray();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }

    }

    private void addThought(){



        String thoughtdata= mEt_add_edit_thought.getText().toString();
        try{
            DatabaseReference db_thought = mDatabase.child(THOUGHTS);
            db_thought.push().setValue(thoughtdata);
            Toast.makeText(getApplicationContext(), "Thought added successfully", Toast.LENGTH_SHORT).show();
            updateThoughtArray();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateLocationArray(){
        DatabaseReference db_loc = mDatabase.child(LOCATION).getRef();

        db_loc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                location_array.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String loc = snapshot.getValue(String.class);
                    location_array.add(loc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateThoughtArray(){
        DatabaseReference db_thought = mDatabase.child(THOUGHTS).getRef();

        db_thought.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                thoughts_array.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String thought = snapshot.getValue(String.class);
                    thoughts_array.add(thought);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //String daysArr[] = getResources().getStringArray(R.array.days_array);
        //String default_day = String.valueOf(daysArr[i]);

        mDatabase.child(DEFAULT_DAY).setValue(String.valueOf(i));


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    private void deleteLocation(){
//        String old_loc = mSpinner_location_admin_settings.getSelectedItem().toString();
//
//        System.out.println("xy"+old_loc);
//
//        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().child(LOCATION).child(old_loc);
//
//        dbNode.setValue(null);
//
//        location_array.remove(old_loc);
//
//    }

    private void setDefaultDay(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int i = Integer.parseInt(dataSnapshot.child(DEFAULT_DAY).getValue(String.class));

                mDefaultday_spinner.setSelection(i, true);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String capitalize(String s){
        s = s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();

        return s;
    }
}
