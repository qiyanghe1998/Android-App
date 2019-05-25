package com.animationrecognition.activities;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.animationrecognition.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class LoginActivityTest extends
        ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity mActivity;
    protected Button button;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Before
    protected void setUp() throws Exception {
        super.setUp();

        mActivity = getActivity();
        button = (Button) mActivity
                .findViewById(R.id.appCompatButtonLogin);
    }

    @After
    protected void tearDown() throws Exception {
        mActivity.finish();
        super.tearDown();
    }



    @Test
    public void testLoginSuccess() {
        ActivityMonitor am = getInstrumentation().addMonitor(
                "com.animationrecognition.activities.PageActivity", null, false);

        String email = "test@mail.com";
        String pwd = "test";
        onView(withId(R.id.textInputEditTextemail)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextpassword)).perform(typeText(pwd),closeSoftKeyboard());

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });

        am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        assertEquals(1, am.getHits());
    }

    @Test
    public void testLoginFailure() {
        ActivityMonitor am = getInstrumentation().addMonitor(
                "com.animationrecognition.activities.PageActivity", null, false);

        String email = "hqy@mail.com";
        String pwd = "1234";
        onView(withId(R.id.textInputEditTextemail)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextpassword)).perform(typeText(pwd),closeSoftKeyboard());

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        };

        mActivity.runOnUiThread(r1);

        am.waitForActivityWithTimeout(100);
        assertEquals(0, am.getHits());
        getInstrumentation().removeMonitor(am);

    }
}
