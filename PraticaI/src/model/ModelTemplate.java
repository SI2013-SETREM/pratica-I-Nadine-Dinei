
package model;

import java.sql.ResultSetMetaData;
import java.util.logging.Level;
import java.util.logging.Logger;
import reflection.ReflectionUtil;
import util.DB;

/**
 *  Template para os modelos do projeto
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
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
     *  Nome da funcionalidade que faz a validação de acesso da entidade
     */
    public static String fncNome;
    
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
     *  Nome do campo de soft delete, caso houver<br>
     * Ex.: "UsrDtaDelecao"
     */
    public static String softDelete;
    
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
     *  Ordenação da listagem
     */
    public static String orderBy;
    
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
     *  Permite inserir registros
     */
    public static boolean allowInsert = true;
    /**
     *  Permite atualizar registros
     */
    public static boolean allowUpdate = true;
    /**
     *  Permite excluir registros
     */
    public static boolean allowDelete = true;
    
    // Implementar se necessário
//    public static String getListSQL() {
//        return "SELECT #FIELDS# FROM #MAINTABLE# #JOINS# WHERE 1=1 #CONDITIONS#";
//    }
    
    protected static java.util.ArrayList<Object> getAll(Class<? extends ModelTemplate> cls) {
        java.util.ArrayList<Object> list = new java.util.ArrayList<>();
        try {
            String sql = "SELECT * FROM " + ReflectionUtil.getDBTableName(cls);
            String softDel = null;
            if (ReflectionUtil.isAttributeExists(cls, "softDelete")) 
                softDel = (String) ReflectionUtil.getAttibute(cls, "softDelete");
            if (softDel != null)
                sql += " WHERE " + softDel + " IS NULL";
            java.sql.ResultSet rs = DB.executeQuery(sql);
            while (rs.next()) {
                Object obj = cls.newInstance();
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String attr = rsmd.getColumnName(i);
                    Class<?> clsType = ReflectionUtil.getAttributeType(cls, attr);
//                    System.out.println(cls + " . " + attr + " - " + clsType);
                    Object val = DB.getColumnByType(rs, attr, clsType);
                    ReflectionUtil.getMethod(obj, "set" + attr, new Object[]{val});
                }
                list.add(obj);
            }
        } catch (Exception ex) {
            Logger.getLogger(ModelTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}
