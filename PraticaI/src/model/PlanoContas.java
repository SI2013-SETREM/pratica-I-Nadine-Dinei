
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import reflection.ReflectionUtil;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldDynamicCombo;
import util.field.FilterFieldText;
import util.sql.LeftOuterJoin;

/**
 *  Classe de Plano de Contas
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class PlanoContas extends ModelTemplate {
    
    // nbsp(non-breaking space): ALT+255
    private static final String CHILD_SIGNAL = "|   ";
    
    private int PlnCodigo;
    private String PlnNome;
    private PlanoContas PlnCodigoPai;
    
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Plano de Contas";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Planos de Contas";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "PLANOS DE CONTA";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"PlnCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Plano Pai", new LeftOuterJoin(PlanoContas.class, new String[]{"PlnCodigoPai"}, PlanoContas.class, new String[]{"PlnCodigo"}, "PlnNome")},
        {"Nome", "PlnNome"},
    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("PlnNome", "Nome", 200),
        new FilterFieldDynamicCombo("PlanoContas.PlnCodigo", "Plano Pai", 200, PlanoContas.class, "PlnNome", null, null, "")
    };
    
    
    
    public PlanoContas() {
    }

    public int getPlnCodigo() {
        return PlnCodigo;
    }

    public void setPlnCodigo(int PlnCodigo) {
        this.PlnCodigo = PlnCodigo;
    }

    public String getPlnNome() {
        return PlnNome;
    }

    public void setPlnNome(String PlnNome) {
        this.PlnNome = PlnNome;
    }

    public PlanoContas getPlnCodigoPai() {
        return PlnCodigoPai;
    }

    public void setPlnCodigoPai(PlanoContas PlnCodigoPai) {
        this.PlnCodigoPai = PlnCodigoPai;
    }
    
    public boolean load(int PlnCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this)
                + " WHERE PlnCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[] {PlnCodigo});
            if (rs.next()) {
                this.fill(rs, true);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sequencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public PlanoContas fill(ResultSet rs, boolean fillChild) throws SQLException {
        this.setPlnCodigo(rs.getInt("PlnCodigo"));
        this.setPlnNome(rs.getString("PlnNome"));
        int PlnCodigoPai = rs.getInt("PlnCodigoPai");
        if (fillChild && PlnCodigoPai != 0) {
            PlanoContas plnPai = new PlanoContas();
            plnPai.load(rs.getInt("PlnCodigoPai"));
            this.setPlnCodigoPai(plnPai);
        }
        return this;
    }
    
    public static ArrayList<PlanoContas> getAllOrdered() {
        ArrayList<PlanoContas> listPlanoContas = getAll();
        ArrayList<PlanoContas> finalList = new ArrayList<>();
        listPlanoContas = findChilds(listPlanoContas, finalList, 0, 0);
        for (PlanoContas pln : listPlanoContas) { //Caso tenha algum com problema, vai no final da fila
            finalList.add(pln);
        }
        return finalList;
    }
    private static ArrayList<PlanoContas> findChilds(ArrayList<PlanoContas> orgList, ArrayList<PlanoContas> dstList, int idPai, int iterations) {
        ArrayList<PlanoContas> newList = new ArrayList<>();
        for (PlanoContas pln : orgList) 
            newList.add(pln);
        
        for (PlanoContas pln : orgList) {
            int PlnCodigoPai = 0;
            if (pln.getPlnCodigoPai() != null) 
                PlnCodigoPai = pln.getPlnCodigoPai().getPlnCodigo();
            
            if (idPai == PlnCodigoPai) {
                pln.setPlnNome(util.Util.strRepeat(CHILD_SIGNAL, iterations) + pln.getPlnNome());
                dstList.add(pln);
                newList.remove(pln);
                findChilds(newList, dstList, pln.getPlnCodigo(), iterations+1);
            }
        }
        return newList;
    }
    
    public static java.util.ArrayList<PlanoContas> getAll() {
        java.util.ArrayList<PlanoContas> list = new java.util.ArrayList<>();
        try {
            String sql = "SELECT * FROM " + ReflectionUtil.getDBTableName(PlanoContas.class);
            sql += " ORDER BY PlnCodigoPai";
            java.sql.ResultSet rs = DB.executeQuery(sql);
            while (rs.next()) {
                PlanoContas pln = new PlanoContas().fill(rs, true);
                list.add(pln);
            }
        } catch (Exception ex) {
            Logger.getLogger(ModelTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
