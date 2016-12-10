package com.progwebavanzada.ui;

import com.progwebavanzada.entidades.Compra;
import com.progwebavanzada.entidades.Factura;
import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.servicios.EmailServices;
import com.progwebavanzada.servicios.FacturaServices;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * Created by rony- on 12/10/2016.
 */
@SpringUI(path = "/compra")
@Theme("valo")
public class CompraCompletada extends UI {

    @Autowired
    EmailServices emailServices;

    @Autowired
    FacturaServices facturaServices;

    @Autowired
    Menu menu;

    private Factura factura;

    private Label compra = new Label("Compra Completa!");

    private Label total = new Label();

    private Button descargar = new Button("Descargar Factura");

    @Override
    protected void init(VaadinRequest vaadinRequest){
        menu.setPagina(this);
        int id=Integer.parseInt(vaadinRequest.getParameter("id"));
        factura = facturaServices.facturaID(id);
        //getUI().getPage().setLocation("http://localhost:8080/report?id=" + id);

        compra.setStyleName("h1");
        Panel panel = new Panel();
        panel.setContent(compra);

        total.setValue(factura.getTotal()+"");
        total.setStyleName("h3");

        HorizontalLayout h1 = new HorizontalLayout();

        Collection<Compra> carrito = factura.getMercancias();
        BeanItemContainer<Compra> container =
                new BeanItemContainer<Compra>(Compra.class, carrito);
        Grid grid = new Grid(container);
        grid.setWidth("100%");
        grid.removeColumn("factura");
        grid.removeColumn("id");
        h1.addComponents(total,descargar);

        descargar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getPage().setLocation("http://localhost:8080/descargar?id="+factura.getId());

            }
        });

        menu.addComponent(panel);
        menu.addComponent(h1);
        menu.addComponent(grid);
        setContent(menu);

    }
}
