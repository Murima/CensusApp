package com.example.knbs.censusapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class EnumeratorHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enumerator_home);

        //register textviews
        TextView tvCensusForms;
        TextView tvEnumID;
        TextView tvTaskList;

        tvCensusForms= (TextView) findViewById(R.id.tvCensusForms);
        tvCensusForms.setOnClickListener(this);
        tvEnumID = (TextView) findViewById(R.id.tvEnumID);
        tvEnumID.setOnClickListener(this);
        tvTaskList = (TextView) findViewById(R.id.tvTaskList);
        tvTaskList.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            TokenManagement tknMgmt = new TokenManagement(this);
            tknMgmt.deleteToken();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enumerator_home, menu);
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

        if (id == R.id.nav_logout) {
            // Handle the logout
        }

        else if (id == R.id.nav_settings) {
             //handle settings option
        }

        else if (id == R.id.nav_share) {
            //handle share option

        } else if (id == R.id.nav_send) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/html");
            String link = "https://www.android.com/";
            String linkValue = "Click Me";
            String body1 = "<a href=\"" + link + "\">" + link+ "</a>";//I don't want this
            String body2 = "<a href=\"" + link + "\">" + linkValue + "</a>";//This is not working
            sendIntent.putExtra(Intent.EXTRA_INTENT, Html.fromHtml(body2));
            startActivity(Intent.createChooser(sendIntent, "Share with"));


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvCensusForms:
                Intent categoryIntent = new Intent(this, CategoryActivity.class);
                startActivity(categoryIntent);
                break;
            case R.id.tvEnumID:
                Intent enumeratorID = new Intent(this, EnumeratorIDActivity.class);
                startActivity(enumeratorID);
                break;
            case R.id.tvTaskList:
                Intent taskList = new Intent(this, TaskLIstActivity.class);
                startActivity(taskList);
                break;
            default:
                Log.i("DEFAULT ONCLICK OPT","This is default switch mode");
                break;
        }
    }
}
