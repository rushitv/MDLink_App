package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Therapy_Activity extends AppCompatActivity {


    String[] therapy={"Stress And Anxiety","trauma and Loss","Anger Management","Behavioral Therapy","Conitive Behavioral Therapy","Dialectical Behavioral Therapy","Emotional Focused Therapy","Post Cardiac Event Counselling","Psychodynamic Therapy"};
    ListView listView;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView= (ListView) findViewById(R.id.therapy_list);
        tv= (TextView) findViewById(R.id.therapy_btn);




        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Therapy_Activity.this,android.R  .layout.simple_list_item_1,therapy);
        listView.setAdapter(arrayAdapter);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Therapy_Activity.this,Patient_portal_Activity.class);
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
