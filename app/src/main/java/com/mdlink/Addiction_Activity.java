package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Addiction_Activity extends AppCompatActivity {

    ListView listView;
    String[] addiction={"Weight loss Counseling","Behavioral Pain Managment","Drug and Alcohol Dependence","Food Addiction","Medical Addiction","Opioid Addiction","Smoking Cessation"};
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addiction_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView= (ListView) findViewById(R.id.addiction_list);
        tv= (TextView) findViewById(R.id.addiction_btn);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Addiction_Activity.this,android.R.layout.simple_expandable_list_item_1,addiction);
        listView.setAdapter(arrayAdapter);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addiction_Activity.this,Patient_portal_Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
