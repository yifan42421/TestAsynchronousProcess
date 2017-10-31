package com.example.byc.testasynchronousprocess;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by byc on 2017/10/30.
 */

public class MIntentService extends IntentService {

    private final static String TAG = "MIntentService--";

    public MIntentService() {
        super("MIntentService");
    }

    public MIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG, Thread.currentThread().getName() + "--" + intent.getStringExtra("info"));

        //Intent服务开启后，执行完onHandleIntent里面的任务就自动销毁结束，
        // 通过打印的线程名称可以发现是新开了一个线程来处理耗时操作的，
        // 即是耗时操作也可以被这个线程管理和执行，同时不会产生ANR的情况
        //直接查看logcat打印的信息即可，主要是threadid
        for (int i = 0; i < 100; i++) {
            Log.i(TAG, "onHandleIntent  " + i + "--" + Thread.currentThread().getName());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }
}
