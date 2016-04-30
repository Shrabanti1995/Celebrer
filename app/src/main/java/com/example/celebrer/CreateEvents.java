package com.example.celebrer;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.parse.ParseException;
import com.parse.SaveCallback;

public class CreateEvents extends AppCompatActivity {

    TextView eventName, eventLocation, eventHost, eventType, eventCategory, ticketPrice;
    EditText evName, evLocation, evHost, evType, evCategory, tPrice;
    String eName, eLocation, eHost, eCategory, eType, tcktPrice;
    Button createBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setTitle("Create New Event");
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        createBtn = (Button) findViewById(R.id.button5);

        eventName = (TextView) findViewById(R.id.textView34);
        eventLocation = (TextView) findViewById(R.id.textView35);
        eventHost = (TextView) findViewById(R.id.textView36);
        eventType = (TextView) findViewById(R.id.textView37);
        eventCategory = (TextView) findViewById(R.id.textView38);
        ticketPrice = (TextView) findViewById(R.id.textView39);

        evName = (EditText) findViewById(R.id.editText);
        evLocation = (EditText) findViewById(R.id.editText2);
        evHost = (EditText) findViewById(R.id.editText3);
        evType = (EditText) findViewById(R.id.editText4);
        evCategory = (EditText) findViewById(R.id.editText5);
        tPrice = (EditText) findViewById(R.id.editText6);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eName = evName.getText().toString();
                eLocation = evLocation.getText().toString();
                eHost = evHost.getText().toString();
                eType = evType.getText().toString();
                eCategory = evCategory.getText().toString();
                tcktPrice = tPrice.getText().toString();

                if(eName.equals("")||eLocation.equals("")||eHost.equals("")||eType.equals("")||eCategory.equals("")||tcktPrice.equals(""))
                {
                    Toast.makeText(CreateEvents.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvents.this);
                    builder.setMessage("Want to create an event?");
                    builder.setTitle("Create");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            ParseEvents event = new ParseEvents();
                            event.setEventName(eName);
                            event.setEventLocation(eLocation);
                            event.setEventHost(eHost);
                            event.setEventCategory(eCategory);
                            event.setEventType(eType);
                            event.setTicketPrice(tcktPrice);
                            event.setPeopleGoing(0);
                            event.setEventCreator(Holder.sUserName);
                            event.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Toast.makeText(CreateEvents.this, "Event is created!", Toast.LENGTH_SHORT).show();
                                    evName.setText("");
                                    evLocation.setText("");
                                    evHost.setText("");
                                    evType.setText("");
                                    evCategory.setText("");
                                    tPrice.setText("");
                                    if (eType.equals("Private")) {
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateEvents.this);
                                        builder1.setMessage("Want to invite your friends?");
                                        builder1.setTitle("Invite");

                                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                                emailIntent.setType("text/plain");
                                                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Holder.sEmailId});
                                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email from phone");
                                                startActivity(Intent.createChooser(emailIntent, "Choose an Email client"));
                                            }
                                        });

                                        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                // TODO Auto-generated method stub

                                            }
                                        });

                                        AlertDialog d = builder1.create();
                                        d.show();


                                    }
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
        });

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
