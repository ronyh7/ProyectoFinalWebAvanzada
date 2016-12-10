package com.progwebavanzada.ui.formularios;

import com.progwebavanzada.entidades.Rol;
import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.repositorios.RolRepository;
import com.progwebavanzada.servicios.EmailServices;
import com.progwebavanzada.servicios.RolServices;
import com.progwebavanzada.servicios.UsuarioServices;
import com.progwebavanzada.ui.Menu;
import com.vaadin.annotations.Theme;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
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
    private EmailServices emailServices;
    @Autowired
    private Menu menu;

    private Usuario usuarioLogueado;

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
        if(getSession().getAttribute("usuario")==null){
            getUI().getPage().setLocation("http://localhost:8080/login");
        }
        else{
            usuarioLogueado=(Usuario)getSession().getAttribute("usuario");
            if(!usuarioLogueado.isAdmin() && !usuarioLogueado.isVentas())
                getUI().getPage().setLocation("http://localhost:8080/indice");
        }
        correo.addValidator(new EmailValidator("Debe ser un Email valido"));

        ListSelect listSelect = new ListSelect("Roles(Para una cuenta cliente, no seleccione ningun rol)");
        listSelect.addItems("ADMIN","INVENTARIO","VENTAS");
        listSelect.setMultiSelect(true);
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
                if(listSelect.getValue().toString().equals("[]")){
                 Rol rol = new Rol();
                    rol.setRol("CLIENTE");
                    rol.setUsuario(usuario);
                    rolServices.crearRol(rol);
                    String subject="Nueva cuenta en el sistema de ventas";
                    String mensaje="Hola,"+usuario.getNombre()+" "+usuario.getApellido()+", los detalles para accesar a su cuenta son " +
                            "su Correo: "+usuario.getCorreo()+" y su contraseña temporal sera: "+usuario.getPassword()+ ". Favor cambiar esta lo mas pronto posible";
                    emailServices.sendMailCliente(usuarioLogueado.getCorreo(),usuario.getCorreo(),subject,mensaje);
                }
                else {
                    String[] roles = listSelect.getValue().toString().split(",");
                    for (int i = 0; i < roles.length; i++) {
                        Rol rol = new Rol();
                        rol.setRol(roles[i]);
                        rol.setUsuario(usuario);
                        rolServices.crearRol(rol);
                    }
                }
                getUI().getPage().setLocation("http://localhost:8080/indice");

            }
        });
        menu.addComponents(correo,password,cedula,nombre,apellido,listSelect,consumidorFinal);
        menu.addComponent(guardar);
        setContent(menu);
    }
}
