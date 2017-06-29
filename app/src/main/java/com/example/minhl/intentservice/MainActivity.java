package com.example.minhl.intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //code mẫu theo trên mang, tự code :D
    private ProgressBar progressBar;
    private Button btn;
    private TextView tv;
    private Intent serviceIntent;
    private ResponseReceiver receiver = new ResponseReceiver();

    //làm 1 class nhận Intent từ service gửi đi
    public class ResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SimpleIntentService.ACTION_1)) {
                int value = intent.getIntExtra("percel", 0);
                new ShowProgressBarTask().execute(value);
            }
        }
    }

    //Synctask thay đổi giá trị của progessbar
    class ShowProgressBarTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            return params[0];//value
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            progressBar.setProgress(result);
            tv.setText(result + " % Loaded");
            if (result == 100) {
                tv.setText("Completed");
                btn.setEnabled(true);//ko cho bấm nút start :D
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.pb);
        tv = (TextView) findViewById(R.id.tv);
        btn = (Button) findViewById(R.id.btn);
    }

    //resume thì gắn kết cái receiver
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(SimpleIntentService.ACTION_1));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    //chạy service như unbound service
    public void starProgessBar(View view) {
        btn.setEnabled(false);
        serviceIntent = new Intent(this, SimpleIntentService.class);
        startService(serviceIntent);
    }

}
