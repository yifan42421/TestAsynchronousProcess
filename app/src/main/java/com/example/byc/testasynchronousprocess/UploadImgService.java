package com.example.byc.testasynchronousprocess;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploadImgService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPLOAD_IMG = "com.example.byc.testasynchronousprocess.action.UPLOAD_IMAGE";

    // TODO: Rename parameters
    public static final String EXTRA_IMG_PATH = "com.example.byc.testasynchronousprocess.extra.IMG_PATH";

    private final static String TAG = "UploadImgService--";

    public static void startUploadImg(Context context, String path) {
        Intent intent = new Intent(context, UploadImgService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH, path);
        context.startService(intent);
    }

    public UploadImgService() {
        super("UploadImgService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPLOAD_IMG.equals(action)) {
                final String path = intent.getStringExtra(EXTRA_IMG_PATH);
                handleActionUploadImg(path);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUploadImg(String param1) {
        Log.e(TAG, "handleActionUploadImg-" + Thread.currentThread().getName() + "--" + param1);
        try {
            Thread.sleep(3000);
            Intent intent = new Intent(IntentServiceActivity.UPLOAD_RESULT);
            intent.putExtra(EXTRA_IMG_PATH, param1);
            //直接用sendBroadcast，在IntentServiceActivity中接收不到，因为IntentServiceActivity中是用LocalBroadcastManager注册的广播
            LocalBroadcastManager.getInstance(UploadImgService.this).sendBroadcast(intent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }
}
