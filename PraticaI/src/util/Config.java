
package util;

/**
 *  Configurações gerais do projeto
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public abstract class Config {
    
    /**
     *  Pasta para buscar as imagens
     */
    public static String imageFolder = "assets";
    
    /**
     *  Formato de exibição de datas
     * @see util.field.FilterFieldDate
     */
    public static final int FORMAT_DATE = java.text.DateFormat.MEDIUM;
    public static final String MASK_DATE = "##/##/####";
    
    /**
     *  Formato de exibição de horas
     */
    public static final int FORMAT_TIME = java.text.DateFormat.MEDIUM;
    public static final String MASK_TIME = "##:##:##";
    
    /**
     *  Formato de exibição de data/hora
     * @see util.field.FilterFieldDateTime
     */
    public static final String MASK_DATETIME = MASK_DATE + " " + MASK_TIME;
    
    /**
     *  Configurações para acesso ao banco de dados
     */
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
