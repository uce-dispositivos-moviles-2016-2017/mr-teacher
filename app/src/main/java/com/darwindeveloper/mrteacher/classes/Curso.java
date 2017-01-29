package com.darwindeveloper.mrteacher.classes;

/**
 * Created by DARWIN on 26/1/2017.
 */

public class Curso {

    private long id, u_id;
    private String nombre, paralelo, descripcion;

    public Curso(long id, String nombre, String paralelo) {
        this.id = id;
        this.nombre = nombre;
        this.paralelo = paralelo;
    }


    public Curso(long id, String nombre, String paralelo, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.paralelo = paralelo;
        this.descripcion = descripcion;
    }

    public Curso(long id, long u_id, String nombre, String paralelo, String descripcion) {
        this.id = id;
        this.u_id = u_id;
        this.nombre = nombre;
        this.paralelo = paralelo;
        this.descripcion = descripcion;
    }


    public long getU_id() {
        return u_id;
    }


    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getParalelo() {
        return paralelo;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
