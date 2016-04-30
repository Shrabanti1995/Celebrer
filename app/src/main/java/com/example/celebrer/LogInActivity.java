package com.example.celebrer;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    String category;
    ImageView img;
    SignInButton btnSignIn;
    TextView tv;

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "LogInActivity";

    private GoogleApiClient mGoogleApiClient;
    SharedPreferences sp;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

    String sUserId;
    Person currentPerson;
    String personName, personPhotoUrl, personGooglePlusProfile, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        img = (ImageView) findViewById(R.id.imageView2);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        tv = (TextView) findViewById(R.id.textView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setTitle("Login");
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        category = i.getStringExtra("type");

        if(category.equals("user"))
        {
            img.setImageResource(R.drawable.user);
            tv.setText("Sign in as User");
        }else if(category.equals("service provider"))
        {
            img.setImageResource(R.drawable.service);
            tv.setText("Sign in as Service Provider");
        }

        btnSignIn.setOnClickListener(this);

        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        sp = getSharedPreferences(Holder.SHARED_PREFERENCES_NAME, Context.MODE_APPEND);
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

    protected void onStart() {
        Log.v(TAG, "Start");
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        Log.v(TAG, "Stop");
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "Application Destroyed");
        super.onDestroy();
    }

    public void onConnected(Bundle bundle) {

        Toast.makeText(this, "User is connected, Yay!", Toast.LENGTH_LONG).show();
        setProfileInformation();
        mSignInClicked = false;
    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
        //updateUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d(TAG, "onConnectionFailed:" + result);

        if (!result.hasResolution()) {
            System.out.println("We're going to give error dialog.");
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
        } else {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        //System.out.println("Hello");
        Log.v(TAG, "ActivityResult: " + requestCode);
        if ((requestCode == RC_SIGN_IN) && (responseCode == RESULT_OK)) {
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // Signin button clicked
                signInWithGplus();
                break;
            /*case R.id.btn_sign_out:
                // Signout button clicked
                signOutFromGplus();
                break;*/
        }
    }

    private void signOutFromGplus() {
        //if(Holder.FLAG==1) {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }
        System.out.println("Logout!");
    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }

    }

    private void resolveSignInError() {
        System.out.println("M connection result " + mConnectionResult);
        if (mConnectionResult.hasResolution()) {
            try {

                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
            }
        }
    }

    public void setProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                personName = currentPerson.getDisplayName();
                personPhotoUrl = currentPerson.getImage().getUrl();
                personGooglePlusProfile = currentPerson.getUrl();
                email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                Holder.sEmailId = email;

                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                SharedPreferences.Editor editor = sp.edit();
                System.out.println("currentPerson" + currentPerson.toString());
                // ----------------Putting in shared preference.
                editor.putString("personName", personName);
                editor.putString("photoUrl", personPhotoUrl);
                editor.putString("profile", personGooglePlusProfile);
                editor.putString("currentEmail", email);
                editor.apply();
                //sp.getString("photoUrl", null);
                signOutFromGplus();
                // I check in the data base if(my email id of user exist in data base)

                checkUser();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkUser() {
        //check if there already is a user
        String usernametxt, passwordtxt;
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            //if there is a current user.
            if(category.equals("user"))
            {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else if(category.equals("service provider"))
            {
                Intent intent = new Intent(LogInActivity.this, ServiceProvider.class);
                startActivity(intent);
                finish();
            }

        } else {
            //signIn to get the current user
            // Retrieve the text entered from the EditText
            usernametxt = personName;
            passwordtxt = "password";


            System.out.println("userName: " + usernametxt);
            // Send data to Parse.com for verification
            ParseUser.logInInBackground(usernametxt, passwordtxt,
                    new LogInCallback() {
                        @Override
                        public void done(ParseUser user, com.parse.ParseException e) {
                            System.out.println("user: " + user);
                            //System.out.println("exception: " + e.toString());
                            if (user != null) {
                                sUserId = ParseUser.getCurrentUser().getObjectId();
                                //System.out.println("Current User Id: " + sUserId);
                                // If user exist and authenticated, send user to Welcome.class
                                if(category.equals("user"))
                                {
                                    login();
                                }else if(category.equals("service provider"))
                                {
                                    loginAsServiceProvider();
                                }
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Logged in",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Creating New User",
                                        Toast.LENGTH_LONG).show();
                                createNewUser();
                            }
                        }
                    });
        }
    }

    private void createNewUser() {
        System.out.println("Creating new user");
        ParseUser user = new ParseUser();
        user.setUsername(personName);
        user.setEmail(email);
        if(category.equals("user"))
        {
            user.setPassword("password");
            user.put("type","user");
        }
        else if(category.equals("service provider"))
        {
            user.setPassword("passService");
            user.put("type", "service provider");
        }

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Show a simple Toast message upon successful registration
                    Toast.makeText(getApplicationContext(),
                            "Successfully Signed up.",
                            Toast.LENGTH_LONG).show();
                    if(category.equals("user"))
                    {
                        login();
                    }else if(category.equals("service provider"))
                    {
                        loginAsServiceProvider();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Sign up Error", Toast.LENGTH_LONG)
                            .show();
                    signOutFromGplus();
                }
            }
        });
    }

    private void loginAsServiceProvider()
    {
        Intent intent = new Intent(
                LogInActivity.this,
                ServiceProvider.class);
        startActivity(intent);
        finish();
    }

    private void login() {
        //KLLmVL886h
        Intent intent = new Intent(
                LogInActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }
}
