package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.splash.SplashActivity;
import com.mdlink.util.Constants;

import static com.mdlink.util.Constants.INVALID;

public class PatientPortalActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private SharedPreferenceManager sharedPreferenceManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView tvThree,tvThree2,tvThree23,tvThree234,tvThree2345,tvThree23456, tvHeaderNavigation;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__portal__data);
        initToolbar();
        initViews();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_doctorregistration), R.color.colorAccent);
    }

    private void initViews() {
        sharedPreferenceManager = new SharedPreferenceManager(this);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_viewPatientPortal);
        drawerLayout = findViewById(R.id.drawer_patient_layout);
        setUpToolbar(toolbar, INVALID);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        tvThree = findViewById(R.id.tvThree);
        tvThree.setOnClickListener(this);
        tvThree2 = findViewById(R.id.tvOne2);
        tvThree2.setOnClickListener(this);
        tvThree23 = findViewById(R.id.tvOne23);
        tvThree23.setOnClickListener(this);
        tvThree234 = findViewById(R.id.tvOne234);
        tvThree234.setOnClickListener(this);
        tvThree2345 = findViewById(R.id.tvOne2345);
        tvThree2345.setOnClickListener(this);
        tvThree23456 = findViewById(R.id.tvOne23456);
        tvThree23456.setOnClickListener(this);

        headerView = navigationView.getHeaderView(0);
        tvHeaderNavigation = headerView.findViewById(R.id.text_header_name);
        tvHeaderNavigation.setText(sharedPreferenceManager.getStringData(Constants.NAME));

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        finishAffinity();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient__portal__data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.pat_profile) {
            Intent intent=new Intent(PatientPortalActivity.this,Patient_Profile.class);
            startActivity(intent);

        } else if (id == R.id.book_appoinment) {

            Intent intent=new Intent(PatientPortalActivity.this,BookAppointmentActivity.class);
            startActivity(intent);


        }else if (id == R.id.ScheduledAppointmentList) {
            Intent intentScheduleAppointment = new Intent(this,ScheduleAppointmentActivity.class);
            intentScheduleAppointment.putExtra(Constants.TYPE, Constants.PATIENT);
            startActivity(intentScheduleAppointment);

        } else if (id == R.id.aboutamne) {

            Intent intent=new Intent(PatientPortalActivity.this,AboutUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contact) {

            Intent intent=new Intent(PatientPortalActivity.this,ContactUs_Activity.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            //clear data
            sharedPreferenceManager.ClearData();
            Intent intent=new Intent(PatientPortalActivity.this,SplashActivity.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.tvThree:
                Intent intent=new Intent(PatientPortalActivity.this,Urgent_Care.class);
                startActivity(intent);
                break;
            case R.id.tvThree2:
                Intent intent2=new Intent(PatientPortalActivity.this,Medical_Advice.class);
                startActivity(intent2);
                break;
            case R.id.tvThree23:
                Intent intent3=new Intent(PatientPortalActivity.this,Labs_and_Screening.class);
                startActivity(intent3);
                break;
            case R.id.tvThree234:
                Intent intent4=new Intent(PatientPortalActivity.this,Addiction_Activity.class);
                startActivity(intent4);
                break;
            case R.id.tvThree2345:
                Intent intent5=new Intent(PatientPortalActivity.this,Addiction_Activity.class);
                startActivity(intent5);
                break;
            case R.id.tvThree23456:
                Intent intent6=new Intent(PatientPortalActivity.this,Mood_Activity.class);
                startActivity(intent6);
                break;
        }
    }
}
