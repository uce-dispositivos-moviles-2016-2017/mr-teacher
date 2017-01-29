package com.darwindeveloper.mrteacher.start_activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.extras.Connectivity;
import com.darwindeveloper.mrteacher.main_app.MainViewActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextNames, editTextLastNames, editTextEmail, editTextPassword1, editTextPassword2;

    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNames = (EditText) findViewById(R.id.EditText_nombres);
        editTextLastNames = (EditText) findViewById(R.id.EditText_apellidos);
        editTextEmail = (EditText) findViewById(R.id.EditText_email);
        editTextPassword1 = (EditText) findViewById(R.id.EditText_password1);
        editTextPassword2 = (EditText) findViewById(R.id.EditText_password2);
        buttonSubmit = (Button) findViewById(R.id.button_submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new Connectivity(RegisterActivity.this).isConnectedToInternet()) {
                    attemptLogin();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setTitle(getString(R.string.error_internet));
                    builder.setMessage(getString(R.string.register_error_internet));
                    builder.create().show();
                }

            }
        });
    }


    private void attemptLogin() {


        // Reset errors.
        editTextEmail.setError(null);
        editTextLastNames.setError(null);
        editTextNames.setError(null);
        editTextPassword1.setError(null);
        editTextPassword2.setError(null);

        // Store values at the time of the login attempt.
        String names = editTextNames.getText().toString();
        String last_names = editTextLastNames.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword1.getText().toString();
        String password2 = editTextPassword2.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(names)) {
            editTextNames.setError(getString(R.string.error_field_required));
            focusView = editTextNames;
            cancel = true;
        }


        if (TextUtils.isEmpty(last_names)) {
            editTextLastNames.setError(getString(R.string.error_field_required));
            focusView = editTextLastNames;
            cancel = true;
        }


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            editTextPassword1.setError(getString(R.string.error_invalid_password));
            focusView = editTextPassword1;
            cancel = true;
        }

        if (TextUtils.isEmpty(password2) || !isPasswordValid(password2)) {
            editTextPassword2.setError(getString(R.string.error_invalid_password));
            focusView = editTextPassword2;
            cancel = true;
        }


        if (!password.equals(password2)) {
            editTextPassword1.setError(getString(R.string.error_equals_password));
            focusView = editTextPassword1;
            cancel = true;
        }


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError(getString(R.string.error_field_required));
            focusView = editTextEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            editTextEmail.setError(getString(R.string.error_invalid_email));
            focusView = editTextEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            new RegisterTask(names, last_names, email, password).execute();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.trim().length() > 4;
    }


    private class RegisterTask extends AsyncTask<Void, Void, Boolean> {

        String names, last_names, email, password;

        public RegisterTask(String names, String last_names, String email, String password) {
            this.names = names;
            this.last_names = last_names;
            this.email = email;
            this.password = password;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(getString(R.string.password_ok), true);
            editor.putString(getString(R.string.teacher_names), names);
            editor.putString(getString(R.string.teacher_last_names), last_names);
            editor.putString(getString(R.string.teacher_email), email);
            editor.putString(getString(R.string.teacher_password), computeMD5Hash(password));
            editor.apply();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Intent i = new Intent(RegisterActivity.this, MainViewActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {
                Toast.makeText(RegisterActivity.this, "No se pudo crear la cuenta, por favor intenta nuevamente", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private String computeMD5Hash(String password) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }


}
