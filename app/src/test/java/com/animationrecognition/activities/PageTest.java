package com.animationrecognition.activities;

import org.junit.Test;

import static junit.framework.Assert.assertNull;


public class PageTest {

    @Test
    public void getDataBaseUtils() {
        assertNull(PageActivity.getDataBaseUtils());
    }

    @Test
    public void getManager() {
        assertNull(PageActivity.getManager());
    }
}
