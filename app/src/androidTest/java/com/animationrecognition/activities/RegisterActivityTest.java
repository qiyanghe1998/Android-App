package com.animationrecognition.activities;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.animationrecognition.R;

import org.junit.Test;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class RegisterActivityTest extends
        ActivityInstrumentationTestCase2<RegisterActivity> {
    protected RegisterActivity mActivity;
    protected Button button;

    public RegisterActivityTest() {
        super(RegisterActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mActivity = getActivity();
        button = (Button) mActivity
                .findViewById(R.id.appCompatButtonRegister);
    }

    @Override
    protected void tearDown() throws Exception {
        mActivity.finish();
        super.tearDown();
    }


    @Test
    public void testRegisterSuccess() {
        ActivityMonitor am = getInstrumentation().addMonitor(
                "com.animationrecognition.activities.PageActivity", null, false);

        String email = "wyh" + (Math.abs(new Random().nextInt())) + "@mail.com";
        String userName = "wyh666";
        String pwd = "123456";
        String confirmPwd = "123456";
        onView(withId(R.id.textInputEditTextemail)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextName)).perform(typeText(userName),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextpassword)).perform(typeText(pwd),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextConfirmpassword)).perform(typeText(confirmPwd),closeSoftKeyboard());

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
    public void testRegisterFailure() {
        ActivityMonitor am = getInstrumentation().addMonitor(
                "com.animationrecognition.activities.PageActivity", null, false);

        String email = "hqy@mail.com";
        String pwd = "1234";
        String userName = "hqy";
        String confirmPwd = "1234";
        onView(withId(R.id.textInputEditTextemail)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextName)).perform(typeText(userName),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextpassword)).perform(typeText(pwd),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextConfirmpassword)).perform(typeText(confirmPwd),closeSoftKeyboard());

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        };

        mActivity.runOnUiThread(r1);

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });

        am.waitForActivityWithTimeout(1000);
        assertEquals(0, am.getHits());
        getInstrumentation().removeMonitor(am);
    }
}

