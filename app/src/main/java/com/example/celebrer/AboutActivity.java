package com.example.celebrer;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView aboutTitle, aboutText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        aboutTitle = (TextView)findViewById(R.id.title_about);
        aboutText = (TextView)findViewById(R.id.text_about);

        setValuesofText();
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

    private void setValuesofText() {
        aboutTitle.setText("About C:elebrer");
        aboutText.setText("C:elebrer is app following the concept of \"Event Management " +
                "to help users to organize and keep track of events with the help of the nearest service providers\". The app was developed as the course project under the " +
                "supervision of Prof. S.Dubey in the course Supply Chain Management.");
    }
}
