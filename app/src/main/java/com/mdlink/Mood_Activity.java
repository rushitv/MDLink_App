package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Mood_Activity extends AppCompatActivity {

    TextView tv;
    ListView listView;
    String[] mood = {"Bipolar Disorder", "Chronic Depression", "Dysthymia", "Fatigue", "Major Depression", "Mood Swings", "Postpartum Depression"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.mood_list);
        tv = (TextView) findViewById(R.id.mood_btn);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Mood_Activity.this, android.R.layout.simple_list_item_1, mood);
        listView.setAdapter(arrayAdapter);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mood_Activity.this, Patient_portal_Activity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}