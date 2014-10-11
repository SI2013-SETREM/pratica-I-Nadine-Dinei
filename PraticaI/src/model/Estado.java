
package model;

/**
 *
 * @author Dinei A. Rockenbach
 */
public class Estado {
    private Pais pais;
    private String EstSigla;
    private String EstNome;

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
