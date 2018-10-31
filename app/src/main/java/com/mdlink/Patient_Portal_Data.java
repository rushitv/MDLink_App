package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mdlink.preferences.SharedPreferenceManager;
import com.mdlink.splash.SplashActivity;

public class Patient_Portal_Data extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferenceManager sharedPreferenceManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__portal__data);

        initToolbar();

        sharedPreferenceManager = new SharedPreferenceManager(this);
        navigationView = findViewById(R.id.nav_viewPatientPortal);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_doctorregistration), R.color.colorAccent);
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
            Intent intent=new Intent(Patient_Portal_Data.this,Patient_Profile.class);
            startActivity(intent);

        } else if (id == R.id.book_appoinment) {

            Intent intent=new Intent(Patient_Portal_Data.this,Book_Appoinment.class);
            startActivity(intent);


        } else if (id == R.id.aboutamne) {

            Intent intent=new Intent(Patient_Portal_Data.this,Scheduled_Appoinment_Doctor.class);
            startActivity(intent);

        } else if (id == R.id.contact) {

            Intent intent=new Intent(Patient_Portal_Data.this,ContactUs_Activity.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            //clear data
            sharedPreferenceManager.ClearData();
            Intent intent=new Intent(Patient_Portal_Data.this,SplashActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
