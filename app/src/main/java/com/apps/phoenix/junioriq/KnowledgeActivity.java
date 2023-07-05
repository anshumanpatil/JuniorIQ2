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

public class KnowledgeActivity extends AppCompatActivity implements OnClickListener, OnTouchListener {
    ImageView nextBtn = null;
    ImageView playBtn = null;
    ImageView prevBtn = null;
    ImageView itemImage = null;
    ResourcePool resourcePool = new ResourcePool();
    private String type = "";
    private int currentPosition = 0;
    private int totalItem = 0;
    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");

        nextBtn = (ImageView) findViewById(R.id.nextId);
        playBtn = (ImageView) findViewById(R.id.playId);
        prevBtn = (ImageView) findViewById(R.id.prevId);
        nextBtn.setOnTouchListener(this);
        playBtn.setOnTouchListener(this);
        prevBtn.setOnTouchListener(this);

        itemImage = (ImageView) findViewById(R.id.knowledgeItemId);

        nextBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        itemImage.setOnClickListener(this);

        if (type.equals(ResourcePool.MONTH)) {
            totalItem = resourcePool.months.length;
            itemImage.setImageResource(resourcePool.months[currentPosition]);
        } else {
            totalItem = resourcePool.weeks.length;
            itemImage.setImageResource(resourcePool.weeks[currentPosition]);
        }
        updatePreviousButton();
        playSound();
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
        }
    }

    private void gotoNext() {
        currentPosition++;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            if (type.equals(ResourcePool.MONTH)) {
                totalItem = resourcePool.months.length;
                itemImage.setImageResource(resourcePool.months[currentPosition]);
            } else {
                totalItem = resourcePool.weeks.length;
                itemImage.setImageResource(resourcePool.weeks[currentPosition]);
            }
            playSound();
        }
    }

    private void gotoPrevious() {
        currentPosition--;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            if (type.equals(ResourcePool.MONTH)) {
                totalItem = resourcePool.months.length;
                itemImage.setImageResource(resourcePool.months[currentPosition]);
            } else {
                totalItem = resourcePool.weeks.length;
                itemImage.setImageResource(resourcePool.weeks[currentPosition]);
            }
            playSound();
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
        if (type.equals(ResourcePool.MONTH)) {
            mediaPlayer = MediaPlayer.create(KnowledgeActivity.this, resourcePool.monthsSound[currentPosition]);
        } else {
            mediaPlayer = MediaPlayer.create(KnowledgeActivity.this, resourcePool.weekSound[currentPosition]);
        }
        mediaPlayer.start();
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