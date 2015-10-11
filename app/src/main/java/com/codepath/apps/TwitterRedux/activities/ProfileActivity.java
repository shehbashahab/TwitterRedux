package com.codepath.apps.TwitterRedux.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.MySimpleTweets.R;
import com.codepath.apps.TwitterRedux.TwitterApplication;
import com.codepath.apps.TwitterRedux.TwitterClient;
import com.codepath.apps.TwitterRedux.fragments.UserTimelineFragment;
import com.codepath.apps.TwitterRedux.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;
    String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // If user icon
        username = getIntent().getStringExtra("screen_name");


        client = TwitterApplication.getRestClient();

        if (username == null) {
            //Get the account info
            client.getCurrentUserInfo(new JsonHttpResponseHandler() {
                // SUCCESS
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    username = user.getScreenName();

                    // My current user account's info
                    getSupportActionBar().setTitle("@" + username);
                    populateProfileHeader(user);
                }

                // FAILURE
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG", "errorResponse: " + errorResponse.toString());
                    Log.d("DEBUG", "onFailure statusCode: " + statusCode);
                }
            });
        } else {
            //Get the account info
            client.getUserInfo(username, new JsonHttpResponseHandler() {
                // SUCCESS
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle("@" + username);
                    populateProfileHeader(user);
                }

                // FAILURE
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("DEBUG", "errorResponse: " + errorResponse.toString());
                    Log.d("DEBUG", "onFailure statusCode: " + statusCode);
                }
            });

        }
        if (savedInstanceState == null) {
            //if (username == null) username = getIntent().getStringExtra("screen_name");

            // Create the user timeline fragment
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(username);

            // Display user fragment within this activity (dynamically)
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit(); // changes the fragments
        }
    }

    private void populateProfileHeader(User user) {
        TextView tvFullName = (TextView) findViewById(R.id.tvFullName);
        TextView tvBio = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);

        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfile);
        tvFullName.setText(user.getName().toString());
        tvBio.setText(user.getTagline().toString());
        tvFollowers.setText(String.valueOf(user.getFollowersCount()) + " Followers");
        tvFollowing.setText(String.valueOf(user.getFriendsCount()) + " Following");
    }
}
