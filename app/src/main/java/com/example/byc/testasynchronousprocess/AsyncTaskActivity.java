package com.example.byc.testasynchronousprocess;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AsyncTaskActivity extends AppCompatActivity {

    private final static String TAG = "AsyncTaskActivity";
    private Button btn_excute;
    private Button btn_cancle;
    private ProgressBar progressBar;
    private TextView tv;
    private MyTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        initView();
    }

    private void initView() {
        btn_excute = (Button) findViewById(R.id.btn_excute);
        btn_excute.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //注意每次需new一个实例,新建的任务只能执行一次,否则会出现异常
                task = new MyTask();
                task.execute("https://www.baidu.com");
                btn_excute.setEnabled(false);
                btn_cancle.setEnabled(true);
            }
        });
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //取消一个正在执行的任务,onCancelled方法将会被调用
                task.cancel(true);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        tv = (TextView) findViewById(R.id.tv);
    }

    private class MyTask extends AsyncTask<String, Integer, String> {

        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute called");
            tv.setText("loading");
        }

        //doInBackground方法内部执行后台任务，不可在此方法内修改UI
        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "doInBackground called string = " + strings[0]);
            try {
                //打开链接
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //必须加上下面三句，要不total为-1
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Encoding", "identity");
                urlConnection.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");

                if (200 == urlConnection.getResponseCode()) {
                    Log.i(TAG, "200");
                    //得到输入流
                    InputStream is = urlConnection.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    int count = 0;

                    long total = urlConnection.getContentLength();
                    Log.i(TAG, "total = " + total);
                    while (-1 != (len = is.read(buffer))) {
                        baos.write(buffer, 0, len);
                        count += len;
                        publishProgress((int) ((count / (float) total) * 100));
                        Log.i(TAG, " baos = " + baos.toString("utf-8"));
                        Thread.sleep(500);
                    }
                    Log.i(TAG, " baos = " + baos.toString("utf-8"));
                    return baos.toString("utf-8");
                } else {
                    Log.i(TAG, " 系统错误 code = " + urlConnection.getResponseCode());
                }
            } catch (MalformedURLException e) {
                Log.i(TAG, "URLException = " + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.i(TAG, "IOException = " + e.toString());
                e.printStackTrace();
            } catch (InterruptedException e) {
                Log.i(TAG, "InterruptedException = " + e.toString());
                e.printStackTrace();
            }
            return null;
        }

        //执行完后台任务后，用于更新UI界面，显示结果
        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, "onPostExecute called s=" + s);
            tv.setText(s);
            btn_excute.setEnabled(true);
            btn_cancle.setEnabled(false);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "onProgressUpdate called");
            progressBar.setProgress(values[0]);
            tv.setText("loading... " + values[0] + "%");
        }

        @Override
        protected void onCancelled() {
            Log.i(TAG, "onCancelled called");
            tv.setText("onCancelled");
            progressBar.setProgress(0);
            btn_excute.setEnabled(true);
            btn_cancle.setEnabled(false);
        }
    }
}
