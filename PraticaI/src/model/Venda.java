package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private boolean VenEfetivada;
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
    public Venda(int CliCodigo, int VenCodigo) {
        load(CliCodigo, VenCodigo);
    }
    public Venda(Cliente cliente, int VenCodigo) {
        this.load(cliente, VenCodigo);
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

    public double getVenPedNumero() {
        return VenPedNumero;
    }

    public void setVenPedNumero(double VenPedNumero) {
        this.VenPedNumero = VenPedNumero;
    }

    public boolean isVenEfetivada() {
        return VenEfetivada;
    }

    public void setVenEfetivada(boolean VenEfetivada) {
        this.VenEfetivada = VenEfetivada;
    }
    
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    

    public boolean save() {
        switch (this.getFlag()) {
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
            
            saveVendaProdutos();
            return true;
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
            DB.executeUpdate(sql, new Object[]{getCliCodigo().getCliCodigo(), getVenCodigo()});
            
            saveVendaProdutos();
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_ALTERACAO, "Falha ao alterar a " + sngTitle + " " + this.getVenCodigo() + "_" + this.getCliCodigo().getCliCodigo() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    private void saveVendaProdutos() {
        for (VendaProduto venPrd : this.getVendaProduto()) {
            venPrd.setFlag(DB.FLAG_INSERT); //Só por garantia
            venPrd.save();
        }
    }
    
    public boolean load(int CliCodigo, int VenCodigo) {
        return this.load(new Cliente(CliCodigo), VenCodigo);
    }
    public boolean load(Cliente cliente, int VenCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this)
                    + " WHERE CliCodigo = ? AND VenCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{cliente.getCliCodigo(), VenCodigo});
            if (rs.next()) {
                fill(rs, cliente);
                //this.setVendaProduto(VendaProduto); -- carregar listagem de produtos
            }
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar a " + sngTitle + " " + this.getVenCodigo() + "_" + this.getCliCodigo().getCliCodigo() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public Venda fill(ResultSet rs) throws SQLException {
        return fill(rs, null);
    }
    public Venda fill(ResultSet rs, Cliente cliente) throws SQLException {
        if (cliente == null) {
            setCliCodigo(new Cliente(rs.getInt("CliCodigo")));
        } else {
            setCliCodigo(cliente);
        }
        setVenCodigo(rs.getInt("VenCodigo"));
        setVenData(rs.getDate("VenData"));
        setVenDesconto(rs.getDouble("VenDesconto"));
        setVenEntrada(rs.getDouble("VenEntrada"));
        setVenParcelas(rs.getInt("VenParcelas"));
        System.out.println(rs.getString("VenTipo"));
        
        String VenTipo = rs.getString("VenTipo");
        if (VenTipo != null && !VenTipo.equals(""))
            setVenTipo(VenTipo.charAt(0));
        
        setVenValor(rs.getDouble("VenValor"));
        setVenValorFinal(rs.getDouble("VenValorFinal"));
        setVenPedNumero(rs.getInt("VenPedNumero"));
        setVenEfetivada(rs.getBoolean("VenEfetivada"));
        
        setFlag(DB.FLAG_UPDATE);
        
        return this;
    }
    
    public static Venda[] listBusca() {
        return listBusca(null);
    }
    public static Venda[] listBusca(Cliente cliente) {
        ArrayList<Venda> list = new ArrayList<>();
        String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(Venda.class) + " v";
        Object[] parms = null;
        if (cliente == null) {
            sql += " INNER JOIN " + reflection.ReflectionUtil.getDBTableName(Cliente.class) + " c ON(v.CliCodigo = c.CliCodigo)";
            sql += " INNER JOIN " + reflection.ReflectionUtil.getDBTableName(Pessoa.class) + " p ON(c.PesCodigo = p.PesCodigo)";
        } else {
            sql += " WHERE v.CliCodigo = ?";
            parms = new Object[]{cliente.getCliCodigo()};
        }
        sql += " ORDER BY v.VenData ASC";
        
        try {
            ResultSet rs;
            if (parms != null) 
                rs = DB.executeQuery(sql, parms);
            else
                rs = DB.executeQuery(sql);
            while (rs.next()) {
                Venda v = new Venda();
                Cliente cli;
                if (cliente == null) {
                    cli = new Cliente().fill(rs, true);
                } else {
                    cli = cliente;
                }
                v.fill(rs, cli);
                list.add(v);
            }
        } catch(SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar as " + prlTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return (Venda[]) list.toArray(new Venda[list.size()]);
    }

    @Override
    public String toString() {
        return toString(true);
    }
    public String toString(boolean isShowCliente) {
        return util.Util.getFormattedDate(getVenData()) 
            + " - " + util.Util.getFormattedMoney(getVenValorFinal()) 
            + (isShowCliente ? " - " + getCliCodigo().getPesCodigo().getPesNome() : "")
                ;
    }
    
    
}
