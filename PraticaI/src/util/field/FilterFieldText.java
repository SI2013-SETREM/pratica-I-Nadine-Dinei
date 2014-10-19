
package util.field;

import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class FilterFieldText extends FilterField {
    private String operator;
    private JTextField jComponent = new JTextField();
    
    public FilterFieldText() {
        super();
    }
    public FilterFieldText(String field, String label) {
        super(field, label);
    }
    public FilterFieldText(String field, String label, int width) {
        super(field, label, width);
    }
    public FilterFieldText(String field, String label, int width, String operator) {
        super(label, field, width);
        this.setOperator(operator);
    }
    
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    @Override
    public JComponent getJComponent() {
        return this.jComponent;
    }
    
    @Override
    public String getValue() {
        return this.jComponent.getText();
    }

    @Override
    public String getSQLValue() {
        return this.getValue();
    }
    
    @Override
    public boolean isEmpty() {
        return "".equals(this.jComponent.getText());
    }
    
}
