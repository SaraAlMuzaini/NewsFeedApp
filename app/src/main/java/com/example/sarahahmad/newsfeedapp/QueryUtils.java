package com.example.sarahahmad.newsfeedapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.author;

/**
 * Created by sarahahmad on 08/10/2017 AD.
 */

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
        // To prevent someone from accidentally instantiating the contract class, give it an empty constructor.
    }

    public static List<News> fetchNews(String jsonResponse) {

        // Extract relevant fields from the JSON response and create a list of {@link News}s
        List<News> newsList = extractNews(jsonResponse);

        // Return the list of {@link News}s
        return newsList;
    }

    static String createStringUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("show-references", "author")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("q", "Android")
                .appendQueryParameter("api-key", "825b566b-6880-4b36-9199-577374389ce0");
        String url = builder.build().toString();
        return url;
    }

    static URL createUrl() {
        String stringUrl = createStringUrl();
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Queryutils", "Error creating URL: ", e);
            return null;
        }
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
      static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    //    Required fields include the title of the article and the name of the section that it belongs to.
    //    If available, author name and date published should be included.

    public static List<News> extractNews(String json) {

        ArrayList<News> newsList = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(json);

            JSONObject jsonResults = jsonResponse.getJSONObject("response");
            JSONArray results = jsonResults.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject newsObject = results.getJSONObject(i);

                //Get Title
                String title = newsObject.getString("webTitle");
                //Get Section
                String section = newsObject.getString("sectionName");

                //Get Authors Name
                JSONArray tagsArray = newsObject.getJSONArray("tags");

                String author = null;

                if (tagsArray.length() == 0) {
                    author = null;
                } else {
                    for (int j = 0; j < tagsArray.length(); j++) {
                        JSONObject firstObject = tagsArray.getJSONObject(j);
                        author += firstObject.getString("webTitle") + ". ";
                    }
                }
                //Get newsList published Date
                String publishedDate = null;
                if (newsObject.has("webPublicationDate")) {
                    publishedDate = newsObject.getString("webPublicationDate");
                    //publishedDate = formatDate();
                }

                //Get URL
                String url = newsObject.getString("webUrl");
                //add to new object
                News CurrentNews = new News(title, section, author, publishedDate, url);
                newsList.add(CurrentNews);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsList;
    }


}
