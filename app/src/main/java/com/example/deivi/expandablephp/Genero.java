package com.example.deivi.expandablephp;

/**
 * Created by Deivi on 29/01/2017.
 */

public class Genero {
    private int codigo;
    private String nombre;

    public Genero(){
        codigo = 0;
        nombre = "";
    }

    public Genero(int codigo, int cod_genero, String nombre, String descripcion){
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    public void setNombre(String nombre){this.nombre = nombre;}
    public int getCodigo(){
        return codigo;
    }
    public String getNombre(){
        return nombre;
    }
}
