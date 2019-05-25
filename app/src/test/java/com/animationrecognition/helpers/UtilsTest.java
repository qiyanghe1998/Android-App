package com.animationrecognition.helpers;

import android.graphics.Bitmap;

import org.junit.Test;

import static org.junit.Assert.assertNull;

public class UtilsTest {

    @Test
    public void test1() {
        Bitmap bitmap = null;
        assertNull(Utils.saveBitmapFile(bitmap));
    }
}
