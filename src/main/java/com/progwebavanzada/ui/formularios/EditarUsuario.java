package com.progwebavanzada.ui.formularios;

import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.servicios.RolServices;
import com.progwebavanzada.servicios.UsuarioServices;
import com.progwebavanzada.ui.Menu;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by rony- on 12/6/2016.
 */
@SpringUI(path = "/editarUsuario")
@Theme("valo")
public class EditarUsuario extends UI {
    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private RolServices rolServices;

    @Autowired
    private Menu menu;

    private int usuarioID;
    private TextField correo=new TextField("Email");
    private TextField password = new TextField("Password");
    private TextField cedula = new TextField("Cedula");
    private TextField nombre=new TextField("Nombre");
    private TextField apellido= new TextField("Apellido");
    private CheckBox consumidorFinal = new CheckBox("Consumidor Final?");
    private Button guardar =new Button("Guardar Cambios");

    @Override
    protected void init(VaadinRequest vaadinRequest){
        usuarioID=Integer.parseInt(vaadinRequest.getParameter("id"));
        Usuario usuario = usuarioServices.usuarioID(usuarioID);
        correo.setValue(usuario.getCorreo());
        password.setValue(usuario.getPassword());
        cedula.setValue(usuario.getCedula());
        nombre.setValue(usuario.getNombre());
        apellido.setValue(usuario.getApellido());
        consumidorFinal.setValue(usuario.isConsumidorFinal());

        guardar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Usuario usuario = new Usuario();
                usuario.setId(usuarioID);
                usuario.setCorreo(correo.getValue());
                usuario.setPassword(password.getValue());
                usuario.setCedula(cedula.getValue());
                usuario.setNombre(nombre.getValue());
                usuario.setApellido(apellido.getValue());
                usuario.setConsumidorFinal(consumidorFinal.getValue());
                usuarioServices.crearUsuario(usuario);
                getUI().getPage().setLocation("http://localhost:8080/indice");

            }
        });
        menu.addComponents(correo,password,cedula,nombre,apellido,consumidorFinal);
        menu.addComponent(guardar);
        setContent(menu);
    }

}
