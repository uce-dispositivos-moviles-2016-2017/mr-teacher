package com.darwindeveloper.mrteacher.classes;

/**
 * Created by SONY on 28/1/2017.
 */

public class Universidad {

    private int id;

    private String nombre, siglas, telefono, direccion, descripcion;

    public Universidad(int id, String nombre, String siglas, String telefono, String direccion, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.siglas = siglas;
        this.telefono = telefono;
        this.direccion = direccion;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }


    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
