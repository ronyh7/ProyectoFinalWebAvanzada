package com.progwebavanzada.ui;

import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.servicios.UsuarioServices;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * Created by rony- on 12/6/2016.
 */
@SpringUI(path = "/listaUsuario")
@Theme("valo")
public class ListaUsuario extends UI {

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private Menu menu;

    private Usuario usuarioLogueado;

    private Button editar = new Button("Editar Usuario");
    private Button borrar = new Button("Borrar Usuario");

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

        Collection<Usuario> usuarios = usuarioServices.allUsuarios();

        BeanItemContainer<Usuario> container =
                new BeanItemContainer<Usuario>(Usuario.class, usuarios);
        Grid grid = new Grid(container);
        grid.setWidth("100%");
        grid.removeColumn("carritoActual");
        grid.removeColumn("admin");
        grid.removeColumn("inventario");
        grid.removeColumn("ventas");
        grid.removeColumn("facturas");

        editar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Usuario usuario= (Usuario) grid.getSelectedRow();
                getUI().getPage().setLocation("http://localhost:8080/editarUsuario?id="+usuario.getId());

            }
        });
        borrar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Usuario usuario= (Usuario) grid.getSelectedRow();
                usuarioServices.borrarUsuario(usuario);
                getUI().getPage().setLocation("http://localhost:8080/listaUsuario");
            }
        });
        HorizontalLayout h1 = new HorizontalLayout();
        h1.addComponents(editar,borrar);
        menu.addComponent(h1);
        menu.addComponent(grid);
        setContent(menu);
    }

}
