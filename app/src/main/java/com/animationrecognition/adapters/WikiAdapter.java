package com.animationrecognition.adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.animationrecognition.R;
import com.animationrecognition.activities.ShowActivity;
import com.animationrecognition.model.WikiIterm;
import com.animationrecognition.networks.GetAsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;



/**
 * save the set in the wikitiem, and bind the wiki_item in the wiki_layout
 *
 * @param <T>
 */
public abstract class WikiAdapter<T> extends BaseAdapter {

    protected ArrayList<T> mData;
    Integer mLayoutRes;
    protected Context mContext;
    protected int adapterFlag;
    protected Activity mActivity;
    protected int pos;

    /**
     * constructor
     */
    public WikiAdapter() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    protected WikiAdapter(Context context, ArrayList<T> mData, int mLayoutRes, int adapterFlag, Activity activity) {
        this.mContext = context;
        this.mData = mData;
        if(this.mLayoutRes == null){
            this.mLayoutRes = mLayoutRes;
        }
        this.adapterFlag = adapterFlag;
        this.mActivity = activity;
    }

    /**
     * method to get the size of mData
     *
     * @return the size of mData
     */
    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * method to get the element of mData
     *
     * @param position index of mData
     * @return the element of mData
     */
    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * method to get the index in mData
     *
     * @param position the item index
     * @return the index
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * method to get the view
     *
     * @param position the index
     * @param convertView the convert view
     * @param parent the object of view group
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = null;
        if (v == null) {
            switch (adapterFlag) {
                case 0:
                    v = LayoutInflater.from(mContext).inflate(R.layout.wiki_item, null);
                    break;
                case 1:
                    v = LayoutInflater.from(mContext).inflate(R.layout.community_item, null);
                    break;
                case 2:
                    v = LayoutInflater.from(mContext).inflate(R.layout.show_item, null);
                    break;
                default:
                    break;
            }

            holder = new ViewHolder();
            holder.item = v;
            if (holder != null) v.setTag(holder);
        }else {
            holder = (ViewHolder) v.getTag();
        }
        bindView(holder, getItem(position));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterFlag == 1) {
                    Intent accountsIntent = new Intent(mActivity, ShowActivity.class);
                    accountsIntent.putExtra("NAME", ((WikiIterm) getItem(position)).getName());
                    accountsIntent.putExtra("CID", ((WikiIterm) getItem(position)).getcId());
                    mActivity.startActivity(accountsIntent);
                }

            }
        });
        if(adapterFlag == 2) {
            ImageView iv = (ImageView) v.findViewById(R.id.img_icon_show);
            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    pos = position;
                    alertDialod();
                    return false;
                }
            });
        }


        Button btn = null;
        switch (adapterFlag) {
            case 0:
                btn = (Button) v.findViewById(R.id.btnSubscribe);
                break;
            case 1:
                btn = (Button) v.findViewById(R.id.btnUnsubscribe);
                break;
            case 2:
                btn = (Button) v.findViewById(R.id.btnLike);
                break;
            default:
                break;
        }
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = ((WikiIterm)getItem(position)).getName();
                String reqUrl = null;
                switch (adapterFlag) {
                    case 0:
                        reqUrl = "http://120.78.200.80/subscribe.php";
                        new GetAsyncTask().execute(reqUrl, name, "null");
                        break;
                    case 1:
                        reqUrl = "http://120.78.200.80/unSubscribe.php";
                        new GetAsyncTask().execute(reqUrl, name, "null");
                        break;
                    case 2:
                        reqUrl = "http://120.78.200.80/like.php";
                        new GetAsyncTask().executeOnExecutor(Executors.newCachedThreadPool(), reqUrl, name, "like");
                        break;
                    default:
                        break;
                }
            }
        });

        return holder.getItemView();
    }

    /**
     * abstract method to bind the view
     *
     * @param holder the object of viewHolder
     * @param obj multiple possible types
     */
    public abstract void bindView(ViewHolder holder, T obj);

    /**
     * class to bind the text and view
     */
    public static  class ViewHolder {

        private final SparseArray<View> mViews = new SparseArray<>();
        protected View item;
//        private int position;

        protected ViewHolder(){
            // a constuctor for the class ViewHolder
        }

        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
        }

        private static ViewHolder bind(Context context, View convertView, ViewGroup parent,
                                       int layoutRes, int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
           // holder.position = position;
            return holder;
        }

        private <T extends View> T getView(int id) {
            T t = (T) mViews.get(id);
            if (t == null) {
                t = (T) item.findViewById(id);
                mViews.put(id, t);
            }
            return t;
        }

        /**
         * method to get the item view
         *
         * @return item
         */
        public View getItemView() {
            return item;
        }

        /**
         * method to set up the text
         *
         * @param id the text id
         * @param text the text
         * @return view
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        /**
         * method to set the image
         *
         * @param id the View id
         * @param bitmap the bitmap
         * @return view
         */
        public ViewHolder setImage(int id, Bitmap bitmap) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
                view.invalidate();
            }
            return this;
        }
    }

    protected void alertDialod(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Are you sure you want to download this picture?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        downloadPNG();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
    }

    protected void downloadPNG() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-HH:mm:ss", Locale.CHINA);
            Date curDate = new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);
            File file = new File(Environment.getExternalStorageDirectory() + "/"+ str + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            ((WikiIterm) getItem(pos)).getbitmap().compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Uri uri = Uri.fromFile(file);
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            Toast.makeText(mContext, R.string.download_success, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, R.string.download_failure, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
