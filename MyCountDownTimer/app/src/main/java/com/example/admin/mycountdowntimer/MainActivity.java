package com.example.admin.mycountdowntimer;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView  mTimerText;
    MyCountDownTimer mTimer;
    FloatingActionButton mFab;
    SoundPool mSoundPool;
    int mSoundResId;

    public class MyCountDownTimer extends CountDownTimer {
        public boolean isRunnning = false;
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minute = millisUntilFinished /1000 /60;
            long second = millisUntilFinished /1000 %60;
            mTimerText.setText(String.format("%1d:%2$02d", minute, second));
            mSoundPool.play(mSoundResId, 1.0f,1.0f,0,0,1.0f);
        }

        @Override
        public void onFinish() {
            mTimerText.setText("0:00");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSoundPool = new SoundPool(2, AudioManager.STREAM_ALARM, 0);
        mSoundResId = mSoundPool.load(this, R.raw.bellsound, 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerText = (TextView)findViewById(R.id.timer_text);
        mTimerText.setText("3:00");
        mTimer = new MyCountDownTimer(3*60*1000, 100);

        mFab = (FloatingActionButton)findViewById(R.id.play_stop);
        mFab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mTimer.isRunnning){
                    mTimer.isRunnning = false;
                    mTimer.cancel();
                    mFab.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }else{
                    mTimer.isRunnning = true;
                    mTimer.start();
                    mFab.setImageResource(R.drawable.ic_stop_black_24dp);
                }
            }
        });

    }
}
