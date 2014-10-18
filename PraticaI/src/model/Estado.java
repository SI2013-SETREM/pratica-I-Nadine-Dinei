
package model;

import java.util.ArrayList;
import java.util.Collection;
import reflection.ComboBoxItem;
import reflection.FilterField;
import reflection.FilterFieldComboBox;
import reflection.FilterFieldDynamicCombo;
import reflection.FilterFieldText;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Estado extends ModelTemplate {
    private Pais pais;
    private String EstSigla;
    private String EstNome;
    
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Estado";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Estados";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"Pais.PaiCodigo", "EstSigla"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static String[][] listTableFields = {
        {"País",        "Sigla",    "Nome"}, //Rótulo da coluna
        {"Pais.PaiNome", "EstSigla", "EstNome"}, //Nome do campo no banco / atributo do model
    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("EstNome", "Nome", 200),
//        new FilterFieldComboBox("EstSigla", "Sigla", 200, new ComboBoxItem[]{
//            new ComboBoxItem("RS", "Rio Grande do Sul")
//        }, ""),
        new FilterFieldDynamicCombo("PaiCodigo", "País", 200, Pais.class, "PaiNome", null, null, "")
    };
    
    public Estado() {
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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

}
