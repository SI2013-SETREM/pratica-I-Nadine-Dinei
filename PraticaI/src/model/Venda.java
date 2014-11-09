
package model;

import java.sql.Date;
import util.field.FilterField;
import util.field.FilterFieldDate;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Venda extends ModelTemplate {
    
    public static final char TIPO_VISTA = 'V';
    public static final char TIPO_PRAZO = 'P';
    
    private Cliente CliCodigo;
    private int VenCodigo;
    private java.sql.Date VenData;
    private double VenValor;
    private double VenDesconto;
    private double VenValorFinal;
    private char VenTipo;
    private int VenParcelas;
    private double VenEntrada;
    
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Venda";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Vendas";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "VENDAS";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "calculatorEdit.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"Cliente.CliCodigo", "VenCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Data", "VenData"},
        {"Cliente", "Cliente.Pessoa.PesNome"},
        {"Valor", "VenValor"},
        {"Desconto", "VenDesconto"},
        {"Valor Final", "VenValorFinal"},
    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldDate("VenData", "Data"),
    };

    public Venda() {
    }

    public Cliente getCliCodigo() {
        return CliCodigo;
    }

    public void setCliCodigo(Cliente CliCodigo) {
        this.CliCodigo = CliCodigo;
    }

    public int getVenCodigo() {
        return VenCodigo;
    }

    public void setVenCodigo(int VenCodigo) {
        this.VenCodigo = VenCodigo;
    }

    public Date getVenData() {
        return VenData;
    }

    public void setVenData(Date VenData) {
        this.VenData = VenData;
    }

    public double getVenValor() {
        return VenValor;
    }

    public void setVenValor(double VenValor) {
        this.VenValor = VenValor;
    }

    public double getVenDesconto() {
        return VenDesconto;
    }

    public void setVenDesconto(double VenDesconto) {
        this.VenDesconto = VenDesconto;
    }

    public double getVenValorFinal() {
        return VenValorFinal;
    }

    public void setVenValorFinal(double VenValorFinal) {
        this.VenValorFinal = VenValorFinal;
    }

    public char getVenTipo() {
        return VenTipo;
    }

    public void setVenTipo(char VenTipo) {
        this.VenTipo = VenTipo;
    }

    public int getVenParcelas() {
        return VenParcelas;
    }

    public void setVenParcelas(int VenParcelas) {
        this.VenParcelas = VenParcelas;
    }

    public double getVenEntrada() {
        return VenEntrada;
    }

    public void setVenEntrada(double VenEntrada) {
        this.VenEntrada = VenEntrada;
    }
    
    
    
    
}
