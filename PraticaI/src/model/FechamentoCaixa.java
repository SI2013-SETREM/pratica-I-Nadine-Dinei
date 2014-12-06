package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;

/**
 *
 * @author Nadine
 */
public class FechamentoCaixa extends ModelTemplate {

    private ContaCapital CntCodigo;
    private int FchCodigo;
    private Usuario UsuCodigo;
    private Timestamp FchDataHora;
    private double FchSaldo;
    
    private String flag = DB.FLAG_INSERT;
    
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Fechamento de Período";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Fechamentos de Período";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "FECHAMENTOS DE PERIODO";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "accept.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"ContaCapital.CntCodigo", "FchCodigo"};

    public FechamentoCaixa() {

    }

    public ContaCapital getCntCodigo() {
        return CntCodigo;
    }

    public void setCntCodigo(ContaCapital CntCodigo) {
        this.CntCodigo = CntCodigo;
    }

    public int getFchCodigo() {
        return FchCodigo;
    }

    public void setFchCodigo(int FchCodigo) {
        this.FchCodigo = FchCodigo;
    }

    public Usuario getUsuCodigo() {
        return UsuCodigo;
    }

    public void setUsuCodigo(Usuario UsuCodigo) {
        this.UsuCodigo = UsuCodigo;
    }

    public Timestamp getFchDataHora() {
        return FchDataHora;
    }

    public void setFchDataHora(Timestamp FchDataHora) {
        this.FchDataHora = FchDataHora;
    }

    public double getFchSaldo() {
        return FchSaldo;
    }

    public void setFchSaldo(double FchSaldo) {
        this.FchSaldo = FchSaldo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public FechamentoCaixa fill(ResultSet rs) throws SQLException {
        return fill(rs, null);
    }
    public FechamentoCaixa fill(ResultSet rs, ContaCapital contaCapital) throws SQLException {
        return fill(rs, contaCapital, null);
    }
    public FechamentoCaixa fill(ResultSet rs, ContaCapital contaCapital, Usuario usuario) throws SQLException {
        ContaCapital cc = new ContaCapital();
        if (contaCapital == null) 
            cc.load(rs.getInt("CntCodigo"));
        else
            cc = contaCapital;
        setCntCodigo(cc);
        setFchCodigo(rs.getInt("FchCodigo"));
        if (usuario == null)
            ;//Não carrega o usuário, por enquanto
        else
            setUsuCodigo(usuario);
        setFchDataHora(rs.getTimestamp("FchDataHora"));
        setFchSaldo(rs.getDouble("FchSaldo"));
        
        setFlag(DB.FLAG_UPDATE);
        
        return this;
    }
    
    public boolean save() {
        switch(getFlag()) {
            case DB.FLAG_INSERT:
                return insert();
            case DB.FLAG_UPDATE:
                return update();
        }
        return false;
    }
    
    public boolean insert() {
        setFchCodigo(Sequencial.getNextSequencial(FechamentoCaixa.class.getSimpleName() + "_" + getCntCodigo().getCntCodigo()));
        try {
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(this);
            sql += "(CntCodigo, FchCodigo, UsuCodigo, FchDataHora, FchSaldo)";
            sql += "VALUES (?, ?, ?, ?, ?)";
            
            int UsuCodigo = 0;
            if (getUsuCodigo() != null)
                UsuCodigo = getUsuCodigo().getUsuCodigo();
            
            DB.executeUpdate(sql, new Object[]{getCntCodigo().getCntCodigo(), getFchCodigo(), UsuCodigo, getFchDataHora(), getFchSaldo()});
            
            setFlag(DB.FLAG_UPDATE);
            
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_INSERCAO, "Falha ao inserir o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public boolean update() {
        try {
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " SET UsuCodigo = ?, FchDataHora = ?, FchSaldo = ?";
            sql += " WHERE CntCodigo = ?, FchCodigo = ?";
            
            int UsuCodigo = 0;
            if (getUsuCodigo() != null)
                UsuCodigo = getUsuCodigo().getUsuCodigo();
            
            DB.executeUpdate(sql, new Object[]{UsuCodigo, getFchDataHora(), getFchSaldo(), getCntCodigo().getCntCodigo(), getFchCodigo()});
            
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_ALTERACAO, "Falha ao alterar o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public static FechamentoCaixa getLast(ContaCapital contaCapital) {
        FechamentoCaixa fchCx = null;
        if (contaCapital != null) {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(FechamentoCaixa.class);
            sql += " WHERE CntCodigo = ?";
            sql += " ORDER BY FchDataHora DESC";
            sql += " LIMIT 1";
            try {
                ResultSet rs = DB.executeQuery(sql, new Object[]{contaCapital.getCntCodigo()});
                if (rs.next()) {
                    fchCx = new FechamentoCaixa().fill(rs, contaCapital);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FechamentoCaixa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fchCx;
    }
    
    public static ResultSet getFechamentosReport(java.sql.Timestamp from, java.sql.Timestamp to) {
        try {
            String sql = "SELECT cc.CntNome as Conta, fc.FchDataHora as DataHora,  CONCAT('R$ ', FORMAT(COALESCE(fc.FchSaldo,0), 2)) AS Saldo ";
            sql += " FROM " + reflection.ReflectionUtil.getDBTableName(FechamentoCaixa.class) + " fc";
            sql += " INNER JOIN " + reflection.ReflectionUtil.getDBTableName(ContaCapital.class) + " cc ON (fc.CntCodigo = cc.CntCodigo)";
            ArrayList<java.sql.Timestamp> parms = new ArrayList<>();
            if (from != null && to != null) {
                sql += " WHERE fc.FchDataHora BETWEEN ? AND ?";
                parms.add(from);
                parms.add(to);
            } else if (from != null) {
                sql += " WHERE fc.FchDataHora >= ?";
                parms.add(from);
            } else if (to != null) {
                sql += " WHERE fc.FchDataHora <= ?";
                parms.add(to);
            }
            sql += " ORDER BY fc.FchDataHora ASC";
            
            return DB.executeQuery(sql, parms.toArray(new java.sql.Timestamp[parms.size()]));
        } catch (SQLException ex) {
            Logger.getLogger(FechamentoCaixa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
