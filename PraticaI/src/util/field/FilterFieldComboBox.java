
package util.field;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *  Campo de Filtro de Combo Box
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class FilterFieldComboBox extends FilterField {
    
    private String operator = "=";
    private JComboBox jComponent;
    private ComboBoxItem[] options;
    private boolean emptyItem = false;
    private String emptyItemText = "";
    private Object defaultSelectedId;
    
    public FilterFieldComboBox() {
        super();
    }
    public FilterFieldComboBox(String field, String label) {
        super(field, label);
    }
    public FilterFieldComboBox(String field, String label, int width) {
        super(field, label, width);
    }
    public FilterFieldComboBox(String field, String label, int width, ComboBoxItem[] options) {
        super(field, label, width);
        this.setOptions(options);
    }
    public FilterFieldComboBox(String field, String label, int width, ComboBoxItem[] options, Object selectedId) {
        super(field, label, width);
        this.setDefaultSelectedId(selectedId);
        this.setOptions(options);
    }
    public FilterFieldComboBox(String field, String label, int width, ComboBoxItem[] options, String emptyItem) {
        super(field, label, width);
        this.setEmptyItem(true);
        this.setEmptyItemText(emptyItem);
        this.setOptions(options);
    }
    public FilterFieldComboBox(String field, String label, int width, ComboBoxItem[] options, String emptyItem, Object selectedId) {
        super(field, label, width);
        this.setDefaultSelectedId(selectedId);
        this.setEmptyItem(true);
        this.setEmptyItemText(emptyItem);
        this.setOptions(options);
    }

    public String getOperator() {
        return operator;
    }
    public boolean isEmptyItem() {
        return emptyItem;
    }
    public void setEmptyItem(boolean emptyItem) {
        this.emptyItem = emptyItem;
    }
    public String getEmptyItemText() {
        return emptyItemText;
    }
    public void setEmptyItemText(String emptyItemText) {
        this.emptyItemText = emptyItemText;
    }
    public boolean isDefaultSelectedId() {
        return (defaultSelectedId != null);
    }
    public Object getDefaultSelectedId() {
        return defaultSelectedId;
    }
    public void setDefaultSelectedId(Object defaultSelectedId) {
        this.defaultSelectedId = defaultSelectedId;
    }
    
    public ComboBoxItem[] getOptions() {
        return options;
    }
    public void setOptions(ComboBoxItem[] options) {
        if (this.isDefaultSelectedId()) {
            for (ComboBoxItem selected : options) {
                if (selected.getId() == this.getDefaultSelectedId()) {
                    this.setOptions(options, selected);
                    return;
                }
            }
        }
        this.setOptions(options, null);
    }
    public void setOptions(ComboBoxItem[] options, ComboBoxItem selected) {
        if (this.isEmptyItem()) {
            this.options = new ComboBoxItem[options.length+1];
            this.options[0] = new ComboBoxItem(null, this.getEmptyItemText());
            System.arraycopy(options, 0, this.options, 1, options.length);
        } else {
            this.options = options;
        }
        this.getJComponent().setModel(new DefaultComboBoxModel<>(this.options));
        if (selected != null)
            this.getJComponent().setSelectedItem(selected);
    }
    public void addOption(ComboBoxItem option) {
        ComboBoxItem[] optaux = new ComboBoxItem[this.options.length+1];
        System.arraycopy(options, 0, optaux, 0, options.length);
        optaux[optaux.length-1] = option;
        this.options = optaux;
        DefaultComboBoxModel dcbm = (DefaultComboBoxModel) this.getJComponent().getModel();
        dcbm.addElement(option);
    }
    public void removeOption(ComboBoxItem option) {
        
    }
    public void removeOption(int index) {
        ComboBoxItem[] auxOptions = new ComboBoxItem[this.options.length];
        if (index > 0) { //Copia até o item anterior ao que se quer remover
            System.arraycopy(options, 0, auxOptions, 0, index);
        }
        if (index < (this.options.length-1)) { //Copia o restante dos itens depois do que se quer remover
            System.arraycopy(options, index+1, auxOptions, index, options.length-index-1);
        }
        this.options = auxOptions;
        this.getJComponent().setModel(new DefaultComboBoxModel<>(options));
    }
    
    @Override
    public JComboBox getJComponent() {
        if (this.jComponent == null) {
            this.jComponent = new JComboBox();
        }
        return this.jComponent;
    }

    @Override
    public ComboBoxItem getValue() {
        return (ComboBoxItem) this.getJComponent().getSelectedItem();
    }

    @Override
    public String getSQLValue() {
        return String.valueOf(this.getValue().getId());
    }

    @Override
    public boolean isEmpty() {
        if (this.getValue() != null) {
            ComboBoxItem cboxItem = (ComboBoxItem) this.getValue();
            Object id = cboxItem.getId();
//            System.out.println("Campo " + this.getField() + " item " + cboxItem + " Id " + (String) id + " empty: " + ("".equals((String) id)));
            if (id == null)
                return true;
            if (id instanceof String)
                return "".equals((String) id);
            else if (id instanceof Integer)
                return ((int) id == 0);
            else if (id instanceof Double)
                return ((int) id == 0);
            else if (id instanceof Float)
                return ((int) id == 0);
            // Existe combo box de data? Acho que não.
//            else if (id instanceof java.sql.Date)
//                return true;
//            else if (id instanceof java.sql.Time)
//                return true;
//            else if (id instanceof java.sql.Timestamp)
//                return true;
        }
        return true;
    }
    
    
    
}
