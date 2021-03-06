package com.progwebavanzada.entidades;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rony- on 12/6/2016.
 */
@Entity
public class Usuario implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String correo;
    private String password;
    private String nombre;
    private String apellido;
    private String cedula;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "usuario",fetch= FetchType.EAGER)
    private List<Rol> roles;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER)
    private List<Factura> facturas;

    private boolean consumidorFinal;

    @OneToOne
    private Factura carritoActual;

    @Transient
    private boolean Admin;

    @Transient
    private boolean Inventario;

    @Transient
    private boolean Ventas;



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

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setCarritos(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public Factura getCarritoActual() {
        return carritoActual;
    }

    public void setCarritoActual(Factura carritoActual) {
        this.carritoActual = carritoActual;
    }

    public boolean isAdmin() {
        if(roles.toString().contains("ADMIN")){
            return true;
        }
        return false;
    }

    public void setAdmin(boolean admin) {
        Admin = admin;
    }

    public boolean isInventario() {
        if(roles.toString().contains("INVENTARIO"))
            return true;
        return false;
    }

    public void setInventario(boolean inventario) {
        Inventario = inventario;
    }

    public boolean isVentas() {
        if(roles.toString().contains("VENTAS"))
            return true;
        return false;
    }

    public void setVentas(boolean ventas) {
        Ventas = ventas;
    }
}
