
package model;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Estado extends ModelTemplate {
    private Pais pais;
    private String EstSigla;
    private String EstNome;
    
    public static String[] idColumn = {"Pais", "EstSigla"};
    
    public static String[][] listTableFields = {
        {"Sigla",    "Nome"}, //Nome da coluna
        {"EstSigla", "EstNome"}, //Nome do campo no banco / atributo do model
    };
    
    public static Object[][] listFilterFields = {
        {"Sigla", "EstSigla", 60},
        {"Nome", "EstNome", 200}
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
