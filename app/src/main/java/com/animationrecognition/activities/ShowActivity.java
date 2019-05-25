package com.animationrecognition.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.animationrecognition.DataBase.DataBaseUtils;
import com.animationrecognition.R;
import com.animationrecognition.adapters.WikiAdapter;
import com.animationrecognition.model.WikiIterm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * PageActivity.java is to control the main page and the logic of UI.
 */
public class ShowActivity extends Activity implements View.OnClickListener {

    protected final String tag = getClass().getSimpleName();
    protected static final int COMPLETED = 0;
    protected static ArrayList<View> viewList;
    protected DataBaseUtils dataBaseUtils;
    protected ArrayList<WikiIterm> mShowData;
    protected boolean isShowUpdated = false;

    protected String res;
    protected String res2;
    protected GridView showGridPhoto;
    protected HashMap<String, String> picId2Like = new HashMap<>();

    /* community */

    protected Button btnCamera;
    protected Button btnSelect;
    protected ImageView ivCaptured;
    protected static final String FILE_PROVIDER_AUTHORITY = "com.zz.fileprovider";
    protected Uri mImageUri, mImageUriFromFile;
    protected File imageFile;
    protected File fileUpload;
    BaseAdapter showAdapter;

    /* wiki */
    protected Button btnLike;
    protected int adapterFlag;


    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            InputStream is1 = (InputStream) ((ArrayList) msg.obj).get(0);
            InputStream is2 = (InputStream) ((ArrayList) msg.obj).get(1);
            if (msg.what == COMPLETED) {
                try {
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"), 8);
                    final BufferedReader likeReader = new BufferedReader(new InputStreamReader(is2, "iso-8859-1"), 8);
                    Thread t1 = new Thread() {
                        public void run() {
                        try {
                            res = "";
                            res2 = "";
                            String line;
                            while ((line = reader.readLine()) != null) {
                                res += line;
                            }
                            while ((line = likeReader.readLine()) != null) {
                                res2 += line;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        }
                    };
                    t1.start();
                    t1.join();
                    try {
                        String jb = new JSONObject(res).optString("data");
                        System.out.println("jb: " + jb);
                        if ("[]".equals(jb))
                            return;
                        JSONArray jsonArray = new JSONArray(jb);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            final String u = jsonObject.optString("img");
                            final String cname = jsonObject.optString("p_id");
                            final String cout = "";
                            Thread t = new Thread() {
                                public void run() {
                                    URL url;
                                    try {
                                        url = new URL(u);
                                        HttpURLConnection conn;
                                        conn = (HttpURLConnection) url.openConnection();
                                        assert conn != null;
                                        conn.setConnectTimeout(5000);
                                        conn.setRequestMethod("GET");
                                        if (conn.getResponseCode() == 200) {
                                            InputStream ins = conn.getInputStream();
                                            Bitmap bitmap = BitmapFactory.decodeStream(ins);
                                            mShowData.add(new WikiIterm(bitmap, cname, cout));
                                            ins.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            t.start();
                            t.join();
                        }
                        isShowUpdated = true;
                    } catch (JSONException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                try {
//                    String jb = new JSONObject(res2).optString("data");
//                    System.out.println("jb: " + jb);
//                    if(jb.equals("[]"))
//                        return;
                    JSONArray jsonArray = new JSONArray(res2);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final String picId = jsonObject.optString("pic_id");
                        final String likeNum = jsonObject.optString("like_num");
                        picId2Like.put(picId, likeNum);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            showAdapter = new WikiAdapter<WikiIterm>(getBaseContext(), mShowData, R.layout.show_item, adapterFlag, ShowActivity.this) {
                @Override
                public void bindView(ViewHolder holder, WikiIterm obj) {
                    holder.setImage(R.id.img_icon_show, obj.getbitmap());
                    holder.setText(R.id.txt_icon_show, picId2Like.get(obj.getName()));
                }
            };
            showGridPhoto.setAdapter(showAdapter);
            showGridPhoto.invalidate();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initObjects();
        initView();
        community();
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v the object of View
     */
    @Override
    public void onClick(View v) {
    }

    private void initObjects() {
        mShowData = new ArrayList<>();
        dataBaseUtils = new DataBaseUtils();
        adapterFlag = 2;
    }

    private void initView() {

    }


    protected void community() {
        btnLike = (Button) findViewById(R.id.btnLike);
        showGridPhoto = (GridView) findViewById(R.id.showGridPhoto);
        if (isShowUpdated) {
            mShowData.clear();
        }
        isShowUpdated = true;
        dataBaseUtils.setId(getIntent().getStringExtra("NAME"));
        dataBaseUtils.setcId(getIntent().getStringExtra("CID"));
        System.out.println("db cId: " + dataBaseUtils.getcId());
        Thread t1 = new Thread(dataBaseUtils.showThread);
        Thread t2 = new Thread(dataBaseUtils.likeThread);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList insList = new ArrayList();
        insList.add(dataBaseUtils.getShowStatus());
        insList.add(dataBaseUtils.getLikeStatus());
        Message msg = new Message();
        msg.what = 0;
        msg.obj = insList;
        handler.sendMessage(msg);
    }
}
