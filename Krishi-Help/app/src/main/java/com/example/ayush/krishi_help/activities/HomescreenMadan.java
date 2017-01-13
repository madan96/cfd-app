package com.example.ayush.krishi_help.activities;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomescreenMadan extends AppCompatActivity {

    public ImageView img, about, news, contact, event, location, sponsor, register, schedule;
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen_madan);
        img = (ImageView) findViewById(R.id.imgView);
        about = (ImageView) findViewById(R.id.about);
        news = (ImageView) findViewById(R.id.news);
        contact = (ImageView) findViewById(R.id.contact);
        event = (ImageView) findViewById(R.id.events);
        location = (ImageView) findViewById(R.id.location);
        sponsor = (ImageView) findViewById(R.id.sponsors);
        register = (ImageView) findViewById(R.id.register);
        schedule = (ImageView) findViewById(R.id.schedule);
        tv= (TextView)findViewById(R.id.tv);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setVisibility(View.INVISIBLE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        about.setVisibility(View.VISIBLE);
                    }
                }, 100);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        event.setVisibility(View.VISIBLE);
                    }
                }, 200);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        register.setVisibility(View.VISIBLE);

                    }
                }, 300);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        schedule.setVisibility(View.VISIBLE);

                    }
                }, 400);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        location.setVisibility(View.VISIBLE);
                    }
                }, 500);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sponsor.setVisibility(View.VISIBLE);
                    }
                }, 600);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        news.setVisibility(View.VISIBLE);

                    }
                }, 700);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        contact.setVisibility(View.VISIBLE);

                    }
                }, 800);
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                location.setAlpha(0.2f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void run() {
                        location.setAlpha(1f);
                    }
                }, 25);


                Intent fIntent = new Intent(HomescreenMadan.this, MapsActivity.class);
                startActivity(fIntent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                about.setAlpha(0.2f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        about.setAlpha(1f);
                    }
                }, 25);


                Intent fIntent = new Intent(HomescreenMadan.this, AboutUs.class);
                startActivity(fIntent);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                news.setAlpha(0.2f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        news.setAlpha(1f);
                    }
                }, 25);


                Intent fIntent = new Intent(HomescreenMadan.this, New.class);
                startActivity(fIntent);
            }
        });

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event.setAlpha(0.2f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        event.setAlpha(1f);
                    }
                }, 25);


                Intent fIntent = new Intent(HomescreenMadan.this, Events.class);
                startActivity(fIntent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register.setAlpha(0.2f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        register.setAlpha(1f);
                    }
                }, 25);

                if (haveNetworkConnection() == true) {
                    Intent fIntent = new Intent(HomescreenMadan.this, Registration.class);
                    startActivity(fIntent);
                } else {
                    Toast.makeText(HomescreenMadan.this, "Sorry! You Need Internet Connection To Register", Toast.LENGTH_SHORT).show();
                }
            }
        });


        sponsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sponsor.setAlpha(0.2f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sponsor.setAlpha(1f);
                    }
                }, 25);


                Intent fIntent = new Intent(HomescreenMadan.this, Sponsors.class);
                startActivity(fIntent);
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schedule.setAlpha(0.2f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        schedule.setAlpha(1f);
                    }
                }, 25);


                Intent fIntent = new Intent(HomescreenMadan.this, Schedule.class);
                startActivity(fIntent);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact.setAlpha(0.2f);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        contact.setAlpha(1f);
                    }
                }, 25);


                Intent fIntent = new Intent(HomescreenMadan.this, ContactUs.class);
                startActivity(fIntent);
            }
        });
    }

    private boolean haveNetworkConnection(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                //Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                //Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                return  true;
            }
            else{
                return false;
            }
        } else {
            // not connected to the internet
            return false;
        }

    }

    public void traceBack()
    {
        //  Toast.makeText(this, "Please click BACK again to exit",Toast.LENGTH_SHORT).show();
        contact.setVisibility(View.INVISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                news.setVisibility(View.INVISIBLE);
            }
        }, 100);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sponsor.setVisibility(View.INVISIBLE);
            }
        }, 200);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                location.setVisibility(View.INVISIBLE);

            }
        }, 300);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                schedule.setVisibility(View.INVISIBLE);

            }
        }, 400);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                register.setVisibility(View.INVISIBLE);
            }
        }, 500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                event.setVisibility(View.INVISIBLE);
            }
        }, 600);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                about.setVisibility(View.INVISIBLE);

            }
        }, 700);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setVisibility(View.VISIBLE);

            }
        }, 800);
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce==true) {
            moveTaskToBack(true);
            killApplication("in.co.eloquence.newfirebase");
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press Back Again To Exit",Toast.LENGTH_SHORT).show();
        traceBack();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 3000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);

        switch(item.getItemId())
        {
            case R.id.facebook :
                String url = "https://www.facebook.com/groups/1757497641175266/";
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;

            case R.id.instagram :
                String url2 = "https://www.instagram.com/eloquence.bpit/";
                Uri webpage2 = Uri.parse(url2);
                Intent intent2 = new Intent(Intent.ACTION_VIEW, webpage2);
                if (intent2.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent2);
                }

                break;

            case R.id.aboutDev:
                Intent devIntent = new Intent(this, Developers.class);
                startActivity(devIntent);
                break;
        }

        return true;
    }

    public void killApplication(String killPackage){
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        android.os.Process.killProcess(android.os.Process.myPid() );
        am.killBackgroundProcesses(killPackage);
    }
}

