package com.qc.advertisement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.qc.advertisement.util.Constants;
import com.qc.advertisement.util.SharedPreferencesUtils;

import java.util.Timer;
import java.util.TimerTask;



public class SplashActivity extends AppCompatActivity {

    private TextView mTime;
    private int recLen = 3;
    private Timer mTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        intiView();
        initData();
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    recLen --;
                    mTime.setText("" + recLen);
                    if(recLen == 0)
                    {
                        mTimer.cancel();
                        Boolean b = SharedPreferencesUtils.getBoolean(SplashActivity.this, Constants.ISFIRSTINTO,true);
                        if (b){
                            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                        }else{
                            startActivity(new Intent(SplashActivity.this, IndexActivity.class));
                        }
                        finish();

                    }
                }
            });

        }
    };

    private void initData() {
        mTimer.schedule(task,1000,1000);
    }

    private void intiView() {
        mTime = (TextView) findViewById(R.id.time);
    }
}