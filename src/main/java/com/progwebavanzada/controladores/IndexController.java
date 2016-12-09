package com.progwebavanzada.controladores;

import com.progwebavanzada.entidades.Compra;
import com.progwebavanzada.entidades.Factura;
import com.progwebavanzada.entidades.Mercancia;
import com.progwebavanzada.servicios.FacturaServices;
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

    @RequestMapping("/")
    public String login(){
        return "redirect:/login";
    }

    @RequestMapping(value="/report",method = { RequestMethod.GET })
    public void getReport(HttpServletResponse response, @RequestParam(value = "id") int id){
        Factura factura = facturaServices.facturaID(id);
        System.out.println("FACTURA: ");
        System.out.println("ID: "+factura.getId());
        System.out.println("DATE: "+factura.getFecha());
        System.out.println("USUARIO: "+factura.getCliente().getCorreo());
        System.out.println("TOTAL: "+factura.getTotal());
        Compra compra = factura.getMercancias().get(0);
        System.out.println("COMPRA: "+compra.toString());

        JRBeanCollectionDataSource collection = new JRBeanCollectionDataSource(factura.getMercancias());

        JasperPrint jasperPrint = getObjectPdf("TestReport.jrxml", new HashMap<String, Object>(), collection);

        sendPdfResponse(response, jasperPrint, "Factura-" + id);
    }

    public JasperPrint getObjectPdf(String path, Map<String, Object> parameters, JRDataSource dataSource) {
        JasperPrint jasperPrint = null;

        InputStream inStream = null;
        try {
            System.out.println("PATH: "+getClass().getClassLoader());
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

    public static void sendPdfResponse(HttpServletResponse response, JasperPrint jasperPrint, String fileName){
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        } catch (JRException e1) {
            e1.printStackTrace();
        }

        byte[] data = out.toByteArray();

        response.setContentType("application/pdf");
        //To make it a download change "inline" to "attachment"
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

}
