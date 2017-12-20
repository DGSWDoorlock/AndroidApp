package com.dgsw.doorlock.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.fragment.Approve;
import com.dgsw.doorlock.fragment.EntryApply;
import com.dgsw.doorlock.fragment.MainFrag;
import com.dgsw.doorlock.fragment.NFC;
import com.kabouzeid.appthemehelper.ATH;
import com.kabouzeid.appthemehelper.ThemeStore;
import com.kabouzeid.appthemehelper.util.TintHelper;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String IP_ADDRESS = "192.168.0.6";

    Fragment main, entryApply, approve, nfc;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ThemeStore.primaryColor(this));
        setSupportActionBar(toolbar);

        main = new MainFrag();
        entryApply = new EntryApply();
        approve = new Approve();
        nfc = new NFC();

        if (savedInstanceState == null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.content_fragment_layout, main);
            transaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ATH.setTaskDescriptionColor(this, ThemeStore.primaryColor(this));
        ATH.setStatusbarColor(this, ThemeStore.statusBarColor(this));
        ATH.setLightStatusbarAuto(this, ThemeStore.statusBarColor(this));
        ATH.setNavigationbarColor(this, ThemeStore.navigationBarColor(this));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar row_approve clicks here. The action bar will
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view row_approve clicks here.
        int id = item.getItemId();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_apply_coming_and_going) {
            transaction.replace(R.id.content_fragment_layout, entryApply);
        } else if (id == R.id.nav_approve_coming_and_going) {
            transaction.replace(R.id.content_fragment_layout, approve);
        } else if (id == R.id.nav_coming_and_going) {
            transaction.replace(R.id.content_fragment_layout, nfc);
        }

        transaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
