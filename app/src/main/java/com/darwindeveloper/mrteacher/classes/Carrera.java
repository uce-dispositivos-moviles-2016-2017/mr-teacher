package com.darwindeveloper.mrteacher.classes;

/**
 * Created by DARWIN on 29/1/2017.
 */

public class Carrera {

    private int id;
    private String nombre;
    private int universidad_id;


    public Carrera(int id, String nombre, int universidad_id) {
        this.id = id;
        this.nombre = nombre;
        this.universidad_id = universidad_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUniversidad_id() {
        return universidad_id;
    }

    public void setUniversidad_id(int universidad_id) {
        this.universidad_id = universidad_id;
    }
}
