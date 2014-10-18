
package reflection;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class ComboBoxItem {
    private Object[] multipleId;
    private Object singleId;
    private String description;

    public ComboBoxItem() {
    }
    public ComboBoxItem(Object id, String description) {
        this.setSingleId(id);
        this.setDescription(description);
    }
    public ComboBoxItem(Object[] ids, String description) {
        this.setMultipleId(ids);
        this.setDescription(description);
    }
    
    public Object getId() {
        if (this.singleId == null) {
            return this.multipleId;
        } else {
            return this.singleId;
        }
    }
    
    public boolean isSingleId() {
        return (this.singleId != null);
    }
    
    public boolean isMultipleId() {
        return (this.multipleId != null);
    }
    
    public Object[] getMultipleId() {
        return multipleId;
    }
    public void setMultipleId(Object[] multipleId) {
        this.singleId = null;
        this.multipleId = multipleId;
    }
    
    public Object getSingleId() {
        return singleId;
    }
    public void setSingleId(Object singleId) {
        this.singleId = singleId;
        this.multipleId = null;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return this.getDescription();
    }
    
}
