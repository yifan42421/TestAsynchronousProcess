package com.example.byc.testasynchronousprocess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntentServiceActivity extends AppCompatActivity {

    public static final String UPLOAD_RESULT = "UPLOAD_RESULT";
    private Button btn_add_task;
    private LinearLayout cur_linearlayout;
    private int i = 0;
    private LocalBroadcastManager lbm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        initView();
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() == UPLOAD_RESULT){
                handlePath(intent.getStringExtra(UploadImgService.EXTRA_IMG_PATH));
            }
        }
    };
    private void initView(){
        cur_linearlayout = (LinearLayout)findViewById(R.id.cur_linearlayout);
        btn_add_task = (Button)findViewById(R.id.add_task);
        btn_add_task.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        lbm  = LocalBroadcastManager.getInstance(this);


        lbm.registerReceiver(br,new IntentFilter(UPLOAD_RESULT));
    }

    private void handlePath(String path){
        TextView tv = (TextView)cur_linearlayout.findViewWithTag(path);
        tv.setText(path + " upload success ~~~~ ");
    }

    public void addTask(){
        //模拟路径
        String path = "/sdcard/imgs/"+(++i)+".png";
        UploadImgService.startUploadImg(this,path);

        TextView tv = new TextView(this);
        cur_linearlayout.addView(tv);
        tv.setText(path + " is uploading...");
        tv.setTag(path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lbm.unregisterReceiver(br);
    }
}
