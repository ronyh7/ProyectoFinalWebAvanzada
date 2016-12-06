package com.progwebavanzada.vistas;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

/**
 * Created by rony- on 12/6/2016.
 */
@SpringUI(path = "/indice")
@Theme("valo")
public class Indice extends UI {

    @Override
    protected void init(VaadinRequest request) {
        AbsoluteLayout absoluteLayout = new AbsoluteLayout();
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Usuario", null);
        absoluteLayout.addComponent(menuBar);
    }
}
