package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Labs_and_Screening extends AppCompatActivity {

    TextView tv;
    String[] labs={"STDs","Anemia","Carsiovascular","Chronic disease","Depression","Diabetes","Drug levels","Fatigue","Fertility","Thyroid Disease","Vitamin deficiencies"};
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labs_and__screening);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView= (ListView) findViewById(R.id.labs_list);
        tv= (TextView) findViewById(R.id.lab_btn);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Labs_and_Screening.this,android.R.layout.simple_expandable_list_item_1,labs);
        listView.setAdapter(arrayAdapter);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Labs_and_Screening.this,Patient_portal_Activity.class);
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
