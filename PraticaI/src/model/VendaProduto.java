/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import util.DB;

/**
 *
 * @author Nadine
 */
public class VendaProduto extends ModelTemplate {

    public Cliente CliCodigo;
    public Venda VenCodigo;
    public int VenPrdCodigo;
    public Produto PrdCodigo;
    public String VenPrdNome;
    public String VenPrdDescricao;
    public double VenPrdPreco;
    public float VenPrdQuantidade;

    private String flag = DB.FLAG_INSERT;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Produto da Venda";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Produtos da Venda";
    /**
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "VENDAS";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "calculatorEdit.png";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"Cliente.CliCodigo", "Venda.VenCodigo", "VenPrdCodigo"};

    /**
     * @see model.ModelTemplate#listTableFields
     */
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

    public int getVenPrdCodigo() {
        return VenPrdCodigo;
    }

    public void setVenPrdCodigo(int VenPrdCodigo) {
        this.VenPrdCodigo = VenPrdCodigo;
    }

    public Produto getPrdCodigo() {
        return PrdCodigo;
    }

    public void setPrdCodigo(Produto PrdCodigo) {
        this.PrdCodigo = PrdCodigo;
    }

    public String getVenPrdNome() {
        return VenPrdNome;
    }

    public void setVenPrdNome(String VenPrdNome) {
        this.VenPrdNome = VenPrdNome;
    }

    public String getVenPrdDescricao() {
        return VenPrdDescricao;
    }

    public void setVenPrdDescricao(String VenPrdDescricao) {
        this.VenPrdDescricao = VenPrdDescricao;
    }

    public double getVenPrdPreco() {
        return VenPrdPreco;
    }

    public void setVenPrdPreco(double VenPrdPreco) {
        this.VenPrdPreco = VenPrdPreco;
    }

    public float getVenPrdQuantidade() {
        return VenPrdQuantidade;
    }

    public void setVenPrdQuantidade(float VenPrdQuantidade) {
        this.VenPrdQuantidade = VenPrdQuantidade;
    }

    public double getVenPrdTotal() {
        return (double) (this.VenPrdPreco * this.VenPrdQuantidade);
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean load(Cliente CliCodigo, Venda VenCodigo, int PrdCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(Produto.class);
            sql += " WHERE PrdCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{PrdCodigo});
            if (rs.next()) {
                this.setPrdCodigo(new Produto(rs.getInt("PrdCodigo")));
                this.setCliCodigo(new Cliente(rs.getInt("CliCodigo")));
                this.setVenCodigo(new Venda(this.getCliCodigo(), rs.getInt("VenCodigo")));
                this.setVenPrdCodigo(rs.getInt("VenPrdCodigo"));
                this.setVenPrdDescricao(rs.getString("VenPrdDescricao"));
                this.setVenPrdNome(rs.getString("VenPrdNome"));
                this.setVenPrdPreco(rs.getDouble("VenPrdPreco"));
                this.setVenPrdQuantidade(rs.getFloat("VenPrdQuantidade"));
                this.setFlag(DB.FLAG_UPDATE);
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar o produto " + this.getPrdCodigo() + " da Venda '" + this.getVenCodigo() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
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
        try {
            this.setVenPrdCodigo(Sequencial.getNextSequencial(VendaProduto.class.getSimpleName() + "_"));
            String sql = "insert into " + reflection.ReflectionUtil.getDBTableName(this) + "  (CliCodigo, VenCodigo, VenPrdCodigo, PrdCodigo, VenPrdNome, VenPrdDescricao, VenPrdPreco, VenPrdQuantidade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            DB.executeUpdate(sql, new Object[]{CliCodigo.getCliCodigo(), VenCodigo.getVenCodigo(), VenPrdCodigo, PrdCodigo.getPrdCodigo(), VenPrdNome, VenPrdDescricao, VenPrdPreco, VenPrdQuantidade});
            flag = DB.FLAG_UPDATE;
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_INSERCAO, "Falha ao inserir o produto " + this.getPrdCodigo() + " da Venda '" + this.getVenCodigo() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public boolean update() {
        try {
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " SET PrdCodigo=?, VenPrdNome=?, VenPrdDescricao=?, VenPrdPreco=?, VenPrdQuantidade=? where CliCodigo=? and VenCodigo=? and VenPrdCodigo=?";
            DB.executeUpdate(sql, new Object[]{PrdCodigo.getPrdCodigo(), VenPrdNome, VenPrdDescricao, VenPrdPreco, VenPrdQuantidade, CliCodigo.getCliCodigo(), VenCodigo.getVenCodigo(), VenPrdCodigo,});
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_ALTERACAO, "Falha ao alterar o produto " + this.getPrdCodigo() + " da Venda '" + this.getVenCodigo() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public static java.util.ArrayList<VendaProduto> getAll(Venda VenCodigo) {
        java.util.ArrayList<VendaProduto> list = new java.util.ArrayList<>();
        try {
            String sql = "Select * from " + reflection.ReflectionUtil.getDBTableName(VendaProduto.class) + " where CliCodigo=? and VenCodigo=?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{VenCodigo.getCliCodigo().getCliCodigo(), VenCodigo.getVenCodigo()});
            while (rs.next()) {
                VendaProduto venPrd = new VendaProduto();
                venPrd.setCliCodigo(new Cliente(rs.getInt("CliCodigo")));
                venPrd.setPrdCodigo(new Produto(rs.getInt("PrdCodigo")));
                venPrd.setVenCodigo(new Venda(venPrd.getCliCodigo(), rs.getInt("VenCodigo")));
                venPrd.setVenPrdCodigo(rs.getInt("VenPrdCodigo"));
                venPrd.setVenPrdDescricao(rs.getString("VenPrdDescricao"));
                venPrd.setVenPrdNome(rs.getString("VenPrdNome"));
                venPrd.setVenPrdPreco(rs.getDouble("VenPrdPreco"));
                venPrd.setVenPrdQuantidade(rs.getFloat("VenPrdQuantidade"));
                list.add(venPrd);
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar a listagem de " + prlTitle + " " + VenCodigo.getVenCodigo() + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return list;
    }
}
