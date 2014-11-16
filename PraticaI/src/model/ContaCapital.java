package model;

import java.sql.ResultSet;
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
    private int aux;
    private String CntNome;
    private String CntBncNumero;
    private String CntBncAgencia;
    private String CntBncTitular;
    private boolean CntPadrao;
    private double CntSaldo;
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
        {"Saldo", "CntSaldo"}
    };

    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("CntNome", "Descrição", 200)
    };

    public ContaCapital() {
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
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

    public static java.util.ArrayList<ContaCapital> getAll() {
        java.util.ArrayList<ContaCapital> list = new java.util.ArrayList<>();
        for (Object o : ModelTemplate.getAll(ContaCapital.class)) {
            list.add((ContaCapital) o);
        }
        return list;
    }

    public void load(int CntCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " WHERE CntCodigo=?";
            ResultSet rs;
            rs = DB.executeQuery(sql, new Object[]{CntCodigo});
            rs.next();
            this.setCntBncAgencia(rs.getString("CntBncAgencia"));
            this.setCntBncNumero(rs.getString("CntBncNumero"));
            this.setCntBncTitular(rs.getString("CntBncTitular"));
            this.setCntNome(rs.getString("CntNome"));
            this.setCntPadrao(rs.getInt("CntPadrao"));
            this.setCntSaldo(rs.getDouble("CntSaldo"));
            flag = DB.FLAG_UPDATE;
        } catch (Exception ex) {
            Logger.getLogger(ContaCapital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert() {
        try {
            int a = 0;
            if (CntPadrao == true) {
                a = 1;
            }
            this.setCntCodigo(Sequencial.getNextSequencial(ContaCapital.class));
            String sql = "Insert into " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " (CntCodigo,CntNome,CntBncNumero,CntBncAgencia,CntBncTitular,CntPadrao,CntSaldo)";
            sql += " VALUES(?,?,?,?,?,?,?)";
            DB.executeUpdate(sql, new Object[]{CntCodigo, CntNome, CntBncNumero, CntBncAgencia, CntBncTitular, a, CntSaldo});
            flag = DB.FLAG_UPDATE;
        } catch (Exception ex) {
            Logger.getLogger(ContaCapital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        try {
            int a = 0;
            if (CntPadrao == true) {
                a = 1;
            }
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(this);
            sql += "  SET CntNome=?,CntBncNumero=?,CntBncAgencia=?,CntBncTitular=?,CntPadrao=?,CntSaldo=? WHERE (CntCodigo=?)";
            DB.executeUpdate(sql, new Object[]{CntNome, CntBncNumero, CntBncAgencia, CntBncTitular, a, CntSaldo, CntCodigo});
        } catch (Exception ex) {
            Logger.getLogger(ContaCapital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean VerificaContaPadrao() {
        try {
            String sql = "SELECT CntCodigo,CntPadrao FROM contacapital where CntPadrao <>0;";
            ResultSet rs = DB.executeQuery(sql);
            if (rs.next()) {
                //if (rs.getInt("CntPadrao") != 0 && rs.getObject("CntPadrao") != null) {
                this.setAux(rs.getInt("CntCodigo"));
                return true;
                //  }
            } else {
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(ContaCapital.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean save() {
        switch (flag) {
            case DB.FLAG_INSERT:
                insert();
                break;
            case DB.FLAG_UPDATE:
                update();
                break;
        }
        return false;
    }
}
