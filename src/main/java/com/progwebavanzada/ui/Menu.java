package com.progwebavanzada.ui;

import com.progwebavanzada.entidades.Usuario;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.stereotype.Component;

/**
 * Created by rony- on 12/6/2016.
 */
@Component
@UIScope
public class Menu extends VerticalLayout {

    private UI pagina;

    public Menu(){
        MenuBar menuBar = new MenuBar();
        menuBar.setWidth("100%");

        MenuBar.Command home = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getPage().setLocation("http://localhost:8080/indice");
            }
        };

        MenuBar.Command crearUsuarioC = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getPage().setLocation("http://localhost:8080/crearUsuario");
            }
        };

        MenuBar.Command listarUsuariosC = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getPage().setLocation("http://localhost:8080/listaUsuario");
            }
        };

        MenuBar.Command editarClienteC = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getPage().setLocation("http://localhost:8080/editarCliente");

            }
        };

        MenuBar.Command crearMercanciaC = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getPage().setLocation("http://localhost:8080/crearMercancia");
            }
        };

        MenuBar.Command listaMercanciaC = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getPage().setLocation("http://localhost:8080/listaMercancia");
            }
        };

        MenuBar.Command carritoC = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getPage().setLocation("http://localhost:8080/carrito");
            }
        };

        MenuBar.Command logoutC = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getSession().setAttribute("usuario",null);
                getUI().getPage().setLocation("http://localhost:8080/login");
            }
        };

        MenuBar.MenuItem bienvenido = menuBar.addItem("Bienvenido",home);

        MenuBar.MenuItem usuario = menuBar.addItem("Usuario", null);
        MenuBar.MenuItem crearUsuario = usuario.addItem("Crear Usuario", crearUsuarioC);
        MenuBar.MenuItem listaUsuario = usuario.addItem("Lista de Usuarios", listarUsuariosC);
        MenuBar.MenuItem editarCliente= usuario.addItem("Editar Cliente",editarClienteC);

        MenuBar.MenuItem mercancias = menuBar.addItem("Mercancias",null);

        MenuBar.MenuItem crearMercancia = mercancias.addItem("Crear Mercancia",crearMercanciaC );
        MenuBar.MenuItem listaMercancia = mercancias.addItem("Lista de Mercancias",listaMercanciaC);

        MenuBar.MenuItem carrito = menuBar.addItem("Carrito",carritoC);

        MenuBar.MenuItem logout = menuBar.addItem("Cerrar Sesion",logoutC);

        addComponent(menuBar);

    }


    public void setPagina(UI pagina) {
        this.pagina = pagina;
    }

    public void usuarioLogueado(){
        if(getSession().getAttribute("usuario")==null){
            getUI().getPage().setLocation("http://localhost:8080/login");
        }
    }
}
