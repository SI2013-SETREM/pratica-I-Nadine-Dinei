package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldText;

/**
 *
 * @author Nadine
 */
public class PessoaEmail extends ModelTemplate {

    private Pessoa PesCodigo;
    private int PesEmlCodigo;
    private String PesEmlEmail;

    private String flag = DB.FLAG_INSERT;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "E-mail";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "E-mails";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "email.png";
    /**
     * @see model.ModelTemplate#softDelete
     */
    public static String softDelete = "";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"PesCodigo", "PesEmlCodigo"};

    /**
     * @see model.ModelTemplate#listTableFields
     */
//    public static Object[][] listTableFields = {
//        {"Nome", "PaiNome"},
//        {"Sigla", "PaiAlfa2"},
//    };
//    public static FilterField[] listFilterFields = {
//        {"Nome", "PaiNome", 200},
//        {"Sigla", "PaiAlfa2", 60}
//    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
//    public static FilterField[] listFilterFields = {
//        new FilterFieldText("PaiNome", "Nome", 200),
//        new FilterFieldText("PaiAlfa2", "Sigla", 60),
//    };
    public PessoaEmail() {
    }

    public Pessoa getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(Pessoa PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public int getPesEmlCodigo() {
        return PesEmlCodigo;
    }

    public void setPesEmlCodigo(int PesEmlCodigo) {
        this.PesEmlCodigo = PesEmlCodigo;
    }

    public String getPesEmlEmail() {
        return PesEmlEmail;
    }

    public void setPesEmlEmail(String PesEmlEmail) {
        this.PesEmlEmail = PesEmlEmail;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean load(int PesCod, int PesEmlCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(PessoaEmail.class);
            sql += " WHERE PesCodigo = ? AND PesEmlCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{PesCod, PesEmlCodigo});
            if (rs.next()) {
                this.setPesCodigo((Pessoa) rs.getObject("PesCodigo"));
                this.setPesEmlCodigo(rs.getInt("PesEmlCodigo"));
                this.setPesEmlEmail(rs.getString("PesEmlEmail"));
                
                this.setFlag(DB.FLAG_UPDATE);
                return true;
            }
        } catch (SQLException ex) {
            Log.log(Pessoa.fncNome, Log.INT_OUTRA, "Falha ao buscar o " + sngTitle + " da " + Pessoa.sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public boolean insert() {
        try {
            setPesEmlCodigo(Sequencial.getNextSequencial(PessoaEmail.class.getName() + "_" + getPesCodigo().getPesCodigo()));
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " (PesCodigo, PesEmlCodigo, PesEmlEmail)";
            sql += " VALUES (?, ?, ?)";
            
            DB.executeUpdate(sql, new Object[] {
                getPesCodigo().getPesCodigo(),
                getPesEmlCodigo(),
                getPesEmlEmail(),
            });
            
            setFlag(DB.FLAG_UPDATE);
            return true;
        } catch (SQLException ex) {
            Log.log(Pessoa.fncNome, Log.INT_INSERCAO, "Falha ao inserir o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public static ArrayList<PessoaEmail> getAll(Pessoa PesCodigo) {
        ArrayList<PessoaEmail> list = new ArrayList<>();
        String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(PessoaEmail.class);
        sql += " WHERE PesCodigo = ?";
        try {
            ResultSet rs = DB.executeQuery(sql, new Object[]{PesCodigo.getPesCodigo()});
            while (rs.next()) {
                PessoaEmail pe = new PessoaEmail();
                pe.setPesCodigo(PesCodigo);
                pe.setPesEmlCodigo(rs.getInt("PesEmlCodigo"));
                pe.setPesEmlEmail(rs.getString("PesEmlEmail"));
                pe.setFlag(DB.FLAG_UPDATE);
                list.add(pe);
            }
        } catch (SQLException ex) {
            Log.log(Pessoa.fncNome, Log.INT_OUTRA, "Falha ao buscar os " + prlTitle + " da " + Pessoa.sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return list;
//        return (PessoaEmail[]) list.toArray(new PessoaEmail[list.size()]);
    }
}
