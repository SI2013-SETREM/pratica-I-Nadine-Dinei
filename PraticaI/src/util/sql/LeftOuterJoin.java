
package util.sql;

import model.ModelTemplate;

/**
 *  Classe que identifica um LEFT OUTER JOIN no SQL
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class LeftOuterJoin extends Join {
    
    public LeftOuterJoin(Class<? extends ModelTemplate> leftClass, Class<? extends ModelTemplate> rightClass) {
        super(leftClass, rightClass);
        this.joinType = "LEFT OUTER JOIN";
    }

    public LeftOuterJoin(Class<? extends ModelTemplate> leftClass, Class<? extends ModelTemplate> rightClass, String rightField) {
        super(leftClass, rightClass, rightField);
        this.joinType = "LEFT OUTER JOIN";
    }

    public LeftOuterJoin(Class<? extends ModelTemplate> leftClass, String[] leftJoinFields, Class<? extends ModelTemplate> rightClass, String[] rightJoinFields) {
        super(leftClass, leftJoinFields, rightClass, rightJoinFields);
        this.joinType = "LEFT OUTER JOIN";
    }

    public LeftOuterJoin(Class<? extends ModelTemplate> leftClass, String[] leftJoinFields, Class<? extends ModelTemplate> rightClass, String[] rightJoinFields, String rightField) {
        super(leftClass, leftJoinFields, rightClass, rightJoinFields, rightField);
        this.joinType = "LEFT OUTER JOIN";
    }
    
}
