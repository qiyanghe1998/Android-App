package com.animationrecognition.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.animationrecognition.DataBase.DataBaseUtils;
import com.animationrecognition.R;
import com.animationrecognition.helpers.InputValidation;

/**
 * LoginActivity class is to handle the  features about login logic.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutemail;
    private TextInputLayout textInputLayoutpassword;

    private TextInputEditText textInputEditTextemail;
    private TextInputEditText textInputEditTextpassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;

    private static DataBaseUtils databaseUtils = new DataBaseUtils();

    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();


    }

    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutemail = (TextInputLayout) findViewById(R.id.textInputLayoutemail);
        textInputLayoutpassword = (TextInputLayout) findViewById(R.id.textInputLayoutpassword);

        textInputEditTextemail = (TextInputEditText) findViewById(R.id.textInputEditTextemail);
        textInputEditTextpassword = (TextInputEditText) findViewById(R.id.textInputEditTextpassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

    }

    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);

    }


    /**
     * method to create the option menu
     *
     * @param menu an object of menu
     * @return whether create teh menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * method to select the option item
     *
     * @param item an object of menu item
     * @return whether teh item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            System.out.println(mode);
            if(mode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v object of View
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromMySQL();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            default:
                break;
        }
    }

    private void verifyFromMySQL() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextemail, textInputLayoutemail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextemail(textInputEditTextemail, textInputLayoutemail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextpassword, textInputLayoutpassword, getString(R.string.error_message_password))) {
            return;
        }

        databaseUtils.setemail(textInputEditTextemail.getText().toString());
        System.out.println(textInputEditTextpassword.getText().toString());
        databaseUtils.setpassword(textInputEditTextpassword.getText().toString());
        Thread t1 = new Thread(databaseUtils.loginThread);

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String status = databaseUtils.getLoginStatus();
        System.out.println(status);
        if ("login_success".equals(status)) {
            Intent accountsIntent = new Intent(activity, PageActivity.class);
            accountsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            accountsIntent.putExtra("email", textInputEditTextemail.getText().toString().trim());
            accountsIntent.putExtra("NAME", databaseUtils.getuserName());
            accountsIntent.putExtra("id", databaseUtils.getId());
            emptyInputEditText();
            startActivity(accountsIntent);
        } else if ("Exception".equals(status)) {
            Snackbar.make(nestedScrollView, getString(R.string.network_error), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        textInputEditTextemail.setText(null);
        textInputEditTextpassword.setText(null);
    }
}
