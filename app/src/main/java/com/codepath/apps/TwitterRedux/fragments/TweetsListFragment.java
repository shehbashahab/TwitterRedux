package com.codepath.apps.TwitterRedux.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.MySimpleTweets.R;
import com.codepath.apps.TwitterRedux.adapters.TweetsArrayAdapter;
import com.codepath.apps.TwitterRedux.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import utils.EndlessScrollListener;

public abstract class TweetsListFragment extends Fragment {

    protected SwipeRefreshLayout swipeContainer;
    TweetsArrayAdapter aTweets;
    ArrayList<Tweet> tweets;
    private ListView lvTweets;

    //inflation logic
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline();
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                long lowestId = aTweets.getItem(aTweets.getCount() - 1).getUid();
                loadMoreTimeline(lowestId - 1);
                return true;
            }
        });
        lvTweets.setAdapter(aTweets);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    protected abstract void loadMoreTimeline(long max_id);

    protected abstract void populateTimeline();

    void clearAdapter()
    {
        aTweets.clear();
        tweets.clear();
    }
}
