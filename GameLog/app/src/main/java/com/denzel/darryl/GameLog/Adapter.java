package com.denzel.darryl.GameLog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by darryl on 17-Mar-16.
 */
public class Adapter extends ArrayAdapter<LogItem> {
    private LayoutInflater inflater;

    //Constructor
    public Adapter(Context context, int resource, List<LogItem> objects) {
        super(context, resource, objects);

        //Initialize the layout inflater
        inflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate layout into the View for the Row
        convertView = inflater.inflate(R.layout.row_detail, parent, false);

        //Retrieve
        LogItem item = getItem(position);

        //Retrieve all Views
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView steamSite = (TextView) convertView.findViewById(R.id.steamSite);

        //Set the title for this row
        title.setText(item.getLogitem());

        //Set the description for this row
        description.setText(item.getDescription());

        //Set the site for this row
        steamSite.setText(item.getSteamSite());


        return convertView;
    }

}
