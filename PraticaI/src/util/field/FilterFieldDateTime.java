
package util.field;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import util.Config;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class FilterFieldDateTime extends FilterField {

    public static final int TYPE_NORMAL = 1;
    //@TODO
//    public static final int TYPE_MONTH = 2;
//    public static final int TYPE_BETWEEN = 3;
    
    private int type = TYPE_NORMAL;
    private JFormattedTextField jComponent;
    
    public FilterFieldDateTime() {
        super();
    }
    public FilterFieldDateTime(String field, String label) {
        super(field, label);
        this.setType(TYPE_NORMAL);
    }
    public FilterFieldDateTime(String field, String label, int type) {
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
                width = 115;
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
                javax.swing.text.MaskFormatter mf = new javax.swing.text.MaskFormatter(Config.MASK_DATETIME);
                this.jComponent = new JFormattedTextField(
                        new javax.swing.text.DefaultFormatterFactory(mf)
                );
            } catch (ParseException ex) {
                Logger.getLogger(FilterFieldDate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.jComponent;
    }

    @Override
    public java.sql.Timestamp getValue() {
        java.sql.Timestamp date = null;
        if (!isEmpty()) {
            String text = this.getJComponent().getText();
            String[] dateTime = text.split("\\s");
            String[] dateParts = dateTime[0].split("/");
            System.out.println(text + " - " + dateTime.length + " - " + dateParts.length);
            try {
                date = java.sql.Timestamp.valueOf(dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0] + " " + dateTime[1]);
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
        return ("".equals(this.getJComponent().getText()) 
                //|| Config.MASK_DATETIME.replaceAll("#", " ").trim().equals(this.getJComponent().getText().trim())
                || Config.MASK_DATETIME.replaceAll("[^/:\\s]", " ").trim().equals(this.getJComponent().getText().trim()));
    }

}
