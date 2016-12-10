package com.progwebavanzada.ui;

import com.progwebavanzada.entidades.Factura;
import com.progwebavanzada.entidades.Mercancia;
import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.servicios.FacturaServices;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * Created by rony- on 12/10/2016.
 */
@SpringUI(path = "/listaFactura")
@Theme("valo")
public class FacturasPendientes extends UI {

    @Autowired
    Menu menu;

    @Autowired
    FacturaServices facturaServices;

    private Usuario usuarioLogueado;

    @Override
    protected void init(VaadinRequest vaadinRequest){
        menu.setPagina(this);
        if(getSession().getAttribute("usuario")==null){
            getUI().getPage().setLocation("http://localhost:8080/login");
        }
        else{
            usuarioLogueado=(Usuario)getSession().getAttribute("usuario");
            if(!usuarioLogueado.isAdmin())
                getUI().getPage().setLocation("http://localhost:8080/indice");
        }
        Collection<Factura> facturas =  facturaServices.allFacturas();
        BeanItemContainer<Factura> container =new BeanItemContainer<Factura>(Factura.class, facturas);
        Grid grid = new Grid(container);
        grid.setWidth("100%");

        menu.addComponent(grid);
        setContent(menu);

    }

}
