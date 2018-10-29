package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Doctor_Portel_Data extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView urgentcare,medicaladvice,labs,addiction,mood,therapy;


    View headerView;
    TextView tvHeaderNavigation;
    String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__portel__data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().getExtras() != null){
            UserName = getIntent().getStringExtra("UserName");
        }

        urgentcare= (TextView) findViewById(R.id.con_urgantcare);
        medicaladvice= (TextView) findViewById(R.id.con_medical);
        labs= (TextView) findViewById(R.id.con_labs);
        addiction= (TextView) findViewById(R.id.con_addiction);
        mood= (TextView) findViewById(R.id.con_mood);
        therapy= (TextView) findViewById(R.id.con_therapy);


        urgentcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Doctor_Portel_Data.this,Urgent_Care.class);
                startActivity(intent);
            }
        });
        medicaladvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Doctor_Portel_Data.this,Medical_Advice.class);
                startActivity(intent);
            }
        });
        labs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Doctor_Portel_Data.this,Labs_and_Screening.class);
                startActivity(intent);
            }
        });
        addiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Doctor_Portel_Data.this,Addiction_Activity.class);
                startActivity(intent);
            }
        });


        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Doctor_Portel_Data.this,Mood_Activity.class);
                startActivity(intent);
            }
        });







        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        tvHeaderNavigation = headerView.findViewById(R.id.tvDrawerHeader);
        tvHeaderNavigation.setText(UserName);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Intent intent=new Intent(Doctor_Portel_Data.this,Doctor_Profile.class);
            startActivity(intent);

        } else if (id == R.id.Scheduled_app) {
            Intent intent=new Intent(Doctor_Portel_Data.this,Scheduled_Appoinment_Doctor.class);
            startActivity(intent);

        } else if (id == R.id.medical_checkout) {
            Intent intent=new Intent(Doctor_Portel_Data.this,Medical_CheckOut_Doctor.class);
            startActivity(intent);

        } else if (id == R.id.about) {
            Intent intent=new Intent(Doctor_Portel_Data.this,AboutUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.contact) {
            Intent intent=new Intent(Doctor_Portel_Data.this,ContactUs_Activity.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            Intent intent=new Intent(Doctor_Portel_Data.this,Doctor_Profile.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
