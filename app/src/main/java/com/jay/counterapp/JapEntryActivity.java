package com.jay.counterapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class JapEntryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText dateEditText;
    private EditText japCountEditText;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jap_entry);

        realm = Realm.getDefaultInstance();

        dateEditText = (EditText) findViewById(R.id.date_editText);
        japCountEditText = (EditText) findViewById(R.id.jap_count_editText);
        Button dateButton = (Button) findViewById(R.id.date_button);
        Button fetchRealmButton = (Button) findViewById(R.id.fetch_realm);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        JapEntryActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        // Show a datepicker when the dateButton is clicked
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateEditText.getText().toString().trim().isEmpty()){
                    dateEditText.setError("Please select date!");
                } else if (japCountEditText.getText().toString().trim().isEmpty()){
                    japCountEditText.setError("Please enter jap count!");
                } else {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Jap jap = realm.createObject(Jap.class, getNextKey());
                            Date date = null;
                            try{
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // here set the pattern as you date in string was containing like date/month/year
                                date = sdf.parse(dateEditText.getText().toString().trim());
                            }catch(ParseException ex){
                                Toast.makeText(JapEntryActivity.this,"Date parsing failed",Toast.LENGTH_SHORT).show();
                                dateEditText.setError("Invalid Date!");
                                return;
                            }
                            int japCount = 0;
                            try {
                                japCount = Integer.parseInt(japCountEditText.getText().toString());
                            } catch(NumberFormatException nfe) {
                                Toast.makeText(JapEntryActivity.this,"Jap count parsing failed",Toast.LENGTH_SHORT).show();
                                japCountEditText.setError("Invalid jap count!");
                                return;
                            }
                            jap.setDate(date);
                            jap.setJapCount(japCount);
                            Toast.makeText(JapEntryActivity.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        fetchRealmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RealmQuery<Jap> query = realm.where(Jap.class);
//                RealmResults<Jap> result = query.findAll();
//                for (Jap res : result){
//                    Toast.makeText(JapEntryActivity.this,"Date : "+res.getDate()+"\nJap Count : "+res.getJapCount(),Toast.LENGTH_SHORT).show();
//                }
                Intent intent = new Intent(JapEntryActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        dateEditText.setText(date);
    }

    public long getNextKey()
    {
        try {
            return realm.where(Jap.class).max("id").longValue() + 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
