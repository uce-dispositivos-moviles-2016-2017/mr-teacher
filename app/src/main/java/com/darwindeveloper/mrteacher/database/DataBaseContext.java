package com.darwindeveloper.mrteacher.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;


import com.darwindeveloper.mrteacher.classes.Constants;

import java.io.File;

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

        File sdcard = Environment.getExternalStorageDirectory();
        String dbfile = sdcard.getAbsolutePath() + "/" + Constants.APP_NAME + "/databases/" + name;

        if (!dbfile.endsWith(".db")) {
            dbfile += ".db";
        }

        File result = new File(dbfile);

        if (!result.getParentFile().exists()) {
            result.getParentFile().mkdirs();
        }

        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
            Log.w(DEBUG_CONTEXT,
                    "getDatabasePath(" + name + ") = " + result.getAbsolutePath());
        }

        return result;
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
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), Constants.APP_NAME+"/databases");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
            }
        }
    }


}