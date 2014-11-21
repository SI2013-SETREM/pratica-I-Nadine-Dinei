package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.VendaProduto.fncNome;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldDate;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Venda extends ModelTemplate {

    public static final char TIPO_VISTA = 'V';
    public static final char TIPO_PRAZO = 'P';

    private Cliente CliCodigo;
    private int VenCodigo;
    private java.sql.Date VenData;
    private double VenValor;
    private double VenDesconto;
    private double VenValorFinal;
    private char VenTipo;
    private int VenParcelas;
    private double VenEntrada;
    private double VenPedNumero;
    private VendaProduto[] VendaProduto;
    private String flag = DB.FLAG_INSERT;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Venda";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Vendas";
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
    public static String[] idColumn = {"Cliente.CliCodigo", "VenCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Data", "VenData"},
        {"Cliente", "Cliente.Pessoa.PesNome"},
        {"Valor", "VenValor"},
        {"Desconto", "VenDesconto"},
        {"Valor Final", "VenValorFinal"},};
    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldDate("VenData", "Data"),};

    public Venda() {
    }

    public Venda(Cliente cli, int Venda) {
        this.load(cli, Venda);
    }

    public Cliente getCliCodigo() {
        return CliCodigo;
    }

    public void setCliCodigo(Cliente CliCodigo) {
        this.CliCodigo = CliCodigo;
    }

    public int getVenCodigo() {
        return VenCodigo;
    }

    public void setVenCodigo(int VenCodigo) {
        this.VenCodigo = VenCodigo;
    }

    public Date getVenData() {
        return VenData;
    }

    public void setVenData(Date VenData) {
        this.VenData = VenData;
    }

    public double getVenValor() {
        return VenValor;
    }

    public void setVenValor(double VenValor) {
        this.VenValor = VenValor;
    }

    public double getVenDesconto() {
        return VenDesconto;
    }

    public void setVenDesconto(double VenDesconto) {
        this.VenDesconto = VenDesconto;
    }

    public double getVenValorFinal() {
        return VenValorFinal;
    }

    public void setVenValorFinal(double VenValorFinal) {
        this.VenValorFinal = VenValorFinal;
    }

    public char getVenTipo() {
        return VenTipo;
    }

    public void setVenTipo(char VenTipo) {
        this.VenTipo = VenTipo;
    }

    public int getVenParcelas() {
        return VenParcelas;
    }

    public void setVenParcelas(int VenParcelas) {
        this.VenParcelas = VenParcelas;
    }

    public double getVenEntrada() {
        return VenEntrada;
    }

    public void setVenEntrada(double VenEntrada) {
        this.VenEntrada = VenEntrada;
    }

    public VendaProduto[] getVendaProduto() {
        return VendaProduto;
    }

    public void setVendaProduto(VendaProduto[] VendaProduto) {
        this.VendaProduto = VendaProduto;
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
        this.setVenCodigo(Sequencial.getNextSequencial(Venda.class.getSimpleName() + "_" + this.getCliCodigo().getCliCodigo()));
        try {
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " (CliCodigo, VenCodigo, VenData, VenValor, VenDesconto, VenValorFinal, VenTipo, VenParcelas, VenEntrada, VenPedNumero)";
            sql += " VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
            DB.executeQuery(sql, new Object[]{CliCodigo.getCliCodigo(), VenCodigo, VenData, VenValor, VenDesconto, VenValorFinal, VenTipo, VenParcelas, VenEntrada, VenPedNumero});

            for (VendaProduto venPrd : this.getVendaProduto()) {
                venPrd.setFlag(DB.FLAG_INSERT); //Só por garantia
                venPrd.save();
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_INSERCAO, "Falha ao inserir a " + sngTitle + " " + this.getVenCodigo() + " - " + this.getCliCodigo().getCliCodigo() + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public boolean update() {
        try {
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " VenData=?, VenValor=?, VenDesconto=?, VenValorFinal=?, VenTipo=?, VenParcelas=?, VenEntrada=?, VenPedNumero=?";
            sql += " WHERE CliCodigo = ? and VenCodigo = ?";
            DB.executeUpdate(sql, new Object[]{VenData, VenValor, VenDesconto, VenValorFinal, VenTipo, VenParcelas, VenEntrada, VenPedNumero, CliCodigo.getCliCodigo(), VenCodigo});

            // Exclui todos os produtos e cadastra de novo
            sql = "DELETE FROM " + reflection.ReflectionUtil.getDBTableName(VendaProduto.class);
            sql += " WHERE CliCodigo = ? and VenCodigo = ?";
            DB.executeUpdate(sql, new Object[]{CliCodigo.getCliCodigo(), VenCodigo});

            for (VendaProduto venPrd : this.getVendaProduto()) {
                venPrd.setFlag(DB.FLAG_INSERT); //Só por garantia
                venPrd.save();
            }
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_ALTERACAO, "Falha ao alterar a " + sngTitle + " " + this.getVenCodigo() + "_" + this.getCliCodigo().getCliCodigo() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public boolean load(Cliente CliCodigo, int VenCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this)
                    + " WHERE CliCodigo = ? AND VenCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{CliCodigo.getCliCodigo(), VenCodigo});
            if (rs.next()) {
                //   this.setCliCodigo(new Cliente(rs.getInt("CliCodigo")));
                this.setVenCodigo(rs.getInt("VenCodigo"));
                this.setVenData(rs.getDate("VenData"));
                this.setVenDesconto(rs.getDouble("VenDesconto"));
                this.setVenEntrada(rs.getDouble("VenEntrada"));
                this.setVenParcelas(rs.getInt("VenParcelas"));
                this.setVenTipo((char) rs.getObject("VenTipo"));
                this.setVenValor(rs.getDouble("VenValor"));
                this.setVenValorFinal(rs.getDouble("VenValorFinal"));
                //this.setVendaProduto(VendaProduto); -- carregar listagem de produtos
            }
            flag = DB.FLAG_UPDATE;
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar a " + sngTitle + " " + this.getVenCodigo() + "_" + this.getCliCodigo().getCliCodigo() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
}
