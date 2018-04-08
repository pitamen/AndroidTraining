package com.taregan.servicereceiver;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pitambar on 4/6/18.
 */

public class DownloadService extends IntentService {

    private static final int ONGOING_NOTIFICATION_ID = 1;
    private int result = Activity.RESULT_CANCELED;
    public static final String URL = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION_PACKAGE = "com.targan.servicereceiver";

    NotificationManager notificationManager;
    Notification myNotification;


    public DownloadService() {
        super("DownloadService");
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String urlPath = intent.getStringExtra(URL);
        String fileName= intent.getStringExtra(FILENAME);

        File output = new File(Environment.getExternalStorageDirectory(),fileName);

        if(output.exists()){
            output.delete();
        }

        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            URL url = new URL(urlPath);

            inputStream = url.openConnection().getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            outputStream=new FileOutputStream(output.getPath());
            int next = -1;
            while ((next = inputStreamReader.read()) != -1) {
                outputStream.write(next);
            }
            // successfully finished
            result = Activity.RESULT_OK;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String notificationText = "Downloading file from the server";

        myNotification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Progress")
                .setContentText(notificationText)
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_mms_black_24dp)
                .build();

        notificationManager.notify(ONGOING_NOTIFICATION_ID, myNotification);


        publishResults(output.getAbsolutePath(),result);

    }

    private void publishResults(String outputPath, int result) {
        Intent intent = new Intent(NOTIFICATION_PACKAGE);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    }
}
