package com.progwebavanzada.controladores;

import com.progwebavanzada.entidades.Compra;
import com.progwebavanzada.entidades.Factura;
import com.progwebavanzada.entidades.Mercancia;
import com.progwebavanzada.entidades.Usuario;
import com.progwebavanzada.servicios.EmailServices;
import com.progwebavanzada.servicios.FacturaServices;
import com.progwebavanzada.servicios.UsuarioServices;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rony- on 12/8/2016.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    FacturaServices facturaServices;

    @Autowired
    EmailServices emailServices;

    @Autowired
    UsuarioServices usuarioServices;

    @RequestMapping("/")
    public String login(){
        return "redirect:/login";
    }

    @RequestMapping(value="/report")
    public void getReport(HttpServletResponse response, @RequestParam(value = "id") int id){

        Factura factura = facturaServices.facturaID(id);
        Usuario usuario = new Usuario();
        List<Usuario> usuarios = usuarioServices.allUsuarios();
        for(int i=0;i< usuarios.size();i++){
            if(usuarios.get(i).isInventario()){
                usuario = usuarios.get(i);
                break;
            }
        }

        JRBeanCollectionDataSource collection = new JRBeanCollectionDataSource(factura.getMercancias());

        JasperPrint jasperPrint = getObjectPdf("TestReport.jrxml", new HashMap<String, Object>(), collection);
        System.out.println("usuario a recibir:"+ usuario.getCorreo());

        sendPdfEmail(response, jasperPrint, "Factura-" + id,usuario.getCorreo());

    }

    @RequestMapping(value = "/descargar")
    public String factura(HttpServletResponse response, @RequestParam(value = "id") int id){
        Factura factura = facturaServices.facturaID(id);

        JRBeanCollectionDataSource collection = new JRBeanCollectionDataSource(factura.getMercancias());

        JasperPrint jasperPrint = getObjectPdf("TestReport.jrxml", new HashMap<String, Object>(), collection);

        downloadPdf(response, jasperPrint, "Factura-" + id);

        return "redirect:/indice";
    }

    public JasperPrint getObjectPdf(String path, Map<String, Object> parameters, JRDataSource dataSource) {
        JasperPrint jasperPrint = null;

        InputStream inStream = null;
        try {
            inStream = getClass().getClassLoader().getResourceAsStream(path);
            JasperDesign jasperDesign = JRXmlLoader.load(inStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } catch (JRException jre) {
            System.out.println("Error creando el PDF");
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    System.out.println("Error cerrando stream");
                }
            }
        }
        return jasperPrint;
    }

    public static void downloadPdf(HttpServletResponse response, JasperPrint jasperPrint, String fileName){
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        } catch (JRException e1) {
            e1.printStackTrace();
        }

        byte[] data = out.toByteArray();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
        response.setContentLength(data.length);

        try {
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendPdfEmail(HttpServletResponse response, JasperPrint jasperPrint, String fileName,String correoRecibir){
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        } catch (JRException e1) {
            e1.printStackTrace();
        }

        byte[] data = out.toByteArray();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
        response.setContentLength(data.length);

        try {
            response.getOutputStream().write(data);
            String correo="palomoUnDosTres@gmail.com";
            emailServices.sendMailPdf(correo,correoRecibir,"Prueba","Factura",fileName, data);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
