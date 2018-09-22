package com.example.yuya2.myslideshow;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageSwitcher mImageSwitcher;
    int[] mImageResources = {R.drawable.slide00,R.drawable.slide01,
            R.drawable.slide02,R.drawable.slide03,
            R.drawable.slide04,R.drawable.slide05,
            R.drawable.slide06,R.drawable.slide07,
            R.drawable.slide08,R.drawable.slide09};
    int mPosition = 0;

    boolean mIsSlideshow = false;
    MediaPlayer mMediaPlayer;

    public class MainTimerTask extends TimerTask {
        @Override
        public void run(){
            if(mIsSlideshow){
                mHandler.post(new Runnable(){
                    @Override public void run(){
                        movePosition(1);
                    }
                });
            }
        }
    }


    Timer mTimer = new Timer();
    TimerTask mTimerTask = new MainTimerTask();
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory(){
              @Override
              public View makeView() {
                  ImageView imageView = new ImageView(getApplicationContext());
                  return imageView;
              }
          }
        );
        mImageSwitcher.setImageResource(mImageResources[0]);

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new OnClickListener(){
             @Override
             public void onClick(View v) {
                 onAnimationButtonTapped(v);
             }
         }
        );
        Button prevButton = (Button)findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onPrevButtonTapped(v);
            }
        });
        Button nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonTapped(v);
            }
        });
        Button slideShowButton = (Button)findViewById(R.id.slideshowButton);
        slideShowButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSlideshowButtonTapped(v);
            }
        });

        mTimer.schedule(mTimerTask, 0, 5000);
        mMediaPlayer = MediaPlayer.create(this, R.raw.getdown);
        mMediaPlayer.setLooping(true);
    }
    public void onAnimationButtonTapped(View view){
//        ViewPropertyAnimator animator = view.animate();
//        animator.setDuration(3000);
//        animator.rotation(360.0f * 5.0f);

        //write single line
//        view.animate().setDuration(2500).rotation(360.0f * 6.0f).scaleX(2.5f).scaleY(3.0f).x(150.0f).y(300.0f);

        //Add animation effect
        float y = view.getY() + 100;
        view.animate().setDuration(1000).setInterpolator(new BounceInterpolator()).y(y);
    }
    private void movePosition(int move){\

        mPosition = mPosition + move;
        if(mPosition >= mImageResources.length){
            mPosition = 0;
        }else if(mPosition < 0){
            mPosition = mImageResources.length -1;
        }
        mImageSwitcher.setImageResource(mImageResources[mPosition]);
    }
    public void onPrevButtonTapped(View view){
//        mImageSwitcher.setInAnimation(this, android.R.anim.overshoot_interpolator);
        mImageSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        movePosition(-1);
        findViewById(R.id.imageView).animate().setDuration(1000).alpha(0.0f);
    }
    public void onNextButtonTapped(View view){
        mImageSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
        mImageSwitcher.setOutAnimation(this,android.R.anim.slide_out_right);
        movePosition(1);
        findViewById(R.id.imageView).animate().setDuration(1000).alpha(0.0f);
    }
    public void onSlideshowButtonTapped(View view){
        mIsSlideshow = !mIsSlideshow;
        if(mIsSlideshow){
            mMediaPlayer.start();
        }else{
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0);
        }
    }
}
