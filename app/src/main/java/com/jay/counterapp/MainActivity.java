package com.jay.counterapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Jap> query = realm.where(Jap.class);
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        RealmResults<Jap> result = realm.where(Jap.class).equalTo("date", date.toString()).findAll();

        Random rnd = new Random();
        Collection<FitChartValue> values = new ArrayList<>();
        for (Jap res : result) {
            values.add(new FitChartValue(res.getJapCount(), Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))));
        }

        final FitChart fitChart = (FitChart)findViewById(R.id.fitChart);
        fitChart.setMinValue(0f);
        fitChart.setMaxValue(10000f);
        fitChart.setValues(values);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JapEntryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
