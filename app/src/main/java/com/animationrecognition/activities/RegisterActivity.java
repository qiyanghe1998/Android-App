package com.animationrecognition.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.animationrecognition.DataBase.DataBaseUtils;
import com.animationrecognition.R;
import com.animationrecognition.helpers.InputValidation;

/**
 * RegisterActivity.java is to implement the feature of register.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutemail;
    private TextInputLayout textInputLayoutpassword;
    private TextInputLayout textInputLayoutConfirmpassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextemail;
    private TextInputEditText textInputEditTextpassword;
    private TextInputEditText textInputEditTextConfirmpassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;

    private static DataBaseUtils databaseUtils = new DataBaseUtils();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutemail = (TextInputLayout) findViewById(R.id.textInputLayoutemail);
        textInputLayoutpassword = (TextInputLayout) findViewById(R.id.textInputLayoutpassword);
        textInputLayoutConfirmpassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmpassword);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextemail = (TextInputEditText) findViewById(R.id.textInputEditTextemail);
        textInputEditTextpassword = (TextInputEditText) findViewById(R.id.textInputEditTextpassword);
        textInputEditTextConfirmpassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmpassword);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }

    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v the object of view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
            default:
                break;
        }
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextemail, textInputLayoutemail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextemail(textInputEditTextemail, textInputLayoutemail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextpassword, textInputLayoutpassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextpassword, textInputEditTextConfirmpassword,
                textInputLayoutConfirmpassword, getString(R.string.error_password_match))) {
            System.out.println("password errors");
            return;
        }

        databaseUtils.setuserName(textInputEditTextName.getText().toString());
        databaseUtils.setemail(textInputEditTextemail.getText().toString());
        databaseUtils.setpassword(textInputEditTextpassword.getText().toString());
        Thread t1 = new Thread(databaseUtils.registerThread);

        System.out.println(textInputEditTextemail.getText().toString());

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String status = databaseUtils.getRegisterStatus();
        System.out.println(status);
        if ("register_success".equals(status)) {
            Intent accountsIntent = new Intent(activity, PageActivity.class);
            accountsIntent.putExtra("email", textInputEditTextemail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_register), Snackbar.LENGTH_LONG).show();
        }

    }

    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextemail.setText(null);
        textInputEditTextpassword.setText(null);
        textInputEditTextConfirmpassword.setText(null);
    }
}
