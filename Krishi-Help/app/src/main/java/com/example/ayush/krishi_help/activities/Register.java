package com.example.ayush.krishi_help.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ayush.krishi_help.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ayush on 31/10/16.
 */
public class Register extends Activity{
    EditText f_name,l_name,password,re_password,contact_no,email_id;
    Button submit;


    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        f_name =(EditText) findViewById(R.id.et_f_name) ;
        l_name = (EditText) findViewById(R.id.et_l_name) ;
        email_id = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password) ;
        re_password = (EditText) findViewById(R.id.et_re_password) ;
        contact_no = (EditText) findViewById(R.id.et_contact_no) ;
        submit = (Button) findViewById(R.id.b_new_user) ;


        dialog= new ProgressDialog(Register.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Re-pass", re_password.getText().toString());
                Log.d("pass", password.getText().toString());
//                Toast.makeText(Register.this, re_password.getText().toString() , Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(f_name.getText().toString())) {
                    Toast.makeText(Register.this, "You forgot to enter your first name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(l_name.getText().toString())) {
                    Toast.makeText(Register.this, "You forgot to enter your last name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email_id.getText().toString())) {
                    Toast.makeText(Register.this, "You forgot to enter your email id", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(contact_no.getText().toString())) {
                    Toast.makeText(Register.this, "You forgot to enter your contact number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(Register.this, "You forgot to set the password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(re_password.getText().toString())) {
                    Toast.makeText(Register.this, "You forgot to retype the password", Toast.LENGTH_SHORT).show();
                } else if (!password.getText().toString().equals(re_password.getText().toString())) {
                    Toast.makeText(Register.this, "Sorry! Password did not match", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.setMessage("Signing you up\n  Please Wait... ");
                    dialog.show();
                    sendData();
                }
            }
        });


//        f_name = f_name.getText().toString()

    }

    String url = "http://ubuntu-server1463.cloudapp.net//register";

    public void sendData()
    {
        Log.d("Check", "Sending data opened");
        RequestParams params = new RequestParams();
        params.put("first_name", f_name.getText().toString());
        params.put("last_name", l_name.getText().toString());
        params.put("contact_no", contact_no.getText().toString());
        params.put("password", password.getText().toString());
        params.put("email",email_id.getText().toString());
//        params.setUseJsonStreamer(true);

//        params.put("first_name", f_name.getText().toString());

        AsyncHttpClient client = new AsyncHttpClient();


        AsyncHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getString("status").equals("dup_user")){
                        Toast.makeText(Register.this, "Hi "+f_name.getText().toString()+". You are already registered. Please log in to continue.", Toast.LENGTH_LONG).show();
                        Intent login_page = new Intent("com.example.ayush.krishi_help.activities.homepage") ;
                        startActivity(login_page);
                    }
                    else {
                        Toast.makeText(Register.this, "Hi "+f_name.getText().toString()+". You have been successfully registered", Toast.LENGTH_SHORT).show();
                        Intent login_page = new Intent("com.example.ayush.krishi_help.activities.homepage") ;
                        startActivity(login_page);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

                if (dialog.isShowing())
                    dialog.dismiss();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                dialog.dismiss();
                Toast.makeText(Register.this, "Unable to register \n Please try again later.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                Log.d("post", "Retry " + retryNo);

                dialog.setMessage("Internet Connection Error\n   Trying again...");
                if (!dialog.isShowing())
                {
                    dialog.show();
                }
                if(retryNo==3)
                {
                    dialog.dismiss();
                    Toast.makeText(Register.this, "Registration failed \n Try again later", Toast.LENGTH_SHORT).show();
                }


            }
        };


        client.post(url, params, responseHandler);
//        final AsyncHttpClient client = new AsyncHttpClient();
//        client.setMaxRetriesAndTimeout(3,999);
//        client.post(url, params,new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
//                Log.d("post","Start");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                if
//                Toast.makeText(Register.this, "Hi "+f_name.getText().toString()+". You have been successfully registered", Toast.LENGTH_SHORT).show();
//                Intent login_page = new Intent("com.example.ayush.krishi_help.activities.homepage") ;
//                startActivity(login_page);
//                if (dialog.isShowing())
//                    dialog.dismiss();
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                Log.d("post","Failed");
//                if (dialog.isShowing())
//                    dialog.dismiss();
//                Toast.makeText(Register.this, "Registration failed \n Try again later", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//                Log.d("post", "Retry " + retryNo);
//
//                dialog.setMessage("Internet Connection Error\n   Trying again...");
//                if (!dialog.isShowing())
//                {
//                    dialog.show();
//                }
//                if(retryNo==3)
//                {
//                    dialog.dismiss();
//                    Toast.makeText(Register.this, "Registration failed \n Try again later", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
    }


}
