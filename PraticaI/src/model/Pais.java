
package model;

/**
 *
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
    
    public static String[] idColumn = {"PaiCodigo"};
    
    public static String[][] listTableFields = {
        {"Nome",    "Sigla"}, //Nome da coluna
        {"PaiNome", "PaiAlfa2"}, //Nome do campo no banco / atributo do model
    };
    
    public static Object[][] listFilterFields = {
        {"Nome", "PaiNome", 200},
        {"Sigla", "PaiAlfa2", 60}
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
