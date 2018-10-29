package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Urgent_Care extends AppCompatActivity {


    String[] urgentcare={"Allergies","Back Pain","Bronchitis and Pneumonia","Cellulitis And Skin Infections","Colds,Coughs,Congestion","Conunctivitis","Headache/Migraine","Influenza","Rashes And Skin Conditions","Sinus Infections","Allergy & Asthma","Obesity","High Blood Pressure","High Cholesterol ","Metabolic Syndrome","Pre-Diabetes/Diabetes","Stress Management","Thyroid","Urinary tract Infections","Vaginal and Yeast Infections","Sexually Transmitted Diseases","Vomiting and diarrhea","Sport Injuries","Sprains and Bruises","Eractile Dysfunctions","Acne","Prescription Renewal"};
    ListView listView;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent__care);

        listView= (ListView) findViewById(R.id.urgent_list);
        tv= (TextView) findViewById(R.id.urgent_btn);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Urgent_Care.this,Patient_portal_Activity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Urgent_Care.this,android.R.layout.simple_expandable_list_item_1,urgentcare);
        listView.setAdapter(arrayAdapter);

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
