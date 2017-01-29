package com.darwindeveloper.mrteacher.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.darwindeveloper.mrteacher.classes.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by DARWIN on 20/1/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {


    private String DB_PATH;//The Android's default system path of your application database.

    private static final int DATABASE_VERSION = 3;//version de la base de datos
    private static String DATABASE_NAME = "MrTeacher.db";//nombre de la base de datos
    private static String DATABASE_FILE = "MrTeacher.sqlite";//nombre del archivo que contiene la esructura de la base
    private final Context context;//contexto de la actividad

    public DataBaseHelper(Context context) {
        super(new DataBaseContext(context), DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DB_PATH = Environment.getExternalStorageDirectory() + File.separator + Constants.APP_NAME + "/databases/";
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    /**
     * crea una nueva version de la base de datos
     *
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLA_INSTITUCIONES_EDUCATIVAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLA_CURSOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLA_ESTUDIANTES);
        onCreate(sqLiteDatabase);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /***
     * comprueba si la base de datos no existe en el dispositivo, entonces crea una nueva
     *
     * @throws IOException
     */
    public void createDataBase() throws IOException {
        if (!checkDataBase()) { //si la base de datos no existe
            Toast.makeText(context, "Creando base de datos", Toast.LENGTH_SHORT).show();
            this.getReadableDatabase();
            try {
                copyDataBase();//creamos la base de datos
            } catch (IOException e) {
                Log.e("ERROR Create DB", e.getMessage());
            }
        }
    }


    /**
     * comprueba si el archivo .sqlite existe en la ruta por defecto
     *
     * @return true o false
     */
    private boolean checkDataBase() {
        String myPath = DB_PATH + DATABASE_NAME;
        File dbFile = new File(myPath);
        return dbFile.exists();
    }


    /**
     * copia la base de datos de la carpeta assets
     *
     * @throws IOException
     */
    public void copyDataBase() throws IOException {
        try {

            InputStream myInput = context.getAssets().open(DATABASE_FILE);//InpuStream del archivo del cual vamos a leer la informacion
            String outputFileName = DB_PATH + DATABASE_NAME;//ruta del  archivo en el cual vamos a escribir la informacion
            OutputStream myOutput = new FileOutputStream(outputFileName);//OutputStream del archivo en el que vamos a escribir la informacion

            byte[] buffer = new byte[1024];//cuantos bytes vamos a leer por iteracion
            int length;

            //mientras haya datos que leer
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);//escribimos en el archivo de nuestra base de datos
            }

            myOutput.flush();//guradamos la informacion
            myOutput.close();//cerramos la conneccion con el OutputStream
            myInput.close();//cerramos la conneccion con el InputStream

        } catch (Exception e) {
            Log.e(Constants.APP_NAME + " copyDatabase", e.getMessage());
        }
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openDatabase(DB_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
