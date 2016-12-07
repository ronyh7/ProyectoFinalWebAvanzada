package com.progwebavanzada.ui;

import com.progwebavanzada.entidades.Mercancia;
import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.servicios.MercanciaServices;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * Created by rony- on 12/7/2016.
 */
@SpringUI(path = "/listaMercancia")
@Theme("valo")
public class ListaMercancia extends UI {
    @Autowired
    private MercanciaServices mercanciaServices;

    @Autowired
    private Menu menu;

    private Button editar = new Button("Editar Mercancia");
    private Button borrar = new Button("Borrar Mercancia");

    @Override
    protected void init(VaadinRequest vaadinRequest){
        menu.setPagina(this);
        Collection<Mercancia> mercancias = mercanciaServices.allMercancias();
        BeanItemContainer<Mercancia> container =
                new BeanItemContainer<Mercancia>(Mercancia.class, mercancias);
        Grid grid = new Grid(container);
        grid.setWidth("100%");


        editar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Mercancia mercancia= (Mercancia) grid.getSelectedRow();
                getUI().getPage().setLocation("http://localhost:8080/editarMercancia?id="+mercancia.getId());

            }
        });
        borrar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Mercancia mercancia= (Mercancia) grid.getSelectedRow();
                mercanciaServices.borrarMercancia(mercancia);
                getUI().getPage().setLocation("http://localhost:8080/listaMercancia");
            }
        });
        HorizontalLayout h1 = new HorizontalLayout();
        h1.addComponents(editar,borrar);
        menu.addComponent(h1);
        menu.addComponent(grid);
        setContent(menu);
    }
}
