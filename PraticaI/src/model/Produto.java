package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldText;

/**
 * Model de Produtos
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Produto extends ModelTemplate {

    private int PrdCodigo;
    private String PrdNome;
    private String PrdDescricao;
    private double PrdPreco;
    private java.sql.Timestamp PrdDtaDelecao;

    private String flag = DB.FLAG_INSERT;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Produto";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Produtos";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "PRODUTOS";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "produtos.png";
    /**
     * @see model.ModelTemplate#softDelete
     */
    public static String softDelete = "PrdDtaDelecao";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"PrdCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Nome", "PrdNome"},
        {"Descrição", "PrdDescricao"},
        {"Preço", "PrdPreco"},};
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("PrdNome", "Nome", 200, 200),
        new FilterFieldText("PrdDescricao", "Descrição", 200),};

    public Produto() {
    }

    public Produto(int PrdCodigo) {
        this.load(PrdCodigo);
    }

    public int getPrdCodigo() {
        return PrdCodigo;
    }

    public void setPrdCodigo(int PrdCodigo) {
        this.PrdCodigo = PrdCodigo;
    }

    public String getPrdNome() {
        return PrdNome;
    }

    public void setPrdNome(String PrdNome) {
        this.PrdNome = PrdNome;
    }

    public String getPrdDescricao() {
        return PrdDescricao;
    }

    public void setPrdDescricao(String PrdDescricao) {
        this.PrdDescricao = PrdDescricao;
    }

    public double getPrdPreco() {
        return PrdPreco;
    }

    public void setPrdPreco(double PrdPreco) {
        this.PrdPreco = PrdPreco;
    }

    public Timestamp getPrdDtaDelecao() {
        return PrdDtaDelecao;
    }

    public void setPrdDtaDelecao(Timestamp PrdDtaDelecao) {
        this.PrdDtaDelecao = PrdDtaDelecao;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean load(int PrdCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(Produto.class);
            sql += " WHERE PrdCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{PrdCodigo});
            if (rs.next()) {
                this.setPrdCodigo(rs.getInt("PrdCodigo"));
                this.setPrdNome(rs.getString("PrdNome"));
                this.setPrdDescricao(rs.getString("PrdDescricao"));
                this.setPrdPreco(rs.getDouble("PrdPreco"));
                this.setPrdDtaDelecao(rs.getTimestamp("PrdDtaDelecao"));
                this.setFlag(DB.FLAG_UPDATE);
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar o produto " + this.getPrdCodigo() + " - '" + this.getPrdNome() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
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
        this.setPrdCodigo(Sequencial.getNextSequencial(Produto.class));
        String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(Produto.class);
        sql += " (PrdCodigo, PrdNome, PrdDescricao, PrdPreco)";
        sql += " VALUES (?,?,?,?)";
        try {
            DB.executeUpdate(sql, new Object[]{PrdCodigo, PrdNome, PrdDescricao, PrdPreco});
            this.setFlag(DB.FLAG_UPDATE);
            Log.log(fncNome, Log.INT_INSERCAO, "Inseriu o produto " + this.getPrdCodigo() + " - '" + this.getPrdNome() + "'", Log.NV_INFO);
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_INSERCAO, "Falha ao inserir o produto " + this.getPrdCodigo() + " - '" + this.getPrdNome() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public boolean update() {
        String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(Produto.class);
        sql += " SET PrdNome = ?, PrdDescricao = ?, PrdPreco = ?";
        sql += " WHERE PrdCodigo = ?";
        try {
            DB.executeUpdate(sql, new Object[]{PrdNome, PrdDescricao, PrdPreco, PrdCodigo});
            Log.log(fncNome, Log.INT_ALTERACAO, "Alterou o produto " + this.getPrdCodigo() + " - '" + this.getPrdNome() + "'", Log.NV_INFO);
            return true;
        } catch (Exception ex) {
            Log.log(fncNome, Log.INT_ALTERACAO, "Falha ao alterar o produto " + this.getPrdCodigo() + " - '" + this.getPrdNome() + "' [" + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public static Produto[] listBusca() {
        ArrayList<Produto> list = new ArrayList<>();
        try {
            String sql = "select * from produto where PrdDtaDelecao is null";
            ResultSet rs = DB.executeQuery(sql);
            while (rs.next()) {
                Produto p = new Produto();
                p.setPrdNome(rs.getString(rs.getString("PrdNome")));
                p.setPrdPreco(rs.getDouble("PrdPreco"));
                p.setPrdDescricao(rs.getString("PrdDescricao"));
                p.setPrdCodigo(rs.getInt("PrdCodigo"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar as " + prlTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return (Produto[]) list.toArray(new Produto[list.size()]);
    }
}
