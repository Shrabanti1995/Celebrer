package com.example.celebrer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ConductEvent extends AppCompatActivity{

    ListView eventList;
    private ArrayList<ParseEvents> mEvents;
    CustomMyListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conduct_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setTitle("My Events");
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConductEvent.this,CreateEvents.class);
                startActivity(i);
            }
        });

        if(ParseUser.getCurrentUser()!=null)
        {
            startWithCurrentUser();
        }
    }


    private void startWithCurrentUser() {
        setUpEventList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void setUpEventList() {
        eventList = (ListView) findViewById(R.id.listView2);
        mEvents = new ArrayList<>();
        receiveEvents();
        adapter = new CustomMyListAdapter(ConductEvent.this, mEvents);
        adapter = new CustomMyListAdapter(ConductEvent.this, mEvents);
        eventList.setAdapter(adapter);
    }

    private void receiveEvents() {
        ParseQuery<ParseEvents> query = ParseQuery.getQuery(ParseEvents.class);
        query.whereEqualTo("eventCreator",Holder.sUserName);
        query.findInBackground(new FindCallback<ParseEvents>() {
            @Override
            public void done(List<ParseEvents> list, ParseException e) {
                if (e == null) {
                    mEvents.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

}
