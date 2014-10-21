
package model;

import util.field.FilterField;
import util.field.FilterFieldText;

/**
 *  Classe de País
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Pais extends ModelTemplate {
    
    private int PaiCodigo;
    private String PaiAlfa2;
    private String PaiAlfa3;
    private int PaiBacenIbge;
    private int PaiISO3166;
    private String PaiNome;
    
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "País";
    /**
     * {@inheritDoc}
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Países";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "locate.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"PaiCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Nome",  "PaiNome"},
        {"Sigla", "PaiAlfa2"}
    };
    
//    public static FilterField[] listFilterFields = {
//        {"Nome", "PaiNome", 200},
//        {"Sigla", "PaiAlfa2", 60}
//    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("PaiNome", "Nome", 200),
        new FilterFieldText("PaiAlfa2", "Sigla", 60)
    };
    
    public Pais() {
    }

    public int getPaiCodigo() {
        return PaiCodigo;
    }

    public void setPaiCodigo(int PaiCodigo) {
        this.PaiCodigo = PaiCodigo;
    }

    public String getPaiAlfa2() {
        return PaiAlfa2;
    }

    public void setPaiAlfa2(String PaiAlfa2) {
        this.PaiAlfa2 = PaiAlfa2;
    }

    public String getPaiAlfa3() {
        return PaiAlfa3;
    }

    public void setPaiAlfa3(String PaiAlfa3) {
        this.PaiAlfa3 = PaiAlfa3;
    }

    public int getPaiBacenIbge() {
        return PaiBacenIbge;
    }

    public void setPaiBacenIbge(int PaiBacenIbge) {
        this.PaiBacenIbge = PaiBacenIbge;
    }

    public int getPaiISO3166() {
        return PaiISO3166;
    }

    public void setPaiISO3166(int PaiISO3166) {
        this.PaiISO3166 = PaiISO3166;
    }
    
    public String getPaiNome() {
        return PaiNome;
    }

    public void setPaiNome(String PaiNome) {
        this.PaiNome = PaiNome;
    }
    
}
