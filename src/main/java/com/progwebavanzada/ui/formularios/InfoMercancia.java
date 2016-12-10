package com.progwebavanzada.ui.formularios;

import com.progwebavanzada.entidades.Mercancia;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.stereotype.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by rony- on 12/9/2016.
 */
@org.springframework.stereotype.Component
@UIScope
public class InfoMercancia extends VerticalLayout {

    public Embedded image = new Embedded();
    private TextField nombre = new TextField("Nombre");
    private TextArea descripcion = new TextArea("Descripcion");
    private TextField precio = new TextField("Precio");
    private TextField cantidad = new TextField("Cantidad Existente");
    public File file;


    public InfoMercancia(){}
    public InfoMercancia(Mercancia mercancia) {
        file = new File("C:/var/" + mercancia.getRutaImagen());
        image.setVisible(true);
        image.setSource(new FileResource(file));
        image.setWidth("300px");
        image.setHeight("300px");
        descripcion.setHeight("30%");
        descripcion.setWidth("50%");
        nombre.setValue(mercancia.getNombre());
        descripcion.setValue(mercancia.getDescripcion());
        precio.setValue(mercancia.getPrecio()+"");
        cantidad.setValue(mercancia.getCantidad()+"");
        nombre.setReadOnly(true);
        descripcion.setReadOnly(true);
        precio.setReadOnly(true);
        cantidad.setReadOnly(true);
        addComponents(image,nombre,precio,cantidad,descripcion);

    }


}


