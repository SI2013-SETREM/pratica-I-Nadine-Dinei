package model;

import java.util.Date;

/**
 *
 * @author Nadine
 */
public class FechamentoCaixa extends ModelTemplate {

    private int CntCodigo;
    private int FchCodigo;
    private int UsuCodigo;
    private Date FchDataHora;
    private double FchSaldo;
    
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "FECHAMENTOS DE PERIODO";

    public FechamentoCaixa() {

    }

    public int getCntCodigo() {
        return CntCodigo;
    }

    public void setCntCodigo(int CntCodigo) {
        this.CntCodigo = CntCodigo;
    }

    public int getFchCodigo() {
        return FchCodigo;
    }

    public void setFchCodigo(int FchCodigo) {
        this.FchCodigo = FchCodigo;
    }

    public int getUsuCodigo() {
        return UsuCodigo;
    }

    public void setUsuCodigo(int UsuCodigo) {
        this.UsuCodigo = UsuCodigo;
    }

    public Date getFchDataHora() {
        return FchDataHora;
    }

    public void setFchDataHora(Date FchDataHora) {
        this.FchDataHora = FchDataHora;
    }

    public double getFchSaldo() {
        return FchSaldo;
    }

    public void setFchSaldo(double FchSaldo) {
        this.FchSaldo = FchSaldo;
    }

}
