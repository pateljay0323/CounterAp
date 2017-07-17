package com.jay.counterapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        Date date = new Date(dateFormat.format(cal.getTime()));
//        query.equalTo("date", date);
        RealmResults<Jap> result = query.findAll();
        for (Jap res : result){
            Toast.makeText(MainActivity.this,"Id : "+res.getId()+"\nDate : "+res.getDate()+"\nJap Count : "+res.getJapCount(),Toast.LENGTH_SHORT).show();
        }

//        final FitChart fitChart = (FitChart)findViewById(R.id.fitChart);
//        fitChart.setMinValue(0f);
//        fitChart.setMaxValue(100f);
        Random rnd = new Random();
        Collection<FitChartValue> values = new ArrayList<>();
//        values.add(new FitChartValue(30, getResources().getColor(android.R.color.holo_red_light)));
        values.add(new FitChartValue(30, Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))));
//        values.add(new FitChartValue(20, getResources().getColor(android.R.color.holo_blue_dark)));
        values.add(new FitChartValue(20, Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))));
//        values.add(new FitChartValue(15, getResources().getColor(android.R.color.holo_green_dark)));
        values.add(new FitChartValue(15, Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))));
//        values.add(new FitChartValue(10, getResources().getColor(android.R.color.holo_orange_dark)));
        values.add(new FitChartValue(10, Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))));
        final FitChart fitChart = (FitChart)findViewById(R.id.fitChart);
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
}
