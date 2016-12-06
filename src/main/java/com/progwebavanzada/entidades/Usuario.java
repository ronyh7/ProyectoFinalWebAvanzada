package com.progwebavanzada.entidades;

import com.vaadin.spring.annotation.UIScope;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by rony- on 12/6/2016.
 */
@Entity
public class Usuario {
    @Id
    @GeneratedValue
    private int id;
    private String correo;
    private String password;
    private String nombre;
    private String apellido;
    private String cedula;
    @OneToMany(mappedBy = "usuario")
    private List<Rol> roles;
    private boolean consumidorFinal;

    public Usuario(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public boolean isConsumidorFinal() {
        return consumidorFinal;
    }

    public void setConsumidorFinal(boolean consumidorFinal) {
        this.consumidorFinal = consumidorFinal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
