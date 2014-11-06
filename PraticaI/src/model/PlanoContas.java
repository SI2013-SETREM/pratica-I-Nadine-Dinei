
package model;

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
    
    private int PlnCodigo;
    private String PlnNome;
    private PlanoContas planocontas;
    
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Plano de Contas";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Planos de Contas";
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

    public PlanoContas getPlanoContasPai() {
        return planocontas;
    }

    public void setPlanoContasPai(PlanoContas planocontas) {
        this.planocontas = planocontas;
    }
    
    public static java.util.ArrayList<PlanoContas> getAll() {
        java.util.ArrayList<PlanoContas> list = new java.util.ArrayList<>();
        for (Object o : ModelTemplate.getAll(PlanoContas.class)) {
            list.add((PlanoContas) o);
        }
        return list;
    }

}
