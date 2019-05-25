package com.animationrecognition.networks;

import android.os.AsyncTask;

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

import static com.animationrecognition.activities.PageActivity.getDataBaseUtils;

/**
 * class to get the async task
 */
public class GetAsyncTask extends AsyncTask<Object, Integer, InputStream> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected InputStream doInBackground(Object... params) {
        InputStream is1 = null;
        final String url = (String) params[0];
        String cName = (String) params[1];
        String id = getDataBaseUtils().getId();
        String newUrl;
        if(((String)params[2]).equals("like")) {
            newUrl = url + "?" + "pic_id=" + cName + "&u_id=" + id;
        }else {
            newUrl = url + "?" + "c_name=" + cName + "&u_id=" + id;
        }
        Request request = new Request.Builder()
                .url(newUrl)
                .get()
                .build();
        System.out.println(request);
        System.out.println(cName + " " + id);
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
                buf.append(line+"\n");
            }
            text = buf.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(text);
    }
}