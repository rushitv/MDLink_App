package com.mdlinkhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mdlinkhealth.util.Constants;

import static com.mdlinkhealth.util.Constants.INVALID;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Toolbar toolbar;
    TextView tvThree, tvThree2, tvThree23, tvThree234, tvThree2345, tvThree23456;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        setUpToolbar(toolbar, INVALID);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        tvThree = findViewById(R.id.tvThree);
        tvThree.setOnClickListener(this);
        tvThree2 = findViewById(R.id.tvThree2);
        tvThree2.setOnClickListener(this);
        tvThree23 = findViewById(R.id.tvThree23);
        tvThree23.setOnClickListener(this);
        tvThree234 = findViewById(R.id.tvThree234);
        tvThree234.setOnClickListener(this);
        tvThree2345 = findViewById(R.id.tvThree2345);
        tvThree2345.setOnClickListener(this);
        tvThree23456 = findViewById(R.id.tvThree23456);
        tvThree23456.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);


        } else if (id == R.id.doctor_portal) {

            DoctorRegistrationActivity.start(this);

            /*Intent intent = new Intent(MainActivity.this, DoctorRegistrationActivity.class);
            startActivity(intent);*/

        } else if (id == R.id.patient_portal) {
            Intent intent = new Intent(MainActivity.this, PatientRegistrationActivity.class);
            startActivity(intent);

        } else if (id == R.id.contactus) {
            Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.aboutus) {
            /*Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);*/
            AboutUsActivity.start(this);

        } else if (id == R.id.userguide) {
            Intent intent = new Intent(MainActivity.this, UserGuideActivity.class);
            startActivity(intent);

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvThree:
                OpenTreatmentListActivity(this, TreatmentListActivity.class, Constants.URGENTCARE);
                break;
            case R.id.tvThree2:
                OpenTreatmentListActivity(this, TreatmentListActivity.class, Constants.MEDICAL_ADVICE);
                break;
            case R.id.tvThree23:
                OpenTreatmentListActivity(this, TreatmentListActivity.class, Constants.LABS_AND_SCREEN);
                break;
            case R.id.tvThree234:
                OpenTreatmentListActivity(this, TreatmentListActivity.class, Constants.LABEL_ADDICTION);
                break;
            case R.id.tvThree2345:
                OpenTreatmentListActivity(this, TreatmentListActivity.class, Constants.LABEL_DEPRESSION);
                break;
            case R.id.tvThree23456:
                OpenTreatmentListActivity(this, TreatmentListActivity.class, Constants.LABEL_THERAPY);
                break;
        }
    }

}
