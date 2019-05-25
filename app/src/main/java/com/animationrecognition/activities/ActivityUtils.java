package com.animationrecognition.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.animationrecognition.R;
import com.animationrecognition.helpers.InputValidation;
import com.animationrecognition.model.PokemonTFlite;
import com.animationrecognition.networks.DetectTask;
import com.animationrecognition.networks.MyAsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.animationrecognition.activities.PageActivity.CROP_PHOTO;

/**
 * class of activity utils
 */
class ActivityUtils {
    void crop(PageActivity pageActivity, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 224);
        intent.putExtra("outputY", 224);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        pageActivity.startActivityForResult(intent, CROP_PHOTO);
    }

    /**
     * method to notify the detection
     *
     * @param pageActivity an object of page activity
     */
    void notificationDetection(PageActivity pageActivity) {
        String url = "http://120.78.200.80/detect.php";
        String id = pageActivity.getIntent().getStringExtra("id");
        new DetectTask().execute(url, id, pageActivity);
    }


    protected void wiki(PageActivity pageActivity) {
        pageActivity.btnSubscribe = (Button) pageActivity.findViewById(R.id.btnSubscribe);
        pageActivity.wikiGridPhoto = (GridView) pageActivity.findViewById(R.id.wikiGridPhoto);
        if (!pageActivity.isWikiUpdated) {
            pageActivity.isWikiUpdated = true;
            Thread t1 = new Thread(PageActivity.dataBaseUtils.wikiThread);
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InputStream status = PageActivity.dataBaseUtils.getWikiStatus();
            Message msg = new Message();
            msg.what = 0;
            msg.obj = status;
            pageActivity.adapterFlag = PageActivity.WIKIFLAG;
            pageActivity.handler.sendMessage(msg);
        }
    }

    protected void community(PageActivity pageActivity) {
        pageActivity.btnUnsubscribe = (Button) pageActivity.findViewById(R.id.btnUnsubscribe);
        pageActivity.communityGridPhoto = (GridView) pageActivity.findViewById(R.id.communityGridPhoto);
        if (pageActivity.isCommunityUpdated) {
            pageActivity.mCommunityData.clear();
        }
        pageActivity.isCommunityUpdated = true;
        PageActivity.dataBaseUtils.setId(pageActivity.getIntent().getStringExtra("id"));

        Thread t1 = new Thread(PageActivity.dataBaseUtils.communityThread);
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InputStream status = PageActivity.dataBaseUtils.getCommunityStatus();
        Message msg = new Message();
        msg.what = 0;
        msg.obj = status;
        pageActivity.adapterFlag = PageActivity.COMMUNITFLAG;
        pageActivity.handler.sendMessage(msg);
    }

    protected void recognition(PageActivity pageActivity) {
        pageActivity.ivCaptured = (ImageView) pageActivity.findViewById(R.id.ivCaptured);
        pageActivity.btnCamera = (Button) pageActivity.findViewById(R.id.btnCamera);
        PageActivity.dataBaseUtils.setId(pageActivity.getIntent().getStringExtra("id"));
        pageActivity.btnCamera.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(pageActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(pageActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(pageActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PageActivity.REQUEST_CODE);
                    System.out.println("No permission");
                }
                pageActivity.takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (pageActivity.takePhotoIntent.resolveActivity(pageActivity.getPackageManager()) != null) {
                    pageActivity.imageFile = createImageFile(pageActivity);
                    if (pageActivity.imageFile != null) {
                        pageActivity.mImageUri = FileProvider.getUriForFile(pageActivity, PageActivity.FILE_PROVIDER_AUTHORITY, pageActivity.imageFile);
                        pageActivity.takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, pageActivity.mImageUri);
                        pageActivity.startActivityForResult(pageActivity.takePhotoIntent, PageActivity.TAKE_PHOTO);
                    }
                }
            }
        });

        pageActivity.btnSelect = (Button) pageActivity.findViewById(R.id.btnSelect);
        pageActivity.btnSelect.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pageActivity.startActivityForResult(i, PageActivity.CHOOSE_PHOTO);
            }
        });

        NestedScrollView scrollView = (NestedScrollView) pageActivity.findViewById(R.id.scrollView);
        Button btnShare = (Button) pageActivity.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> {
            if (pageActivity.fileUpload == null) {
                Snackbar.make(scrollView, pageActivity.getString(R.string.no_photo_chosen), Snackbar.LENGTH_LONG).show();
                Snackbar.make(scrollView, pageActivity.getString(R.string.no_photo_chosen), Snackbar.LENGTH_LONG).show();
                System.out.println("no photo");
            } else {
                String imagePath = pageActivity.fileUpload.getAbsolutePath();
                System.out.println(imagePath);
                Uri imageUri = Uri.fromFile(new File(imagePath));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/*");
                pageActivity.startActivity(Intent.createChooser(shareIntent, "Share to "));
            }
        });

        Button btnRecognition = (Button) pageActivity.findViewById(R.id.btnRecognition);
        btnRecognition.setOnClickListener(v -> {
            PokemonTFlite ptflite = new PokemonTFlite();
            try {
                ptflite.create(pageActivity.getAssets());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = null;
            try {
                if (pageActivity.fileUpload != null)
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(pageActivity.fileUpload));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bitmap == null) {
                Snackbar.make(scrollView, "No photo ", Snackbar.LENGTH_LONG).show();
                return;
            }
            String res = ptflite.run(bitmap);
            Snackbar.make(scrollView, "The recognition result is " + res, Snackbar.LENGTH_LONG).show();

            String reqUrl = "http://120.78.200.80/picture_upload.php";
            new MyAsyncTask().execute(reqUrl, pageActivity.fileUpload, res);
        });
    }

    protected void update(PageActivity pageActivity) {
        pageActivity.inputValidation = new InputValidation(pageActivity);
        final TextInputEditText textInputEditTextemail = (TextInputEditText) pageActivity.findViewById(R.id.textInputEditTextemail);
        final TextInputEditText textInputEditTextName = (TextInputEditText) pageActivity.findViewById(R.id.textInputEditTextName);
        final TextInputEditText textInputEditTextpassword = (TextInputEditText) pageActivity.findViewById(R.id.textInputEditTextpassword);
        final TextInputEditText textInputEditTextpassword2 = (TextInputEditText) pageActivity.findViewById(R.id.textInputEditTextConfirmpassword);
        final TextInputLayout textInputLayoutName = (TextInputLayout) pageActivity.findViewById(R.id.textInputLayoutName);
        final TextInputLayout textInputLayoutemail = (TextInputLayout) pageActivity.findViewById(R.id.textInputLayoutemail);
        final TextInputLayout textInputLayoutpassword = (TextInputLayout) pageActivity.findViewById(R.id.textInputLayoutpassword);
        AppCompatButton appCompatButtonUpdate = (AppCompatButton) pageActivity.findViewById(R.id.appCompatButtonUpdate);
        String emailFromIntent = pageActivity.getIntent().getStringExtra("email");
        textInputEditTextemail.setText(emailFromIntent);
        final NestedScrollView nestedScrollView = (NestedScrollView) pageActivity.findViewById(R.id.nestedScrollView);
        String nameFromIntent = pageActivity.getIntent().getStringExtra("NAME");
        textInputEditTextName.setText(nameFromIntent);
        appCompatButtonUpdate.setOnClickListener(new View.OnClickListener() {
            /**
             * This implemented method is to listen the click on view
             *
             * @param v the object of view
             */
            @Override
            public void onClick(View v) {
                pageActivity.inputValidation = new InputValidation(pageActivity);
                if (!pageActivity.inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, pageActivity.getString(R.string.error_message_name))) {
                    return;
                }
                if (!pageActivity.inputValidation.isInputEditTextFilled(textInputEditTextemail, textInputLayoutemail, pageActivity.getString(R.string.error_message_email))) {
                    return;
                }
                if (!pageActivity.inputValidation.isInputEditTextemail(textInputEditTextemail, textInputLayoutemail, pageActivity.getString(R.string.error_message_email))) {
                    return;
                }
                if (!pageActivity.inputValidation.isInputEditTextFilled(textInputEditTextpassword, textInputLayoutpassword, pageActivity.getString(R.string.error_message_password))) {
                    return;
                }
                if (!pageActivity.inputValidation.isInputEditTextMatches(textInputEditTextpassword, textInputEditTextpassword2, textInputLayoutpassword, pageActivity.getString(R.string.error_password_match))) {
                    return;
                }

                PageActivity.dataBaseUtils.setemail(textInputEditTextemail.getText().toString());
                System.out.println(textInputEditTextemail.getText().toString());

                PageActivity.dataBaseUtils.setpassword(textInputEditTextpassword.getText().toString());
                System.out.println(textInputEditTextpassword.getText().toString());
                PageActivity.dataBaseUtils.setuserName(textInputEditTextName.getText().toString());

                System.out.println(textInputEditTextemail.getText().toString());
                Thread t1 = new Thread(PageActivity.dataBaseUtils.updateThread);

                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String status = PageActivity.dataBaseUtils.getUpdateStatus();
                if ("update_success".equals(status)) {
                    Snackbar.make(nestedScrollView, pageActivity.getString(R.string.text_update_success), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(nestedScrollView, pageActivity.getString(R.string.text_update_fail), Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

    protected File createImageFile(PageActivity pageActivity) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = pageActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    /**
     * method to initialize the page activity
     * @param pageActivity an object of page activity
     */
    void initView(PageActivity pageActivity) {
        LayoutInflater lf = pageActivity.getLayoutInflater().from(pageActivity);
        View view0 = lf.inflate(R.layout.first_page, null);
        View view1 = lf.inflate(R.layout.activity_recognition, null);
        View view2 = lf.inflate(R.layout.activity_wiki, null);
        View view3 = lf.inflate(R.layout.activity_community, null);
        View view4 = lf.inflate(R.layout.activity_update, null);

        pageActivity.viewList = new ArrayList<>();
        pageActivity.viewList.add(view0);
        pageActivity.viewList.add(view1);
        pageActivity.viewList.add(view2);
        pageActivity.viewList.add(view3);
        pageActivity.viewList.add(view4);

    }






}
