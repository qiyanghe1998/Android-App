package com.animationrecognition.activities;


import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.ViewGroup;

import com.animationrecognition.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import devlight.io.library.ntb.NavigationTabBar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotEquals;

public class PageActivityTest extends ActivityInstrumentationTestCase2<PageActivity> {

    protected PageActivity pageActivity;
    protected NavigationTabBar navigationTabBar;
    protected ViewPager viewPager;

    public PageActivityTest() {
        super(PageActivity.class);
    }

    @Before
    protected void setUp() throws Exception{
        super.setUp();
        pageActivity = getActivity();
        viewPager = (ViewPager) pageActivity.findViewById(R.id.vp_horizontal_ntb);
        navigationTabBar = (NavigationTabBar)pageActivity.findViewById(R.id.ntb_horizontal);
    }

    @After
    protected void tearDown() throws Exception {
        pageActivity.finish();
        super.tearDown();
    }

    @Test
    public void testPageActivitySuccess() throws Throwable {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                navigationTabBar.setViewPager(viewPager, 1);
            }
        };
        runTestOnUiThread(r);
        SystemClock.sleep(5000);
        assertNotEquals(0, ((ViewGroup)pageActivity.findViewById(R.id.wikiGridPhoto)).getChildCount());
    }

    @Test
    public void testPageActivityFail() throws Throwable {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                navigationTabBar.setViewPager(viewPager, 1);
            }
        };
        runTestOnUiThread(r);
        assertEquals(0, ((ViewGroup)pageActivity.findViewById(R.id.wikiGridPhoto)).getChildCount());
    }

    @Test
    public void testShareActivity() throws Throwable {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                navigationTabBar.setViewPager(viewPager, 0);
            }
        };
        runTestOnUiThread(r);
        onView(withId(R.id.btnShare)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("No Photo Chosen")))
                .check(matches(isDisplayed()));
    }
}
