
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
    public FilterFieldText(String field, String label, int width, int maxChars) {
        this(field, label, width);
        util.Util.setLimitChars(this.getJComponent(), maxChars);
    }
    public FilterFieldText(String field, String label, int width, String operator) {
        this(label, field, width);
        this.setOperator(operator);
    }
    public FilterFieldText(String field, String label, int width, int maxChars, String operator) {
        this(field, label, width, maxChars);
        this.setOperator(operator);
    }
    
    public String getOperator() {
        return operator;
    }
    public FilterFieldText setOperator(String operator) {
        this.operator = operator;
        return this;
    }
    
    @Override
    public JTextField getJComponent() {
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
