package com.example.drudispuzzle.entidades;

import java.io.Serializable;


public class Usuario implements  Serializable{

    private String p;
    private String t;


    public Usuario(String p, String t) {
        this.p = p;
        this.t = t;

    }

    public Usuario(){

    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    }

