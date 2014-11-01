
package util;

/**
 *  Configurações gerais do projeto
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public abstract class Config {
    
    public static String imageFolder = "assets";
    
    /**
     * Locais onde são verificados os tipos de variáveis:
     * @see util.DB#setParmsByType(java.sql.PreparedStatement, java.lang.Object[]) 
     * @see util.DB#getColumnByType(java.sql.ResultSet, java.lang.String, java.lang.Class) 
     * @see reflection.ListJFrame#listData()
     * @see util.field.FilterFieldComboBox#isEmpty() 
     * 
     * Documentação:
     * - Como determinar o tipo da variável [http://stackoverflow.com/a/4964359/3136474]
     * 
     */
    private void checkType() {}
    
}
