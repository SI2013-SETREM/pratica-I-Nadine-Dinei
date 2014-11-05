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

    public static java.util.ArrayList<Estado> getAll() {
        java.util.ArrayList<Estado> list = new java.util.ArrayList<>();
        for (Object o : ModelTemplate.getAll(Estado.class)) {
            list.add((Estado) o);
        }
        return list;
    }

    public boolean load(int EstCodigo, int PaiCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " WHERE EstSigla = ? and PaiCodigo=?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{EstCodigo, PaiCodigo});
            if (rs.next()) {
                this.setEstNome(rs.getString("EstNome"));
                this.setEstSigla(rs.getString("EstSigla"));
                this.setPaiCodigo(Pais.getPais(PaiCodigo));
            }
        } catch (Exception ex) {
            Logger.getLogger(Estado.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}
