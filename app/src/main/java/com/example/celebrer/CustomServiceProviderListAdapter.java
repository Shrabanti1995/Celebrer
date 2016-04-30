package com.example.celebrer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Shrabanti on 03-12-2015.
 */
public class CustomServiceProviderListAdapter extends ArrayAdapter {
    List<ParseServiceProviders> serviceProvidersList;
    private ViewHolder holder;
    public CustomServiceProviderListAdapter(Context context, List<ParseServiceProviders> serviceProviders) {
        super(context, 0, serviceProviders);
        this.serviceProvidersList = serviceProviders;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.service_list_row, parent, false);
            holder = new ViewHolder();
            holder.serviceName = (TextView) convertView.findViewById(R.id.textView45);
            holder.viewserviceName = (TextView) convertView.findViewById(R.id.textView44);
            holder.serviceLocation = (TextView) convertView.findViewById(R.id.textView47);
            holder.viewServiceLocation = (TextView) convertView.findViewById(R.id.textView46);
            holder.serviceContact = (TextView) convertView.findViewById(R.id.textView49);
            holder.viewServiceContact = (TextView) convertView.findViewById(R.id.textView48);
            holder.imgView = (ImageView) convertView.findViewById(R.id.imageView5);
            convertView.setTag(holder);
        }

        holder = (ViewHolder)convertView.getTag();
        String serviceprovidername = serviceProvidersList.get(position).getServiceProviderName();
        String location = serviceProvidersList.get(position).getServiceLocation();
        String contact = serviceProvidersList.get(position).getServiceProviderContact();

        holder.serviceName.setText(serviceprovidername);
        holder.serviceLocation.setText(location);
        holder.serviceContact.setText(contact);

        return convertView;
    }

    final class ViewHolder {
        public TextView serviceName;
        public TextView viewserviceName;
        public TextView serviceLocation;
        public TextView viewServiceLocation;
        public TextView serviceContact;
        public TextView viewServiceContact;
        public ImageView imgView;
    }
}
