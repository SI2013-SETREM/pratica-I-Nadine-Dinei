/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import util.DB;

/**
 *
 * @author Nadine
 */
public class PessoaEndereco extends ModelTemplate {

    private int PesCodigo;
    private int PesEndCodigo;
    private String PesEndDescricao;
    private String PesEndCep;
    private Pais PaiCodigo;
    private Estado EstSigla;
    private Cidade CidCodigo;
    private String PesEndEndereco;
    private String PesEndComplemento;
    private String PesEndNumero;
    private String PesEndBairro;

    private String flag = DB.FLAG_INSERT;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Endereço";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Endereços";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "googleMap.png";
    /**
     * @see model.ModelTemplate#softDelete
     */
    public static String softDelete = "";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"PesCodigo", "PesEndCodigo"};

    public int getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(int PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public int getPesEndCodigo() {
        return PesEndCodigo;
    }

    public void setPesEndCodigo(int PesEndCodigo) {
        this.PesEndCodigo = PesEndCodigo;
    }

    public String getPesEndDescricao() {
        return PesEndDescricao;
    }

    public void setPesEndDescricao(String PesEndDescricao) {
        this.PesEndDescricao = PesEndDescricao;
    }

    public String getPesEndCep() {
        return PesEndCep;
    }

    public void setPesEndCep(String PesEndCep) {
        this.PesEndCep = PesEndCep;
    }

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

    public Cidade getCidCodigo() {
        return CidCodigo;
    }

    public void setCidCodigo(Cidade CidCodigo) {
        this.CidCodigo = CidCodigo;
    }

    public String getPesEndEndereco() {
        return PesEndEndereco;
    }

    public void setPesEndEndereco(String PesEndEndereco) {
        this.PesEndEndereco = PesEndEndereco;
    }

    public String getPesEndComplemento() {
        return PesEndComplemento;
    }

    public void setPesEndComplemento(String PesEndComplemento) {
        this.PesEndComplemento = PesEndComplemento;
    }

    public String getPesEndNumero() {
        return PesEndNumero;
    }

    public void setPesEndNumero(String PesEndNumero) {
        this.PesEndNumero = PesEndNumero;
    }

    public String getPesEndBairro() {
        return PesEndBairro;
    }

    public void setPesEndBairro(String PesEndBairro) {
        this.PesEndBairro = PesEndBairro;
    }

}
