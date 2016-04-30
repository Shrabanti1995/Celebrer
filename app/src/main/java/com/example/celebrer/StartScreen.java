package com.example.celebrer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    public void signInAsUser(View v)
    {
        Intent i = new Intent(StartScreen.this,LogInActivity.class);
        i.putExtra("type","user");
        startActivity(i);
    }

    public void signInAsServiceProvider(View v)
    {
        Intent i = new Intent(StartScreen.this,LogInActivity.class);
        i.putExtra("type","service provider");
        startActivity(i);
    }
}
