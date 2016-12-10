package com.progwebavanzada.ui.formularios;

import com.progwebavanzada.entidades.Usuario;
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
 * Created by rony- on 12/10/2016.
 */
@SpringUI(path = "/editarCliente")
@Theme("valo")
public class EditarCliente extends UI{

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private Menu menu;
    private Usuario usuarioLogueado;
    private TextField correo=new TextField("Email");
    private TextField password = new TextField("Password");
    private TextField nombre=new TextField("Nombre");
    private TextField apellido= new TextField("Apellido");
    private Button guardar =new Button("Guardar Cambios");

    @Override
    protected void init(VaadinRequest vaadinRequest){
        menu.setPagina(this);
        boolean usuarioExiste=false;
        if(getSession().getAttribute("usuario")==null){
            getUI().getPage().setLocation("http://localhost:8080/login");
        }
        else{
            usuarioLogueado=(Usuario)getSession().getAttribute("usuario");
            usuarioExiste=true;
        }
        if(usuarioExiste) {
            correo.setValue(usuarioLogueado.getCorreo());
            password.setValue(usuarioLogueado.getPassword());
            nombre.setValue(usuarioLogueado.getNombre());
            apellido.setValue(usuarioLogueado.getApellido());

            guardar.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Usuario usuario = new Usuario();
                    usuario.setId(usuarioLogueado.getId());
                    usuario.setCorreo(correo.getValue());
                    usuario.setPassword(password.getValue());
                    usuario.setCedula(usuarioLogueado.getCedula());
                    usuario.setNombre(nombre.getValue());
                    usuario.setApellido(apellido.getValue());
                    usuario.setConsumidorFinal(usuarioLogueado.isConsumidorFinal());
                    usuarioServices.crearUsuario(usuario);
                    getUI().getPage().setLocation("http://localhost:8080/indice");
                }
            });
            menu.addComponents(correo, password, nombre, apellido);
            menu.addComponent(guardar);
            setContent(menu);
        }
    }
}
