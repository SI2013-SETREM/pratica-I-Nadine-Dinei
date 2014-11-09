
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import util.DB;
import util.Encryption;
import util.field.FilterField;

/**
 *  Model de Usuário
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Usuario extends ModelTemplate {
    public static final int IDX_VISUALIZAR = 0;
    public static final int IDX_INSERIR = 1;
    public static final int IDX_ALTERAR = 2;
    public static final int IDX_EXCLUIR = 3;
    
    public static Usuario UsuLogado;
    
    private int UsuCodigo;
    private String UsuLogin;
    private String UsuHash;
    private String UsuSalt;
    private int UsuIterations;
    private boolean UsuAtivo;
    private Pessoa PesCodigo;
    private NivelAcesso NivAceCodigo;
    private java.sql.Timestamp UsuDtaDelecao;
    
    private String flag = DB.FLAG_INSERT;
    
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Usuário";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Usuários";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "USUARIOS";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "user.png";
    /**
     * @see model.ModelTemplate#softDelete
     */
    public static String softDelete = "UsuDtaDelecao";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"UsuCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Nome", "Pessoa.PesNome"},
    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {};
    
    public Usuario() {
    }

    public int getUsuCodigo() {
        return UsuCodigo;
    }
    public void setUsuCodigo(int UsuCodigo) {
        this.UsuCodigo = UsuCodigo;
    }

    public String getUsuLogin() {
        return UsuLogin;
    }
    public void setUsuLogin(String UsuLogin) {
        this.UsuLogin = UsuLogin;
    }

    public String getUsuHash() {
        return UsuHash;
    }
    public void setUsuHash(String UsuHash) {
        this.UsuHash = UsuHash;
    }

    public String getUsuSalt() {
        return UsuSalt;
    }
    public void setUsuSalt(String UsuSalt) {
        this.UsuSalt = UsuSalt;
    }

    public int getUsuIterations() {
        return UsuIterations;
    }
    public void setUsuIterations(int UsuIterations) {
        this.UsuIterations = UsuIterations;
    }

    public boolean getUsuAtivo() {
        return UsuAtivo;
    }
    public void setUsuAtivo(boolean UsuAtivo) {
        this.UsuAtivo = UsuAtivo;
    }

    public Pessoa getPesCodigo() {
        return PesCodigo;
    }
    public void setPesCodigo(Pessoa PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public NivelAcesso getNivAceCodigo() {
        return NivAceCodigo;
    }
    public void setNivAceCodigo(NivelAcesso NivAceCodigo) {
        this.NivAceCodigo = NivAceCodigo;
    }

    public Timestamp getUsuDtaDelecao() {
        return UsuDtaDelecao;
    }
    public void setUsuDtaDelecao(Timestamp UsuDtaDelecao) {
        this.UsuDtaDelecao = UsuDtaDelecao;
    }
    
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    
    public boolean load(int UsuCodigo) {
        try {
            String sql = "SELECT * FROM usuario"
                    + " WHERE UsuAtivo"
                    + " AND UsuDtaDelecao IS NULL"
                    + " AND UsuCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Integer[]{UsuCodigo});
            if (rs.next()) {
                this.fill(rs);
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar o usuário [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public static Usuario login(String UsuLogin, String UsuSenha) {
        try {
            String sql = "SELECT * FROM usuario"
                    + " WHERE UsuAtivo"
                    + " AND UsuDtaDelecao IS NULL"
                    + " AND UsuLogin = ?";
            System.out.println(sql);
            ResultSet rs = DB.executeQuery(sql, new String[]{UsuLogin});
            while (rs.next()) {
                if (Encryption.validate(UsuSenha, rs.getString("UsuHash"), rs.getString("UsuSalt"), rs.getInt("UsuIterations"))) {
                    UsuLogado = new Usuario().fill(rs);
                    Log.log(fncNome, Log.INT_OUTRA, "Usuário logado [" + UsuLogado.getUsuCodigo() + " - " + UsuLogado.getUsuLogin() + "]", Log.NV_INFO);
                }
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha no login [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return UsuLogado;
    }
    
    private Usuario fill(ResultSet rs) throws SQLException {
        this.setUsuCodigo(rs.getInt("UsuCodigo"));
        this.setUsuLogin(rs.getString("UsuLogin"));
        this.setUsuHash(rs.getString("UsuHash"));
        this.setUsuSalt(rs.getString("UsuSalt"));
        this.setUsuIterations(rs.getInt("UsuIterations"));
        this.setUsuAtivo(rs.getInt("UsuAtivo") == 1);
        
        Pessoa pessoa = new Pessoa();
        if (pessoa.load(rs.getInt("PesCodigo")))
            this.setPesCodigo(pessoa);
        
        NivelAcesso nivelacesso = new NivelAcesso();
        if (nivelacesso.load(rs.getInt("NivAceCodigo")))
            this.setNivAceCodigo(nivelacesso);
        
        this.setUsuDtaDelecao(rs.getTimestamp("UsuDtaDelecao"));
        this.setFlag(DB.FLAG_UPDATE);
        return this;
    }
    
    public boolean[] verificaAcesso(String FncNome) {
        boolean[] acessos = new boolean[]{false, false, false, false};
        try {
            String sql = "SELECT * FROM usuariofuncionalidade"
                    + " WHERE UsuCodigo = ?"
                    + " AND FncNome = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{this.getUsuCodigo(), FncNome});
            if (rs.next()) {
                acessos[IDX_VISUALIZAR] = rs.getInt("UsuFncVisualizar") == 1;
                acessos[IDX_INSERIR]    = rs.getInt("UsuFncInserir") == 1;
                acessos[IDX_ALTERAR]    = rs.getInt("UsuFncAlterar") == 1;
                acessos[IDX_EXCLUIR]    = rs.getInt("UsuFncExcluir") == 1;
            } else {
                sql = "SELECT * FROM nivelacessofuncionalidade"
                    + " WHERE NivAceCodigo = ?"
                    + " AND FncNome = ?";
                rs = DB.executeQuery(sql, new Object[]{this.getNivAceCodigo().getNivAceCodigo(), FncNome});
                if (rs.next()) {
                    acessos[IDX_VISUALIZAR] = rs.getInt("NivAceFncVisualizar") == 1;
                    acessos[IDX_INSERIR]    = rs.getInt("NivAceFncInserir") == 1;
                    acessos[IDX_ALTERAR]    = rs.getInt("NivAceFncAlterar") == 1;
                    acessos[IDX_EXCLUIR]    = rs.getInt("NivAceFncExcluir") == 1;
                }
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao verificar os acessos do usuário [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return acessos;
    }
    
}
