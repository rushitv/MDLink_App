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

public class DoctorPortalActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private SharedPreferenceManager sharedPreferenceManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView tvThree, tvThree2, tvThree23, tvThree234, tvThree2345, tvThree23456, tvHeaderNavigation;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__portel__data);

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
        navigationView = findViewById(R.id.nav_doctor_view);
        drawerLayout = findViewById(R.id.drawer_doctor_layout);
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
        getMenuInflater().inflate(R.menu.doctor__portel__data, menu);
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

        if (id == R.id.profile) {
            Intent intent = new Intent(DoctorPortalActivity.this, DoctorProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.Scheduled_app) {
            Intent intent = new Intent(DoctorPortalActivity.this, ScheduleAppointmentActivity.class);
            startActivity(intent);
        } else if (id == R.id.medical_checkout) {
            Intent intent = new Intent(DoctorPortalActivity.this, Medical_CheckOut_Doctor.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent = new Intent(DoctorPortalActivity.this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.contact) {
            Intent intent = new Intent(DoctorPortalActivity.this, ContactUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            sharedPreferenceManager.ClearData();
            Intent intent = new Intent(DoctorPortalActivity.this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
