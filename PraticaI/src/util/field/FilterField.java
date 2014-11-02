
package util.field;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public abstract class FilterField {
    
    protected String field;
    protected String label;
    private int width;
    protected JLabel jLabel = new JLabel();
    
    public FilterField() {
    }
    public FilterField(String field, String label) {
        this();
        this.setField(field);
        this.setLabel(label);
    }
    public FilterField(String field, String label, int width) {
        this();
        this.setField(field);
        this.setLabel(label);
        this.setWidth(width);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        this.jLabel.setText(label);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    public JLabel getJLabel() {
        return this.jLabel;
    }
    
    //@TODO
//    public int getTotalWidth() {
//        return getWidth() + (this.getLabel().length()*5);
//    }
    
    public abstract JComponent getJComponent();
    public abstract Object getValue();
    public abstract String getSQLValue();
    public abstract boolean isEmpty();
    
}
