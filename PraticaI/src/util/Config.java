
package util;

/**
 *  Configurações gerais do projeto
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public abstract class Config {
    
    public static String imageFolder = "assets";
    
    public static final String DB_DBMS  = "mysql";
    public static final String DB_HOST  = "localhost";
    public static final int    DB_PORT  = 3306;
    public static final String DB_NAME  = "pratica_i";
    public static final String DB_USER  = "root";
    public static final String DB_PASS  = "root";
    
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
