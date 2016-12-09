package com.progwebavanzada.repositorios;

import com.progwebavanzada.entidades.Compra;
import com.progwebavanzada.entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by rony- on 12/8/2016.
 */
public interface
FacturaRepository extends JpaRepository<Factura,Long> {
    Factura id(int id);

    List<Compra> mercancias(Factura factura);
}
