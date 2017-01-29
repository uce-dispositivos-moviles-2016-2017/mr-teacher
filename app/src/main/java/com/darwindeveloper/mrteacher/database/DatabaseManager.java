package com.darwindeveloper.mrteacher.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.darwindeveloper.mrteacher.classes.Carrera;
import com.darwindeveloper.mrteacher.classes.Constants;
import com.darwindeveloper.mrteacher.classes.Estudiante;
import com.darwindeveloper.mrteacher.classes.Universidad;


import java.util.ArrayList;

/**
 * Created by DARWIN on 20/1/2017.
 */

public class DatabaseManager {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseManager(Context context, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.sqLiteDatabase = sqLiteDatabase;
    }


    public ArrayList<Estudiante> todos_los_estudiantes() {

        ArrayList<Estudiante> students = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constants.TABLA_ESTUDIANTES, null);

        if (cursor == null) return null;

        cursor.moveToFirst();
        do {
            String id = cursor.getString(cursor.getColumnIndex("estudiante_id"));
            String nombres = cursor.getString(cursor.getColumnIndex("nombres"));
            String apellidos = cursor.getString(cursor.getColumnIndex("apellidos"));
            students.add(new Estudiante(id, nombres, apellidos));
        } while (cursor.moveToNext());
        cursor.close();

        return students;

    }






    public long insertNuevaCarrera(String nombre, int universidad_id) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("institucion_id", universidad_id);
        //values.put(ChatContract.ChatsEntry.DATE_TIME, getDateTime());

        // Insert the new row, returning the primary key value of the new row
        return sqLiteDatabase.insert(Constants.TABLA_CARRERAS, null, values);

    }


    public ArrayList<Universidad> get_instituciones_educativas(String jquery, String[] selectionArgs) {

        ArrayList<Universidad> universidads = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(jquery, selectionArgs);

        if (cursor == null) return null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex("institucion_id"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String siglas = cursor.getString(cursor.getColumnIndex("siglas"));
                String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
                String telefono = cursor.getString(cursor.getColumnIndex("telefono"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                universidads.add(new Universidad(id, nombre, siglas, telefono, direccion, descripcion));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return universidads;

    }


    public long insertNuevaUniversidad(String nombre, String siglas, String telefono, String direccion, String observaciones) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("siglas", siglas);
        values.put("telefono", telefono);
        values.put("direccion", direccion);
        values.put("descripcion", observaciones);
        //values.put(ChatContract.ChatsEntry.DATE_TIME, getDateTime());

        // Insert the new row, returning the primary key value of the new row
        return sqLiteDatabase.insert(Constants.TABLA_INSTITUCIONES_EDUCATIVAS, null, values);

    }

    /**
     * actualiza una institucion educativa segun su id
     *
     * @param id
     * @param nombre
     * @param siglas
     * @param telefono
     * @param direccion
     * @param observaciones
     */
    public void updateUniversidad(int id, String nombre, String siglas, String telefono, String direccion, String observaciones) {
        String bingArgs[] = {nombre, siglas, telefono, direccion, observaciones, id + ""};
        String sql = "update " + Constants.TABLA_INSTITUCIONES_EDUCATIVAS + " set nombre=? , siglas=? , telefono=? , direccion=? , descripcion=? where institucion_id=? ";
        sqLiteDatabase.execSQL(sql, bingArgs);
    }


    public void deleteUniversidad(int id) {
        String bingArgs[] = {id + ""};
        String sql = "delete from " + Constants.TABLA_INSTITUCIONES_EDUCATIVAS + " where institucion_id=?";
        sqLiteDatabase.execSQL(sql, bingArgs);
    }


    public ArrayList<Carrera> getCarreras(String jquery, String[] selectionArgs) {

        ArrayList<Carrera> carreraArrayList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(jquery, selectionArgs);

        if (cursor == null) return null;


        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex("carrera_id"));
                int uid = cursor.getInt(cursor.getColumnIndex("institucion_id"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                carreraArrayList.add(new Carrera(id, nombre, uid));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return carreraArrayList;

    }

}
