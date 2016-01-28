package com.codepath.apps.TwitterRedux.activities;

import com.codepath.apps.MySimpleTweets.R;
import com.codepath.apps.TwitterRedux.TwitterApplication;
import com.codepath.apps.TwitterRedux.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ComposeTweetActivity extends AppCompatActivity {

    private TwitterClient client;
    private EditText mEditText;
    private TextView tvCharCount;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvCharCount.setText(String.valueOf(140 - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        Button btnTweet;
        Button btnCancel;

        mEditText = (EditText) findViewById(R.id.etTweet);
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);
        btnTweet = (Button) findViewById(R.id.btnTweet);
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTweet(mEditText.getText().toString());
            }

        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissActivity();
            }

        });

        mEditText.addTextChangedListener(mTextEditorWatcher);
        client = TwitterApplication.getRestClient();
    }

    private void dismissActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void postTweet(String tweetBody) {
        // SUCCESS
        client.postTweet(tweetBody, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject success) {
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "errorResponse: " + errorResponse.toString());
                Log.d("DEBUG", "onFailure statusCode: " + statusCode);
            }
        });
        dismissActivity();
    }
}