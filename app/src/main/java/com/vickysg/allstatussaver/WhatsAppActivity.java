package com.vickysg.allstatussaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayoutMediator;
import com.vickysg.allstatussaver.databinding.ActivityWhatsAppBinding;
import com.vickysg.allstatussaver.fragments.ImageFragment;
import com.vickysg.allstatussaver.fragments.VideoFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WhatsAppActivity extends AppCompatActivity {

    private ActivityWhatsAppBinding binding ;
    private WhatsAppActivity activity ;
    private ViewPagerAdapter viewPagerAdapter ;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_whats_app);

        binding = DataBindingUtil.setContentView(this , R.layout.activity_whats_app);

        activity = this ;

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        InitView() ;
    }

    private void InitView() {
        viewPagerAdapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), activity.getLifecycle());

        viewPagerAdapter.AddFragment(new ImageFragment() , "Images");
        viewPagerAdapter.AddFragment(new VideoFragment() , "Videos");

        binding.viewPager.setAdapter(viewPagerAdapter);

        binding.viewPager.setOffscreenPageLimit(1);

        new TabLayoutMediator(binding.tabLayout , binding.viewPager , ((tab, position) -> {

            tab.setText(viewPagerAdapter.FragmentTitleList.get(position));

        })).attach();

        for (int i = 0 ; i < binding.tabLayout.getTabCount() ; i++){
            TextView tv = (TextView) LayoutInflater.from(activity).inflate(R.layout.custom_tab,null);

            binding.tabLayout.getTabAt(i).setCustomView(tv) ;
        }
    }

    class ViewPagerAdapter extends FragmentStateAdapter {

        private final List<Fragment> FragmentList = new ArrayList<>();
        private final List<String> FragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull @org.jetbrains.annotations.NotNull FragmentManager fragmentManager, @NonNull @org.jetbrains.annotations.NotNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public  void AddFragment(Fragment fragment , String title){
            FragmentList.add(fragment);
            FragmentTitleList.add(title);
        }

        @NonNull
        @org.jetbrains.annotations.NotNull
        @Override
        public Fragment createFragment(int position) {
            return FragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return FragmentList.size();
        }
    }
}