package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import reflection.ReflectionUtil;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldDynamicCombo;
import util.field.FilterFieldText;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Estado extends ModelTemplate {

    private Pais PaiCodigo;
    private String EstSigla;
    private String EstNome;
    private String flag = DB.FLAG_INSERT;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Estado";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Estados";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "flagorange.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"PaiCodigo", "EstSigla"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"País", "Pais.PaiNome"},
        {"Sigla", "EstSigla"},
        {"Nome", "EstNome"}
    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("EstNome", "Nome", 200),
        new FilterFieldDynamicCombo("Pais.PaiCodigo", "País", 200, Pais.class, "PaiNome", null, null, "")
    };

    public Estado() {
    }

    public Pais getPaiCodigo() {
        return PaiCodigo;
    }

    public void setPaiCodigo(Pais PaiCodigo) {
        this.PaiCodigo = PaiCodigo;
        System.out.println(PaiCodigo);
    }

    public String getEstSigla() {
        return EstSigla;
    }

    public void setEstSigla(String EstSigla) {
        this.EstSigla = EstSigla;
    }

    public String getEstNome() {
        return EstNome;
    }

    public void setEstNome(String EstNome) {
        this.EstNome = EstNome;
    }

    public static java.util.ArrayList<Estado> getAlll(Pais pai) {
        Estado est = new Estado();
        java.util.ArrayList<Estado> list = new java.util.ArrayList<>();
        String sql = "Select * from estado where 1=1 and PaiCodigo =?";
        try {
            ResultSet rs = DB.executeQuery(sql, new Object[]{pai.getPaiCodigo()});
            while (rs.next()) {
                est.setEstNome(rs.getString("EstNome"));
                est.setEstSigla(rs.getString("EstSigla"));
                est.setPaiCodigo(Pais.getPais(rs.getInt("PaiCodigo")));
                list.add(est);
            }
        } catch (Exception ex) {
            Logger.getLogger(Estado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean load(int PaiCodigo, String EstCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " WHERE  PaiCodigo = ? and EstSigla= ?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{PaiCodigo, EstCodigo});
            if (rs.next()) {
                this.setEstNome(rs.getString("EstNome"));
                this.setEstSigla(rs.getString("EstSigla"));
                this.setPaiCodigo(Pais.getPais(PaiCodigo));
                flag = DB.FLAG_UPDATE;
            }
        } catch (Exception ex) {
            Logger.getLogger(Estado.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean insert() {
        String sql = " INSERT INTO " + reflection.ReflectionUtil.getDBTableName(this)
                + " (PaiCodigo, EstSigla, EstNome)"
                + " VALUES (?, ?, ?)";
        try {
            DB.executeUpdate(sql, new Object[]{PaiCodigo.getPaiCodigo(), EstSigla, EstNome});
            flag = DB.FLAG_UPDATE;
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Estado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean update() {
        try {
            String sql = "update " + reflection.ReflectionUtil.getDBTableName(this) + " set "
                    + " PaiCodigo=?, EstSigla=?, EstNome=? where PaiCodigo=? and EstSigla=?";
            DB.executeUpdate(sql, new Object[]{PaiCodigo.getPaiCodigo(), EstSigla, EstNome, PaiCodigo.getPaiCodigo(), EstSigla});
            return true;
        } catch (Exception e) {
            Logger.getLogger(Estado.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean save() {
        switch (flag) {
            case DB.FLAG_INSERT:
                insert();
            case DB.FLAG_UPDATE:
                update();
        }
        return false;
    }

}
