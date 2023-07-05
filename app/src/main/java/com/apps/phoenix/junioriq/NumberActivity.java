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

public class NumberActivity extends AppCompatActivity implements OnClickListener, OnTouchListener {
    ImageView nextBtn = null;
    ImageView playBtn = null;
    ImageView prevBtn = null;

    ImageView itemImage = null;
    ImageView itemName = null;
    ResourcePool resourcePool = new ResourcePool();
    private int currentPosition = 0;
    private int totalItem = 0;
    private MediaPlayer mp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        nextBtn = (ImageView) findViewById(R.id.nextId);
        playBtn = (ImageView) findViewById(R.id.playId);
        prevBtn = (ImageView) findViewById(R.id.prevId);
        nextBtn.setOnTouchListener(this);
        playBtn.setOnTouchListener(this);
        prevBtn.setOnTouchListener(this);

        itemImage = (ImageView) findViewById(R.id.numberItemId);
        itemName = (ImageView) findViewById(R.id.numberImageId);

        nextBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        itemImage.setOnClickListener(this);
        itemName.setOnClickListener(this);

        totalItem = resourcePool.numberImages.length;
        itemImage.setImageResource(resourcePool.numberImages[currentPosition]);
        itemName.setImageResource(resourcePool.numberPics[currentPosition]);
        updatePreviousButton();

        mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberSounds[currentPosition]);
        mp.start();
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
            mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberSounds[currentPosition]);
            mp.start();
            return;
        }
        if (v.getId() == R.id.numberItemId) {
            mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberSounds[currentPosition]);
            mp.start();
            return;
        }
        if (v.getId() == R.id.numberImageId) {
            mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberPicSounds[currentPosition]);
            mp.start();
        }
    }

    private void gotoNext() {
        currentPosition++;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            itemImage.setImageResource(resourcePool.numberImages[currentPosition]);
            itemName.setImageResource(resourcePool.numberPics[currentPosition]);
            mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberSounds[currentPosition]);
            mp.start();
        }
    }

    private void gotoPrevious() {
        currentPosition--;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            itemImage.setImageResource(resourcePool.numberImages[currentPosition]);
            itemName.setImageResource(resourcePool.numberPics[currentPosition]);
            mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberSounds[currentPosition]);
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