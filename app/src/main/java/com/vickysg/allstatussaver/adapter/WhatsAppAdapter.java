package com.vickysg.allstatussaver.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vickysg.allstatussaver.FullImageActivity;
import com.vickysg.allstatussaver.PlayVideoActivity;
import com.vickysg.allstatussaver.R;
import com.vickysg.allstatussaver.Util;
import com.vickysg.allstatussaver.databinding.WhatsapplayoutBinding;
import com.vickysg.allstatussaver.model.WhatsAppStatusModel;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WhatsAppAdapter extends RecyclerView.Adapter<WhatsAppAdapter.WhatsAppViewHolder> {

    private ArrayList<WhatsAppStatusModel> list ;
    private Context context ;

    private LayoutInflater inflater ;
    private String saveFilePath = Util.RootDirectoryWhatsApp+"/";

    public WhatsAppAdapter(ArrayList<WhatsAppStatusModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public WhatsAppViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       if (inflater == null){
           inflater = LayoutInflater.from(parent.getContext());
       }
       return new WhatsAppViewHolder(DataBindingUtil.inflate(inflater,R.layout.whatsapplayout,parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WhatsAppAdapter.WhatsAppViewHolder holder, int position) {
        WhatsAppStatusModel item  = list.get(position);

        if (item.getUri().toString().endsWith(".mp4")){
            holder.binding.play.setVisibility(View.VISIBLE);
        }else{
            holder.binding.play.setVisibility(View.GONE);
        }

        Glide.with(context).load(item.getPath()).into(holder.binding.statusImage);

        holder.itemView.setOnClickListener(v -> {
            if (item.getUri().toString().endsWith(".mp4")) {
                Intent intent = new Intent(context, PlayVideoActivity.class);
                intent.putExtra("video", item.getPath());
                v.getContext().startActivity(intent);
            }else {
                Intent intent = new Intent(context, FullImageActivity.class);
                intent.putExtra("image", item.getPath());

                v.getContext().startActivity(intent);
            }
        });

        holder.binding.share.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            sharingIntent.setType("image/*");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(item.getPath()));
            v.getContext().startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));

        });

        holder.binding.download.setOnClickListener( v -> {
            Util.CreateFileFolder();
            final String path = item.getPath();
            final File file = new File(path) ;

            File destFile = new File(saveFilePath);

            try {
                FileUtils.copyFileToDirectory(file , destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(context, "Saved to "+saveFilePath, Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WhatsAppViewHolder extends RecyclerView.ViewHolder {

        WhatsapplayoutBinding binding ;
        public WhatsAppViewHolder(WhatsapplayoutBinding binding) {

            super(binding.getRoot());
            this.binding = binding ;
        }
    }
}
