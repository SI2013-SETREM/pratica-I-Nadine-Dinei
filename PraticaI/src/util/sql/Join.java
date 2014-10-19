
package util.sql;

import model.ModelTemplate;
import reflection.ReflectionUtil;

/**
 *  Classe que identifica um JOIN no SQL
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Join {
    
    /**
     *  O tipo de Join (LEFT, RIGHT, LEFT OUTER, INNER, etc)<br>
     */
    protected String joinType = "JOIN";
    /**
     *  Classe principal
     */
    private Class<? extends model.ModelTemplate> leftClass;
    /**
     *  Classe para onde será feito o Join
     */
    private Class<? extends model.ModelTemplate> rightClass;
    /**
     *  Tabela principal
     */
    private String leftTable;
    /**
     *  Tabela para onde será feito o Join
     */
    private String rightTable;
    /**
     *  Alias da tabela principal
     */
    private String leftAlias;
    /**
     *  Alias da tabela para onde será feito o Join
     */
    private String rightAlias;
    /**
     *  Campos da tabela principal
     */
    private String[] leftJoinFields;
    /**
     *  Campos da tabela para onde será feito o Join
     */
    private String[] rightJoinFields;
    /**
     *  Campo da tabela do join que deve ser retornado
     */
    private String rightField;

    public Join(Class<? extends ModelTemplate> leftClass, Class<? extends ModelTemplate> rightClass) {
        this.setLeftClass(leftClass);
        this.setRightClass(rightClass);
        
        String[] joinFields = (String[]) ReflectionUtil.getAttibute(rightClass, "idColumn");
        this.setLeftJoinFields(joinFields);
        this.setRightJoinFields(joinFields);
    }
    public Join(Class<? extends ModelTemplate> leftClass, Class<? extends ModelTemplate> rightClass, String rightField) {
        this(leftClass, rightClass);
        
        this.setRightField(rightField);
    }
    public Join(Class<? extends ModelTemplate> leftClass, String[] leftJoinFields, Class<? extends ModelTemplate> rightClass, String[] rightJoinFields) {
        this.setLeftClass(leftClass);
        this.setRightClass(rightClass);
        
        this.setLeftJoinFields(leftJoinFields);
        this.setRightJoinFields(rightJoinFields);
    }
    public Join(Class<? extends ModelTemplate> leftClass, String[] leftJoinFields, Class<? extends ModelTemplate> rightClass, String[] rightJoinFields, String rightField) {
        this(leftClass, leftJoinFields, rightClass, rightJoinFields);
        
        this.setRightField(rightField);
    }
    
    public Class<? extends ModelTemplate> getLeftClass() {
        return leftClass;
    }
    public void setLeftClass(Class<? extends ModelTemplate> leftClass) {
        this.leftClass = leftClass;
        this.setLeftTable(ReflectionUtil.getDBTableName(leftClass));
        if (this.getLeftJoinFields() == null && this.getRightJoinFields() == null && this.getRightClass() != null) {
            this.setLeftJoinFields((String[]) ReflectionUtil.getAttibute(this.getRightClass(), "idColumn"));
            this.setRightJoinFields(this.getLeftJoinFields());
        }
    }
    
    public Class<? extends ModelTemplate> getRightClass() {
        return rightClass;
    }
    public void setRightClass(Class<? extends ModelTemplate> rightClass) {
        this.rightClass = rightClass;
        this.setRightTable(ReflectionUtil.getDBTableName(rightClass));
        if (this.getLeftJoinFields() == null && this.getRightJoinFields() == null) {
            this.setRightJoinFields((String[]) ReflectionUtil.getAttibute(rightClass, "idColumn"));
            this.setLeftJoinFields(this.getRightJoinFields());
        }
    }

    public String getLeftTable() {
        return leftTable;
    }
    public void setLeftTable(String leftTable) {
        this.leftTable = leftTable;
        this.leftAlias = leftTable + java.util.UUID.randomUUID().toString().split("-")[0];
    }
    
    public String getRightTable() {
        return rightTable;
    }
    public void setRightTable(String rightTable) {
        this.rightTable = rightTable;
        this.rightAlias = rightTable + java.util.UUID.randomUUID().toString().split("-")[0];
    }

    public String getLeftAlias() {
        return leftAlias;
    }
    public void setLeftAlias(String leftAlias) {
        this.leftAlias = leftAlias;
    }

    public String getRightAlias() {
        return rightAlias;
    }
    public void setRightAlias(String rightAlias) {
        this.rightAlias = rightAlias;
    }

    public String[] getLeftJoinFields() {
        return leftJoinFields;
    }
    public void setLeftJoinFields(String[] leftJoinFields) {
        this.leftJoinFields = leftJoinFields;
    }

    public String[] getRightJoinFields() {
        return rightJoinFields;
    }
    public void setRightJoinFields(String[] rightJoinFields) {
        this.rightJoinFields = rightJoinFields;
    }

    public String getRightField() {
        return rightField;
    }
    public void setRightField(String rightField) {
        this.rightField = rightField;
    }
    
    /**
     *  Retorna a SQL de JOIN definida pelo objeto
     * @return SQL de JOIN (Ex.: "JOIN right rgt ON (lft.LftField=rgt.RgtField)")
     */
    public String getJoinSQL() {
        String sql = "";
        sql += this.joinType + " " + this.getRightTable() + " " + this.getRightAlias();
        String[] leftJoinFields = this.getLeftJoinFields();
        String[] rightJoinFields = this.getRightJoinFields();
        if (leftJoinFields != null && leftJoinFields.length > 0
        && rightJoinFields != null && rightJoinFields.length > 0) {
            sql += " ON (";
            for (int i = 0; i < leftJoinFields.length; i++) {
                sql += this.getLeftAlias() + "." + leftJoinFields[i] + "=" + this.getRightAlias() + "." + rightJoinFields[i] + " AND ";
            }
            sql = sql.substring(0, (sql.length()-5)); //Tira o último AND
            sql += ")";
        }
        return sql;
    }

    @Override
    public String toString() {
        return getJoinSQL();
    }
    
}
