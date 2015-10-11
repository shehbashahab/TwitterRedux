package com.codepath.apps.TwitterRedux.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;
    //private SwipeRefreshLayout swipeContainer;
    private ListView lvTweets;

    //inflation logic
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
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


    //creation lifeycle  event
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
}
