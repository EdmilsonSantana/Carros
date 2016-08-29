package br.com.livroandroid.carros;

import android.app.Application;
import android.util.Log;

import com.squareup.otto.Bus;

/**
 * Created by Edmilson on 11/08/2016.
 */
public class CarrosApplication extends Application {

    private Bus bus = new Bus();

    private static final String TAG = "CarrosApplication";

    private static CarrosApplication instance = null;

    public static CarrosApplication getInstance() {
        return instance;
    }

    public Bus getBus() {
        return bus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "CarrosApplication.onCreate()");
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "CarrosApplication.onTerminate()");
    }
}
