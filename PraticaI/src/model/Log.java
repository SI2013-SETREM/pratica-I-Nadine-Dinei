
package model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;
import util.field.FilterField;

/**
 *  Model de Logs
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Log extends ModelTemplate {
    
    public static final int NV_INFO = 1;
    public static final int NV_ALERTA = 2;
    public static final int NV_ERRO = 3;
    
    public static final char INT_INSERCAO = 'I';
    public static final char INT_EXCLUSAO = 'E';
    public static final char INT_ALTERACAO = 'A';
    public static final char INT_OUTRA = 'Z';
    
    private int LogCodigo;
    private String FncNome;
    private Usuario UsuCodigo;
    private java.sql.Timestamp LogDtaHora;
    private char LogInteracao;
    private String LogDescricao;
    private int LogNivel;
    
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Log";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Logs";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "LOGS";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "logs.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"LogCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Data", "LogDtaHora", 85},
        {"Funcionalidade", "FncNome"},
//        {"Usuário", "Usuario.Pessoa.PesNome"},
        {"Interação", "LogInteracao"},
        {"Descrição", "LogDescricao"},
        {"Nível", "LogNivel"},
    };
    /**
     * @see model.ModelTemplate#orderBy
     */
    public static String orderBy = "LogDtaHora DESC";
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
//        new FilterFieldDynamicCombo("CntCodigo", "Conta de Capital", 150, ContaCapital.class, "CntNome", null, null, ""),
//        new FilterFieldDynamicCombo("PlnCodigo", "Plano de Contas", 150, PlanoContas.class, "PlnNome", null, null, ""),
//        new FilterFieldText("LanDescricao", "Descrição", 150),
    };
    /**
     * @see model.ModelTemplate#allowInsert
     */
    public static boolean allowInsert = false;
    /**
     * @see model.ModelTemplate#allowUpdate
     */
    public static boolean allowUpdate = false;
    /**
     * @see model.ModelTemplate#allowDelete
     */
    public static boolean allowDelete = false;

    public Log() {
    }

    public int getLogCodigo() {
        return LogCodigo;
    }
    public void setLogCodigo(int LogCodigo) {
        this.LogCodigo = LogCodigo;
    }

    public String getFncNome() {
        return FncNome;
    }
    public void setFncNome(String FncNome) {
        this.FncNome = FncNome;
    }

    public Usuario getUsuCodigo() {
        return UsuCodigo;
    }
    public void setUsuCodigo(Usuario UsuCodigo) {
        this.UsuCodigo = UsuCodigo;
    }

    public Timestamp getLogDtaHora() {
        return LogDtaHora;
    }
    public void setLogDtaHora(Timestamp LogDtaHora) {
        this.LogDtaHora = LogDtaHora;
    }

    public char getLogInteracao() {
        return LogInteracao;
    }
    public void setLogInteracao(char LogInteracao) {
        this.LogInteracao = LogInteracao;
    }

    public String getLogDescricao() {
        return LogDescricao;
    }
    public void setLogDescricao(String LogDescricao) {
        this.LogDescricao = LogDescricao;
    }

    public int getLogNivel() {
        return LogNivel;
    }
    public void setLogNivel(int LogNivel) {
        this.LogNivel = LogNivel;
    }
    
    public boolean insert() {
        this.setLogCodigo(Sequencial.getNextSequencial(Log.class));
        try {
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(Log.class);
            sql += "(LogCodigo, FncNome, UsuCodigo, LogDtaHora, LogInteracao, LogDescricao, LogNivel)";
            sql += " VALUES(?, ?, ?, ?, ?, ?, ?)";
//            int UsuCodigo = 0;
//            if (this.getUsuCodigo() != null)
//                UsuCodigo = this.getUsuCodigo().getUsuCodigo();
            
            util.Util.debug("LOG" 
                + "(LogCodigo, FncNome, UsuCodigo, LogDtaHora, LogInteracao, LogDescricao, LogNivel)\r\n"
                + this.getLogCodigo()+", "
                + this.getFncNome()+", "
                + getUsuCodigo() + ", "
                + this.getLogDtaHora()+", "
                + this.getLogInteracao()+", "
                + this.getLogDescricao()+", "
                + this.getLogNivel());
            
            DB.executeUpdate(sql, new Object[]{
                this.getLogCodigo(), 
                this.getFncNome(), 
                (getUsuCodigo() != null ? getUsuCodigo().getUsuCodigo() : null), 
                this.getLogDtaHora(), 
                this.getLogInteracao(), 
                this.getLogDescricao(), 
                this.getLogNivel()});
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Lancamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void log(String FncNome, char LogInteracao, String LogDescricao, int LogNivel) {
        Log l = new Log();
        l.setFncNome(FncNome);
        l.setUsuCodigo(Usuario.UsuLogado);
        l.setLogDtaHora(util.Util.getNow());
        l.setLogInteracao(LogInteracao);
        l.setLogDescricao(LogDescricao);
        l.setLogNivel(LogNivel);
        l.insert();
    }
    
    
}
