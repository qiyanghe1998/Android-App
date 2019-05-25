package com.animationrecognition.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;

import com.animationrecognition.R;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

import static com.animationrecognition.activities.PageActivity.CROP_PHOTO;
import static com.animationrecognition.helpers.Utils.saveBitmapFile;

/**
 * class to initialize the activity utils
 */
class ActivityInitUtils {
    static ActivityUtils activityUtils = new ActivityUtils();

    /**
     * method to initialize the UI
     *
     * @param pageActivity an object of page activity
     */
    void initUI(PageActivity pageActivity) {
        final ViewPager viewPager = (ViewPager) pageActivity.findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return 5;
            }


            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }


            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }


            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                container.addView(pageActivity.viewList.get(position));
                return pageActivity.viewList.get(position);
            }
        });

        final String[] colors = pageActivity.getResources().getStringArray(R.array.medical_express);

        pageActivity.navigationTabBar = (NavigationTabBar) pageActivity.findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        pageActivity.getResources().getDrawable(R.drawable.homepage),
                        Color.parseColor(colors[0]))
                        .title("Homepage")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        pageActivity.getResources().getDrawable(R.drawable.recognize),
                        Color.parseColor(colors[1]))
                        .title("Recognition")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        pageActivity.getResources().getDrawable(R.drawable.wiki),
                        Color.parseColor(colors[2]))
                        .title("Wiki")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        pageActivity.getResources().getDrawable(R.drawable.theme),
                        Color.parseColor(colors[3]))
                        .title("Community")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        pageActivity.getResources().getDrawable(R.drawable.info),
                        Color.parseColor(colors[4]))
                        .title("Info")
                        .build()
        );
        
        pageActivity.navigationTabBar.setModels(models);
        pageActivity.navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {

            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });
        pageActivity.isStarted = true;
        pageActivity.navigationTabBar.setViewPager(viewPager, 0);
        pageActivity.navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * method to scroll on page
             *
             * @param position the position on page
             * @param positionOffset the offset of the position
             * @param positionOffsetPixels the offset pixels of the position
             */

            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                if(pageActivity.isStarted) {
                    activityUtils.recognition(pageActivity);
                    pageActivity.isStarted = false;
                }
            }

            /**
             * method to select the position on page
             *
             * @param position which button be clicked
             */
            @Override
            public void onPageSelected(final int position) {
                System.out.println(position);
                switch (position) {
                    case 1:
                        activityUtils.recognition(pageActivity);
                        break;
                    case 2:
                        activityUtils.wiki(pageActivity);
                        break;
                    case 3:
                        activityUtils.community(pageActivity);
                        break;
                    case 4:
                        activityUtils.update(pageActivity);
                        break;
                    default:
                        break;
                }
            }


            /**
             * method to scroll the changed state on page
             *
             * @param state the state
             */
            @Override
            public void onPageScrollStateChanged(final int state) {
            }

        });
    }

    /**
     * method to do the manipulation
     *
     * @param requestCode the request code
     * @param resultCode the result code
     * @param data an object of intent
     * @param pageActivity an object of page activity
     */
    void manipulation(int requestCode, int resultCode, Intent data, PageActivity pageActivity) {
        switch (requestCode) {
            case PageActivity.TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(pageActivity.getContentResolver(), pageActivity.mImageUri);
                        activityUtils.crop(pageActivity, pageActivity.mImageUri);
                    } catch (Exception e){
                        final NestedScrollView nestedScrollView = (NestedScrollView) pageActivity.findViewById(R.id.nestedScrollView);
                        Snackbar.make(nestedScrollView, pageActivity.getString(R.string.xiaomi_failure), Snackbar.LENGTH_LONG).show();
                    }
                }else{
                    final NestedScrollView nestedScrollView = (NestedScrollView) pageActivity.findViewById(R.id.nestedScrollView);
                    Snackbar.make(nestedScrollView, pageActivity.getString(R.string.xiaomi_failure), Snackbar.LENGTH_LONG).show();
                }
                break;
            case PageActivity.CHOOSE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    activityUtils.crop(pageActivity, uri);
                }else{
                    final NestedScrollView nestedScrollView = (NestedScrollView) pageActivity.findViewById(R.id.nestedScrollView);
                    Snackbar.make(nestedScrollView, pageActivity.getString(R.string.xiaomi_failure), Snackbar.LENGTH_LONG).show();
                }
                break;
            case CROP_PHOTO:
                if(data == null){
                    final NestedScrollView nestedScrollView = (NestedScrollView) pageActivity.findViewById(R.id.nestedScrollView);
                    Snackbar.make(nestedScrollView, pageActivity.getString(R.string.xiaomi_failure), Snackbar.LENGTH_LONG).show();
                }else {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        final NestedScrollView nestedScrollView = (NestedScrollView) pageActivity.findViewById(R.id.nestedScrollView);
                        Snackbar.make(nestedScrollView, pageActivity.getString(R.string.xiaomi_failure), Snackbar.LENGTH_LONG).show();

                    }else{
                        Bitmap image = bundle.getParcelable("data");
                        pageActivity.ivCaptured.setImageBitmap(image);
                        pageActivity.fileUpload = saveBitmapFile(image);
                    }
                }
                break;
            case PageActivity.REQUEST_SAVE_CODE:
                if (!Settings.canDrawOverlays(pageActivity)) {
                    final NestedScrollView nestedScrollView = (NestedScrollView) pageActivity.findViewById(R.id.nestedScrollView);
                    Snackbar.make(nestedScrollView, pageActivity.getString(R.string.save_failure), Snackbar.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }


    /**
     * method to initialize the page activity
     * @param pageActivity an object of page activity
     */
    void initObjects(PageActivity pageActivity) {
        pageActivity.mWikiData = new ArrayList<>();
        pageActivity.mCommunityData = new ArrayList<>();
        //DataBaseUtils d = new DataBaseUtils();
        //setDataBaseUtils(d);
        pageActivity.fileUpload = null;
    }


}
