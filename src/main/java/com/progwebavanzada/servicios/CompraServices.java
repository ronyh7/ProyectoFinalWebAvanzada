package com.progwebavanzada.servicios;

import com.progwebavanzada.entidades.Compra;
import com.progwebavanzada.repositorios.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rony- on 12/8/2016.
 */
@Service
public class CompraServices {
    @Autowired
    CompraRepository compraRepository;

    public Compra crearCompra(Compra compra){
        return compraRepository.save(compra);
    }
    public List<Compra> allCompras(){
        return compraRepository.findAll();
    }
}
