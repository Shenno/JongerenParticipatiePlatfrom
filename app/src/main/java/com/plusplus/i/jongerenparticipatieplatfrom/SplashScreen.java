package com.plusplus.i.jongerenparticipatieplatfrom;

/**
 * Created by Maxim on 31/03/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        haveNetworkConnection();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;


        }
        if (!haveConnectedMobile && !haveConnectedWifi){
            //Toast.makeText(getApplicationContext(),"Je hebt een netwerkverbinding nodig om deze app te kunnen gebruiken",Toast.LENGTH_LONG).show();
            AppMsg.makeText(this,"Je hebt een netwerkverbinding nodig om deze app te kunnen gebruiken",AppMsg.STYLE_INFO).show();
            SPLASH_TIME_OUT = 3000;



        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}