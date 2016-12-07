package com.progwebavanzada.servicios;

import com.progwebavanzada.entidades.Rol;
import com.progwebavanzada.repositorios.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rony- on 12/6/2016.
 */
@Service
public class RolServices {
    @Autowired
    private RolRepository rolRepository;

    @Transactional
    public Rol crearRol(Rol rol){
        return rolRepository.save(rol);
    }

}
