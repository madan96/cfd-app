package com.example.ayush.krishi_help.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.krishi_help.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Prices extends AppCompatActivity {
    Button pesticide,fertilizer,seed;
    TextView[] pesticide_tv =  new TextView[12] , fertilizer_tv  =  new TextView[12] , seed_tv  =  new TextView[12] ;
    String pesticide_url[] = new String[12] , fertilizer_url[] = new String[12] , seed_url[] = new String[12] ;
    ScrollView ll_pesticide , ll_fertilizer , ll_seed;
    ProgressDialog load_dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        load_dialog = new ProgressDialog(Prices.this);
        load_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        load_dialog.setMessage("Loading");

        pesticide = (Button) findViewById(R.id.b_p);
        fertilizer = (Button) findViewById(R.id.b_f);
        seed = (Button) findViewById(R.id.b_s);

        ll_pesticide = (ScrollView) findViewById(R.id.sv_p);
        ll_fertilizer =(ScrollView) findViewById(R.id.sv_f);
        ll_seed = (ScrollView) findViewById(R.id.sv_s);

        pesticide_tv[0] = (TextView) findViewById(R.id.tv_p_1);
        pesticide_tv[1] = (TextView) findViewById(R.id.tv_p_2);
        pesticide_tv[2] = (TextView) findViewById(R.id.tv_p_3);
        pesticide_tv[3] = (TextView) findViewById(R.id.tv_p_4);
        pesticide_tv[4] = (TextView) findViewById(R.id.tv_p_5);
        pesticide_tv[5] = (TextView) findViewById(R.id.tv_p_6);
        pesticide_tv[6] = (TextView) findViewById(R.id.tv_p_7);
        pesticide_tv[7] = (TextView) findViewById(R.id.tv_p_8);
        pesticide_tv[8] = (TextView) findViewById(R.id.tv_p_9);
        pesticide_tv[9] = (TextView) findViewById(R.id.tv_p_10);
        pesticide_tv[10] = (TextView) findViewById(R.id.tv_p_11);
        pesticide_tv[11] = (TextView) findViewById(R.id.tv_p_12);

        pesticide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_dialog.show();
                sendData("pesticide");
                ll_seed.setVisibility(View.GONE);
                ll_fertilizer.setVisibility(View.GONE);

                ll_pesticide.setVisibility(View.VISIBLE);
            }
        });

        fertilizer_tv[0] = (TextView) findViewById(R.id.tv_f_1);
        fertilizer_tv[1] = (TextView) findViewById(R.id.tv_f_2);
        fertilizer_tv[2] = (TextView) findViewById(R.id.tv_f_3);
        fertilizer_tv[3] = (TextView) findViewById(R.id.tv_f_4);
        fertilizer_tv[4] = (TextView) findViewById(R.id.tv_f_5);
        fertilizer_tv[5] = (TextView) findViewById(R.id.tv_f_6);
        fertilizer_tv[6] = (TextView) findViewById(R.id.tv_f_7);
        fertilizer_tv[7] = (TextView) findViewById(R.id.tv_f_8);
        fertilizer_tv[8] = (TextView) findViewById(R.id.tv_f_9);
        fertilizer_tv[9] = (TextView) findViewById(R.id.tv_f_10);
        fertilizer_tv[10] = (TextView) findViewById(R.id.tv_f_11);
        fertilizer_tv[11] = (TextView) findViewById(R.id.tv_f_12);

        fertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_dialog.show();
                sendData("fertilizer");
                ll_pesticide.setVisibility(View.GONE);
                ll_seed.setVisibility(View.GONE);

                ll_fertilizer.setVisibility(View.VISIBLE);
            }
        });

        seed_tv[0] = (TextView) findViewById(R.id.tv_s_1);
        seed_tv[1] = (TextView) findViewById(R.id.tv_s_2);
        seed_tv[2] = (TextView) findViewById(R.id.tv_s_3);
        seed_tv[3] = (TextView) findViewById(R.id.tv_s_4);
        seed_tv[4] = (TextView) findViewById(R.id.tv_s_5);
        seed_tv[5] = (TextView) findViewById(R.id.tv_s_6);
        seed_tv[6] = (TextView) findViewById(R.id.tv_s_7);
        seed_tv[7] = (TextView) findViewById(R.id.tv_s_8);
        seed_tv[8] = (TextView) findViewById(R.id.tv_s_9);
        seed_tv[9] = (TextView) findViewById(R.id.tv_s_10);
        seed_tv[10] = (TextView) findViewById(R.id.tv_s_11);
        seed_tv[11] = (TextView) findViewById(R.id.tv_s_12);

        seed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_dialog.show();
                sendData("seed");
                ll_fertilizer.setVisibility(View.GONE);
                ll_pesticide.setVisibility(View.GONE);

                ll_seed.setVisibility(View.VISIBLE);
            }
        });

        //Setting onclick for pesticide
        pesticide_tv[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[0]));
                startActivity(i);
            }
        });
        pesticide_tv[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[1]));
                startActivity(i);
            }
        });
        pesticide_tv[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[2]));
                startActivity(i);
            }
        });
        pesticide_tv[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[3]));
                startActivity(i);
            }
        });
        pesticide_tv[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[4]));
                startActivity(i);
            }
        });
        pesticide_tv[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[5]));
                startActivity(i);
            }
        });
        pesticide_tv[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[6]));
                startActivity(i);
            }
        });
        pesticide_tv[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[7]));
                startActivity(i);
            }
        });
        pesticide_tv[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[8]));
                startActivity(i);
            }
        });
        pesticide_tv[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[9]));
                startActivity(i);
            }
        });
        pesticide_tv[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[10]));
                startActivity(i);
            }
        });
        pesticide_tv[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(pesticide_url[11]));
                startActivity(i);
            }
        });
        // Setting onclick for fertilizer
        fertilizer_tv[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[0]));
                startActivity(i);
            }
        });
        fertilizer_tv[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[1]));
                startActivity(i);
            }
        });
        fertilizer_tv[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[2]));
                startActivity(i);
            }
        });
        fertilizer_tv[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[3]));
                startActivity(i);
            }
        });
        fertilizer_tv[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[4]));
                startActivity(i);
            }
        });
        fertilizer_tv[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[5]));
                startActivity(i);
            }
        });
        fertilizer_tv[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[6]));
                startActivity(i);
            }
        });
        fertilizer_tv[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[7]));
                startActivity(i);
            }
        });
        fertilizer_tv[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[8]));
                startActivity(i);
            }
        });
        fertilizer_tv[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[9]));
                startActivity(i);
            }
        });
        fertilizer_tv[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[10]));
                startActivity(i);
            }
        });
        fertilizer_tv[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fertilizer_url[11]));
                startActivity(i);
            }
        });

        //Setting onclick for seed
        seed_tv[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[0]));
                startActivity(i);
            }
        });
        seed_tv[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[1]));
                startActivity(i);
            }
        });
        seed_tv[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[2]));
                startActivity(i);
            }
        });
        seed_tv[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[3]));
                startActivity(i);
            }
        });
        seed_tv[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[4]));
                startActivity(i);
            }
        });
        seed_tv[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[5]));
                startActivity(i);
            }
        });
        seed_tv[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[6]));
                startActivity(i);
            }
        });
        seed_tv[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[7]));
                startActivity(i);
            }
        });
        seed_tv[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[8]));
                startActivity(i);
            }
        });
        seed_tv[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[9]));
                startActivity(i);
            }
        });
        seed_tv[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[10]));
                startActivity(i);
            }
        });
        seed_tv[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(seed_url[11]));
                startActivity(i);
            }
        });

    }
    String url = "http://ubuntu-server1463.cloudapp.net/prices";
    public void sendData(final String req) {
        Log.d("Check", "Sending data opened");
        RequestParams params = new RequestParams();
        params.put("request",req);
//        params.setUseJsonStreamer(true);

//        params.put("first_name", f_name.getText().toString());


        AsyncHttpClient client = new AsyncHttpClient();


        AsyncHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                load_dialog.dismiss();
//                Log.d("Result", response.toString());
                try {
                    Log.d("Response", response.getString("1"));
                }catch (JSONException e){
                    e.printStackTrace();
                }
                if (req.equals("pesticide")){
                    JSONObject obj ;
                    Integer i=0 ;
                    for (i=0 ; i<12 ; i++) {
                        try {
                            obj = new JSONObject(response.getString(String.valueOf(i)));
                            pesticide_tv[i].setText(obj.getString("productname") + "\nPrice : " + obj.getString("pricet"));
                            pesticide_url[i] = obj.getString("link");
//                            Log.d("pesticide" , obj.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }else if (req.equals("fertilizer")){
                    JSONObject obj ;
                    Integer i=0 ;
                    for (i=0 ; i<12 ; i++) {
                        try {
                            obj = new JSONObject(response.getString(String.valueOf(i)));
                            fertilizer_tv[i].setText(obj.getString("productname") + "\nPrice : " + obj.getString("pricet"));
                            fertilizer_url[i] = obj.getString("link");
//                            Log.d("pesticide" , obj.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }else if (req.equals("seed")){
                    JSONObject obj ;
                    Integer i=0 ;
                    for (i=0 ; i<12 ; i++) {
                        try {
                            obj = new JSONObject(response.getString(String.valueOf(i)));
                            seed_tv[i].setText(obj.getString("productname") + "\nPrice : " + obj.getString("pricet"));
                            seed_url[i] = obj.getString("link");
//                            Log.d("pesticide" , obj.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                load_dialog.dismiss();
                Toast.makeText(Prices.this, "Unable to retrieve data. Please try again", Toast.LENGTH_SHORT).show();
            }
        };


        client.post(url, params, responseHandler);
    }

    @Override
    public void onBackPressed() {
        if ((ll_pesticide.getVisibility() == View.VISIBLE) || (ll_fertilizer.getVisibility() == View.VISIBLE) || (ll_seed.getVisibility() == View.VISIBLE) ) {
            ll_pesticide.setVisibility(View.GONE);
            ll_seed.setVisibility(View.GONE);
            ll_fertilizer.setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }
    }







}

