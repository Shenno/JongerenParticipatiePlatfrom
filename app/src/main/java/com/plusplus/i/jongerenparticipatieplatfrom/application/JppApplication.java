package com.plusplus.i.jongerenparticipatieplatfrom.application;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.service.JppService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Shenno on 12/04/2015.
 */
public class JppApplication extends Application {
    private static JppService jppService;

    @Override
    public void onCreate() {
        super.onCreate();
        jppService = createDemoService(this);
    }

    public static JppService getJppService() {
        return jppService;
    }

    private JppService createDemoService(Context context) {
        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") // Om DateTimes van C# te converteren
                .create();

        return new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(getString(R.string.baseUrlRetro))
                .setLogLevel(RestAdapter.LogLevel.FULL) // Om af te drukken welke http-calls er effectief gebeuren
                .build()
                .create(JppService.class);
    }
}
