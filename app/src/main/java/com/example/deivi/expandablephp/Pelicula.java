package com.example.deivi.expandablephp;

/**
 * Created by Deivi on 29/01/2017.
 */

public class Pelicula {
    private int codigo;
    private int cod_genero;
    private String nombre;
    private String descripcion;

    public Pelicula(){
        codigo = 0;
        cod_genero = 0;
        nombre = "";
        descripcion = "";
    }

    public Pelicula(int codigo, int cod_genero, String nombre, String descripcion){
        this.codigo = codigo;
        this.cod_genero = cod_genero;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    public void setCodigoGenero(int cod_genero){
        this.cod_genero = cod_genero;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public int getCodigo(){
        return codigo;
    }
    public int getCod_genero(){
        return cod_genero;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }
}
