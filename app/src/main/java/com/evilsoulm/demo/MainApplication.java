package com.evilsoulm.demo;

import android.app.Application;
import android.util.Log;

import com.evilsoulm.memorywatcher.MemoryListener;
import com.evilsoulm.memorywatcher.MemoryWatcher;
import com.evilsoulm.memorywatcher.Unit;

/**
 * Author by EvilsoulM
 * Data:2015-12-18 12:49
 * Project:MemoryWatcher
 * Detail:
 */
public class MainApplication extends Application {

    private static final String TAG = "EvilsoulM";

    private MemoryWatcher memoryWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        initMemoryWatcher();
    }

    private void initMemoryWatcher() {
        MemoryWatcher.Builder builder = new MemoryWatcher.Builder().setInterval(5 * 1000).setListener(new MemoryListener() {
            @Override
            public void onMemorySize(float size) {
                Log.i(TAG, "size->" + size);
                if (size > 100) {//eg 大雨100MB内存
                    //TODO clear memory cache
                }
            }
        }).setUnit(Unit.MB);

        memoryWatcher = builder.build();
        memoryWatcher.start();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate() called with: " + "");
        memoryWatcher.stopWatch();
        memoryWatcher = null;
    }
}
