package com.progwebavanzada.ui;

import com.progwebavanzada.entidades.Compra;
import com.progwebavanzada.entidades.Factura;
import com.progwebavanzada.entidades.Mercancia;
import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.servicios.CompraServices;
import com.progwebavanzada.servicios.FacturaServices;
import com.progwebavanzada.servicios.MercanciaServices;
import com.progwebavanzada.servicios.UsuarioServices;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by rony- on 12/8/2016.
 */
@SpringUI(path = "/carrito")
@Theme("valo")
public class Carrito extends UI {

    @Autowired
    private UsuarioServices usuarioServices;
    @Autowired
    private FacturaServices facturaServices;

    @Autowired
    private CompraServices compraServices;

    @Autowired
    private MercanciaServices mercanciaServices;

    @Autowired
    private Menu menu;

    private Usuario usuarioLogueado;

    private Factura nuevaFactura= new Factura();

    Button comprar = new Button("comprar");

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
            Usuario usuario = usuarioServices.crearUsuario(usuarioLogueado);
            if (usuario.getCarritoActual() == null)
                usuario.setCarritoActual(new Factura());
            Collection<Compra> carrito = usuario.getCarritoActual().getMercancias();
            List<Compra> compras = usuario.getCarritoActual().getMercancias();
            BeanItemContainer<Compra> container =
                    new BeanItemContainer<Compra>(Compra.class, carrito);
            Grid grid = new Grid(container);
            grid.setWidth("100%");
            grid.removeColumn("factura");
            grid.removeColumn("id");

            comprar.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    boolean compraCompletada = true;
                    for (int i = 0; i < compras.size(); i++) {
                        if (compras.get(i).getCantidad() <= compras.get(i).getMercancia().getCantidad()) {
                            int resta = compras.get(i).getMercancia().getCantidad() - compras.get(i).getCantidad();
                            compras.get(i).getMercancia().setCantidad(resta);
                            mercanciaServices.crearMercancia(compras.get(i).getMercancia());
                        } else {
                            compraCompletada = false;
                            break;
                        }
                    }
                    if (compraCompletada) {
                        nuevaFactura.setFacturada(true);
                        nuevaFactura.setCliente(usuario);
                        nuevaFactura.setFecha(new Date());
                        facturaServices.crearFactura(nuevaFactura);
                        float total = 1;
                        for (int i = 0; i < compras.size(); i++) {
                            compras.get(i).setFactura(nuevaFactura);
                            total += compras.get(i).getCantidad() * compras.get(i).getMercancia().getPrecio();
                            compraServices.crearCompra(compras.get(i));
                        }
                        nuevaFactura.setTotal(total);
                        facturaServices.crearFactura(nuevaFactura);
                        Factura facturavieja = usuario.getCarritoActual();
                        usuario.setCarritoActual(null);
                        usuarioServices.crearUsuario(usuario);
                        getSession().setAttribute("usuario", usuario);
                        facturaServices.borrarFactura(facturavieja);

                        getUI().getPage().setLocation("http://localhost:8080/report?id=" + nuevaFactura.getId());
                    }
                }
            });


            menu.addComponent(grid);
            menu.addComponent(comprar);
            setContent(menu);
        }
    }
}
