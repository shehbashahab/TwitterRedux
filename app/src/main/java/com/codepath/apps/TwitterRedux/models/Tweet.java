package com.codepath.apps.TwitterRedux.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shehba.shahab on 9/30/15.
 */
public class Tweet {

    private String body;
    private long uid; // unique id for the tweet
    private User user; // store embedded user object
    private String createdAt;

    // Deserialize the JSON and build tweet objects
    // Tweet.fromJSON("{...}"} => <TWEET>
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        // Extract the values from the json, and store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //return the tweet object
        return tweet;
    }

    //Return an arraylist of <Tweets>
    // Tweet.fromJSONArray([{...}, {...}]) => List<Tweet>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        // Iterate the raw json array and create tweets
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);//getting each of the JSONObject
                Tweet tweet = Tweet.fromJSON(tweetJson);// create the tweet (POJO) form the JSONObject
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;//if one fail, continue to next and others
            }

        }
        return tweets;//finished list
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }
}
