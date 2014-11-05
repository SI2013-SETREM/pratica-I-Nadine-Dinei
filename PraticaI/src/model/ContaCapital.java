package model;

import util.field.FilterField;
import util.field.FilterFieldText;

/**
 *
 * @author Nadine
 */
public class ContaCapital extends ModelTemplate {

    private int CntCodigo;
    private String CntNome;
    private String CntBncNumero;
    private String CntBncAgencia;
    private String CntBncTitular;
    private int CntPadrao;
    private double CntSaldo;
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Conta Capital";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Contas de Capital";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "safe.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"CntCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Descrição", "CntNome"},
        {"Saldo", "CntSaldo"}
    };

    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("CntNome", "Descrição", 200)
    };

    public ContaCapital() {
    }

    public int getCntCodigo() {
        return CntCodigo;
    }

    public void setCntCodigo(int CntCodigo) {
        this.CntCodigo = CntCodigo;
    }

    public String getCntNome() {
        return CntNome;
    }

    public void setCntNome(String CntNome) {
        this.CntNome = CntNome;
    }

    public String getCntBncNumero() {
        return CntBncNumero;
    }

    public void setCntBncNumero(String CntBncNumero) {
        this.CntBncNumero = CntBncNumero;
    }

    public String getCntBncAgencia() {
        return CntBncAgencia;
    }

    public void setCntBncAgencia(String CntBncAgencia) {
        this.CntBncAgencia = CntBncAgencia;
    }

    public String getCntBncTitular() {
        return CntBncTitular;
    }

    public void setCntBncTitular(String CntBncTitular) {
        this.CntBncTitular = CntBncTitular;
    }

    public int getCntPadrao() {
        return CntPadrao;
    }

    public void setCntPadrao(int CntPadrao) {
        this.CntPadrao = CntPadrao;
    }

    public double getCntSaldo() {
        return CntSaldo;
    }

    public void setCntSaldo(double CntSaldo) {
        this.CntSaldo = CntSaldo;
    }

}
