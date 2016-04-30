package com.example.celebrer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ShowServiceProviders extends AppCompatActivity {

    CustomServiceProviderListAdapter adapter;
    ListView serviceProvidersList;
    ImageView img;
    String serviceCategory;
    private ArrayList<ParseServiceProviders> mServiceProviders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_service_providers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setTitle("Service Providers");
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        img = (ImageView) findViewById(R.id.imageView8);
        serviceProvidersList = (ListView) findViewById(R.id.listView3);

        Intent i = getIntent();
        serviceCategory = i.getStringExtra("category");
        if(serviceCategory.equals("Wedding"))
        {
            img.setImageResource(R.drawable.wedding);
        }else if(serviceCategory.equals("Birthday"))
        {
            img.setImageResource(R.drawable.birthday);
        }else if(serviceCategory.equals("Musical"))
        {
            img.setImageResource(R.drawable.music);
        }
        setUpEventList();

    }

    private void setUpEventList() {
        receiveServiceProviders();
        mServiceProviders = new ArrayList<>();
        adapter = new CustomServiceProviderListAdapter(ShowServiceProviders.this, mServiceProviders);
        serviceProvidersList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
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

    private void receiveServiceProviders() {
        ParseQuery<ParseServiceProviders> query = ParseQuery.getQuery(ParseServiceProviders.class);
        query.whereEqualTo("serviceCategory", serviceCategory);
        query.findInBackground(new FindCallback<ParseServiceProviders>() {
            @Override
            public void done(List<ParseServiceProviders> list, ParseException e) {
                if (e == null) {
                    mServiceProviders.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }
}
