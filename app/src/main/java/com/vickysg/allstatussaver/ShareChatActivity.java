package com.vickysg.allstatussaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.vickysg.allstatussaver.databinding.ActivityShareChatBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShareChatActivity extends AppCompatActivity {

    private ActivityShareChatBinding binding ;
    private ShareChatActivity activity ;
    private InterstitialAd mInterstitialAd;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_share_chat);
        activity = this ;

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3912259549278001/5882941283", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                mInterstitialAd.show(ShareChatActivity.this);

            }
        });

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        binding.download.setOnClickListener(v -> {
            getShareChatActivity();
        });
    }

    private void getShareChatActivity() {
        URL url = null;
        try {
            url = new URL(binding.paste.getText().toString());
            String host = url.getHost();
            if (host.contains("sharechat")){
                new CallGetShareChatData().execute(binding.paste.getText().toString());
            }else{
                Toast.makeText(activity, "Url is Invalid....", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ShareChatActivity.this , MainActivity.class));
                finish();

            }
        } catch (MalformedURLException e) {
            Toast.makeText(activity, "Url is Invalid....", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ShareChatActivity.this , MainActivity.class));
            finish();

        }
    }

    class CallGetShareChatData extends AsyncTask<String , Void , Document> {

        Document scDoc;

        @Override
        protected Document doInBackground(String... strings) {
            try {
                scDoc = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return scDoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = document.select("meta[property=\"og:video:secure_url\"]")
                    .last().attr("content");

            if (!videoUrl.equals("")){
                Util.download(videoUrl , Util.RootDirectoryShareChat ,activity ,"sharechat "+System.currentTimeMillis()+".mp4");
            }else{
                Toast.makeText(activity, "Please Paste the Url Link Here", Toast.LENGTH_SHORT).show();
            }
        }
    }
}