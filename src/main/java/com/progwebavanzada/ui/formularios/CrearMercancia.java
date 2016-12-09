package com.progwebavanzada.ui.formularios;

import com.progwebavanzada.entidades.Mercancia;
import com.progwebavanzada.servicios.MercanciaServices;
import com.progwebavanzada.ui.Menu;
import com.vaadin.annotations.Theme;
import com.vaadin.data.validator.FloatRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by rony- on 12/6/2016.
 */
@SpringUI(path = "/crearMercancia")
@Theme("valo")
public class CrearMercancia extends UI {

    @Autowired
    private MercanciaServices mercanciaServices;

    private TextField nombre=new TextField("Nombre");
    private TextField descripcion=new TextField("Descripcion");
    private TextField cantidad = new TextField("Cantidad");
    private TextField precio = new TextField("Precio");
    @Autowired
    private Menu menu;
    //Upload Images TO DO
    private Button guardar = new Button("guardar");

    @Override
    protected void init(VaadinRequest vaadinRequest){
        menu.setPagina(this);
        cantidad.addValidator(new IntegerRangeValidator("tiene que ser un numero entero de 1 a 1000",1,1000));
        precio.addValidator(new FloatRangeValidator("tiene que ser un numero",1.0f,10000000.0f));
        guardar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Mercancia mercancia = new Mercancia();
                mercancia.setNombre(nombre.getValue());
                mercancia.setDescripcion(descripcion.getValue());
                mercancia.setCantidad(Integer.parseInt(cantidad.getValue()));
                mercancia.setPrecio(Float.parseFloat(precio.getValue()));
                mercanciaServices.crearMercancia(mercancia);
                getUI().getPage().setLocation("http://localhost:8080/indice");
            }
        });

        menu.addComponents(nombre,descripcion,cantidad,precio,guardar);
        setContent(menu);

    }

}
