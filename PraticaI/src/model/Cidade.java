package model;

/**
 *
 * @author Nadine
 */
public class Cidade {

    private Pais PaiCodigo;
    private Estado EstSigla;
    private int CidCodigo;
    private String CidNome;

    public Pais getPaiCodigo() {
        return PaiCodigo;
    }

    public void setPaiCodigo(Pais PaiCodigo) {
        this.PaiCodigo = PaiCodigo;
    }

    public Estado getEstSigla() {
        return EstSigla;
    }

    public void setEstSigla(Estado EstSigla) {
        this.EstSigla = EstSigla;
    }

    public int getCidCodigo() {
        return CidCodigo;
    }

    public void setCidCodigo(int CidCodigo) {
        this.CidCodigo = CidCodigo;
    }

    public String getCidNome() {
        return CidNome;
    }

    public void setCidNome(String CidNome) {
        this.CidNome = CidNome;
    }

    public Cidade() {
    }

}
