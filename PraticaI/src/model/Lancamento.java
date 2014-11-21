
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
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
    
    public static final java.awt.Color COR_ENTRADA = new java.awt.Color(50, 130, 60);
    public static final java.awt.Color COR_ENTRADA_SEL = new java.awt.Color(200, 255, 200);
    public static final java.awt.Color COR_SAIDA = new java.awt.Color(200, 25, 25);
    public static final java.awt.Color COR_SAIDA_SEL = new java.awt.Color(255, 200, 200);
    public static final java.awt.Color COR_INATIVO = new java.awt.Color(150, 150, 150);
    public static final java.awt.Color COR_INATIVO_SEL = new java.awt.Color(200, 200, 200);
    public static final java.awt.Color COR_ESTORNADO = new java.awt.Color(75, 75, 75);
    
    private ContaCapital CntCodigo;
    private int LanCodigo;
    private Venda VenCodigo;
    private Pessoa PesCodigo;
    private PlanoContas PlnCodigo;
    private int LanTipo;
    private Timestamp LanDataHora;
    private double LanValorEntrada;
    private double LanValorEntradaOriginal;
    private double LanValorSaida;
    private double LanValorSaidaOriginal;
    private String LanDescricao;
    private String LanDocumento;
    private boolean LanEfetivado;
    private boolean LanEfetivadoOriginal;
    private boolean LanEstornado;
    private boolean LanEstornadoOriginal;
    
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
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "LANCAMENTOS";
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

    public Venda getVenCodigo() {
        return VenCodigo;
    }

    public void setVenCodigo(Venda VenCodigo) {
        this.VenCodigo = VenCodigo;
    }

    public Pessoa getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(Pessoa PesCodigo) {
        this.PesCodigo = PesCodigo;
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
    
    private double getLanValorEntradaOriginal() {
        return LanValorEntradaOriginal;
    }
    
    private void setLanValorEntradaOriginal(double LanValorEntrada) {
        this.LanValorEntradaOriginal = LanValorEntrada;
    }

    public double getLanValorSaida() {
        return LanValorSaida;
    }

    public void setLanValorSaida(double LanValorSaida) {
        this.LanValorSaida = LanValorSaida;
    }

    private double getLanValorSaidaOriginal() {
        return LanValorSaidaOriginal;
    }
    
    private void setLanValorSaidaOriginal(double LanValorSaida) {
        this.LanValorSaidaOriginal = LanValorSaida;
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

    private boolean isLanEfetivadoOriginal() {
        return LanEfetivadoOriginal;
    }
    
    private void setLanEfetivadoOriginal(boolean LanEfetivado) {
        this.LanEfetivadoOriginal = LanEfetivado;
    }

    public boolean isLanEstornado() {
        return LanEstornado;
    }

    public void setLanEstornado(boolean LanEstornado) {
        this.LanEstornado = LanEstornado;
    }

    private boolean isLanEstornadoOriginal() {
        return LanEstornadoOriginal;
    }
    
    private void setLanEstornadoOriginal(boolean LanEstornado) {
        this.LanEstornadoOriginal = LanEstornado;
    }

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public boolean load(int CntCodigo, int LanCodigo) {
        return load(CntCodigo, LanCodigo, true);
    }
    public boolean load(int CntCodigo, int LanCodigo, boolean fillChild) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(Lancamento.class)
                + " WHERE CntCodigo = ? AND LanCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[] {CntCodigo, LanCodigo});
            if (rs.next()) {
                this.fill(rs, fillChild);
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar o lançamento [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    private Lancamento fill(ResultSet rs, boolean fillChild) throws SQLException {
        this.setLanCodigo(rs.getInt("LanCodigo"));
        if (fillChild) {
            Pessoa pessoa = new Pessoa();
            int PesCodigo = rs.getInt("PesCodigo");
            if (PesCodigo != 0 && pessoa.load(PesCodigo))
                this.setPesCodigo(pessoa);
            
            ContaCapital cntCapital = new ContaCapital();
            int CntCodigo = rs.getInt("CntCodigo");
            if (CntCodigo != 0 && cntCapital.load(CntCodigo))
                this.setCntCodigo(cntCapital);
            
            Venda venda = new Venda();
            int VenCodigo = rs.getInt("VenCodigo");
            int CliCodigo = rs.getInt("CliCodigo");
            if (VenCodigo != 0 && CliCodigo != 0 && venda.load(CliCodigo, VenCodigo))
                this.setVenCodigo(venda);
            
            PlanoContas plnContas = new PlanoContas();
            if (plnContas.load(rs.getInt("PlnCodigo")))
                this.setPlnCodigo(plnContas);
        }
        this.setLanTipo(rs.getInt("LanTipo"));
        this.setLanDataHora(rs.getTimestamp("LanDataHora"));
        this.setLanValorEntrada(rs.getDouble("LanValorEntrada"));
        this.setLanValorEntradaOriginal(rs.getDouble("LanValorEntrada"));
        this.setLanValorSaida(rs.getDouble("LanValorSaida"));
        this.setLanValorSaidaOriginal(rs.getDouble("LanValorSaida"));
        this.setLanDescricao(rs.getString("LanDescricao"));
        this.setLanDocumento(rs.getString("LanDocumento"));
        this.setLanEfetivado(rs.getBoolean("LanEfetivado"));
        this.setLanEfetivadoOriginal(rs.getBoolean("LanEfetivado"));
        this.setLanEstornado(rs.getBoolean("LanEstornado"));
        this.setLanEstornadoOriginal(rs.getBoolean("LanEstornado"));
        
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
        try {
            this.setLanCodigo(Sequencial.getNextSequencial(Lancamento.class.getSimpleName() + "_" + this.getCntCodigo().getCntCodigo()));
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(Lancamento.class);
            sql += "(CntCodigo, LanCodigo, CliCodigo, VenCodigo, PesCodigo, PlnCodigo, LanTipo, LanDataHora,";
            sql += " LanValorEntrada, LanValorSaida, LanDescricao, LanDocumento, LanEfetivado, LanEstornado)";
            sql += " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] parms = this.getSQLParms();
            DB.executeUpdate(sql, parms);
            
            Log.log(fncNome, Log.INT_INSERCAO, "Inseriu o " + sngTitle + " de código " + this.getLanCodigo() + " na Conta de Capital " + this.getCntCodigo().getCntCodigo() + " (" + this.getCntCodigo().getCntNome() + ")", Log.NV_INFO);
            
            this.updateSaldoContaCapital();
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_INSERCAO, "Falha ao inserir o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public boolean update() {
        try {
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(Lancamento.class) + " SET";
            sql += " CliCodigo = ?, VenCodigo = ?, PesCodigo = ?, PlnCodigo = ?, LanTipo = ?, LanDataHora = ?, LanValorEntrada = ?,";
            sql += " LanValorSaida = ?, LanDescricao = ?, LanDocumento = ?, LanEfetivado = ?, LanEstornado = ?";
            sql += " WHERE CntCodigo = ? AND LanCodigo = ?";
            Object[] parms = this.getSQLParms();
            System.out.println("UPDATE " + sql + "\r\n" + getCntCodigo().getCntCodigo() + "," + getLanCodigo());
            DB.executeUpdate(sql, parms);
            
            Log.log(fncNome, Log.INT_INSERCAO, "Alterou o " + sngTitle + " de código " + this.getLanCodigo() + " da Conta de Capital " + this.getCntCodigo().getCntCodigo() + " (" + this.getCntCodigo().getCntNome() + ")", Log.NV_INFO);
            
            this.updateSaldoContaCapital();
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_ALTERACAO, "Falha ao alterar o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public void efetivar() {
        this.setLanEfetivado(true);
        this.save();
    }
    public void estornar() {
        this.setLanEfetivado(true); //Efetiva antes de estornar, para manter o saldo da conta de capital correto
        this.setLanEstornado(true);
        this.save();
    }
    
    private void updateSaldoContaCapital() {
        this.updateSaldoContaCapital(this.getLanTipo());
    }
    private void updateSaldoContaCapital(int LanTipo) {
        ContaCapital cc = this.getCntCodigo();
        boolean alterou = false;
        switch (LanTipo) {
            case TIPO_ENTRADA:
                if (this.isLanEfetivado() && !this.isLanEfetivadoOriginal()) { //Efetivou o lançamento
                    cc.debito(this.getLanValorEntrada());
                    alterou = true;
                }
                if (this.isLanEstornado() && !this.isLanEstornadoOriginal()) { //Estornou o lançamento
                    cc.credito(this.getLanValorEntrada());
                    alterou = true;
                }
                if (!alterou && this.getLanValorEntrada() != this.getLanValorEntradaOriginal()) { //Alterou o lançamento
                    cc.debito(this.getLanValorEntradaOriginal() - this.getLanValorEntrada());
                }
                break;
            case TIPO_SAIDA:
                if (this.isLanEfetivado() && !this.isLanEfetivadoOriginal()) { //Efetivou o lançamento
                    cc.credito(this.getLanValorSaida());
                    alterou = true;
                }
                if (this.isLanEstornado() && !this.isLanEstornadoOriginal()) { //Estornou o lançamento
                    cc.debito(this.getLanValorEntrada());
                    alterou = true;
                }
                if (!alterou && this.getLanValorSaida() != this.getLanValorSaidaOriginal()) { //Alterou o lançamento
                    cc.credito(this.getLanValorSaidaOriginal() - this.getLanValorSaida());
                }
                break;
            case TIPO_TRANSFERENCIA:
                if (this.getLanValorSaida() != 0) 
                    this.updateSaldoContaCapital(TIPO_SAIDA);
                if (this.getLanValorEntrada() != 0) 
                    this.updateSaldoContaCapital(TIPO_ENTRADA);
                break;
        }
        cc.save();
    }
    
    private Object[] getSQLParms() {
        Object[] parms = new Object[14];
        int i = 0;
        if (this.flag.equals(DB.FLAG_INSERT)) {
            parms[i++] = this.getCntCodigo().getCntCodigo();
            parms[i++] = this.getLanCodigo();
        }
        if (this.getVenCodigo() != null) {
            parms[i++] = this.getVenCodigo().getCliCodigo().getCliCodigo();
            parms[i++] = this.getVenCodigo().getVenCodigo();
        } else {
            i++;
            i++;
        }
        if (this.getPesCodigo() != null) {
            parms[i] = this.getPesCodigo().getPesCodigo();
        }
        i++;
        if (this.getPlnCodigo() != null) {
            parms[i] = this.getPlnCodigo().getPlnCodigo();
        }
        i++;
        parms[i++] = this.getLanTipo();
        parms[i++] = this.getLanDataHora();
        parms[i++] = this.getLanValorEntrada();
        parms[i++] = this.getLanValorSaida();
        parms[i++] = this.getLanDescricao();
        parms[i++] = this.getLanDocumento();
        parms[i++] = this.isLanEfetivado();
        parms[i++] = this.isLanEstornado();
        if (!this.flag.equals(DB.FLAG_INSERT)) {
            parms[i++] = this.getCntCodigo().getCntCodigo();
            parms[i++] = this.getLanCodigo();
        }
        return parms;
    }
    
    public static ResultSet getList(int CntCodigo, int PlnCodigo, Pessoa pessoa, Venda venda, String LanDescricao, Timestamp LanDataHoraFrom, Timestamp LanDataHoraTo, Boolean LanEfetivado, Boolean LanEstornado) {
        ResultSet rs = null;
        try {
            String sql = "SELECT l.CntCodigo, l.LanCodigo, l.LanEfetivado, l.LanEstornado, l.LanDataHora, cc.CntNome, p.PlnNome,";
            sql += " l.LanDescricao, l.LanValorSaida, l.LanValorEntrada, pes.PesNome, l.VenCodigo, ven.VenData, ven.VenValorFinal";
            sql += " FROM " + reflection.ReflectionUtil.getDBTableName(Lancamento.class) + " l";
            sql += " LEFT OUTER JOIN " + reflection.ReflectionUtil.getDBTableName(PlanoContas.class) + " p ON (l.PlnCodigo = p.PlnCodigo)";
            sql += " LEFT OUTER JOIN " + reflection.ReflectionUtil.getDBTableName(ContaCapital.class) + " cc ON (l.CntCodigo = cc.CntCodigo)";
            sql += " LEFT OUTER JOIN " + reflection.ReflectionUtil.getDBTableName(Venda.class) + " ven ON (l.CliCodigo = ven.CliCodigo AND l.VenCodigo = ven.VenCodigo)";
//            sql += " LEFT OUTER JOIN " + reflection.ReflectionUtil.getDBTableName(Cliente.class) + " cli ON (l.CliCodigo = cli.CliCodigo)";
            sql += " LEFT OUTER JOIN " + reflection.ReflectionUtil.getDBTableName(Pessoa.class) + " pes ON (l.PesCodigo = pes.PesCodigo)";
            sql += " WHERE 1=1";
            
            ArrayList<Object> parms = new ArrayList<>();
            if (CntCodigo != 0) {
                sql += " AND l.CntCodigo = ?";
                parms.add(CntCodigo);
            }
            if (PlnCodigo != 0) {
                sql += " AND l.PlnCodigo = ?";
                parms.add(PlnCodigo);
            }
            if (pessoa != null) {
                sql += " AND l.PesCodigo = ?";
                parms.add(pessoa.getPesCodigo());
            }
            if (venda != null) {
                sql += " AND l.CliCodigo = ? AND l.VenCodigo = ?";
                parms.add(venda.getCliCodigo().getCliCodigo());
                parms.add(venda.getVenCodigo());
            }
            if (LanDescricao != null && !"".equals(LanDescricao)) {
                sql += " AND l.LanDescricao LIKE ?";
                parms.add("%" + LanDescricao + "%");
            }
            if (LanDataHoraFrom != null && LanDataHoraTo != null) {
                sql += " AND l.LanDataHora BETWEEN ? AND ?";
                parms.add(LanDataHoraFrom);
                parms.add(LanDataHoraTo);
            } else if (LanDataHoraFrom != null) {
                sql += " AND l.LanDataHora >= ?";
                parms.add(LanDataHoraFrom);
            } else if (LanDataHoraTo != null) {
                sql += " AND l.LanDataHora <= ?";
                parms.add(LanDataHoraTo);
            }
            if (LanEfetivado != null || LanEstornado != null) {
                sql += " AND (";
                if (LanEfetivado == null) {
                    sql += "LanEfetivado OR NOT LanEfetivado"; // necessário caso tenha LanEstornado
                } else {
                    if (LanEfetivado) {
                        sql += "LanEfetivado";
                    } else {
                        sql += "NOT LanEfetivado";
                    }
                }
                sql += " OR ";
                if (LanEstornado == null) {
                    sql += "LanEstornado OR NOT LanEstornado"; // necessário caso tenha LanEfetivado
                } else {
                    if (LanEstornado) {
                        sql += "LanEstornado";
                    } else {
                        sql += "NOT LanEstornado";
                    }
                }
                sql += ")";
            }
//            System.out.println("SQL LANCAMENTOS: " + sql);
            rs = DB.executeQuery(sql, (Object[]) parms.toArray(new Object[0]));
            
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar a lista de " + prlTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return rs;
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
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar os " + prlTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return list;
    }
    
    public static ResultSet getLancamentosReport(java.sql.Timestamp from, java.sql.Timestamp to) {
        try {
            String sqlTbls = " FROM " + reflection.ReflectionUtil.getDBTableName(Lancamento.class) + " l";
            sqlTbls += " LEFT OUTER JOIN " + reflection.ReflectionUtil.getDBTableName(PlanoContas.class) + " p ON (l.PlnCodigo = p.PlnCodigo)";
            sqlTbls += " LEFT OUTER JOIN " + reflection.ReflectionUtil.getDBTableName(ContaCapital.class) + " cc ON (l.CntCodigo = cc.CntCodigo)";
            sqlTbls += " LEFT OUTER JOIN " + reflection.ReflectionUtil.getDBTableName(Cliente.class) + " cli ON (l.CliCodigo =cli.CliCodigo)";
            sqlTbls += " LEFT OUTER JOIN " + reflection.ReflectionUtil.getDBTableName(Pessoa.class) + " pes ON (cli.PesCodigo = pes.PesCodigo)";
            sqlTbls += " WHERE LanEfetivado";
            sqlTbls += " AND LanDataHora BETWEEN ? AND ?";
            
            String sql = "SELECT * FROM ";
            sql += "(SELECT DATE_FORMAT(l.LanDataHora,'%d/%m/%Y %H:%i:%s') as LanDataHora, cc.CntNome, p.PlnNome, pes.PesNome, l.LanDescricao,";
            sql += " CONCAT('R$ ', FORMAT(COALESCE(l.LanValorEntrada,0), 2)) AS LanValorEntrada, ";
            sql += " CONCAT('R$ ', FORMAT(COALESCE(l.LanValorSaida,0), 2)) AS LanValorSaida,";
            sql += " NULL AS SumEntrada, NULL AS SumSaida";
            sql += sqlTbls;
            sql += " ORDER BY cc.CntCodigo, l.LanDataHora) AS lancamentos";
            sql += " UNION";
            sql += " SELECT NULL, NULL, NULL, NULL, NULL, NULL, NULL,";
            sql += " CONCAT('R$ ', FORMAT(COALESCE(SUM(LanValorEntrada),0), 2)),";
            sql += " CONCAT('R$ ', FORMAT(COALESCE(SUM(LanValorSaida),0), 2)) ";
            sql += " FROM";
            sql += "(SELECT LanValorEntrada, LanValorSaida";
            sql += sqlTbls;
            sql += ") AS somas";
            System.out.println(sql);
            ResultSet rs = DB.executeQuery(sql, new java.sql.Timestamp[]{from, to, from, to});
            return rs;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar a lista de " + prlTitle + " para o relatório [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return null;
    }
    
}
