
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldDynamicCombo;
import util.field.FilterFieldText;

/**
 *  Model de Lançamentos
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
    
    public String errorDsc;
    
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
    public static String iconTitle = "money.png";
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
    /**
     * @see model.ModelTemplate#allowDelete
     */
    public static boolean allowDelete = false;
    
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
    
    public boolean load(int CntCodigo, int LanCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(Lancamento.class)
                + " WHERE CntCodigo = ? AND LanCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[] {CntCodigo, LanCodigo});
            if (rs.next()) {
                this.fill(rs, true);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sequencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private Lancamento fill(ResultSet rs, boolean fillChild) throws SQLException {
        this.setLanCodigo(rs.getInt("LanCodigo"));
        if (fillChild) {
            //@TODO
//            this.setCliCodigo(rs.getInt("CliCodigo"));
//            this.setVenCodigo(rs.getInt("VenCodigo"));
            
            PlanoContas plnContas = new PlanoContas();
            if (plnContas.load(rs.getInt("PlnCodigo")))
                this.setPlnCodigo(plnContas);
        }
        this.setLanTipo(rs.getInt("LanTipo"));
        this.setLanDataHora(rs.getTimestamp("LanDataHora"));
        this.setLanValorEntrada(rs.getDouble("LanValorEntrada"));
        this.setLanValorSaida(rs.getDouble("LanValorSaida"));
        this.setLanDescricao(rs.getString("LanDescricao"));
        this.setLanDocumento(rs.getString("LanDocumento"));
        this.setLanEfetivado(rs.getBoolean("LanEfetivado"));
        this.setFlag(DB.FLAG_UPDATE);
        return this;
    }
    
    public boolean save() {
        if (this.validate()) {
            switch(this.getFlag()) {
                case DB.FLAG_INSERT:
                    return this.insert();
                case DB.FLAG_UPDATE:
                    return this.update();
            }
        }
        return false;
    }
    
    public boolean validate() {
        if ((this.getLanTipo() == Lancamento.TIPO_ENTRADA && this.getLanValorEntrada() == 0)
            || (this.getLanTipo() == Lancamento.TIPO_SAIDA && this.getLanValorSaida()== 0)) {
            this.errorDsc = "O valor da entrada/saída é obrigatório";
            return false;
        }
//        if (this.get)
        return true;
    }
    
    public boolean insert() {
        this.setLanCodigo(Sequencial.getNextSequencial(Lancamento.class));
        try {
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(Lancamento.class);
            sql += "(CntCodigo, LanCodigo, CliCodigo, VenCodigo, PlnCodigo, LanTipo, LanDataHora,";
            sql += " LanValorEntrada, LanValorSaida, LanDescricao, LanDocumento, LanEfetivado)";
            sql += " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] parms = this.getSQLParms();
            DB.executeUpdate(sql, parms);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Lancamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean update() {
        try {
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(Lancamento.class) + " SET";
            sql += " CliCodigo = ?, VenCodigo = ?, PlnCodigo = ?, LanTipo = ?, LanDataHora = ?,";
            sql += " LanValorEntrada = ?, LanValorSaida = ?, LanDescricao = ?, LanDocumento = ?, LanEfetivado = ?";
            sql += " WHERE CntCodigo = ? AND LanCodigo = ?";
            Object[] parms = this.getSQLParms();
            DB.executeUpdate(sql, parms);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Lancamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private Object[] getSQLParms() {
        Object[] parms = new Object[12];
        int i = 0;
        if (this.flag.equals(DB.FLAG_INSERT)) {
            parms[i++] = this.getCntCodigo().getCntCodigo();
            parms[i++] = this.getLanCodigo();
        }
        if (this.getCliCodigo() != null) 
            parms[i] = this.getCliCodigo().getCliCodigo();
//        else
//            parms[i] = "NULL";
        i++;
        if (this.getVenCodigo() != null) 
            parms[i] = this.getVenCodigo();//.getVenCodigo;
//        else
//            parms[i] = "NULL";
        i++;
        if (this.getPlnCodigo() != null) 
            parms[i] = this.getPlnCodigo().getPlnCodigo();
//        else
//            parms[i] = "NULL";
        i++;
        parms[i++] = this.getLanTipo();
        parms[i++] = this.getLanDataHora();
        parms[i++] = this.getLanValorEntrada();
        parms[i++] = this.getLanValorSaida();
        parms[i++] = this.getLanDescricao();
        parms[i++] = this.getLanDocumento();
        parms[i++] = this.isLanEfetivado();
        if (!this.flag.equals(DB.FLAG_INSERT)) {
            parms[i++] = this.getCntCodigo().getCntCodigo();
            parms[i++] = this.getLanCodigo();
        }
        return parms;
    }
    
    public static ArrayList<Lancamento> getLancamentos(java.sql.Timestamp from, java.sql.Timestamp to) {
        ArrayList<Lancamento> list = new ArrayList<>();
        try {
            String sql = "SELECT p.*, l.* FROM " + reflection.ReflectionUtil.getDBTableName(Lancamento.class) + " l";
            sql += " LEFT OUTER JOIN " + reflection.ReflectionUtil.getDBTableName(PlanoContas.class) + " p ON (l.PlnCodigo = p.PlnCodigo)";
            sql += " WHERE LanDataHora BETWEEN ? AND ?";
            ResultSet rs = DB.executeQuery(sql, new java.sql.Timestamp[]{from, to});
            Lancamento lancamento = new Lancamento();
            lancamento.fill(rs, false);
            lancamento.setPlnCodigo(new PlanoContas().fill(rs, false));
            
            list.add(lancamento);
        } catch (SQLException ex) {
            Logger.getLogger(Lancamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}
