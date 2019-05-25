package com.animationrecognition.activities;


import android.app.Activity;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.animationrecognition.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import devlight.io.library.ntb.NavigationTabBar;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class RecognizeTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    Activity currentActivity;
    @Test
    public void recognizeTest() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.textInputEditTextemail),
                        childAtPosition(
                                allOf(withId(R.id.textInputLayoutemail),
                                        childAtPosition(
                                                withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                                0)),
                                0),
                        isDisplayed()));
        textInputEditText.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.textInputEditTextemail),
                        childAtPosition(
                                allOf(withId(R.id.textInputLayoutemail),
                                        childAtPosition(
                                                withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                                0)),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("hqy@mail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.textInputEditTextpassword),
                        childAtPosition(
                                allOf(withId(R.id.textInputLayoutpassword),
                                        childAtPosition(
                                                withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                                1)),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.appCompatButtonLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nestedScrollView),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        getActivityInstance();
        NavigationTabBar navigationTabBar = (NavigationTabBar) currentActivity.findViewById(R.id.ntb_horizontal);
        ViewPager viewPager = (ViewPager)currentActivity.findViewById(R.id.vp_horizontal_ntb);
        currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                navigationTabBar.setViewPager(viewPager, 1);
            }
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            pressBack();
            pressBack();
        }catch (Exception ignored) {
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
    private Activity getActivityInstance(){
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Boolean flag = false;
                Collection<Activity> resumedActivities =
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                for (Activity activity: resumedActivities){
                    String name = activity.getClass().getName();
                    currentActivity = activity;
                    flag = true;
                    if (flag) break;
                }

            }
        });

        return currentActivity;
    }
}
