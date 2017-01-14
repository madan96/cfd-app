package com.example.ayush.krishi_help.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.krishi_help.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class MainPage extends AppCompatActivity {
    int SELECT_PHOTO = 12;
    ProgressDialog dialog_spinner;
    RequestHandle client_reponse;
    String server_ip ;
    String upload_url,crop_upload_url,disease_upload_url ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        server_ip = getString(R.string.server_ip);
        crop_upload_url = server_ip.concat("/crop_check");
        disease_upload_url = server_ip.concat("/disease_check");
        dialog_spinner= new ProgressDialog(MainPage.this);
        dialog_spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog_spinner.setCanceledOnTouchOutside(false);
        ImageView bFindDisease = (ImageView) findViewById(R.id.agriDiseaseCheck);
        ImageView bFindCrop = (ImageView) findViewById(R.id.agriCropCheck);
        ImageView bPrices = (ImageView) findViewById(R.id.agrimart);
        ImageView bNews = (ImageView) findViewById(R.id.agrinews);

        TextView tvDisease = (TextView) findViewById(R.id.tvAgriCropCheck);
        TextView tvCrop = (TextView) findViewById(R.id.tvAgriDiseaseCheck);
        TextView tvMart = (TextView) findViewById(R.id.tvAgrimart);
        TextView tvNews = (TextView) findViewById(R.id.tvAgrinews);

        tvDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spinner.setMessage("Processing Image");
                upload_url = disease_upload_url;
                imagePicker();
            }
        });

        tvCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spinner.setMessage("Processing Image");
                upload_url = crop_upload_url;
                imagePicker();
            }
        });

        tvMart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prices_activity = new Intent("com.example.ayush.krishi_help.ActivityPricesTabbed");
                startActivity(prices_activity);
            }
        });
        tvNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent news_activity = new Intent("com.example.ayush.krishi_help.ActivityNews");
                startActivity(news_activity);
            }
        });



        bFindDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spinner.setMessage("Processing Image");
                upload_url=disease_upload_url;
                imagePicker();

            }
        });

        bFindCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_spinner.setMessage("Processing Image");
                upload_url = crop_upload_url;
                imagePicker();

            }
        });


        bPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prices_activity = new Intent("com.example.ayush.krishi_help.ActivityPricesTabbed");
                startActivity(prices_activity);
            }
        });

        bNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent news_activity = new Intent("com.example.ayush.krishi_help.ActivityNews");
                startActivity(news_activity);
            }
        });
    }

    public void imagePicker()
    {
//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, SELECT_PHOTO);



        new AlertDialog.Builder(MainPage.this).setTitle("Select Picture")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        } else if (items[item].equals("Gallery")) {
                            if (Build.VERSION.SDK_INT <= 19) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                            } else if (Build.VERSION.SDK_INT > 19) {
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                            }
                        }else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                }).show();

    }





//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK)
//        {
//            if (requestCode==SELECT_PHOTO)
//            {
//                try {
//                    final Uri imageUri = data.getData();
//                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//
//                    AsyncHttpClient client= new AsyncHttpClient();
//                    RequestParams params = new RequestParams();
//                    params.put("file",imageStream);
//                    AsyncHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                            super.onSuccess(statusCode, headers, response);
//                            try {
//                                if (response.getInt("status")==1) {
//                                    Log.d("MainPage", "Success");
//                                }
//                                Log.d("JSON", response.getString("status"));
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                            super.onFailure(statusCode, headers, responseString, throwable);
//                        }
//                    };
//
//                    client.post(url, params, responseHandler);
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }

    private void sendFile(final File f) throws FileNotFoundException
    {
        dialog_spinner.show();
        final AsyncHttpClient client= new AsyncHttpClient();

                    RequestParams params = new RequestParams();
                    params.put("file",f);
                    AsyncHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                if (response.getInt("status")==1) {
                                    dialog_spinner.dismiss();
                                    Bundle b = new Bundle() ;
                                    b.putString("path" , f.getAbsolutePath());
                                    b.putString("data" , response.getString("data"));
                                    Intent disease = new Intent("com.example.ayush.krishi_help.ActivityDisease");
                                    disease.putExtras(b);
                                    startActivity(disease);
                                    Log.d("MainPage", response.toString());
                                }
                                else if (response.getInt("status")==0){
                                    dialog_spinner.dismiss();
                                    Toast.makeText(MainPage.this, "Unknown error occured. Please try again !" , Toast.LENGTH_SHORT).show();
                                }
                                else if (response.getInt("status")==2)  //image does not have leaf
                                {
                                    dialog_spinner.dismiss();
                                    Toast.makeText(MainPage.this, "Error! No leaf found in the given image." , Toast.LENGTH_LONG).show();

                                }
                                    Log.d("JSON", response.getString("status"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onRetry(int retryNo) {
                            super.onRetry(retryNo);
                            if (dialog_spinner.isShowing()) {
                                Toast.makeText(MainPage.this, "Upload failed due to connection error. Trying again!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                client_reponse.cancel(true);
                            }
                            if (retryNo == 2) {
                                dialog_spinner.dismiss();
                                Log.d("upload", "fail after three reply");
                                Toast.makeText(MainPage.this, "Upload failed. Please try again later!" , Toast.LENGTH_SHORT).show();
                                client_reponse.cancel(true);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            dialog_spinner.dismiss();
                            Log.d("upload","fail");
                            Toast.makeText(MainPage.this, "Upload failed. Please try again !" , Toast.LENGTH_SHORT).show();
                        }
                    };

        client_reponse =  client.post(upload_url, params, responseHandler);
    }


    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_CAMERA = 2;

    final String[] items = new String[]{"Camera", "Gallery", "Cancel"};



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImageUri = data.getData();
            String selectedImagePath = getRealPathFromURIForGallery(selectedImageUri);
            decodeFile(selectedImagePath);
        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && null != data) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(MainPage.this.getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));
            decodeFile(finalFile.toString());
        }
    }
    public String getRealPathFromURIForGallery(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = MainPage.this.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = MainPage.this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }



    public void decodeFile(String path){
        File f = new File(path);
        if (f.exists())
        {
            try {
                Log.d("MainPage","Sending file ");
                sendFile(f);
            }catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(MainPage.this, "File error", Toast.LENGTH_SHORT).show();
        }
    }
}
