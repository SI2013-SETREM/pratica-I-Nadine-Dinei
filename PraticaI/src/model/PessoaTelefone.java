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
public class PessoaTelefone {
    private Pessoa PesCodigo;
    private int PesFonCodigo;
    private String PesFonDescricao;
    private String PesFonTelefone;

    private String flag = DB.FLAG_INSERT;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Telefone";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Telefones";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "cartaoVisitas.png";
    /**
     * @see model.ModelTemplate#softDelete
     */
    public static String softDelete = "";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"PesCodigo", "PesFonCodigo"};
    
    public Pessoa getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(Pessoa PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public int getPesFonCodigo() {
        return PesFonCodigo;
    }

    public void setPesFonCodigo(int PesFonCodigo) {
        this.PesFonCodigo = PesFonCodigo;
    }

    public String getPesFonDescricao() {
        return PesFonDescricao;
    }

    public void setPesFonDescricao(String PesFonDescricao) {
        this.PesFonDescricao = PesFonDescricao;
    }

    public String getPesFonTelefone() {
        return PesFonTelefone;
    }

    public void setPesFonTelefone(String PesFonTelefone) {
        this.PesFonTelefone = PesFonTelefone;
    }
    
    
}
