
package util.field;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import reflection.ReflectionUtil;
import util.DB;

/**
 *  Campo de Filtro de Combo Box Dinâmico
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class FilterFieldDynamicCombo extends FilterFieldComboBox {
    
    private Class<?> dynamicClass;
    
    /**
     *  Combo Box Dinâmico
     */
    public FilterFieldDynamicCombo() {
        super();
    }
    public FilterFieldDynamicCombo(String field, String label) {
        super(field, label);
    }
    /**
     *  Combo Box Dinâmico
     * @param field Campo da tabela que deve ser filtrado
     * @param label Rótulo do campo
     * @param width Largura do campo
     */
    public FilterFieldDynamicCombo(String field, String label, int width) {
        super(field, label, width);
    }
    /**
     *  Combo Box Dinâmico
     * @param field Campo da tabela que deve ser filtrado
     * @param label Rótulo do campo
     * @param width Largura do campo
     * @param dynamicClass Classe de onde os dados serão buscados
     * @param dscField Nome do campo descritivo
     */
    public FilterFieldDynamicCombo(String field, String label, int width, Class<?> dynamicClass, String dscField) {
        this(field, label, width);
        this.setDynamicClass(dynamicClass);
        this.setOptionsByClass(dynamicClass, dscField);
    }
    /**
     *  Combo Box Dinâmico
     * @param field Campo da tabela que deve ser filtrado
     * @param label Rótulo do campo
     * @param width Largura do campo
     * @param dynamicClass Classe de onde os dados serão buscados
     * @param dscField Nome do campo descritivo
     * @param selectedId ID do item que deve vir selecionado (passar null se não deve selecionar nenhum)
     */
    public FilterFieldDynamicCombo(String field, String label, int width, Class<?> dynamicClass, String dscField, Object selectedId) {
        this(field, label, width);
        this.setDynamicClass(dynamicClass);
        this.setDefaultSelectedId(selectedId);
        this.setOptionsByClass(dynamicClass, dscField);
    }
    /**
     *  Combo Box Dinâmico
     * @param field Campo da tabela que deve ser filtrado
     * @param label Rótulo do campo
     * @param width Largura do campo
     * @param dynamicClass Classe de onde os dados serão buscados
     * @param dscField Nome do campo descritivo
     * @param idField Nome do campo identificador (passar null se deve buscar da classe)
     * @param selectedId ID do item que deve vir selecionado (passar null se não deve selecionar nenhum)
     */
    public FilterFieldDynamicCombo(String field, String label, int width, Class<?> dynamicClass, String dscField, String idField, Object selectedId) {
        this(field, label, width);
        this.setDynamicClass(dynamicClass);
        this.setDefaultSelectedId(selectedId);
        this.setOptionsByClass(dynamicClass, dscField, idField);
    }
    /**
     *  Combo Box Dinâmico
     * @param field Campo da tabela que deve ser filtrado
     * @param label Rótulo do campo
     * @param width Largura do campo
     * @param dynamicClass Classe de onde os dados serão buscados
     * @param dscField Nome do campo descritivo
     * @param idField Nome do campo identificador (passar null se deve buscar da classe)
     * @param selectedId ID do item que deve vir selecionado (passar null se não deve selecionar nenhum)
     * @param emptyItem Rótulo do item vazio no início
     */
    public FilterFieldDynamicCombo(String field, String label, int width, Class<?> dynamicClass, String dscField, String idField, Object selectedId, String emptyItem) {
        this(field, label, width);
        this.setDynamicClass(dynamicClass);
        this.setDefaultSelectedId(selectedId);
        this.setEmptyItem(true);
        this.setEmptyItemText(emptyItem);
        this.setOptionsByClass(dynamicClass, dscField, idField);
    }
    
    public Class<?> getDynamicClass() {
        return dynamicClass;
    }
    public void setDynamicClass(Class<?> dynamicClass) {
        this.dynamicClass = dynamicClass;
    }
    
    /**
     *  Define as opções do combo através de uma classe<br>
     * O campo identificador é o campo chave da tabela da classe.
     * @param dynamicClass Classe de onde os dados serão recuperados
     * @param dscField Nome do campo descritivo
     */
    public void setOptionsByClass(Class<?> dynamicClass, String dscField) {
        this.setOptionsByClass(dynamicClass, dscField, ((String[]) ReflectionUtil.getAttibute(dynamicClass, "idColumn"))[0]);
    }
    
    /**
     *  Define as opções do combo através de uma classe
     * @param dynamicClass Classe de onde os dados serão recuperados
     * @param dscField Nome do campo descritivo
     * @param idField Nome do campo identificador (passar null se deve buscar da classe)
     */
    public void setOptionsByClass(Class<?> dynamicClass, String dscField, String idField) {
        this.setDynamicClass(dynamicClass);
        if (idField == null) {
            this.setOptionsByClass(dynamicClass, dscField);
        } else {
            String mainTable = ReflectionUtil.getDBTableName(dynamicClass);
            String sql = "SELECT " + mainTable + "." + idField + ", " 
                        + mainTable + "." + dscField + " "
                        + "FROM " + mainTable;
            this.setOptionsBySQL(sql, ReflectionUtil.getAttributeType(dynamicClass, idField));
        }
    }
    
    /**
     *  Define as opções do combo através de uma SQL<br>
     * A primeira coluna retornada deve ser o identificador e a segunda deve 
     * ser a descrição do combo.
     * @param sql SQL para fazer a consulta
     * @param idType Tipo do campo identificador (String.class, etc)
     */
    public void setOptionsBySQL(String sql, Class<?> idType) {
        try {
            ComboBoxItem selected = null;
            
            ResultSet rs = DB.executeQuery(sql);
            
            ArrayList<ComboBoxItem> options = new ArrayList<>();
            while (rs.next()) {
                Object id = null;
                if (idType == String.class)
                    id = rs.getString(1);
                else if (idType == Integer.class || idType == int.class)
                    id = rs.getInt(1);
                else if (idType == Double.class)
                    id = rs.getDouble(1);
                else if (idType == Float.class)
                    id = rs.getFloat(1);
                
                String description = rs.getString(2);
                
                ComboBoxItem cboxItem = new ComboBoxItem(id, description);
                if (this.isDefaultSelectedId() && id == this.getDefaultSelectedId()) 
                    selected = cboxItem;
                
                options.add(cboxItem);
            }
            
            super.setOptions((ComboBoxItem[]) options.toArray(new ComboBoxItem[options.size()]), selected);
        } catch (Exception ex) {
            Logger.getLogger(FilterFieldDynamicCombo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
