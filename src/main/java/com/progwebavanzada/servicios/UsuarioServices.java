package com.progwebavanzada.servicios;

import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rony- on 12/6/2016.
 */
@Service
public class UsuarioServices
{
    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> allUsuarios(){
        return usuarioRepository.findAll();
    }
}
