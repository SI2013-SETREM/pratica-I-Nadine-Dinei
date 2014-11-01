
package util.field;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;

/**
 *  Campo de filtro de data
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class FilterFieldDate extends FilterField {
    
    // @TODO: Criar outra classe para DateTime ou usar novos tipos?
    public static final int TYPE_NORMAL = 1;
    //@TODO
//    public static final int TYPE_MONTH = 2;
//    public static final int TYPE_BETWEEN = 3;
    
    private int type = TYPE_NORMAL;
    private JFormattedTextField jComponent;
    
    public FilterFieldDate() {
        super();
    }
    public FilterFieldDate(String field, String label) {
        super(field, label);
        this.setType(TYPE_NORMAL);
    }
    public FilterFieldDate(String field, String label, int type) {
        this(label, field);
        this.setType(type);
    }
    
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
        int width = 0;
        //Calcula o tamanho necessário para aparecer o formato desejado
        switch (type) {
            case TYPE_NORMAL:
                width = 65;
                break;
//            case TYPE_MONTH:
//                width = 50;
//                break;
        }
        this.setWidth(width);
    }
    
    @Override
    public JFormattedTextField getJComponent() {
        if (this.jComponent == null) {
            try {
                javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("##/##/####");
                this.jComponent = new JFormattedTextField(
                        new javax.swing.text.DefaultFormatterFactory(mask)
                );
            } catch (ParseException ex) {
                Logger.getLogger(FilterFieldDate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.jComponent;
    }

    @Override
    public java.sql.Date getValue() {
        java.sql.Date date = null;
        if (!isEmpty()) {
            String text = this.getJComponent().getText();
            String[] dateParts = text.split("/");
            try {
                date = java.sql.Date.valueOf(dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0]);
            } catch (IllegalArgumentException ex) {
                // Retorna null
            }
        }
        return date;
    }
    
    @Override
    public String getSQLValue() {
        return this.getValue().toString();
    }

    @Override
    public boolean isEmpty() {
        return ("".equals(this.getJComponent().getText()) || "/  /".equals(this.getJComponent().getText().trim()));
    }

}
