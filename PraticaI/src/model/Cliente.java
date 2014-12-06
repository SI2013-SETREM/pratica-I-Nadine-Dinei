package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import util.DB;
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
    private java.sql.Timestamp CliDtaDelecao;
    
    private String flag = DB.FLAG_INSERT;
    
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
        {"CPF/CNPJ", "Pessoa.PesCPFCNPJ"}
    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("Pessoa.PesNome", "Nome", 200),
    };

    public Cliente() {
    }
    public Cliente (int CliCodigo){
        this.load(CliCodigo);
    }
    public Cliente (Pessoa pessoa){
        this.load(pessoa);
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

    public java.sql.Timestamp getCliDtaDelecao() {
        return CliDtaDelecao;
    }

    public void setCliDtaDelecao(java.sql.Timestamp CliDtaDelecao) {
        this.CliDtaDelecao = CliDtaDelecao;
    }
    
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public boolean load(Pessoa pessoa) {
        Pessoa.fncNome = fncNome;
        this.setPesCodigo(pessoa);
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this) + " c";
            sql += " WHERE c.PesCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[] {pessoa.getPesCodigo()});
            if (rs.next()) {
                this.setCliCodigo(rs.getInt("CliCodigo"));
                this.setCliDtaDelecao((java.sql.Timestamp) DB.getColumnByType(rs, "CliDtaDelecao", java.sql.Timestamp.class));
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    public boolean load(int CliCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this) + " c";
            sql += " INNER JOIN " + reflection.ReflectionUtil.getDBTableName(Pessoa.class) + " p ON(c.PesCodigo = p.PesCodigo)";
            sql += " WHERE c.CliCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[] {CliCodigo});
            if (rs.next()) {
                this.fill(rs, true);
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public Cliente fill(ResultSet rs, boolean joinPessoa) throws SQLException {
        this.setCliCodigo(rs.getInt("CliCodigo"));
        this.setCliDtaDelecao((java.sql.Timestamp) DB.getColumnByType(rs, "CliDtaDelecao", java.sql.Timestamp.class));
        Pessoa pessoa = new Pessoa();
        if (joinPessoa) {
            pessoa.fill(rs);
        } else {
            pessoa.load(rs.getInt("PesCodigo"));
        }
        this.setPesCodigo(pessoa);
        
        this.setFlag(DB.FLAG_UPDATE);
        
        return this;
    }
    
    public boolean save() {
        Pessoa.fncNome = fncNome; //Pessoa não tem funcionalidade, então sempre sobrescrevemos antes de trabalhar com ela
        getPesCodigo().setPesIsCliente(true);
        getPesCodigo().save(); //Salva os dados alterados na pessoa
        
        switch (flag) {
            case DB.FLAG_INSERT:
                return insert();
            case DB.FLAG_UPDATE:
                return update();
        }
        return false;
    }
    
    public boolean insert() {
        try {
            this.setCliCodigo(Sequencial.getNextSequencial(Cliente.class));
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " (CliCodigo, PesCodigo)";
            sql += " VALUES(?, ?)";
            
            DB.executeUpdate(sql, new Object[]{
                getCliCodigo(),
                getPesCodigo().getPesCodigo(),
            });
            
            flag = DB.FLAG_UPDATE;
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_INSERCAO, "Falha ao inserir o " + sngTitle + " '" + getPesCodigo().getPesNome() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public boolean update() {
        try {
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " SET PesCodigo = ?";
            sql += " WHERE CliCodigo = ?";
            
            DB.executeUpdate(sql, new Object[]{
                getPesCodigo().getPesCodigo(),
                getCliCodigo(),
            });
            
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_ALTERACAO, "Falha ao alterar o " + sngTitle + " '" + getPesCodigo().getPesNome() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
}
