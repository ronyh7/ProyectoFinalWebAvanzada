package com.progwebavanzada.ui;

import com.progwebavanzada.entidades.Mercancia;
import com.progwebavanzada.servicios.MercanciaServices;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by rony- on 12/6/2016.
 */
@SpringUI(path = "/indice")
@Theme("valo")
public class Indice extends UI {

    @Autowired
    private Menu menuBar;

    @Autowired
    private MercanciaServices mercanciaServices;

    @Override
    protected void init(VaadinRequest request) {
        menuBar.setPagina(this);
        ListSelect select = new ListSelect("Mercancias");
        List<Mercancia> mercancias = mercanciaServices.allMercancias();
        for(int i=0; i < mercancias.size();i++){
           select.addItem(mercancias.get(i).getNombre());
        }
        menuBar.addComponent(select);
        setContent(menuBar);
    }
}
