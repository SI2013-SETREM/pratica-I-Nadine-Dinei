
package model;

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
    
}
