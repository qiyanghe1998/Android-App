package com.animationrecognition.model;

import android.graphics.Bitmap;

/**
 * class to store the bitmap, name, link of a wiki
 */
public class WikiIterm {
    private Bitmap bitmap;
    private String name;
    private String cId;

    /**
     * constructor
     */
    public WikiIterm() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    /**
     * constructor with parameter
     *
     * @param bitmap object of bitmap
     * @param name the wiki name
     * @param cId which animation does the character id
     */
    public WikiIterm(Bitmap bitmap, String name, String cId) {
        this.bitmap = bitmap;
        this.name = name;
        this.cId = cId;
    }

    /**
     * method to get the bitmap
     *
     * @return the bitmap
     */
    public Bitmap getbitmap() {
        return bitmap;
    }

    /**
     * method to get the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * method to get the cId
     *
     * @return the object cId
     */
    public String getcId() {
        return cId;
    }

    /**
     * method to set the name
     *
     * @param cId assign the value of cId
     */
    public void setcId(String cId) {
        this.cId = cId;
    }

    /**
     * method to set the bitmap
     *
     * @param bitmap assign the value of bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * method to set the name
     *
     * @param name assign the value of name
     */
    public void setName(String name) {
        this.name = name;
    }
}
