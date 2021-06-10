package com.vickysg.allstatussaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.vickysg.allstatussaver.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);

        binding.whatsview.setOnClickListener( v -> {
            startActivity(new Intent(this , WhatsAppActivity.class));
        });
        binding.fbview.setOnClickListener( v -> {
            startActivity(new Intent(this , FacebookActivity.class));
        });
        binding.shareview.setOnClickListener( v -> {
            startActivity(new Intent(this , ShareChatActivity.class));
        });
        binding.privacyview.setOnClickListener( v -> {
            startActivity(new Intent(this , PrivacyActivity.class));
        });

        CheckPermissions();

    }

    private void CheckPermissions(){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                if (!report.areAllPermissionsGranted()){
                    CheckPermissions();
                }

           }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }
}