package com.progwebavanzada.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rony- on 12/8/2016.
 */
@Entity
public class Factura implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Usuario cliente;

    private Date Fecha;

    private boolean facturada;

    private float total;

    private boolean entregada;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "factura")
    private List<Compra> mercancias;

    public Factura(){
        mercancias=new ArrayList<>();
        facturada=false;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public List<Compra> getMercancias() {
        return mercancias;
    }

    public void setMercancias(List<Compra> mercancias) {
        this.mercancias = mercancias;
    }

    public boolean isFacturada() {
        return facturada;
    }

    public void setFacturada(boolean facturada) {
        this.facturada = facturada;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public boolean isEntregada() {
        return entregada;
    }

    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }
}
