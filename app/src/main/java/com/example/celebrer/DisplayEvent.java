package com.example.celebrer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class DisplayEvent extends AppCompatActivity {

    TextView evName, evLocation, evHost, evTicketPrice, evPeopleGoing, evCategory;
    TextView viewEventName, viewEventLocation, viewEventHost, viewTicketPrice, viewPeopleGoing, viewEventCategory;
    String eventName, eventLocation, eventHost, eventCategory, eventTicketPrice, eventPeopleGoing;

    SQLiteDatabase db;
    Button joinBtn, leaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);

        db = openOrCreateDatabase("events", Context.MODE_PRIVATE, null);
        db.execSQL("create table IF NOT EXISTS myjoinedevents (eventname text,status text)");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null) {
            setSupportActionBar(toolbar);
        }    // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        evName = (TextView) findViewById(R.id.textView11);
        viewEventName = (TextView) findViewById(R.id.textView2);
        evLocation = (TextView) findViewById(R.id.textView13);
        viewEventLocation = (TextView) findViewById(R.id.textView12);
        evHost = (TextView) findViewById(R.id.textView20);
        viewEventHost = (TextView) findViewById(R.id.textView14);
        evTicketPrice = (TextView) findViewById(R.id.textView16);
        viewTicketPrice = (TextView) findViewById(R.id.textView15);
        evPeopleGoing = (TextView) findViewById(R.id.textView18);
        viewPeopleGoing = (TextView) findViewById(R.id.textView17);
        evCategory = (TextView) findViewById(R.id.textView21);
        viewEventCategory = (TextView) findViewById(R.id.textView19);

        joinBtn = (Button) findViewById(R.id.button3);
        leaveBtn = (Button) findViewById(R.id.button4);

        Intent i = getIntent();
        eventName = i.getStringExtra("Event_Name");
        ab.setTitle(eventName);
        eventLocation = i.getStringExtra("Event_Location");
        eventHost = i.getStringExtra("Event_Host");
        eventCategory = i.getStringExtra("Event_Category");
        eventTicketPrice = i.getStringExtra("Event_TicketPrice");
        eventPeopleGoing = i.getStringExtra("People_Going");
        Log.d("onCreate",joinBtn.isEnabled()+"");
        Log.d("onCreate",leaveBtn.isEnabled()+"");

        enableButtons();
        displayEvents();
    }

    public void leave(View v)
    {
        enableButtons();
        if(!joinBtn.isEnabled())
        {
            ParseQuery<ParseEvents> query = ParseQuery.getQuery(ParseEvents.class);
            query.whereEqualTo("eventName",eventName);
            query.findInBackground(new FindCallback<ParseEvents>() {
                @Override
                public void done(List<ParseEvents> list, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setPeopleGoing(list.get(i).getPeopleGoing() - 1);
                            final String peopleGoing = list.get(i).getPeopleGoing()+"";
                            list.get(i).saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    evPeopleGoing.setText(peopleGoing);
                                    updateLeaveEvents();
                                }
                            });
                        }
                    } else {
                        Log.d("message", "Error: " + e.getMessage());

                    }
                }
            });
        }
    }

    public void join(View v)
    {
        enableButtons();
        if(!leaveBtn.isEnabled())
        {
            ParseQuery<ParseEvents> query = ParseQuery.getQuery(ParseEvents.class);
            query.whereEqualTo("eventName",eventName);
            query.findInBackground(new FindCallback<ParseEvents>() {
                @Override
                public void done(List<ParseEvents> list, ParseException e) {
                    if(e==null)
                    {
                        for(int i = 0 ; i < list.size() ; i++)
                        {
                            list.get(i).setPeopleGoing(list.get(i).getPeopleGoing() + 1);
                            final String peopleGoing = list.get(i).getPeopleGoing()+"";
                            list.get(i).saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    evPeopleGoing.setText(peopleGoing);
                                    updateJoinEvents();
                                }
                            });
                        }
                    }else
                    {
                        Log.d("message", "Error: " + e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateLeaveEvents() {
        Log.d("beforeUpdatingLeave",joinBtn.isEnabled()+"");
        Log.d("beforeUpdatingLeave",leaveBtn.isEnabled()+"");
        Cursor ans = db.rawQuery("update myjoinedevents set status = ? where eventname = ?", new String[]{"left",eventName});
        leaveBtn.setEnabled(false);
        joinBtn.setEnabled(true);
        Log.d("afterUpdatingLeave", joinBtn.isEnabled() + "");
        Log.d("afterUpdatingLeave", leaveBtn.isEnabled() + "");
    }

    public void enableButtons()
    {
        Log.d("beforeEnabling",joinBtn.isEnabled()+"");
        Log.d("beforeEnabling",leaveBtn.isEnabled()+"");
        Cursor ans = db.rawQuery("select status from myjoinedevents where eventname = ?", new String[]{eventName});
        int output = ans.getCount();
        Log.d("output",output+"");
        if(output > 0)
        {
            while(ans.moveToNext())
            {
                Log.d("status",ans.getString(0));
                if(ans.getString(0).equals("joined"))
                {
                    joinBtn.setEnabled(false);
                    leaveBtn.setEnabled(true);
                }else if(ans.getString(0).equals("left"))
                {
                    leaveBtn.setEnabled(false);
                    joinBtn.setEnabled(true);
                }
            }
        }else
        {
            db.execSQL("insert into myjoinedevents (eventname,status)values('"+eventName+"','"+""+"')");
            leaveBtn.setEnabled(false);
            joinBtn.setEnabled(true);
        }
        Log.d("afterEnabling",joinBtn.isEnabled()+"");
        Log.d("afterEnabling",leaveBtn.isEnabled()+"");
    }

    private void updateJoinEvents() {

        Log.d("beforeupdatingjoin",joinBtn.isEnabled()+"");
        Log.d("beforeupdatingjoin",leaveBtn.isEnabled()+"");
        Cursor ans = db.rawQuery("update myjoinedevents set status = ? where eventname = ?", new String[]{"joined",eventName});
        joinBtn.setEnabled(false);
        leaveBtn.setEnabled(true);
        Log.d("afterupdatingjoin", joinBtn.isEnabled() + "");
        Log.d("afterupdatingjoin", leaveBtn.isEnabled() + "");

    }

    private void displayEvents() {
        evName.setText(eventName);
        evLocation.setText(eventLocation);
        evHost.setText(eventHost);
        evTicketPrice.setText(eventTicketPrice);
        evPeopleGoing.setText(eventPeopleGoing);
        evCategory.setText(eventCategory);
    }
}
