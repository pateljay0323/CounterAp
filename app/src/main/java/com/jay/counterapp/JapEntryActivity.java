package com.jay.counterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import io.realm.Realm;

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
                    dateEditText.setError(null);
                    japCountEditText.setError("Please enter jap count!");
                } else {
                    japCountEditText.setError(null);
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Jap jap = realm.createObject(Jap.class, getNextKey());
                            int japCount;
                            try {
                                japCount = Integer.parseInt(japCountEditText.getText().toString());
                            } catch(NumberFormatException nfe) {
                                Toast.makeText(JapEntryActivity.this,"Jap count parsing failed",Toast.LENGTH_SHORT).show();
                                japCountEditText.setError("Invalid jap count!");
                                return;
                            }
                            jap.setDate(dateEditText.getText().toString().trim());
                            jap.setJapCount(japCount);
                            Toast.makeText(JapEntryActivity.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (++monthOfYear) + "-" + year;
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
