package com.vickysg.allstatussaver;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class Util {

    public static File RootDirectoryWhatsApp = new File(Environment.getExternalStorageDirectory()
              + "/Download/MyStatusDownloads/WhatsAppStory");

    public static  String RootDirectoryFacebook = "/MyStatusDownloads/FacebookVideos/";
    public static  String RootDirectoryShareChat = "/MyStatusDownloads/ShareChatVideos/";

    public static void CreateFileFolder(){
        if(!RootDirectoryWhatsApp.exists()){
            RootDirectoryWhatsApp.mkdirs();
        }
    }


    public static void download(String downloadPath , String destinationPath , Context context , String fileName){

        Toast.makeText(context, "Downloading Started.....     \n"+"Download"+destinationPath+fileName, Toast.LENGTH_LONG).show();

        Uri uri = Uri.parse(downloadPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, destinationPath+fileName);

        ((DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);

    }
}
