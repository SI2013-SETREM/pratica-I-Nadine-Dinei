package model;

import java.util.Date;
import util.field.FilterField;
import util.field.FilterFieldDynamicCombo;
import util.field.FilterFieldText;

/**
 *
 * @author Nadine
 */
public class Cliente extends ModelTemplate{

    private int CliCodigo;
    private Pessoa PesCodigo;
    private Date CliDtaDelecao;
     /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Cliente";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Clientes";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "CLIENTES";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "clientes.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"CliCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Nome", "Pessoa.PesNome"},
        {"CPF/CNPJ","Pessoa.PesCPFCNPJ"}
    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("EstNome", "Nome", 200),
        new FilterFieldDynamicCombo("Pais.PaiCodigo", "País", 200, Pais.class, "PaiNome", null, null, "")
    };

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
