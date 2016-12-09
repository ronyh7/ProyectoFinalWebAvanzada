package com.progwebavanzada.utilidad;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRField;

/**
 * Created by rony- on 12/8/2016.
 */
public class FacturaReport implements JRDataSource {
    @Override
    public Object getFieldValue(JRField jrDataSource){
        return jrDataSource;
    }

    @Override
    public boolean next(){
        return true;
    }
}
