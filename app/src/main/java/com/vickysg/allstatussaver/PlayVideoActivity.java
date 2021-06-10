package com.vickysg.allstatussaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.annotation.SuppressLint;
import android.content.Intent;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.vickysg.allstatussaver.databinding.ActivityPlayVideoBinding;

import java.io.File;

public class PlayVideoActivity extends AppCompatActivity {

    private ActivityPlayVideoBinding binding;
    private String videoPath;
    MediaController mediaControls;
    private InterstitialAd mInterstitialAd;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_video);

        Intent intent = getIntent();
        videoPath = intent.getStringExtra("video");
        File file = new File(videoPath);

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3912259549278001/5882941283", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                mInterstitialAd.show(PlayVideoActivity.this);

            }
        });

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        binding.videoView.setVideoURI(Uri.parse(String.valueOf(file)));
        if (mediaControls == null) {
            mediaControls = new MediaController(PlayVideoActivity.this);
            mediaControls.setAnchorView(binding.videoView);
        }

        binding.videoView.setMediaController(mediaControls);
        binding.videoView.setVideoURI(Uri.parse(String.valueOf(file)));

        binding.videoView.start();

        binding.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(getApplicationContext(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show();

                return false;
            }
        });
    }
}
