package com.codepath.apps.TwitterRedux.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.TwitterRedux.TwitterApplication;
import com.codepath.apps.TwitterRedux.TwitterClient;
import com.codepath.apps.TwitterRedux.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    // Send an API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json
    private void populateTimeline() {

        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            // SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                addAll(Tweet.fromJSONArray(json));
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "errorResponse: " + errorResponse.toString());
                Log.d("DEBUG", "onFailure statusCode: " + statusCode);
            }
        });
    }
}
