package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Medical_Advice extends AppCompatActivity {


    String[] urgentcare={"Am I experiencing a medical emergency?","Can I Take these medications Together?","Can you help me when my doctor is out of town?","Do i need a Specialist?","Is this a side effect of a medication or another issu?","Night And Weekend Medical questions ","Should i go to the ER or Urgent Care?","What can i expect with my new medication ?"};
    ListView listView;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical__advice);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView= (ListView) findViewById(R.id.medical_list);
        tv= (TextView) findViewById(R.id.medical_btn);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Medical_Advice.this,android.R.layout.simple_expandable_list_item_1,urgentcare);
        listView.setAdapter(arrayAdapter);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Medical_Advice.this,Patient_portal_Activity.class);
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
