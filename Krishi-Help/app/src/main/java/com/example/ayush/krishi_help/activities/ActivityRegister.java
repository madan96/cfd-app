package com.example.ayush.krishi_help.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by ayush on 14/01/17.
 */
public class ActivityRegister extends AppCompatActivity {
    EditText f_name,l_name,password,re_password,contact_no,email_id;
    String server_ip,url ;
    ProgressDialog dialog;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        server_ip = getString(R.string.server_ip);

        f_name =(EditText) findViewById(R.id.et_f_name) ;
        l_name = (EditText) findViewById(R.id.et_l_name) ;
        email_id = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password) ;
        re_password = (EditText) findViewById(R.id.et_re_password) ;
        contact_no = (EditText) findViewById(R.id.et_contact_no) ;
        register = (Button) findViewById(R.id.btn_signup) ;

        dialog= new ProgressDialog(ActivityRegister.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = server_ip.concat("/register");
                    if (TextUtils.isEmpty(f_name.getText().toString())) {
                        Toast.makeText(ActivityRegister.this, getString(R.string.activity_register_no_firstname), Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(l_name.getText().toString())) {
                        Toast.makeText(ActivityRegister.this, getString(R.string.activity_register_no_lastname), Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(email_id.getText().toString())) {
                        Toast.makeText(ActivityRegister.this, getString(R.string.activity_register_no_emailid), Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(contact_no.getText().toString())) {
                        Toast.makeText(ActivityRegister.this, getString(R.string.activity_register_no_contactno), Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(password.getText().toString())) {
                        Toast.makeText(ActivityRegister.this, getString(R.string.activity_register_no_password), Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(re_password.getText().toString())) {
                        Toast.makeText(ActivityRegister.this, getString(R.string.activity_register_no_repassword), Toast.LENGTH_SHORT).show();
                    } else if (!password.getText().toString().equals(re_password.getText().toString())) {
                        Toast.makeText(ActivityRegister.this, getString(R.string.activity_register_passwords_not_match), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.setMessage(getString(R.string.activity_register_register_dialog));
                        dialog.show();
                        sendData(0);
                    }
                
//                Intent register_new_user = new Intent("com.example.ayush.krishi_help.register");
//                startActivity(register_new_user);
            }
        });

    }

    public void sendData(Integer flag) {
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

//                String str = new String(bytes, "UTF-8");
                try {
                    String login_status;
                    login_status = response.getString("status");
                    if (response.getString("status").equals("dup_user")){
                        dialog.dismiss();
                        Toast.makeText(ActivityRegister.this, getString(R.string.activity_register_duplicateuser_1)+f_name.getText().toString()+getString(R.string.activity_register_duplicateuser_2), Toast.LENGTH_LONG).show();
                            Intent fIntent = new Intent(ActivityRegister.this, ActivityLogin.class);
                            startActivity(fIntent);
                    }
                    else if (response.getString("status").equals("ok")) {
                        dialog.dismiss();
                        Toast.makeText(ActivityRegister.this,getString(R.string.activity_register_success_register_1)+f_name.getText().toString()+getString(R.string.activity_register_success_register_2), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityRegister.this, MainPageMadan.class));

                    }
                    Log.d("JSON", response.getString("status"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                response.getInt("status");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                dialog.dismiss();
                Toast.makeText(ActivityRegister.this, getString(R.string.activity_register_error), Toast.LENGTH_SHORT).show();
            }
        };


        client.post(url, params, responseHandler);
    }
}
