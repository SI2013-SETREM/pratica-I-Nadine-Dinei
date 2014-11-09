
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldText;

/**
 *  Modelo de Níveis de Acesso
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class NivelAcesso extends ModelTemplate {
    private int NivAceCodigo;
    private String NivAceNome;
    
    private String flag = DB.FLAG_INSERT;
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Perfil de Usuário";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Perfis de Usuário";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "PERFIS DE USUARIO";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "cartaoVisitas.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"NivAceCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Nome", "NivAceNome"},
    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("NivAceNome", "Nome", 200, 50),
    };
    
    public NivelAcesso() {
    }
    public int getNivAceCodigo() {
        return NivAceCodigo;
    }
    public void setNivAceCodigo(int NivAceCodigo) {
        this.NivAceCodigo = NivAceCodigo;
    }
    public String getNivAceNome() {
        return NivAceNome;
    }
    public void setNivAceNome(String NivAceNome) {
        this.NivAceNome = NivAceNome;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public boolean load(int NivAceCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(NivelAcesso.class)
                    + " WHERE NivAceCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Integer[]{NivAceCodigo});
            if (rs.next()) {
                this.setNivAceCodigo(rs.getInt("NivAceCodigo"));
                this.setNivAceNome(rs.getString("NivAceNome"));
                this.setFlag(DB.FLAG_UPDATE);
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar o perfil de usuário [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
}
