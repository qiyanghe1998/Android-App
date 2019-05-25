package com.animationrecognition.helpers;

import android.os.SystemClock;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.animationrecognition.R;
import com.animationrecognition.activities.RegisterActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * class to test the public method in InputValidationRegister class
 */
public class InputValidationRegisterTest extends ActivityInstrumentationTestCase2<RegisterActivity> {

    private RegisterActivity registerActivity;

    private TextInputEditText textInputEditTextpassword;
    private TextInputEditText textInputEditTextConfirmpassword;
    private TextInputLayout textInputLayoutConfirmpassword;
    protected Button button;
    InputValidation inputValidation;


    /**
     * constructor
     */
    public InputValidationRegisterTest() {
        super(RegisterActivity.class);
    }

    /**
     * method to do initialization work before
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        registerActivity = getActivity();

        inputValidation = new InputValidation(registerActivity);
        TextInputEditText textInputEditTextName = (TextInputEditText) registerActivity.findViewById(R.id.textInputEditTextName);
        TextInputEditText textInputEditTextemail = (TextInputEditText) registerActivity.findViewById(R.id.textInputEditTextemail);
        textInputEditTextpassword = (TextInputEditText)registerActivity.findViewById(R.id.textInputEditTextpassword);
        textInputEditTextConfirmpassword = (TextInputEditText)registerActivity.findViewById(R.id.textInputEditTextConfirmpassword);
        textInputLayoutConfirmpassword = (TextInputLayout)registerActivity.findViewById(R.id.textInputLayoutConfirmpassword);
        button = (Button) registerActivity
                .findViewById(R.id.appCompatButtonRegister);
    }

    @After
    public void tearDown() throws Exception {
        registerActivity.finish();
        super.tearDown();
    }


    @Test
    public void testIsInputEditTextMatchesSuccess() {
        String name = "hqy";
        String password = "123";
        onView(withId(R.id.textInputEditTextName)).perform(typeText(name),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextpassword)).perform(typeText(password),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextConfirmpassword)).perform(typeText(password),closeSoftKeyboard());
        assertTrue(inputValidation.isInputEditTextMatches(textInputEditTextpassword, textInputEditTextConfirmpassword, textInputLayoutConfirmpassword, "mail"));
    }

    @Test
    public void testIsInputEditTextMatchesFail() {
        String name = "hqy";
        String email = "hqy@mail.com";
        String password1 = "123";
        String password2 = "1234";
        onView(withId(R.id.textInputEditTextName)).perform(typeText(name),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextemail)).perform(typeText(email),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextpassword)).perform(typeText(password1),closeSoftKeyboard());
        onView(withId(R.id.textInputEditTextConfirmpassword)).perform(typeText(password2),closeSoftKeyboard());
        registerActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("password Does Not Matches", textInputLayoutConfirmpassword.getError());
    }
}