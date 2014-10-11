package model;

/**
 *
 * @author Nadine
 */
public class PessoaEmail {

    private Pessoa PesCodigo;
    private int PesEmlCodigo;
    private String PesEmlEmail;

    public PessoaEmail() {
    }

    public Pessoa getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(Pessoa PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public int getPesEmlCodigo() {
        return PesEmlCodigo;
    }

    public void setPesEmlCodigo(int PesEmlCodigo) {
        this.PesEmlCodigo = PesEmlCodigo;
    }

    public String getPesEmlEmail() {
        return PesEmlEmail;
    }

    public void setPesEmlEmail(String PesEmlEmail) {
        this.PesEmlEmail = PesEmlEmail;
    }

}
