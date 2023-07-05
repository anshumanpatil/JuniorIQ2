package com.apps.phoenix.junioriq;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AlphabetActivity extends AppCompatActivity implements OnClickListener, OnTouchListener {

    ImageView nextBtn = null;
    ImageView playBtn = null;
    ImageView prevBtn = null;

    ImageView itemCapital = null;
    ImageView itemSmall = null;
    ImageView itemImage = null;
    ImageView itemImageName = null;
    ResourcePool resourcePool = new ResourcePool();
    private int currentPosition = 0;
    private int totalItem = 0;
    private MediaPlayer mp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alphabet);

        nextBtn = (ImageView) findViewById(R.id.nextId);
        playBtn = (ImageView) findViewById(R.id.playId);
        prevBtn = (ImageView) findViewById(R.id.prevId);
        nextBtn.setOnTouchListener(this);
        playBtn.setOnTouchListener(this);
        prevBtn.setOnTouchListener(this);

        itemCapital = (ImageView) findViewById(R.id.capitalItemId);
        itemImage = (ImageView) findViewById(R.id.alphabetImageId);

        nextBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        itemCapital.setOnClickListener(this);
        itemImage.setOnClickListener(this);

        totalItem = resourcePool.alphabetCapital.length;
        itemCapital.setImageResource(resourcePool.alphabetCapital[currentPosition]);
        itemImage.setImageResource(resourcePool.alphabetImage[currentPosition]);

        updateNextButton();
        mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetSound[currentPosition]);
        mp.start();

//        AdView adView = (AdView)this.findViewById(R.id.adView);
//		/*adView.setAdSize( AdSize.BANNER);
//		adView.setAdUnitId("ca-app-pub-1219685878939660/2634943234");*/
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//        adView.loadAd(adRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tawmain, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nextId) {
            gotoNext();
            return;
        }
        if (v.getId() == R.id.prevId) {
            gotoPrevious();
            return;
        }
        if (v.getId() == R.id.playId) {
            playSound();
            return;
        }
        if (v.getId() == R.id.capitalItemId) {
            mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetSound[currentPosition]);
            mp.start();
            return;
        }
        if (v.getId() == R.id.alphabetImageId) {
            mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetImageSound[currentPosition]);
            mp.start();
        }
    }

    private void gotoNext() {
        currentPosition++;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            itemCapital.setImageResource(resourcePool.alphabetCapital[currentPosition]);
            itemImage.setImageResource(resourcePool.alphabetImage[currentPosition]);
            mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetSound[currentPosition]);
            mp.start();
        }
    }

    private void gotoPrevious() {
        currentPosition--;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            itemCapital.setImageResource(resourcePool.alphabetCapital[currentPosition]);
            itemImage.setImageResource(resourcePool.alphabetImage[currentPosition]);
            mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetSound[currentPosition]);
            mp.start();
        }
    }

    private void updateNextButton() {
        if (currentPosition == totalItem - 1) {
            nextBtn.setAlpha(0.5f);
            nextBtn.setClickable(false);
        } else {
            nextBtn.setAlpha(1f);
            nextBtn.setClickable(true);
        }
    }

    private void updatePreviousButton() {
        if (currentPosition == 0) {
            prevBtn.setAlpha(0.5f);
            prevBtn.setClickable(false);
        } else {
            prevBtn.setAlpha(1f);
            prevBtn.setClickable(true);
        }
    }

    private void playSound() {
        mp = MediaPlayer.create(AlphabetActivity.this, resourcePool.alphabetSound[currentPosition]);
        mp.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.5f);
                break;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1.0f);
                break;

            default:
                break;
        }
        return false;
    }
}