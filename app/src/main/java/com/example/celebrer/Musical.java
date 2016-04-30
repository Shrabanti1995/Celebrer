package com.example.celebrer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shrabanti on 03-12-2015.
 */
public class Musical extends ListFragment {

    CustomServiceProviderListAdapter adapter;
    private ArrayList<ParseServiceProviders> mServiceProviders;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.musical_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mServiceProviders = new ArrayList<>();
        receiveServiceProviders();
        adapter = new CustomServiceProviderListAdapter(getActivity(), mServiceProviders);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void receiveServiceProviders()
    {
        ParseQuery<ParseServiceProviders> query = ParseQuery.getQuery(ParseServiceProviders.class);
        query.whereEqualTo("serviceCategory", "Musical");
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
