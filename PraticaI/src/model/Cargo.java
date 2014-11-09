package model;

import java.sql.ResultSet;

import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldText;

/**
 *
 * @author Nadine
 */
public class Cargo extends ModelTemplate {

    private int CrgCodigo;
    private String CrgNome;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Cargo";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Cargos";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "CARGOS DE FUNCIONARIOS";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "briefcase.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"CrgCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Cargo", "CrgNome"}
    };

    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("CrgNome", "Cargo", 200)
    };

    public Cargo() {
    }

    public int getCrgCodigo() {
        return CrgCodigo;
    }

    public void setCrgCodigo(int CrgCodigo) {
        this.CrgCodigo = CrgCodigo;
    }

    public String getCrgNome() {
        return CrgNome;
    }

    public void setCrgNome(String CrgNome) {
        this.CrgNome = CrgNome;
    }

    public void load(int CrgCodig) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " WHERE CrgCodigo=?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{CrgCodig});
            rs.next();
            this.setCrgCodigo(rs.getInt("CrgCodigo"));
            this.setCrgNome(rs.getString("CrgNome"));
        } catch (Exception ex) {
            Logger.getLogger(Pais.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        try {
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " SET CrgNome = ?";
            sql += " WHERE CrgCodigo=?";
            DB.executeUpdate(sql, new Object[]{CrgNome, CrgCodigo});
        } catch (Exception ex) {
            Logger.getLogger(Cargo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert() {
        try {
            String sql = "insert into " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " (CrgNome,CrgCodigo)";
            sql += " values (?,?)";
            DB.executeUpdate(sql, new Object[]{CrgNome, CrgCodigo});
        } catch (Exception ex) {
            Logger.getLogger(Cargo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
