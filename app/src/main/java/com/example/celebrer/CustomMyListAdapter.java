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
public class CustomMyListAdapter extends ArrayAdapter {
    List<ParseEvents> eventList;
    private ViewHolder holder;
    public CustomMyListAdapter(Context context, List<ParseEvents> events) {
        super(context, 0, events);
        this.eventList = events;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list2_row_item, parent, false);
            holder = new ViewHolder();
            holder.eventName = (TextView) convertView.findViewById(R.id.textView23);
            holder.viewEventName = (TextView) convertView.findViewById(R.id.textView22);
            holder.location = (TextView) convertView.findViewById(R.id.textView25);
            holder.viewLocation = (TextView) convertView.findViewById(R.id.textView24);
            holder.eventType = (TextView) convertView.findViewById(R.id.textView27);
            holder.viewEventType = (TextView) convertView.findViewById(R.id.textView26);
            holder.eventCategory = (TextView) convertView.findViewById(R.id.textView29);
            holder.viewEventCategory = (TextView) convertView.findViewById(R.id.textView28);
            holder.peopleGoing = (TextView) convertView.findViewById(R.id.textView33);
            holder.viewPeopleGoing = (TextView) convertView.findViewById(R.id.textView32);
            holder.ticketPrice = (TextView) convertView.findViewById(R.id.textView31);
            holder.viewTicketPrice = (TextView) convertView.findViewById(R.id.textView30);
            holder.img = (ImageView) convertView.findViewById(R.id.imageView7);
            convertView.setTag(holder);
        }

        holder = (ViewHolder)convertView.getTag();
        String eventname = eventList.get(position).getEventName();
        String location = eventList.get(position).getEventLocation();
        String eventType = eventList.get(position).getEventType();
        String eventCategory = eventList.get(position).getEventCategory();
        int peopleGoing = eventList.get(position).getPeopleGoing();
        String ticketPrice = eventList.get(position).getTicketPrice();

        holder.eventName.setText(eventname);
        holder.location.setText(location);
        holder.eventType.setText(eventType);
        holder.eventCategory.setText(eventCategory);
        holder.peopleGoing.setText(peopleGoing + "");
        holder.ticketPrice.setText(ticketPrice);

        return convertView;
    }

    final class ViewHolder {
        public TextView eventName;
        public TextView viewEventName;
        public TextView location;
        public TextView viewLocation;
        public TextView eventType;
        public TextView viewEventType;
        public TextView eventCategory;
        public TextView viewEventCategory;
        public TextView peopleGoing;
        public TextView viewPeopleGoing;
        public TextView ticketPrice;
        public TextView viewTicketPrice;
        public ImageView img;
    }

}
