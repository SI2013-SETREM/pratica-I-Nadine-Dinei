
package model;

/**
 *  Template para os modelos do projeto
 * @author Dinei A. Rockenbach
 */
public abstract class ModelTemplate {
    
    /**
     *  Título da classe, em singular
     */
    public static String sngTitle;
    
    /**
     *  Título da classe, em plural
     */
    public static String prlTitle;
    
    /**
     *  Ícone que deve ir no título da janela
     */
    public static String iconTitle;
    
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
     * {Nome do campo no banco / atributo do model / instância da classe Join}<br>
     * Ex.: { {"Nome", "Sigla"}, {"PaiNome", "PaiAlfa2"} }
     * Ou { {"Nome", "Sigla"}, {new Join(Pais.class, Estado.class), "PaiAlfa2"} }
     */
    public static Object[][] listTableFields = {};
    
    /**
     *  Joins extras que devem ser feitos
     */
    public static util.sql.Join[] extraJoins = {};
    
    /**
     *  Campos que vão no filtro (acima da tabela), no formato:<br>
     * {Label, campo no banco, tamanho[, operador de comparação]}
     */
    public static util.field.FilterField[] listFilterFields = {};
    
    /**
     *  Permite excluir registros
     */
    public static boolean allowDelete = true;
    /**
     *  Permite atualizar registros
     */
    public static boolean allowUpdate = true;
    
    // Implementar se necessário
//    /**
//     *  Campo que deve ser ordenado
//     */
//    public static String orderBy = "";
//    
//    public static String getListSQL() {
//        return "SELECT #FIELDS# FROM #MAINTABLE# #JOINS# WHERE 1=1 #CONDITIONS#";
//    }
    
}
