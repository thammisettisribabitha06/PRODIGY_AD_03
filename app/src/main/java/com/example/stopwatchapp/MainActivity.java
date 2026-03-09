package com.example.stopwatchapp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView timerText;
    Button startBtn, pauseBtn, resetBtn;

    Handler handler = new Handler();
    long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    int seconds, minutes, milliseconds;

    Runnable updateTimer = new Runnable() {
        @Override
        public void run() {

            timeInMilliseconds = System.currentTimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            seconds = (int) (updatedTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliseconds = (int) (updatedTime % 1000);

            timerText.setText(String.format("%02d:%02d:%03d", minutes, seconds, milliseconds));

            handler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timerText);
        startBtn = findViewById(R.id.startBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        resetBtn = findViewById(R.id.resetBtn);

        startBtn.setOnClickListener(v -> {
            startTime = System.currentTimeMillis();
            handler.postDelayed(updateTimer, 0);
        });

        pauseBtn.setOnClickListener(v -> {
            timeSwapBuff += timeInMilliseconds;
            handler.removeCallbacks(updateTimer);
        });

        resetBtn.setOnClickListener(v -> {

            startTime = 0;
            timeSwapBuff = 0;
            timeInMilliseconds = 0;
            updatedTime = 0;

            handler.removeCallbacks(updateTimer);
            timerText.setText("00:00:000");
        });
    }
}