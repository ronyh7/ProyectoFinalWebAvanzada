package com.progwebavanzada.servicios;

import com.progwebavanzada.entidades.Mercancia;
import com.progwebavanzada.repositorios.MercanciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rony- on 12/6/2016.
 */
@Service
public class MercanciaServices {
    @Autowired
    MercanciaRepository mercanciaRepository;

    public Mercancia crearMercancia(Mercancia mercancia){
        return mercanciaRepository.save(mercancia);
    }

    public List<Mercancia> allMercancias(){
        return mercanciaRepository.findAll();
    }
}
