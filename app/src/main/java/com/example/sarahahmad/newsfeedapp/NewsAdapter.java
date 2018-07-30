package com.example.sarahahmad.newsfeedapp;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by sarahahmad on 08/10/2017 AD.
 */

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
    }

    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list of earthquakes.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news, parent, false);
        }

        // Find the currentNews at the given position in the list of News
        News currentNews = getItem(position);
//set title
        // Find the TextView with view ID article_title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.article_title);
        // Display the Article Title of the current News in that TextView
        titleTextView.setText(currentNews.getTitle());
//set author name

        // Find the TextView with view ID author_name
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_name);
        // Display the Article author of the current News in that TextView
        authorTextView.setText(currentNews.getAuthor());

//set section

        // Find the TextView with view ID article_section
        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.article_section);
        // Display the Article section of the current News in that TextView
        sectionTextView.setText(currentNews.getSection());

//set published date
        // Find the TextView with view ID published_date
        TextView dateView = (TextView) listItemView.findViewById(R.id.published_date);
        // Format the date string
        String date = formatDate(currentNews.getPublishedDate());

        dateView.setText(date);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    /**
     * Return the formatted date string without Time
     */
    private String formatDate(String dateToFormat) {
        String date;
        if (dateToFormat.contains("T")) {
            String[] parts = dateToFormat.split("T");
            date = parts[0];
        } else {
            date = dateToFormat;
        }

        return date;
    }


}
