package com.example.byc.testasynchronousprocess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_asynctask;
    private Button btn_intentservice;
    private Button btn_intentservice2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        btn_asynctask = (Button)findViewById(R.id.btn_asynctask);
        btn_asynctask.setOnClickListener(this);

        btn_intentservice = (Button)findViewById(R.id.btn_intentservice);
        btn_intentservice.setOnClickListener(this);

        btn_intentservice2 = (Button)findViewById(R.id.btn_intentservice2);
        btn_intentservice2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_asynctask:
                btnAsyncTask();
                break;
            case R.id.btn_intentservice:
                btnIntentService();
                break;
            case R.id.btn_intentservice2:
                btnIntentService2();
                break;
            default:
                break;
        }
    }

    private void btnAsyncTask(){
        Intent intent = new Intent(MainActivity.this,AsyncTaskActivity.class);
        startActivity(intent);
    }

    private void btnIntentService(){
        Intent intent = new Intent(MainActivity.this,MIntentService.class);
        intent.putExtra("info","hi,are you ok?");
        startService(intent);
    }

    private void btnIntentService2(){
        Intent intent = new Intent(MainActivity.this,IntentServiceActivity.class);
        startActivity(intent);
    }

}
