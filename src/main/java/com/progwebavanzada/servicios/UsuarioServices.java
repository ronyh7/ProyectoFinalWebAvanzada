package com.progwebavanzada.servicios;

import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.repositorios.RolRepository;
import com.progwebavanzada.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rony- on 12/6/2016.
 */
@Service
public class UsuarioServices
{
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Transactional
    public Usuario crearUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> allUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario usuarioID(int id){
        return usuarioRepository.id(id);
    }

    public Usuario usuarioCorreo(String correo){
        return usuarioRepository.correo(correo);
    }

    @Transactional
    public void borrarUsuario(Usuario usuario){
        for(int i=0;i<usuario.getRoles().size();i++){
            rolRepository.delete(usuario.getRoles().get(i));
        }
        usuarioRepository.delete(usuario);
    }
}
