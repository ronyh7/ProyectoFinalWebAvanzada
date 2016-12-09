package com.progwebavanzada.repositorios;

import com.progwebavanzada.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rony- on 12/6/2016.
 */
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
        Usuario id(int id);
        Usuario correo(String correo);

}
