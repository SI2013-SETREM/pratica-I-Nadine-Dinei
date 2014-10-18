
package util;

/**
 *  Configurações gerais do projeto
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public abstract class Config {
    
    /**
     * Locais onde são verificados os tipos de variáveis:
     * @see util.DB#executeQuery(java.lang.String) 
     * @see reflection.ListJFrame#listData() [2 locais]
     * @see reflection.FilterFieldComboBox#isEmpty() 
     * 
     * Documentação:
     * - Como determinar o tipo da variável [http://stackoverflow.com/a/4964359/3136474]
     * 
     */
    private void checkType() {}
    
}
