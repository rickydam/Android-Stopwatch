package ricky.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;      // Updates the value of the timer
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Button startButton;
        Button pauseButton;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerValue = (TextView) findViewById (R.id.timerValue);

        startButton = (Button)  findViewById (R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               startTime = SystemClock.uptimeMillis();
               customHandler.postDelayed(updateTimerThread, 0);
           }
        });

        pauseButton = (Button) findViewById (R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });
    }

    private Runnable updateTimerThread = new Runnable() {
      public void run() {
          timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
          updatedTime = timeSwapBuff + timeInMilliseconds;

          int seconds = (int) (updatedTime / 1000);
          int minutes = seconds / 60;
          seconds = seconds % 60;
          int milliseconds = (int) (updatedTime % 1000);

          String string = "";
          string += "" + minutes;
          string += ":" + String.format("%02d", seconds);
          string += ":" + String.format("%03d", milliseconds);

          timerValue.setText(string);
          customHandler.postDelayed(this, 0);
      }
    };
}