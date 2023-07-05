package com.apps.phoenix.junioriq;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton fruitBtn = null;
    ImageButton animalBtn = null;
    ImageButton alphabetBtn = null;
    ImageButton numberBtn = null;
    ImageButton monthBtn = null;
    ImageButton weekBtn = null;
    ImageButton drawingBtn = null;
    ImageButton drawingNumberBtn = null;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


        fruitBtn = (ImageButton) findViewById(R.id.fruitId);
        animalBtn = (ImageButton) findViewById(R.id.animalId);
        alphabetBtn = (ImageButton) findViewById(R.id.alphabetId);
        numberBtn = (ImageButton) findViewById(R.id.numberId);
        monthBtn = (ImageButton) findViewById(R.id.monthId);
        weekBtn = (ImageButton) findViewById(R.id.weekId);
        drawingBtn = (ImageButton) findViewById(R.id.drawingId);
        drawingNumberBtn = (ImageButton) findViewById(R.id.drawingNumberId);

        fruitBtn.setOnClickListener(this);
        animalBtn.setOnClickListener(this);
        alphabetBtn.setOnClickListener(this);
        numberBtn.setOnClickListener(this);
        monthBtn.setOnClickListener(this);
        weekBtn.setOnClickListener(this);
        drawingBtn.setOnClickListener(this);
        drawingNumberBtn.setOnClickListener(this);
    }

    public void displayInterstitial() {
        //        if (interstitial.isLoaded()) {
        //            interstitial.show();
        //        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
//        displayInterstitial();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tawmain, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fruitId) {
            Intent fruitIntent = new Intent(MainActivity.this, FruitsActivity.class);
            fruitIntent.putExtra("type", ResourcePool.FRUIT);
            startActivity(fruitIntent);
            return;
        }
        if (v.getId() == R.id.animalId) {
            Intent animalIntent = new Intent(MainActivity.this, FruitsActivity.class);
            animalIntent.putExtra("type", ResourcePool.ANIMAL);
            startActivity(animalIntent);
            return;
        }
        if (v.getId() == R.id.alphabetId) {
            Intent alphabetIntent = new Intent(MainActivity.this, AlphabetActivity.class);
            startActivity(alphabetIntent);
            return;
        }
        if (v.getId() == R.id.numberId) {
            Intent numberIntent = new Intent(MainActivity.this, NumberActivity.class);
            startActivity(numberIntent);
            return;
        }
        if (v.getId() == R.id.monthId) {
            Intent monthIntent = new Intent(MainActivity.this, KnowledgeActivity.class);
            monthIntent.putExtra("type", ResourcePool.MONTH);
            startActivity(monthIntent);
            return;
        }
        if (v.getId() == R.id.weekId) {
            Intent weekIntent = new Intent(MainActivity.this, KnowledgeActivity.class);
            weekIntent.putExtra("type", ResourcePool.WEEK);
            startActivity(weekIntent);
            return;
        }
        if (v.getId() == R.id.drawingId) {
            Intent dawingIntent = new Intent(MainActivity.this, DrawingActivity.class);
            dawingIntent.putExtra("type", ResourcePool.DRAWING_ALPHABET);
            startActivity(dawingIntent);
            return;
        }
        if (v.getId() == R.id.drawingNumberId) {
            Intent dawingNumberIntent = new Intent(MainActivity.this, DrawingActivity.class);
            dawingNumberIntent.putExtra("type", ResourcePool.NUMBER);
            startActivity(dawingNumberIntent);
        }
    }
}