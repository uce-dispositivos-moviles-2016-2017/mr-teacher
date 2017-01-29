package com.darwindeveloper.mrteacher.start_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.main_app.MainViewActivity;

public class SplashActivity extends AppCompatActivity {


    private TextView textView_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        setContentView(R.layout.activity_splash);


        textView_progress = (TextView) findViewById(R.id.textView_progress);


        //Initialize a LoadViewTask object and call the execute() method

        new LoadViewTask().execute();
    }


    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
        //Before running code in separate thread
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            textView_progress.setText("0%");
        }

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params) {
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */
            try {
                //Get the current thread's token
                synchronized (this) {
                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while (counter <= 4) {
                        //Wait 850 milliseconds
                        this.wait(550);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
                        publishProgress(counter * 25);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values) {
            //set the current progress of the progress dialog
            if (values[0] <= 100)
                textView_progress.setText(values[0] + "%");
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result) {

            //si previamente ya se definio una contraseña
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
            boolean is_passoword_ok = preferences.getBoolean(getString(R.string.password_ok), false);
            boolean remember_me = preferences.getBoolean(getString(R.string.remember_me), false);
            if (is_passoword_ok) {
                Intent i = null;
                if (remember_me) {
                    i = new Intent(SplashActivity.this, MainViewActivity.class);
                } else {
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                }

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {
                Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }
    }
}
