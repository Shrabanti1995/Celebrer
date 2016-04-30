package com.example.celebrer;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Shrabanti on 03-12-2015.
 */
@ParseClassName("ParseServiceProviders")
public class ParseServiceProviders extends ParseObject{
    public String getServiceProviderId() {
        return getString("objectId");
    }

    public String getServiceProviderName() {
        return getString("serviceProviderName");
    }

    public String getServiceLocation() {
        return getString("serviceLocation");
    }

    public String getServiceProviderContact() {
        return getString("contact");
    }

    public String getServiceCategory(){return getString("serviceCategory");}

    public void setServiceProviderName(String name){put("serviceProviderName", name);}

    public void setServiceLocation(String location){put("serviceLocation", location);}

    public void setServiceProviderContact(String contact){put("contact", contact);}

    public void setServiceCategory(String category){put("serviceCategory", category);}

}
