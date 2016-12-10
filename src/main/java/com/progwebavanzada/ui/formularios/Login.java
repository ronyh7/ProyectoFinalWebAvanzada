package com.progwebavanzada.ui.formularios;


import com.progwebavanzada.entidades.Rol;
import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.servicios.RolServices;
import com.progwebavanzada.servicios.UsuarioServices;
import com.vaadin.annotations.Theme;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;


@SpringUI(path = "/login")
@Theme("valo")
public class Login extends UI {

    @Autowired
    private UsuarioServices usuarioService;

    @Autowired
    private RolServices rolServices;

    @Override
    protected void init(VaadinRequest vaadinRequest){
        MenuBar menuBar = new MenuBar();
        menuBar.setWidth("100%");
        AbsoluteLayout absoluteLayout = new AbsoluteLayout();
        absoluteLayout.setWidth("2000px");
        absoluteLayout.setHeight("500px");
        TextField correo = new TextField("Correo");
        TextField password=new TextField("Password");
        correo.setValue("rony.hernandez.809@gmail.com");
        password.setValue("admin");

        Label nombreC = new Label("Si desea cambiar el nombre o correo utilizado en la sesion anterior, puede hacerlo ahora");
        correo.addValidator(new EmailValidator("debe ser un correo valido"));
        if(usuarioService.allUsuarios().size() <= 0){
            Usuario usuario = new Usuario();
            usuario.setCorreo("rony.hernandez.809@gmail.com");
            usuario.setPassword("admin");
            usuario.setNombre("Admin");
            usuario.setApellido("Administrador");
            usuario.setConsumidorFinal(false);
            usuario.setCedula("ADMIN");
            usuarioService.crearUsuario(usuario);
            Rol rol= new Rol();
            rol.setRol("ADMIN");
            rol.setUsuario(usuario);
            rolServices.crearRol(rol);

        }
        Button save = new Button("Ingresar", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Usuario usuario = usuarioService.usuarioCorreo(correo.getValue());
                if(usuario!=null) {
                    if (password.getValue().equals(usuario.getPassword())) {
                        getSession().setAttribute("usuario", usuario);
                        getUI().getPage().setLocation("http://localhost:8080/indice");
                    }
                }
                else{
                    password.setValue("");

                }
            }
        });
        absoluteLayout.addComponent(menuBar,"left: 0px; top: 0px");
        absoluteLayout.addComponent(correo,"left: 500px; top: 150px;");
        absoluteLayout.addComponent(password,"left: 500px; top: 225px;");
        absoluteLayout.addComponent(save,"left: 550px; top: 300px;");
        VerticalLayout vertical = new VerticalLayout();
        vertical.addComponent(absoluteLayout);
        setContent(absoluteLayout);

    }




}
