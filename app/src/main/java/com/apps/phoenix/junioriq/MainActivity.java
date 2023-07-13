package com.apps.phoenix.junioriq;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton fruitBtn = null;
    ImageButton animalBtn = null;
    ImageButton alphabetBtn = null;
    ImageButton numberBtn = null;
    ImageButton monthBtn = null;
    ImageButton weekBtn = null;
    ImageButton drawingBtn = null;
    ImageButton drawingNumberBtn = null;
    private InterstitialAd interstitialAd;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String TAG = "JuniorIQ";
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

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        loadAd();

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
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        MainActivity.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
//                        Toast.makeText(MainActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;

//                        String error =
//                                String.format(
//                                        "domain: %s, code: %d, message: %s",
//                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
//                        Toast.makeText(
//                                        MainActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
//                                .show();
                    }
                });
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
            if (interstitialAd != null) {
                interstitialAd.show(this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadAd();
                        startActivity(fruitIntent);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                        loadAd();
                        startActivity(fruitIntent);
                    }
                });
            } else {
                loadAd();
                startActivity(fruitIntent);
            }

            return;
        }
        if (v.getId() == R.id.animalId) {
            Intent animalIntent = new Intent(MainActivity.this, FruitsActivity.class);
            animalIntent.putExtra("type", ResourcePool.ANIMAL);

            if (interstitialAd != null) {
                interstitialAd.show(this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadAd();
                        startActivity(animalIntent);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                        loadAd();
                        startActivity(animalIntent);
                    }
                });
            } else {
                loadAd();
                startActivity(animalIntent);
            }

            return;
        }
        if (v.getId() == R.id.alphabetId) {
            Intent alphabetIntent = new Intent(MainActivity.this, AlphabetActivity.class);

            if (interstitialAd != null) {
                interstitialAd.show(this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadAd();
                        startActivity(alphabetIntent);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                        loadAd();
                        startActivity(alphabetIntent);
                    }
                });
            } else {
                loadAd();
                startActivity(alphabetIntent);
            }

            return;
        }
        if (v.getId() == R.id.numberId) {
            Intent numberIntent = new Intent(MainActivity.this, NumberActivity.class);

            if (interstitialAd != null) {
                interstitialAd.show(this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadAd();
                        startActivity(numberIntent);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                        loadAd();
                        startActivity(numberIntent);
                    }
                });
            } else {
                loadAd();
                startActivity(numberIntent);
            }

            return;
        }
        if (v.getId() == R.id.monthId) {
            Intent monthIntent = new Intent(MainActivity.this, KnowledgeActivity.class);
            monthIntent.putExtra("type", ResourcePool.MONTH);

            if (interstitialAd != null) {
                interstitialAd.show(this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadAd();
                        startActivity(monthIntent);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                        loadAd();
                        startActivity(monthIntent);
                    }
                });
            } else {
                loadAd();
                startActivity(monthIntent);
            }


            return;
        }
        if (v.getId() == R.id.weekId) {
            Intent weekIntent = new Intent(MainActivity.this, KnowledgeActivity.class);
            weekIntent.putExtra("type", ResourcePool.WEEK);

            if (interstitialAd != null) {
                interstitialAd.show(this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadAd();
                        startActivity(weekIntent);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                        loadAd();
                        startActivity(weekIntent);
                    }
                });
            } else {
                loadAd();
                startActivity(weekIntent);
            }

            return;
        }
        if (v.getId() == R.id.drawingId) {
            Intent dawingIntent = new Intent(MainActivity.this, DrawingActivity.class);
            dawingIntent.putExtra("type", ResourcePool.DRAWING_ALPHABET);
            if (interstitialAd != null) {
                interstitialAd.show(this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadAd();
                        startActivity(dawingIntent);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                        loadAd();
                        startActivity(dawingIntent);
                    }
                });
            } else {
                loadAd();
                startActivity(dawingIntent);
            }
            return;
        }
        if (v.getId() == R.id.drawingNumberId) {
            Intent dawingNumberIntent = new Intent(MainActivity.this, DrawingActivity.class);
            dawingNumberIntent.putExtra("type", ResourcePool.NUMBER);
            if (interstitialAd != null) {
                interstitialAd.show(this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        loadAd();
                        startActivity(dawingNumberIntent);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d("TAG", "The ad failed to show.");
                        loadAd();
                        startActivity(dawingNumberIntent);
                    }
                });
            } else {
                loadAd();
                startActivity(dawingNumberIntent);
            }
            return;
        }
    }
}