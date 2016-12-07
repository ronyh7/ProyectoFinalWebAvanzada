package com.progwebavanzada.ui.formularios;

import com.progwebavanzada.entidades.Rol;
import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.repositorios.RolRepository;
import com.progwebavanzada.servicios.RolServices;
import com.progwebavanzada.servicios.UsuarioServices;
import com.progwebavanzada.ui.Menu;
import com.vaadin.annotations.Theme;
import com.vaadin.data.validator.EmailValidator;
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
@SpringUI(path = "/crearUsuario")
@Theme("valo")
public class CrearUsuario extends UI {

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private RolServices rolServices;
    @Autowired
    private Menu menu;

    private TextField correo=new TextField("Email");
    private TextField password = new TextField("Password");
    private TextField cedula = new TextField("Cedula");
    private TextField nombre=new TextField("Nombre");
    private TextField apellido= new TextField("Apellido");
    private CheckBox consumidorFinal = new CheckBox("Consumidor Final?");
    private Button  guardar =new Button("Guardar");


    @Override
    protected void init(VaadinRequest vaadinRequest){
        menu.setPagina(this);
        correo.addValidator(new EmailValidator("Debe ser un Email valido"));
        guardar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Usuario usuario= new Usuario();
                usuario.setCorreo(correo.getValue());
                usuario.setPassword(password.getValue());
                usuario.setNombre(nombre.getValue());
                usuario.setApellido(apellido.getValue());
                usuario.setCedula(cedula.getValue());
                usuario.setConsumidorFinal(consumidorFinal.getValue());
                usuarioServices.crearUsuario(usuario);
                Rol rol = new Rol();
                rol.setRol("ADMIN");
                rol.setUsuario(usuario);
                rolServices.crearRol(rol);
                getUI().getPage().setLocation("http://localhost:8080/indice");

            }
        });
        menu.addComponents(correo,password,cedula,nombre,apellido,consumidorFinal);
        menu.addComponent(guardar);
        setContent(menu);
    }
}
