package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldText;

/**
 * Classe de País
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Pais extends ModelTemplate {
    
    private int PaiCodigo;
    private String PaiAlfa2;
    private String PaiAlfa3;
    private int PaiBacenIbge;
    private int PaiISO3166;
    private String PaiNome;
    private java.sql.Timestamp PaiDtaDelecao;

    private String flag = DB.FLAG_INSERT;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "País";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Países";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "PAISES";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "locate.png";
    /**
     * @see model.ModelTemplate#softDelete
     */
    public static String softDelete = "PaiDtaDelecao";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"PaiCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Nome", "PaiNome", 300},
        {"Sigla", "PaiAlfa2",50},
    };
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("PaiNome", "Nome", 200, 200),
        new FilterFieldText("PaiAlfa2", "Sigla", 60, 2),
    };

    public Pais() {
    }
    public Pais(int PaiCodigo) {
        this.load(PaiCodigo);
    }

    public int getPaiCodigo() {
        return PaiCodigo;
    }
    public void setPaiCodigo(int PaiCodigo) {
        this.PaiCodigo = PaiCodigo;
    }

    public String getPaiAlfa2() {
        return PaiAlfa2;
    }
    public void setPaiAlfa2(String PaiAlfa2) {
        this.PaiAlfa2 = PaiAlfa2;
    }

    public String getPaiAlfa3() {
        return PaiAlfa3;
    }
    public void setPaiAlfa3(String PaiAlfa3) {
        this.PaiAlfa3 = PaiAlfa3;
    }

    public int getPaiBacenIbge() {
        return PaiBacenIbge;
    }
    public void setPaiBacenIbge(int PaiBacenIbge) {
        this.PaiBacenIbge = PaiBacenIbge;
    }

    public int getPaiISO3166() {
        return PaiISO3166;
    }
    public void setPaiISO3166(int PaiISO3166) {
        this.PaiISO3166 = PaiISO3166;
    }

    public String getPaiNome() {
        return PaiNome;
    }
    public void setPaiNome(String PaiNome) {
        this.PaiNome = PaiNome;
    }

    public Timestamp getPaiDtaDelecao() {
        return PaiDtaDelecao;
    }
    public void setPaiDtaDelecao(Timestamp PaiDtaDelecao) {
        this.PaiDtaDelecao = PaiDtaDelecao;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean load(int PaiCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(Pais.class);
            sql += " WHERE PaiCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{PaiCodigo});
            if (rs.next()) {
                this.setPaiCodigo(rs.getInt("PaiCodigo"));
                this.setPaiAlfa2(rs.getString("PaiAlfa2"));
                this.setPaiAlfa3(rs.getString("PaiAlfa3"));
                this.setPaiBacenIbge(rs.getInt("PaiBacenIbge"));
                this.setPaiISO3166(rs.getInt("PaiISO3166"));
                this.setPaiNome(rs.getString("PaiNome"));
                flag = DB.FLAG_UPDATE;
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public boolean save() {
        switch (flag) {
            case DB.FLAG_INSERT:
                return insert();
            case DB.FLAG_UPDATE:
                return update();
        }
        return false;
    }

    public boolean insert() {
        this.setPaiCodigo(Sequencial.getNextSequencial(Pais.class));
        String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(Pais.class);
        sql += " (PaiAlfa2,PaiAlfa3,PaiBacenIbge,PaiISO3166,PaiNome,PaiCodigo)";
        sql += " VALUES (?,?,?,?,?,?)";
        try {
            DB.executeUpdate(sql, new Object[]{PaiAlfa2, PaiAlfa3, PaiBacenIbge, PaiISO3166, PaiNome, PaiCodigo});
            flag = DB.FLAG_UPDATE;
            Log.log(fncNome, Log.INT_INSERCAO, "Inseriu o " + sngTitle + " " + this.getPaiCodigo()+ " - '" + this.getPaiNome()+ "'", Log.NV_INFO);
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_INSERCAO, "Falha ao inserir o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public boolean update() {
        String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(Pais.class);
        sql += " SET PaiAlfa2 = ?,";
        sql += " PaiAlfa3 = ?,";
        sql += " PaiBacenIbge = ?,";
        sql += " PaiISO3166 = ?,";
        sql += " PaiNome = ?";
        sql += " WHERE PaiCodigo = ?";
        try {
            DB.executeUpdate(sql, new Object[]{PaiAlfa2, PaiAlfa3, PaiBacenIbge, PaiISO3166, PaiNome, PaiCodigo});
            Log.log(fncNome, Log.INT_ALTERACAO, "Alterou o " + sngTitle + " " + this.getPaiCodigo()+ " - '" + this.getPaiNome() + "'", Log.NV_INFO);
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_ALTERACAO, "Falha ao alterar o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public static java.util.ArrayList<Pais> getAll() {
        java.util.ArrayList<Pais> list = new java.util.ArrayList<>();
        for (Object o : ModelTemplate.getAll(Pais.class)) {
            list.add((Pais) o);
        }
        return list;
    }
}
