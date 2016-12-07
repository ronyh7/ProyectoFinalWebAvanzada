package com.progwebavanzada.ui.formularios;

import com.progwebavanzada.entidades.Mercancia;
import com.progwebavanzada.servicios.MercanciaServices;
import com.progwebavanzada.ui.Menu;
import com.vaadin.annotations.Theme;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by rony- on 12/7/2016.
 */
@SpringUI(path = "/editarMercancia")
@Theme("valo")
public class EditarMercancia extends UI{
    @Autowired
    private MercanciaServices mercanciaServices;

    private TextField nombre=new TextField("Nombre");
    private TextField descripcion=new TextField("Descripcion");
    private TextField cantidad = new TextField("Cantidad");
    @Autowired
    private Menu menu;

    private int mercanciaID;
    //Upload Images TO DO
    private Button guardar = new Button("Guardar Cambios");

    @Override
    protected void init(VaadinRequest vaadinRequest){
        menu.setPagina(this);
        mercanciaID = Integer.parseInt(vaadinRequest.getParameter("id"));
        Mercancia mercancia = mercanciaServices.mercanciaID(mercanciaID);
        nombre.setValue(mercancia.getNombre());
        descripcion.setValue(mercancia.getDescripcion());
        cantidad.setValue(mercancia.getCantidad()+"");
        cantidad.addValidator( new IntegerRangeValidator("tiene que ser un numero entero de 1 a 1000",1,1000));

        guardar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Mercancia mercanciaNueva = new Mercancia();
                mercanciaNueva.setId(mercanciaID);
                mercanciaNueva.setNombre(nombre.getValue());
                mercanciaNueva.setDescripcion(descripcion.getValue());
                mercanciaNueva.setCantidad(Integer.parseInt(cantidad.getValue()));
                mercanciaServices.crearMercancia(mercanciaNueva);
                getUI().getPage().setLocation("http://localhost:8080/indice");

            }
        });

        menu.addComponents(nombre,descripcion,cantidad,guardar);
        setContent(menu);
    }
}
