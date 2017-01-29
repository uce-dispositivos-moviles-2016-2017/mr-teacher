package com.darwindeveloper.mrteacher.main_app.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.classes.Constants;
import com.darwindeveloper.mrteacher.classes.Universidad;
import com.darwindeveloper.mrteacher.database.DataBaseHelper;
import com.darwindeveloper.mrteacher.database.DatabaseManager;
import com.darwindeveloper.mrteacher.main_app.adapters.SpinnerUniversidadesAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by DARWIN on 29/1/2017.
 */

public class NuevaCarreraDialogActivity extends AppCompatActivity {

    public static final String U_ID = "com.darwindeveloper.mrteacher.NUEVA_CARRERA.uid";

    private DatabaseManager databaseManager;
    private Context context;
    private ArrayList<Universidad> universidades = new ArrayList<>();
    private Spinner spinner;
    private int universidad_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_carrera);
        context = NuevaCarreraDialogActivity.this;

        View mDecorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);

        DataBaseHelper dataBaseHelper = null;
        try {
            dataBaseHelper = new DataBaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }


        databaseManager = new DatabaseManager(context, dataBaseHelper.getWritableDatabase());


        spinner = (Spinner) findViewById(R.id.spinner);
        new LoadUniversidades("select * from " + Constants.TABLA_INSTITUCIONES_EDUCATIVAS, null).execute();


        final EditText editTextNombre = (EditText) findViewById(R.id.editText_df_nombre);

        // Fetch arguments from bundle and set title


        Button ok, cancel;
        ok = (Button) findViewById(R.id.buttonOK);
        cancel = (Button) findViewById(R.id.buttonC);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombre_ = editTextNombre.getText().toString();

                if (nombre_.trim().length() == 0) {
                    Toast.makeText(context, "Debe ingresar un nombre valido ", Toast.LENGTH_SHORT).show();
                } else {
                    nuevaCarrera(nombre_);
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void nuevaCarrera(String nombre) {
        Long id = databaseManager.insertNuevaCarrera(nombre,universidad_id);
        if (id != -1) {

            Toast.makeText(context, "Creado con exito", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra(U_ID, universidad_id);
            setResult(RESULT_OK, intent);
        }
        finish();
    }


    private class LoadUniversidades extends AsyncTask<Void, Void, Void> {

        String query;
        String[] selectionArgs;


        public LoadUniversidades(String query, String[] selectionArgs) {
            this.query = query;
            this.selectionArgs = selectionArgs;
        }

        @Override
        protected void onPreExecute() {
            universidades.clear();
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... params) {
            universidades.addAll(databaseManager.get_instituciones_educativas(query, selectionArgs));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            SpinnerUniversidadesAdapter spinnerAdapter = new SpinnerUniversidadesAdapter(context, R.layout.item_sppiner_universidades, universidades);
            spinner.setAdapter(spinnerAdapter);
            setClickSpinner();
        }
    }


    private void setClickSpinner() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                universidad_id = universidades.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
