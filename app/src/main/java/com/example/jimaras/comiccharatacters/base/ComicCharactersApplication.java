package com.example.jimaras.comiccharatacters.base;

import android.app.Application;

import timber.log.Timber;

public class ComicCharactersApplication extends Application {

    public ComicCharactersApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        Timber.i("application got created");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
