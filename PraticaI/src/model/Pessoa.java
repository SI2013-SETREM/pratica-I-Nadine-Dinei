package model;

import java.util.Date;

/**
 *
 * @author Nadine
 */
public class Pessoa {

    private int PesCodigo;
    private String PesNome;
    private PessoaEmail PesEmlCodigo;
    private int PesSexo;
    private String PesCPFCNPJ;
    private String PesRG;
    private char PesTipoPessoa;
    private Date PesDtaNascimento;
    private int PesIsFuncionario;
    private int PesIsCliente;
    private int PesIsUsuario;
    private int PesIsFornecedor;
    private Date PesDtaDelecao;

    public Pessoa() {
    }

    public int getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(int PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public String getPesNome() {
        return PesNome;
    }

    public void setPesNome(String PesNome) {
        this.PesNome = PesNome;
    }

    public PessoaEmail getPesEmlCodigo() {
        return PesEmlCodigo;
    }

    public void setPesEmlCodigo(PessoaEmail PesEmlCodigo) {
        this.PesEmlCodigo = PesEmlCodigo;
    }

    public int getPesSexo() {
        return PesSexo;
    }

    public void setPesSexo(int PesSexo) {
        this.PesSexo = PesSexo;
    }

    public String getPesCPFCNPJ() {
        return PesCPFCNPJ;
    }

    public void setPesCPFCNPJ(String PesCPFCNPJ) {
        this.PesCPFCNPJ = PesCPFCNPJ;
    }

    public String getPesRG() {
        return PesRG;
    }

    public void setPesRG(String PesRG) {
        this.PesRG = PesRG;
    }

    public char getPesTipoPessoa() {
        return PesTipoPessoa;
    }

    public void setPesTipoPessoa(char PesTipoPessoa) {
        this.PesTipoPessoa = PesTipoPessoa;
    }

    public Date getPesDtaNascimento() {
        return PesDtaNascimento;
    }

    public void setPesDtaNascimento(Date PesDtaNascimento) {
        this.PesDtaNascimento = PesDtaNascimento;
    }

    public int getPesIsFuncionario() {
        return PesIsFuncionario;
    }

    public void setPesIsFuncionario(int PesIsFuncionario) {
        this.PesIsFuncionario = PesIsFuncionario;
    }

    public int getPesIsCliente() {
        return PesIsCliente;
    }

    public void setPesIsCliente(int PesIsCliente) {
        this.PesIsCliente = PesIsCliente;
    }

    public int getPesIsUsuario() {
        return PesIsUsuario;
    }

    public void setPesIsUsuario(int PesIsUsuario) {
        this.PesIsUsuario = PesIsUsuario;
    }

    public int getPesIsFornecedor() {
        return PesIsFornecedor;
    }

    public void setPesIsFornecedor(int PesIsFornecedor) {
        this.PesIsFornecedor = PesIsFornecedor;
    }

    public Date getPesDtaDelecao() {
        return PesDtaDelecao;
    }

    public void setPesDtaDelecao(Date PesDtaDelecao) {
        this.PesDtaDelecao = PesDtaDelecao;
    }

}
