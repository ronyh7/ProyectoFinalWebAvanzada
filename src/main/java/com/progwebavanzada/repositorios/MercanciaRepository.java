package com.progwebavanzada.repositorios;

import com.progwebavanzada.entidades.Mercancia;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rony- on 12/6/2016.
 */
public interface MercanciaRepository extends JpaRepository<Mercancia,Long> {
    Mercancia id(int id);
}
