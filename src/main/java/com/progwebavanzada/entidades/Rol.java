package com.progwebavanzada.entidades;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by rony- on 12/6/2016.
 */
@Entity
public class Rol implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String rol;

    @ManyToOne
    private Usuario usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString(){
        return rol;
    }
}
