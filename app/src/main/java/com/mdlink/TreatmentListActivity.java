package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mdlink.adapter.GeneralListAdapter;
import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.util.Constants;

import java.util.ArrayList;

public class TreatmentListActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView txtBookAppointmentTL;
    RecyclerView rvBookAppointmentTL;
    GeneralListAdapter generalListAdapter;
    SharedPreferenceManager sharedPreferenceManager;
    String val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_list);
        sharedPreferenceManager = new SharedPreferenceManager(this);

        if(null != getIntent()) {
            val = getIntent().getStringExtra(Constants.TYPE);
        }
        initToolbar(val);
        initViews();
    }

    private void initToolbar(String val) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(val, R.color.colorAccent);
    }

    private void initViews(){
        txtBookAppointmentTL = findViewById(R.id.txtBookAppointmentTL);
        rvBookAppointmentTL = findViewById(R.id.rvBookAppointmentTL);

        ArrayList<String> stringArrayList = new ArrayList<>();
        if(null != getIntent()){
            String val = getIntent().getStringExtra(Constants.TYPE);
            switch (val){
                case Constants.URGENTCARE:
                    stringArrayList = new ArrayList<>(Constants.URGENT_LIST);
                    break;
                case Constants.MEDICAL_ADVICE:
                    stringArrayList = new ArrayList<>(Constants.MEDICAL_ADVICE_LIST);
                    break;
                case Constants.LABS_AND_SCREEN:
                    stringArrayList = new ArrayList<>(Constants.LABS_AND_SCREENING);
                    break;
                case Constants.LABEL_ADDICTION:
                    stringArrayList = new ArrayList<>(Constants.ADDICTION);
                    break;
                case Constants.LABEL_DEPRESSION:
                    stringArrayList = new ArrayList<>(Constants.DEPRESSION_AND_MOOD);
                    break;
                case Constants.LABEL_THERAPY:
                    stringArrayList = new ArrayList<>(Constants.THERAPY);
                    break;
            }
        }

        generalListAdapter = new GeneralListAdapter(this,
                stringArrayList, "", new GeneralListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(String appointmentListResponseDetails) {
            }
        });
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        rvBookAppointmentTL.addItemDecoration(itemDecorator);
        rvBookAppointmentTL.setAdapter(generalListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.txtBookAppointmentTL:
                if(TextUtils.isEmpty(sharedPreferenceManager.getStringData(Constants.USER_ID))){
                    finish();
                    Intent intent = new Intent(TreatmentListActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    finish();
                    Intent intent = new Intent(TreatmentListActivity.this, BookAppointmentActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
