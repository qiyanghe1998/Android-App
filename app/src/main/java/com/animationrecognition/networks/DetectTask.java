package com.animationrecognition.networks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;

import com.animationrecognition.R;
import com.animationrecognition.activities.PageActivity;
import com.animationrecognition.activities.ResultActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.animationrecognition.activities.PageActivity.getManager;

/**
 * class to detect the notification
 */
public class DetectTask extends AsyncTask<Object, Integer, InputStream> {
    private int notifRef = 1;
    private PageActivity pageActivity;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected InputStream doInBackground(Object... params) {
        InputStream is1 = null;
        final String url = (String) params[0];
        String id = (String) params[1];
        pageActivity = (PageActivity) params[2];
        String newUrl = url + "?" + "u_id=" + id;
        Request request = new Request.Builder()
                .url(newUrl)
                .get()
                .build();
        final OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            is1 = Objects.requireNonNull(response.body()).byteStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return is1;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(InputStream result) {
        super.onPostExecute(result);
        BufferedReader reader;
        String text = "";
        try {
            reader = new BufferedReader(new InputStreamReader(result, "iso-8859-1"), 8);
            String line = null;

            StringBuffer buf = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            text = buf.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(text.trim().equals("not_updated")){
            Notification notif = null;
            Intent resultIntent = new Intent(pageActivity, ResultActivity.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(pageActivity, 0,
                    resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(pageActivity)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setContentText("Animation Notification")
                    .setContentIntent(resultPendingIntent)
                    .setContentTitle("The information has been updated.")
                    .setPriority(Notification.PRIORITY_MAX);
            sendNotification(builder.build());
            System.out.println(text);
        }
    }

    /**
     * method to send the notification
     *
     * @param notif an object of notification
     */
    protected void sendNotification(Notification notif) {
        NotificationManager manager = getManager();
        manager.notify(notifRef++, notif);
    }
}
