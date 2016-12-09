package com.progwebavanzada.entidades;

import javax.persistence.*;

/**
 * Created by rony- on 12/8/2016.
 */
@Entity
public class Compra {
    @Id
    @GeneratedValue
    private int id;
    private int cantidad;
    @OneToOne
    private Mercancia mercancia;

    @ManyToOne
    private Factura factura;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Mercancia getMercancia() {
        return mercancia;
    }

    public void setMercancia(Mercancia mercancia) {
        this.mercancia = mercancia;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}
