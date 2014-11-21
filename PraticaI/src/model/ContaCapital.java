package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldText;

/**
 *
 * @author Nadine
 */
public class ContaCapital extends ModelTemplate {

    private int CntCodigo;
    private String CntNome;
    private String CntBncNumero;
    private String CntBncAgencia;
    private String CntBncTitular;
    private boolean CntPadrao;
    private double CntSaldo;
    
    private static Integer CntPadraoCodigo;
    private static String CntPadraoNome;
    
    private String flag = DB.FLAG_INSERT;
    
    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Conta de Capital";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Contas de Capital";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "CONTAS DE CAPITAL";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "safe.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"CntCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Descrição", "CntNome"},
        {"Saldo", "CntSaldo"},
        {"Conta Padrão", "CntPadrao"},
    };

    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("CntNome", "Descrição", 200)
    };

    public ContaCapital() {
    }

    public int getCntCodigo() {
        return CntCodigo;
    }

    public void setCntCodigo(int CntCodigo) {
        this.CntCodigo = CntCodigo;
    }

    public String getCntNome() {
        return CntNome;
    }

    public void setCntNome(String CntNome) {
        this.CntNome = CntNome;
    }

    public String getCntBncNumero() {
        return CntBncNumero;
    }

    public void setCntBncNumero(String CntBncNumero) {
        this.CntBncNumero = CntBncNumero;
    }

    public String getCntBncAgencia() {
        return CntBncAgencia;
    }

    public void setCntBncAgencia(String CntBncAgencia) {
        this.CntBncAgencia = CntBncAgencia;
    }

    public String getCntBncTitular() {
        return CntBncTitular;
    }

    public void setCntBncTitular(String CntBncTitular) {
        this.CntBncTitular = CntBncTitular;
    }

    public boolean getCntPadrao() {
        return CntPadrao;
    }

    public void setCntPadrao(boolean CntPadrao) {
        this.CntPadrao = CntPadrao;
    }

    public void setCntPadrao(int CntPadrao) {
        this.CntPadrao = (CntPadrao == 1);
    }

    public double getCntSaldo() {
        return CntSaldo;
    }

    public void setCntSaldo(double CntSaldo) {
        this.CntSaldo = CntSaldo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public void debito(double valor) {
        this.setCntSaldo(this.getCntSaldo() + valor);
    }
    public void credito(double valor) {
        this.setCntSaldo(this.getCntSaldo() - valor);
    }

    public boolean load(int CntCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " WHERE CntCodigo=?";
            ResultSet rs;
            rs = DB.executeQuery(sql, new Object[]{CntCodigo});
            if (rs.next()) {
                this.setCntCodigo(rs.getInt("CntCodigo"));
                this.setCntBncAgencia(rs.getString("CntBncAgencia"));
                this.setCntBncNumero(rs.getString("CntBncNumero"));
                this.setCntBncTitular(rs.getString("CntBncTitular"));
                this.setCntNome(rs.getString("CntNome"));
                this.setCntPadrao(rs.getInt("CntPadrao"));
                this.setCntSaldo(rs.getDouble("CntSaldo"));

                if (this.getCntPadrao()) {
                    ContaCapital.setCntPadraoCodigo(this.getCntCodigo());
                    ContaCapital.setCntPadraoNome(this.getCntNome());
                }

                flag = DB.FLAG_UPDATE;
                
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar a " + sngTitle + " de código " + CntCodigo + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public boolean insert() {
        try {
            int CntPadrao = (this.CntPadrao ? 1 : 0);
            this.setCntCodigo(Sequencial.getNextSequencial(ContaCapital.class));
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " (CntCodigo, CntNome, CntBncNumero, CntBncAgencia, CntBncTitular, CntPadrao, CntSaldo)";
            sql += " VALUES(?, ?, ?, ?, ?, ?, ?)";
            DB.executeUpdate(sql, new Object[]{CntCodigo, CntNome, CntBncNumero, CntBncAgencia, CntBncTitular, CntPadrao, CntSaldo});
            
            if (this.getCntPadrao()) {
                ContaCapital.setCntPadraoCodigo(CntCodigo);
                ContaCapital.setCntPadraoNome(CntNome);
            }
            
            flag = DB.FLAG_UPDATE;
            
            Log.log(fncNome, Log.INT_INSERCAO, "Inseriu a " + sngTitle + " '" + this.getCntNome() + "'", Log.NV_INFO);
            
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_INSERCAO, "Falha ao inserir a " + sngTitle + " '" + this.getCntNome() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public boolean update() {
        try {
            int CntPadrao = (this.CntPadrao ? 1 : 0);
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " SET CntNome = ?,CntBncNumero = ?,CntBncAgencia = ?,CntBncTitular = ?,CntPadrao = ?,CntSaldo = ?";
            sql += " WHERE (CntCodigo = ?)";
            
            DB.executeUpdate(sql, new Object[]{CntNome, CntBncNumero, CntBncAgencia, CntBncTitular, CntPadrao, CntSaldo, CntCodigo});
            
            if (this.getCntPadrao()) {
                ContaCapital.setCntPadraoCodigo(CntCodigo);
                ContaCapital.setCntPadraoNome(CntNome);
            }
            
            Log.log(fncNome, Log.INT_ALTERACAO, "Alterou a " + sngTitle + " '" + this.getCntNome() + "'", Log.NV_INFO);
            
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_ALTERACAO, "Falha ao alterar a " + sngTitle + " '" + this.getCntNome() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

//    public boolean verificaContaPadrao(int CntCodigo) {
//        try {
//            String sql = "SELECT CntCodigo, CntPadrao FROM contacapital WHERE NOT CntPadrao";
//            ResultSet rs = DB.executeQuery(sql);
//            if (rs.next()) {
//                //if (rs.getInt("CntPadrao") != 0 && rs.getObject("CntPadrao") != null) {
////                this.setCntPadraoCodigo(rs.getInt("CntCodigo"));
//                return true;
//                //  }
//            } else {
//                return false;
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(ContaCapital.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }

    public boolean save() {
        switch (flag) {
            case DB.FLAG_INSERT:
                return insert();
            case DB.FLAG_UPDATE:
                return update();
        }
        return false;
    }
    
    public static int getCntPadraoCodigo() {
        if (CntPadraoCodigo == null) {
            try {
                String sql = "SELECT CntCodigo,CntNome FROM contacapital WHERE CntPadrao";
                ResultSet rs = DB.executeQuery(sql);
                if (rs.next()) {
                    setCntPadraoCodigo(rs.getInt("CntCodigo"));
                    setCntPadraoNome(rs.getString("CntNome"));
                }
            } catch (Exception ex) {
                Log.log(ContaCapital.fncNome, Log.INT_OUTRA, "Falha ao buscar a conta de capital padrão", Log.NV_ERRO);
                Logger.getLogger(ContaCapital.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return CntPadraoCodigo;
    }
    
    private static void setCntPadraoCodigo(int CntCodigo) {
        CntPadraoCodigo = CntCodigo;
    }
    
    public static String getCntPadraoNome() {
        getCntPadraoCodigo(); //Para buscar do banco, caso não esteja setado
        return CntPadraoNome;
    }
    private static void setCntPadraoNome(String CntNome) {
        CntPadraoNome = CntNome;
    }

    public static java.util.ArrayList<ContaCapital> getAll() {
        java.util.ArrayList<ContaCapital> list = new java.util.ArrayList<>();
        for (Object o : ModelTemplate.getAll(ContaCapital.class)) {
            list.add((ContaCapital) o);
        }
        return list;
    }

    @Override
    public String toString() {
        return getCntNome();
    }

    
    
}
