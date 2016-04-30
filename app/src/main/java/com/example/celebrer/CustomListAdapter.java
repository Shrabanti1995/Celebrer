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
 * Created by Shrabanti on 02-12-2015.
 */
public class CustomListAdapter extends ArrayAdapter {
    List<ParseEvents> eventList;
    private ViewHolder holder;
    public CustomListAdapter(Context context, List<ParseEvents> events) {
        super(context, 0, events);
        this.eventList = events;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list_row_item, parent, false);
            holder = new ViewHolder();
            holder.eventName = (TextView) convertView.findViewById(R.id.textView4);
            holder.viewName = (TextView) convertView.findViewById(R.id.textView3);
            holder.location = (TextView) convertView.findViewById(R.id.textView6);
            holder.viewLocation = (TextView) convertView.findViewById(R.id.textView5);
            holder.peopleGoing = (TextView) convertView.findViewById(R.id.textView8);
            holder.viewPeopleGoing = (TextView) convertView.findViewById(R.id.textView7);
            holder.ticketPrice = (TextView) convertView.findViewById(R.id.textView10);
            holder.viewTicketPrice = (TextView) convertView.findViewById(R.id.textView9);
            holder.img = (ImageView) convertView.findViewById(R.id.imageView6);
            convertView.setTag(holder);
        }

        holder = (ViewHolder)convertView.getTag();
        String eventname = eventList.get(position).getEventName();
        String location = eventList.get(position).getEventLocation();
        int peopleGoing = eventList.get(position).getPeopleGoing();
        String ticketPrice = eventList.get(position).getTicketPrice();

        holder.eventName.setText(eventname);
        holder.location.setText(location);
        holder.peopleGoing.setText(peopleGoing+"");
        holder.ticketPrice.setText(ticketPrice);

        return convertView;
    }

    final class ViewHolder {
        public TextView eventName;
        public TextView viewName;
        public TextView location;
        public TextView viewLocation;
        public TextView peopleGoing;
        public TextView viewPeopleGoing;
        public TextView ticketPrice;
        public TextView viewTicketPrice;
        public ImageView img;
    }
}
