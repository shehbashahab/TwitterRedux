package com.codepath.apps.TwitterRedux.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.MySimpleTweets.R;
import com.codepath.apps.TwitterRedux.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import utils.Utilities;

/**
 * Created by shehba.shahab on 9/30/15.
 */

// Taking the Tweet objects and turning them into View displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Get the tweet
        Tweet tweet = getItem(position);

        // 2. Find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        // 3. Find the subviews to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvFullName = (TextView) convertView.findViewById(R.id.tvFullName);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);

        // 4. Populate data into the subviews
        tvFullName.setText(tweet.getUser().getName());
        tvUserName.setText(" @" + tweet.getUser().getScreenName());
        tvCreatedAt.setText(Utilities.getRelativeTimeAgo(tweet.getCreatedAt()));
        tvBody.setText(tweet.getBody());
        ivProfileImage.setImageResource(android.R.color.transparent); // clear out the old image for a recycled view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        // 5. Return the view to be inserted into the list
        return convertView;
    }

}
