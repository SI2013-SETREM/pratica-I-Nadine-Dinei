
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;

/**
 *  Classe que gerencia os sequenciais
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Sequencial {
    
    private String SeqIdentificador;
    private int SeqSequencial;
    
    private String flag = DB.FLAG_INSERT;
    
    public Sequencial() {
    }
    public Sequencial(String SeqIdentificador, int SeqSequencial) {
        this.setSeqIdentificador(SeqIdentificador);
        this.setSeqSequencial(SeqSequencial);
    }

    public String getSeqIdentificador() {
        return SeqIdentificador;
    }
    public void setSeqIdentificador(String SeqIdentificador) {
        this.SeqIdentificador = SeqIdentificador;
    }
    
    public int getSeqSequencial() {
        return SeqSequencial;
    }
    public void setSeqSequencial(int SeqSequencial) {
        this.SeqSequencial = SeqSequencial;
    }

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public int getNextSequencial() {
        this.setSeqSequencial(SeqSequencial+1);
        return this.getSeqSequencial();
    }
    
    public boolean load(String SeqIdentificador) {
        this.setSeqIdentificador(SeqIdentificador);
        return load();
    }
    public boolean load() {
        try {
            String sql = "SELECT SeqSequencial FROM `sequencial` WHERE SeqIdentificador = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[] {SeqIdentificador});
            if (rs.next()) {
                SeqSequencial = rs.getInt("SeqSequencial");
                flag = DB.FLAG_UPDATE;
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sequencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean save() {
        String sql;
        switch(flag) {
            case DB.FLAG_INSERT:
                sql = "INSERT INTO `sequencial`(SeqIdentificador, SeqSequencial)";
                sql += " VALUES (?, ?)";
                try {
                    DB.executeUpdate(sql, new Object[]{getSeqIdentificador(), getSeqSequencial()});
                    flag = DB.FLAG_UPDATE;
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(Sequencial.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case DB.FLAG_UPDATE:
                sql = "UPDATE `sequencial` SET SeqSequencial = ?";
                sql += " WHERE SeqIdentificador = ?";
                try {
                    DB.executeUpdate(sql, new Object[]{getSeqSequencial(), getSeqIdentificador()});
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(Sequencial.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        return false;
    }
    
    
    public static int getNextSequencial(Class<? extends ModelTemplate> cls) {
        return getNextSequencial(cls, true);
    }
    public static int getNextSequencial(Class<? extends ModelTemplate> cls, boolean update) {
        return getNextSequencial("model" + cls.getSimpleName(), update);
    }
    public static int getNextSequencial(String SeqIdentificador) {
        return getNextSequencial(SeqIdentificador, true);
    }
    public static int getNextSequencial(String SeqIdentificador, boolean update) {
        int sequencial = 1;
        Sequencial seq = new Sequencial();
        if (seq.load(SeqIdentificador)) {
            sequencial = seq.getNextSequencial();
            if (update)
                seq.save();
        } else {
            seq.SeqIdentificador = SeqIdentificador;
            seq.SeqSequencial = 1;
            sequencial = seq.getSeqSequencial();
            if (update)
                seq.save();
        }
        return sequencial;
    }
    
}
