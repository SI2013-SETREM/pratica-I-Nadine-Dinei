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
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "ESTADOS";
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
        {"País", "Pais.PaiNome", 100},
        {"Sigla", "EstSigla", 30},
        {"Nome", "EstNome", 200}
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

    public Estado(int PaiCodigo, String EstSigla) {
        this.load(PaiCodigo, EstSigla);
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

    public static java.util.ArrayList<Estado> getAll(int PaiCodigo) {
        java.util.ArrayList<Estado> list = new java.util.ArrayList<>();
        try {
            String sql = "Select * from " + reflection.ReflectionUtil.getDBTableName(Estado.class) + " where 1=1 and PaiCodigo =?";
            System.out.println(sql);
            ResultSet rs = DB.executeQuery(sql, new Object[]{PaiCodigo});
            while (rs.next()) {
                Estado est = new Estado();
                est.setPaiCodigo(new Pais(rs.getInt("PaiCodigo")));
                est.setEstSigla(rs.getString("EstSigla"));
                est.setEstNome(rs.getString("EstNome"));
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
                this.setPaiCodigo(new Pais(PaiCodigo));
                flag = DB.FLAG_UPDATE;
            }
        } catch (Exception ex) {
            Logger.getLogger(Estado.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean insert() {
        String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(this)
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
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(this) + " SET "
                    + " PaiCodigo = ?, EstSigla = ?, EstNome = ? WHERE PaiCodigo = ? and EstSigla = ?";
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
                break;
            case DB.FLAG_UPDATE:
                update();
                break;
        }
        return false;
    }

}
