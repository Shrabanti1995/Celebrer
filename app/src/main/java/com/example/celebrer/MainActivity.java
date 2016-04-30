package com.example.celebrer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView img;
    ListView eventList;
    CustomListAdapter adapter;
    private ArrayList<ParseEvents> mEvents;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img = (ImageView) findViewById(R.id.imageView3);

        final ActionBar ab = getSupportActionBar();
        ab.setTitle("C:elebrer");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ConductEvent.class);
                startActivity(i);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(ParseUser.getCurrentUser()!=null)
        {
            Holder.sUserId = ParseUser.getCurrentUser().getObjectId();
            Holder.sUserName = ParseUser.getCurrentUser().getUsername();
            startWithCurrentUser();
        }

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String evName = mEvents.get(position).getEventName();
                String eLocation = mEvents.get(position).getEventLocation();
                String eTicketPrice = mEvents.get(position).getTicketPrice();
                int ePeopleGoing = mEvents.get(position).getPeopleGoing();
                String eHost = mEvents.get(position).getEventHost();
                String eCategory = mEvents.get(position).getEventCategory();
                Intent i = new Intent(MainActivity.this, DisplayEvent.class);
                i.putExtra("Event_Name", evName);
                i.putExtra("Event_Location", eLocation);
                i.putExtra("Event_Host", eHost);
                i.putExtra("Event_TicketPrice", eTicketPrice);
                i.putExtra("People_Going", ePeopleGoing+"");
                i.putExtra("Event_Category", eCategory);
                startActivity(i);
            }
        });
    }

    private void startWithCurrentUser() {
        setUpEventList();
    }

    public void setUpEventList()
    {
        eventList = (ListView) findViewById(R.id.listView);
        mEvents = new ArrayList<>();
        receiveEvents();
        adapter = new CustomListAdapter(MainActivity.this, mEvents);
        eventList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void receiveEvents()
    {
        ParseQuery<ParseEvents> query = ParseQuery.getQuery(ParseEvents.class);
        query.whereEqualTo("eventType","Open");
        query.findInBackground(new FindCallback<ParseEvents>() {
            @Override
            public void done(List<ParseEvents> list, ParseException e) {
                if(e==null)
                {
                    mEvents.addAll(list);
                    adapter.notifyDataSetChanged();
                }else
                {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.about) {
            Intent i = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(i);
        }else if(id == R.id.weddings)
        {
            Intent i = new Intent(MainActivity.this,ShowServiceProviders.class);
            i.putExtra("category","Wedding");
            startActivity(i);
        }else if(id == R.id.birthday)
        {
            Intent i = new Intent(MainActivity.this,ShowServiceProviders.class);
            i.putExtra("category","Birthday");
            startActivity(i);
        }else if(id == R.id.musical)
        {
            Intent i = new Intent(MainActivity.this,ShowServiceProviders.class);
            i.putExtra("category","Musical");
            startActivity(i);
        }else if(id == R.id.nav_logout)
        {
            drawer.closeDrawers();
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    Intent i = new Intent(MainActivity.this, StartScreen.class);
                    startActivity(i);
                }
            });
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
