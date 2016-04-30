package com.example.celebrer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

public class AddServices extends AppCompatActivity {

    TextView name, contact, location, serviceCategory;
    EditText sName,sContact,sLocation,sServiceCategory;
    String personName, personContact, loc, category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setTitle("Add Services");
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        name = (TextView) findViewById(R.id.textView40);
        location = (TextView) findViewById(R.id.textView42);
        contact = (TextView) findViewById(R.id.textView41);
        serviceCategory = (TextView) findViewById(R.id.textView43);

        sName = (EditText) findViewById(R.id.editText7);
        sContact = (EditText) findViewById(R.id.editText8);
        sLocation = (EditText) findViewById(R.id.editText9);
        sServiceCategory = (EditText) findViewById(R.id.editText10);

    }

    public void add(View v)
    {
        personName = sName.getText().toString();
        personContact = sContact.getText().toString();
        loc = sLocation.getText().toString();
        category = sServiceCategory.getText().toString();

        if(personName.equals("")||personContact.equals("")||loc.equals("")||category.equals(""))
        {
            Toast.makeText(AddServices.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddServices.this);
            builder.setMessage("Want to add your service?");
            builder.setTitle("Add Service");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    ParseServiceProviders serviceProvider = new ParseServiceProviders();
                    serviceProvider.setServiceProviderName(personName);
                    serviceProvider.setServiceLocation(loc);
                    serviceProvider.setServiceProviderContact(personContact);
                    serviceProvider.setServiceCategory(category);
                    serviceProvider.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(AddServices.this,"Service is created!",Toast.LENGTH_SHORT).show();
                            sName.setText("");
                            sLocation.setText("");
                            sContact.setText("");
                            sServiceCategory.setText("");
                        }
                    });
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
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
}
