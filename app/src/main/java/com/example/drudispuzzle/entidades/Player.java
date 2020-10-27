package com.example.drudispuzzle.entidades;

import java.sql.Time;

public class Player {

    private String nombre;
    private Time time;

    public Player(String nombre, Time time) {
        this.nombre = nombre;
        this.time = time;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}