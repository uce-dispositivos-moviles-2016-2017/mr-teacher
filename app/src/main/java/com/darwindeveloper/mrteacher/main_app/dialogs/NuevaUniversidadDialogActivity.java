package com.darwindeveloper.mrteacher.main_app.dialogs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.database.DataBaseHelper;
import com.darwindeveloper.mrteacher.database.DatabaseManager;

import java.io.IOException;

public class NuevaUniversidadDialogActivity extends AppCompatActivity {
    private DatabaseManager databaseManager;//para manegar la base de datos
    private Context context;
    public static final String ID = "com.darwindeveloper.mrteacher.ID.nueva_institucion";
    public static final String NOMBRE = "com.darwindeveloper.mrteacher.NOMBRE.nueva_institucion";
    public static final String SIGLAS = "com.darwindeveloper.mrteacher.SIGLAS.nueva_institucion";
    public static final String TELEFONO = "com.darwindeveloper.mrteacher.TELEFONO.nueva_institucion";
    public static final String DIRECCION = "com.darwindeveloper.mrteacher.DIRECCION.nueva_institucion";
    public static final String OBSERVACIONES = "com.darwindeveloper.mrteacher.OBS.nueva_institucion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        context = this;

        DataBaseHelper dataBaseHelper = null;
        try {
            dataBaseHelper = new DataBaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        databaseManager = new DatabaseManager(context, dataBaseHelper.getWritableDatabase());


        setContentView(R.layout.activity_nueva_universidad_dialog);


    }


    public void save(View v) {

        //obtenemos los datos ingresados
        String nombre = ((EditText) findViewById(R.id.editText_nombre)).getText().toString();
        String siglas = ((EditText) findViewById(R.id.editText_siglas)).getText().toString();
        String telefono = ((EditText) findViewById(R.id.editText_telefono)).getText().toString();
        String direccion = ((EditText) findViewById(R.id.editText_direccion)).getText().toString();
        String obs = ((EditText) findViewById(R.id.editText_obs)).getText().toString();

        if (nombre.trim().length() > 0) {//verificamos que los datos ingresados sean correctos
            //ingresamos la institucion educativa en la base de datos
            Long id = databaseManager.insertNuevaUniversidad(nombre, siglas, telefono, direccion, obs);


            if (id != -1) {//si el ingreso se realizo de forma exitosa
                Toast.makeText(context, "Guardado con exito", Toast.LENGTH_SHORT).show();
                //devolvemos la nueva institucion creada
                Intent intent = new Intent();
                intent.putExtra(ID, id);
                intent.putExtra(NOMBRE, nombre);
                intent.putExtra(SIGLAS, siglas);
                intent.putExtra(TELEFONO, telefono);
                intent.putExtra(DIRECCION, direccion);
                intent.putExtra(OBSERVACIONES, obs);
                setResult(RESULT_OK, intent);

            }
            finish();//finalizamos la actividad

        } else {
            Toast.makeText(context, "ingrese un nombre valido", Toast.LENGTH_SHORT).show();
        }


    }


    public void cancel(View v) {
        finish();
    }


}
