package com.animationrecognition.helpers;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class Utils {

    private Utils(){
        // a constructor for class Utils
    }

    /**
     * the class to save bitmap file
     *
     * @param bitmap the object of bitmap
     * @return file the object needs to be compressed
     */
    public static File saveBitmapFile(Bitmap bitmap) {
        if(bitmap == null) return null;
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");//the path to save picture
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            while (file.getTotalSpace())
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
