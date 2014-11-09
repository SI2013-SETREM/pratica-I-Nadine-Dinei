package model;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldDynamicCombo;
import util.field.FilterFieldText;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Cidade extends ModelTemplate {

    private Pais PaiCodigo;
    private Estado EstSigla;
    private int CidCodigo;
    private String CidNome;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Cidade";
    /** 
    * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Cidades";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "CIDADES";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "flag.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"Pais.PaiCodigo", "Estado.EstSigla", "CidCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */

    public static String[][] listTableFields = {
        {"País", "Pais.PaiNome"},
        {"Estado", "Estado.EstNome"},
        {"Nome", "CidNome"},
    };
    
    public static FilterField[] listFilterFields = {
        new FilterFieldDynamicCombo("Estado.EstSigla", "Estado", 200, Estado.class, "EstNome", null, null, ""),
        new FilterFieldDynamicCombo("Pais.PaiCodigo", "País", 200, Pais.class, "PaiNome", null, null, ""),
        new FilterFieldText("CidNome", "Cidade", 200)
    };

    public Cidade() {
    }

    public Pais getPaiCodigo() {
        return PaiCodigo;
    }

    public void setPaiCodigo(Pais PaiCodigo) {
        this.PaiCodigo = PaiCodigo;
    }

    public Estado getEstSigla() {
        return EstSigla;
    }

    public void setEstSigla(Estado EstSigla) {
        this.EstSigla = EstSigla;
    }

    public int getCidCodigo() {
        return CidCodigo;
    }

    public void setCidCodigo(int CidCodigo) {
        this.CidCodigo = CidCodigo;
    }

    public String getCidNome() {
        return CidNome;
    }

    public void setCidNome(String CidNome) {
        this.CidNome = CidNome;
    }

    public static String[] getIdColumn() {
        return idColumn;
    }

    public static void setIdColumn(String[] idColumn) {
        Cidade.idColumn = idColumn;
    }

    public static String[][] getListTableFields() {
        return listTableFields;
    }

    public static void setListTableFields(String[][] listTableFields) {
        Cidade.listTableFields = listTableFields;
    }

    public boolean load(int PaiCodigo, String EstSigla, int CidCodigo) {
        String sql = "Select * from " + reflection.ReflectionUtil.getDBTableName(this)
                + " where PaiCodigo=? and EstSigla =? and CidCodigo=?";
        try {
            ResultSet rs = DB.executeQuery(sql, new Object[]{PaiCodigo, EstSigla, CidCodigo});
            if (rs.next()) {
                this.setCidCodigo(rs.getInt("CidCodigo"));
                this.setCidNome(rs.getString("CidNome"));
                this.setEstSigla(new Estado(rs.getInt("PaiCodigo"), rs.getString("EstSigla")));
                if (this.getEstSigla() != null) {
                    this.setPaiCodigo(this.getEstSigla().getPaiCodigo());

                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Cidade.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

}
