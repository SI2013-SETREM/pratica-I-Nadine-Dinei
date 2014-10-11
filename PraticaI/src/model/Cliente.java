package model;

import java.util.Date;

/**
 *
 * @author Nadine
 */
public class Cliente {

    private int CliCodigo;
    private Pessoa PesCodigo;
    private Date CliDtaDelecao;

    public Cliente() {
    }

    public int getCliCodigo() {
        return CliCodigo;
    }

    public void setCliCodigo(int CliCodigo) {
        this.CliCodigo = CliCodigo;
    }

    public Pessoa getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(Pessoa PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public Date getCliDtaDelecao() {
        return CliDtaDelecao;
    }

    public void setCliDtaDelecao(Date CliDtaDelecao) {
        this.CliDtaDelecao = CliDtaDelecao;
    }

}
