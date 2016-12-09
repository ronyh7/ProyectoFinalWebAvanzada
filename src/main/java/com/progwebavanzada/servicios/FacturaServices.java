package com.progwebavanzada.servicios;

import com.progwebavanzada.entidades.Factura;
import com.progwebavanzada.repositorios.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rony- on 12/8/2016.
 */
@Service
public class FacturaServices {
    @Autowired
    FacturaRepository facturaRepository;

    public Factura crearFactura(Factura factura){
        return facturaRepository.save(factura);
    }

    public List<Factura> allFacturas(){
        return facturaRepository.findAll();
    }

    public void borrarFactura(Factura factura){
        facturaRepository.delete(factura);
    }
}
