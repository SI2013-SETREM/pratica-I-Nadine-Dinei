
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldDynamicCombo;
import util.field.FilterFieldText;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Lancamento extends ModelTemplate {
    
    public static final int TIPO_ENTRADA = 1;
    public static final int TIPO_SAIDA = 2;
    public static final int TIPO_TRANSFERENCIA = 3;
    
    private ContaCapital CntCodigo;
    private int LanCodigo;
    private Cliente CliCodigo;
    private Venda VenCodigo; //@TODO
    private PlanoContas PlnCodigo;
    private int LanTipo;
    private java.sql.Timestamp LanDataHora;
    private double LanValorEntrada;
    private double LanValorSaida;
    private String LanDescricao;
    private String LanDocumento;
    private boolean LanEfetivado;
    
    private String flag = DB.FLAG_INSERT;
    
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Lançamento";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Lançamentos";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "moneydollar.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"CntCodigo", "LanCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Data", "LanDataHora", 80},
        {"Conta de Capital", "ContaCapital.CntNome"},
        {"Plano de Contas", "PlanoContas.PlnNome"},
        {"Descrição", "LanDescricao"},
        {"Saída", "LanValorSaida"},
        {"Entrada", "LanValorEntrada"},
    };
    /**
     * @see model.ModelTemplate#orderBy
     */
    public static String orderBy = "LanDataHora DESC";
    
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldDynamicCombo("CntCodigo", "Conta de Capital", 150, ContaCapital.class, "CntNome", null, null, ""),
        new FilterFieldDynamicCombo("PlnCodigo", "Plano de Contas", 150, PlanoContas.class, "PlnNome", null, null, ""),
        new FilterFieldText("LanDescricao", "Descrição", 150),
    };
    
    public Lancamento() {
    }
    
    public ContaCapital getCntCodigo() {
        return CntCodigo;
    }

    public void setCntCodigo(ContaCapital CntCodigo) {
        this.CntCodigo = CntCodigo;
    }

    public int getLanCodigo() {
        return LanCodigo;
    }

    public void setLanCodigo(int LanCodigo) {
        this.LanCodigo = LanCodigo;
    }

    public Cliente getCliCodigo() {
        return CliCodigo;
    }

    public void setCliCodigo(Cliente CliCodigo) {
        this.CliCodigo = CliCodigo;
    }

    public Venda getVenCodigo() {
        return VenCodigo;
    }

    public void setVenCodigo(Venda VenCodigo) {
        this.VenCodigo = VenCodigo;
    }

    public PlanoContas getPlnCodigo() {
        return PlnCodigo;
    }

    public void setPlnCodigo(PlanoContas PlnCodigo) {
        this.PlnCodigo = PlnCodigo;
    }

    public int getLanTipo() {
        return LanTipo;
    }

    public void setLanTipo(int LanTipo) {
        this.LanTipo = LanTipo;
    }

    public Timestamp getLanDataHora() {
        return LanDataHora;
    }

    public void setLanDataHora(Timestamp LanDataHora) {
        this.LanDataHora = LanDataHora;
    }

    public double getLanValorEntrada() {
        return LanValorEntrada;
    }

    public void setLanValorEntrada(double LanValorEntrada) {
        this.LanValorEntrada = LanValorEntrada;
    }

    public double getLanValorSaida() {
        return LanValorSaida;
    }

    public void setLanValorSaida(double LanValorSaida) {
        this.LanValorSaida = LanValorSaida;
    }

    public String getLanDescricao() {
        return LanDescricao;
    }

    public void setLanDescricao(String LanDescricao) {
        this.LanDescricao = LanDescricao;
    }

    public String getLanDocumento() {
        return LanDocumento;
    }

    public void setLanDocumento(String LanDocumento) {
        this.LanDocumento = LanDocumento;
    }

    public boolean isLanEfetivado() {
        return LanEfetivado;
    }

    public void setLanEfetivado(boolean LanEfetivado) {
        this.LanEfetivado = LanEfetivado;
    }

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    
    public boolean load() {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(Lancamento.class)
                + " WHERE CntCodigo = ? AND LanCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[] {CntCodigo, LanCodigo});
            if (rs.next()) {
                
                //@TODO - FILL
                
                flag = DB.FLAG_UPDATE;
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sequencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
