package com.animationrecognition.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.animationrecognition.DataBase.DataBaseUtils;
import com.animationrecognition.R;
import com.animationrecognition.adapters.WikiAdapter;
import com.animationrecognition.helpers.InputValidation;
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

import devlight.io.library.ntb.NavigationTabBar;

/**
 * PageActivity.java is to control the main page and the logic of UI.
 */
public class PageActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    protected static final int COMPLETED = 0;
    protected ArrayList<View> viewList;
    static DataBaseUtils dataBaseUtils = new DataBaseUtils();
    protected ArrayList<WikiIterm> mWikiData;
    protected ArrayList<WikiIterm> mCommunityData;
    protected boolean isWikiUpdated = false;
    protected boolean isCommunityUpdated = false;
    protected NavigationTabBar navigationTabBar;
    protected String res;
    protected GridView wikiGridPhoto;
    protected GridView communityGridPhoto;
    protected InputValidation inputValidation;
    protected boolean isStarted;

    /* community */
    protected static final int REQUEST_CODE = 200;
    protected static final int REQUEST_SAVE_CODE = 10;
    protected static final int TAKE_PHOTO = 1;
    protected static final int CHOOSE_PHOTO = 2;
    protected static final int CROP_PHOTO = 3;

    private int mode;

    protected Button btnCamera;
    protected Button btnSelect;
    protected ImageView ivCaptured;
    protected static final String FILE_PROVIDER_AUTHORITY = "com.zz.fileprovider";
    protected Uri mImageUri;
    protected File imageFile;
    protected File fileUpload;
    BaseAdapter wikiAdapter;
    BaseAdapter communityAdapter;
    Intent takePhotoIntent;

    /* wiki */
    protected Button btnSubscribe;
    protected Button btnUnsubscribe;
    private long firstClick;
    protected int adapterFlag;
    protected static final int WIKIFLAG = 0;
    protected static final int COMMUNITFLAG = 1;
    private static NotificationManager manager;
    protected ActivityUtils activityUtils;
    protected ActivityInitUtils activityInitUtils;


    @SuppressLint("HandlerLeak")
    protected final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            InputStream is1 = (InputStream) msg.obj;
            if (msg.what == COMPLETED) {
                try {
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"), 8);

                    Thread t1 = new Thread() {
                        public void run() {
                            try {
                                res = "";
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    res += line;
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
                        JSONArray jsonArray = new JSONArray(jb);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            final String u = jsonObject.optString("img");
                            final String cname = jsonObject.optString("cha_name");
                            final String cId = jsonObject.optString("c_id");
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
                                            switch (adapterFlag) {
                                                case WIKIFLAG:
                                                    mWikiData.add(new WikiIterm(bitmap, cname, cId));
                                                    break;
                                                case COMMUNITFLAG:
                                                    mCommunityData.add(new WikiIterm(bitmap, cname, cId));
                                                    break;
                                                default:
                                                    break;
                                            }
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
                        switch (adapterFlag) {
                            case WIKIFLAG:
                                isWikiUpdated = true;
                                break;
                            case COMMUNITFLAG:
                                isCommunityUpdated = true;
                                break;
                            default:
                                break;
                        }
                    } catch (JSONException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            switch (adapterFlag) {
                case WIKIFLAG:
                    wikiAdapter = new WikiAdapter<WikiIterm>(getBaseContext(), mWikiData, R.layout.wiki_item, adapterFlag, PageActivity.this) {
                        @Override
                        public void bindView(ViewHolder holder, WikiIterm obj) {
                            holder.setImage(R.id.img_icon_wiki, obj.getbitmap());
                            holder.setText(R.id.txt_icon_wiki, obj.getName());
                        }
                    };
                    wikiGridPhoto.setAdapter(wikiAdapter);
                    wikiGridPhoto.invalidate();
                    break;
                case COMMUNITFLAG:
                    communityAdapter = new WikiAdapter<WikiIterm>(getBaseContext(), mCommunityData, R.layout.community_item, adapterFlag, PageActivity.this) {
                        @Override
                        public void bindView(ViewHolder holder, WikiIterm obj) {
                            holder.setImage(R.id.img_icon_community, obj.getbitmap());
                            holder.setText(R.id.txt_icon_community, obj.getName());
                        }
                    };
                    communityGridPhoto.setAdapter(communityAdapter);
                    communityGridPhoto.invalidate();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils();
        activityInitUtils = new ActivityInitUtils();
        setContentView(R.layout.activity_horizontal_ntb);
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        ActivityCompat.requestPermissions(PageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE);
        getSupportActionBar().hide();
        activityInitUtils.initObjects(this);
        activityUtils.initView(this);
        activityInitUtils.initUI(this);
        activityUtils.notificationDetection(this);
        if (ContextCompat.checkSelfPermission(PageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(PageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE);
        }
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(PageActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_SAVE_CODE);
        }
    }


    /**
     * method to create the option menu
     *
     * @param menu an object of menu
     * @return whether create teh menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    /**
     * method to select the option item
     *
     * @param item an object of menu item
     * @return whether teh item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            System.out.println(mode);
            if (mode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v the object of view
     */
    @Override
    public void onClick(View v) {
    }


    /*data comes from camera or album */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        activityInitUtils.manipulation(requestCode, resultCode, data, PageActivity.this);
    }


    /**
     * method to judge the item selected
     *
     * @param item an object of menu item
     * @return whether the item on navigation selected
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * judge whether on key down
     *
     * @param keyCode the code of the key
     * @param event   an object of key event
     * @return whether on key down
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - firstClick > 2000) {
                firstClick = System.currentTimeMillis();
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_LONG).show();
            } else {
                try {
                    this.finish();
                } catch (RuntimeException e) {
                    throw e;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * method to get data base utils
     *
     * @return the dataBaseUtils
     */
    public static DataBaseUtils getDataBaseUtils() {
        return dataBaseUtils;
    }

    /**
     * method to get the manager
     *
     * @return the manager
     */
    public static NotificationManager getManager() {
        return manager;
    }

}
