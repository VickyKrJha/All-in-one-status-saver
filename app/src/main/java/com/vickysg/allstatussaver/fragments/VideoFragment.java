package com.vickysg.allstatussaver.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vickysg.allstatussaver.R;
import com.vickysg.allstatussaver.adapter.WhatsAppAdapter;
import com.vickysg.allstatussaver.databinding.FragmentImageBinding;
import com.vickysg.allstatussaver.databinding.FragmentVideoBinding;
import com.vickysg.allstatussaver.model.WhatsAppStatusModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class VideoFragment extends Fragment {

    private FragmentVideoBinding binding ;

    private ArrayList<WhatsAppStatusModel> list ;
    private WhatsAppAdapter whatsAppAdapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_video ,container , false);

        list = new ArrayList<>();

        binding.refresh.setOnRefreshListener(() -> {
            list = new ArrayList<>();
            getData();
            binding.refresh.setRefreshing(false);
        });

        getData();

        return binding.getRoot();
    }

    private void getData() {

        WhatsAppStatusModel model ;

        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/WhatsApp/Media/.statuses";
        File targetDirector = new File(targetPath);

        File[] allFiles = targetDirector.listFiles();


        String targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/WhatsApp Business/Media/.statuses";
        File targetDirectorBusiness = new File(targetPathBusiness);

        File[] allFilesBusiness = targetDirectorBusiness.listFiles();

        if(allFiles != null) {
            Arrays.sort(allFiles, ((o1, o2) -> {
                if (o1.lastModified() > o2.lastModified()) {
                    return -1;
                } else if (o1.lastModified() < o2.lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            }));

            for (int i = 0; i < allFiles.length; i++) {
                File file = allFiles[i];
                if (Uri.fromFile(file).toString().endsWith(".mp4")) {

                    model = new WhatsAppStatusModel("whats " + i, Uri.fromFile(file)
                            , allFiles[i].getAbsolutePath(), file.getName());

                    list.add(model);
                }
            }

        }

        if (allFilesBusiness != null) {
            Arrays.sort(allFilesBusiness, ((o1, o2) -> {
                if (o1.lastModified() > o2.lastModified()) {
                    return -1;
                } else if (o1.lastModified() < o2.lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            }));

            for (int i = 0; i < allFilesBusiness.length; i++) {
                File file = allFilesBusiness[i];
                if (Uri.fromFile(file).toString().endsWith(".mp4")) {

                    model = new WhatsAppStatusModel("whats business " + i, Uri.fromFile(file)
                            , allFilesBusiness[i].getAbsolutePath(), file.getName());

                    list.add(model);
                }
            }

        }

        whatsAppAdapter = new WhatsAppAdapter(list , getActivity());
        binding.rvwhatsapp.setAdapter(whatsAppAdapter);
    }

}