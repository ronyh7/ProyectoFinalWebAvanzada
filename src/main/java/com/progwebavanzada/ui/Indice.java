package com.progwebavanzada.ui;

import com.progwebavanzada.entidades.Compra;
import com.progwebavanzada.entidades.Factura;
import com.progwebavanzada.entidades.Mercancia;
import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.servicios.CompraServices;
import com.progwebavanzada.servicios.FacturaServices;
import com.progwebavanzada.servicios.MercanciaServices;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.jfree.ui.Spinner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * Created by rony- on 12/6/2016.
 */
@SpringUI(path = "/indice")
@Theme("valo")
public class Indice extends UI {

    @Autowired
    private Menu menu;

    @Autowired
    private MercanciaServices mercanciaServices;

    @Autowired
    private CompraServices compraServices;

    @Autowired
    private FacturaServices facturaServices;

    private Usuario usuarioLogueado;

    TextField cantidad = new TextField("cantidad");
    Button carrito = new Button("Agregar al Carrito");

    @Override
    protected void init(VaadinRequest request) {
        menu.setPagina(this);
        if(getSession().getAttribute("usuario")==null){
            getUI().getPage().setLocation("http://localhost:8080/login");
        }
        else{
            usuarioLogueado=(Usuario)getSession().getAttribute("usuario");
        }
        HorizontalLayout comprar= new HorizontalLayout();
        comprar.addComponents(cantidad,carrito);
        comprar.setVisible(false);
        Collection<Mercancia> mercancias = mercanciaServices.allMercancias();
        BeanItemContainer<Mercancia> container =
                new BeanItemContainer<Mercancia>(Mercancia.class, mercancias);
        Grid grid = new Grid(container);
        grid.setWidth("50%");

        grid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent event) {
                comprar.setVisible(true);
            }
        });


        carrito.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Mercancia mercancia = (Mercancia) grid.getSelectedRow();
                Compra compra = new Compra();
                compra.setMercancia(mercancia);
                if(Integer.parseInt(cantidad.getValue())<= mercancia.getCantidad()) {
                    compra.setCantidad(Integer.parseInt(cantidad.getValue()));
                    if (usuarioLogueado.getCarritoActual() == null) {
                        usuarioLogueado.setCarritoActual(new Factura());
                        facturaServices.crearFactura(usuarioLogueado.getCarritoActual());
                    }
                    compra.setFactura(usuarioLogueado.getCarritoActual());
                    compraServices.crearCompra(compra);
                }
                comprar.setVisible(false);
            }
        });

        menu.addComponent(grid);
        menu.addComponent(comprar);
        setContent(menu);
    }
}
