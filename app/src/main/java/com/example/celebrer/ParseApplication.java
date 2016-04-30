package com.example.celebrer;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

/**
 * Created by Shrabanti on 01-12-2015.
 */
public class ParseApplication extends Application{
    public static final String YOUR_APPLICATION_ID = "KMY7Msf5JmEbwAN68V1PDzOXURGWzww3aBgolOhD";
    public static final String YOUR_CLIENT_KEY = "gX9RsazU8kqNrNFVvVC9ioF84BGitM45ZIeZIzfo";

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        //ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(ParseEvents.class);
        ParseObject.registerSubclass(ParseServiceProviders.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        // Test creation of object
        /*ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/
    }
}
