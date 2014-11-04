
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;

/**
 *  Modelo de Níveis de Acesso
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class NivelAcesso extends ModelTemplate {
    private int NivAceCodigo;
    private String NivAceNome;
    
    private String flag = DB.FLAG_INSERT;
    
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
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
