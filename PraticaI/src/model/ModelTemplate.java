
package model;

/**
 *  Template para os modelos do projeto
 * @author Dinei A. Rockenbach
 */
abstract class ModelTemplate {
    
    /**
     *  Nome da tabela, caso seja diferente do nome da classe<br>
     * <b>Necessário apenas quando for diferente do nome da classe</b>
     * Ex.: "pais"
     */
    public static String dbTable;
    
    /**
     * Nome dos campos-chave da tabela<br>
     * Ex.: {"PaiCodigo","EstSigla"}
     */
    public static String[] idColumn = {};
    
    /**
     *  Campos que vão na tabela de listagem, no formato:<br>
     * {Nome da coluna na tabela de listagem},<br>
     * {Nome do campo no banco / atributo do model}<br>
     * Ex.: { {"Nome", "Sigla"}, {"PaiNome", "PaiAlfa2"} }
     */
    public static String[][] listTableFields = {};
    
    /**
     *  Campos que vão no filtro (acima da tabela), no formato:<br>
     * {Label, campo no banco, tamanho[, operador de comparação]}
     */
    public static Object[][] listFilterFields = {};
    
}
