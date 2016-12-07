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

    private Button editar = new Button("Editar Usuario");
    private Button borrar = new Button("Borrar Usuario");

    @Override
    protected void init(VaadinRequest vaadinRequest){
        menu.setPagina(this);
        Collection<Usuario> usuarios = usuarioServices.allUsuarios();

        BeanItemContainer<Usuario> container =
                new BeanItemContainer<Usuario>(Usuario.class, usuarios);

        for(int i=0; i< container.size();i++){
            for(int j=0 ; j<container.getIdByIndex(i).getRoles().size();j++){
                Usuario usuario = container.getIdByIndex(i);
                usuario.getNombreRoles().add(usuario.getRoles().get(j).getRol());
            }
        }
        Grid grid = new Grid(container);
        grid.removeColumn("roles");
        grid.setWidth("100%");


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
