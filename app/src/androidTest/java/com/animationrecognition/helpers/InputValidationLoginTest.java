package com.animationrecognition.helpers;

import android.os.SystemClock;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.animationrecognition.R;
import com.animationrecognition.activities.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class InputValidationLoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity loginActivity;
    private TextInputLayout textInputLayoutemail;
    private TextInputLayout textInputLayoutpassword;

    private TextInputEditText textInputEditTextemail;
    private TextInputEditText textInputEditTextpassword;
    protected Button button;
    InputValidation inputValidation;

    public InputValidationLoginTest() {
        super(LoginActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        loginActivity = getActivity();

        inputValidation = new InputValidation(loginActivity);
        textInputEditTextemail = (TextInputEditText)loginActivity.findViewById(R.id.textInputEditTextemail);
        textInputLayoutemail = (TextInputLayout)loginActivity.findViewById(R.id.textInputLayoutemail);
        textInputLayoutpassword = (TextInputLayout)loginActivity.findViewById(R.id.textInputLayoutpassword);
        textInputEditTextpassword = (TextInputEditText)loginActivity.findViewById(R.id.textInputEditTextpassword);
        button = (Button) loginActivity
                .findViewById(R.id.appCompatButtonLogin);
//        inputValidation = new InputValidation();
    }

    @After
    public void tearDown() throws Exception {
        loginActivity.finish();
        super.tearDown();
    }

    @Test
    public void testIsInputEditTextFilledSuccess() {
        String password = "5346";
        onView(withId(R.id.textInputEditTextpassword)).perform(typeText(password),closeSoftKeyboard());
        assertTrue(inputValidation.isInputEditTextFilled(textInputEditTextpassword, textInputLayoutpassword, "mail"));
    }

    @Test
    public void testIsInputEditTextFilledFail() {
        String email = "hqy@mail.com";
        String password = "    ";
        onView(withId(R.id.textInputEditTextpassword)).perform(typeText(password),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextemail)).perform(typeText(email),closeSoftKeyboard());
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("Enter password", textInputLayoutpassword.getError());
        //assertFalse(inputValidation.isInputEditTextemail(textInputEditTextemail, textInputLayoutemail, "mail"));
    }

    @Test
    public void testIsInputEditTextemailSuccess() {
        String email = "hqy@mail.com";
        onView(withId(R.id.textInputEditTextemail)).perform(typeText(email),closeSoftKeyboard());
        assertTrue(inputValidation.isInputEditTextemail(textInputEditTextemail, textInputLayoutemail, "mail"));
    }

    @Test
    public void testIsInputEditTextemailFail() {
        String email = "hqy";
        onView(withId(R.id.textInputEditTextemail)).perform(typeText(email),closeSoftKeyboard());
        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("Enter Valid email", textInputLayoutemail.getError());
        //assertFalse(inputValidation.isInputEditTextemail(textInputEditTextemail, textInputLayoutemail, "mail"));
    }

}