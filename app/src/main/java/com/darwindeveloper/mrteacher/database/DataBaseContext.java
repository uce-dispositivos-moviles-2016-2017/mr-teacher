package com.darwindeveloper.mrteacher.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;


import com.darwindeveloper.mrteacher.classes.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by DARWIN on 20/1/2017.
 */

public class DataBaseContext extends ContextWrapper {

    private static final String DEBUG_CONTEXT = "DatabaseContext";

    public DataBaseContext(Context base) {
        super(base);
        checkFolder();
    }

    @Override
    public File getDatabasePath(String name) {

        File file = null;
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            String dbfile = sdcard.getAbsolutePath() + "/" + Constants.APP_NAME + "/databases/MrTeacher.db";


            if (!dbfile.endsWith(".db")) {
                dbfile += ".db";
            }

            file = new File(dbfile);

            if (file.exists()) {//si el archivo ya existe lo elimina
                file.delete();
            }

            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);

            out.flush();
            out.close();

            if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
                Log.w(DEBUG_CONTEXT,
                        "getDatabasePath(" + name + ") = " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            Log.e("ERROR DBC", e.getMessage());
        }

        return file;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        // SQLiteDatabase result = super.openOrCreateDatabase(name, mode, factory);
        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
            Log.w(DEBUG_CONTEXT,
                    "openOrCreateDatabase(" + name + ",,) = " + result.getPath());
        }
        return result;
    }


    private void checkFolder() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), Constants.APP_NAME + "/databases");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
            }
        }
    }


}