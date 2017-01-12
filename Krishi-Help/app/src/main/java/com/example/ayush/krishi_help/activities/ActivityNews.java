package com.example.ayush.krishi_help.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ayush.krishi_help.R;
import com.example.ayush.krishi_help.utilities.NewsAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ActivityNews extends AppCompatActivity {

    ListView lv ;
    private JSONArray list;
    ProgressDialog dialog ;
    String server_ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);
        server_ip = getString(R.string.server_ip);
        getSupportActionBar().setTitle("Krishi news");
        dialog= new ProgressDialog(ActivityNews.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Getting News");
        dialog.show();
        lv = (ListView) findViewById(R.id.lv);
        sendData();

    }

    public void setList(JSONArray arr) {
        ArrayList<JSONObject> list = new ArrayList<>();



        for(int i =0 ; i < arr.length(); i++)
            try {
                list.add(arr.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        lv.setAdapter(new NewsAdapter(ActivityNews.this, list));
    }




    String url = server_ip.concat("/news");
    public void sendData() {
        RequestParams params = new RequestParams();
        params.put("news", "news");
//        params.setUseJsonStreamer(true);

//        params.put("first_name", f_name.getText().toString());


        AsyncHttpClient client = new AsyncHttpClient();


        AsyncHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

//                Log.d("news", response.toString());
                JSONArray arr ;
                try {
                    arr = response.getJSONArray("data");
                    setList(arr);
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                dialog.dismiss();
                Toast.makeText(ActivityNews.this, "Please try again later", Toast.LENGTH_SHORT).show();
            }
        };


        client.post(url, params, responseHandler);
    }
}
