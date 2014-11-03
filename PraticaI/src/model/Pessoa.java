package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldText;

/**
 *
 * @author Nadine
 */
public class Pessoa extends ModelTemplate {

    private int PesCodigo;
    private String PesNome;
    private PessoaEmail[] PesEmlCodigo;
    private int PesSexo;
    private String PesCPFCNPJ;
    private String PesRG;
    private String PesTipoPessoa;
    private Date PesDtaNascimento;
    private int PesIsFuncionario;
    private int PesIsCliente;
    private int PesIsUsuario;
    private int PesIsFornecedor;
    private Date PesDtaDelecao;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Pessoa";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Pessoas";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "pessoas2.png";
    /**
     * @see model.ModelTemplate#softDelete
     */
    public static String softDelete = "PesDtaDelecao";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"PesCodigo"};
    /**
     * @see model.ModelTemplate#listTableFields
     */
    public static Object[][] listTableFields = {
        {"Nome", "PesNome"},
        {"CPF/CNPJ", "PesCPFCNPJ"},
        {"Dta Nasc", "PesDtaNascimento"}
    };

    /**
     * @see model.ModelTemplate#listFilterFields
     */
    public static FilterField[] listFilterFields = {
        new FilterFieldText("PesNome", "Nome", 200),
        new FilterFieldText("PesCPFCNPJ", "CPF/CNPJ", 60),};

    public Pessoa() {
    }

    public int getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(int PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public String getPesNome() {
        return PesNome;
    }

    public void setPesNome(String PesNome) {
        this.PesNome = PesNome;
    }

    public PessoaEmail[] getPesEmlCodigo() {
        return PesEmlCodigo;
    }

    public void setPesEmlCodigo(PessoaEmail[] PesEmlCodigo) {
        this.PesEmlCodigo = PesEmlCodigo;
    }

    public int getPesSexo() {
        return PesSexo;
    }

    public void setPesSexo(int PesSexo) {
        this.PesSexo = PesSexo;
    }

    public String getPesCPFCNPJ() {
        return PesCPFCNPJ;
    }

    public void setPesCPFCNPJ(String PesCPFCNPJ) {
        this.PesCPFCNPJ = PesCPFCNPJ;
    }

    public String getPesRG() {
        return PesRG;
    }

    public void setPesRG(String PesRG) {
        this.PesRG = PesRG;
    }

    public String getPesTipoPessoa() {
        return PesTipoPessoa;
    }

    public void setPesTipoPessoa(String PesTipoPessoa) {
        this.PesTipoPessoa = PesTipoPessoa;
    }

    public Date getPesDtaNascimento() {
        return PesDtaNascimento;
    }

    public void setPesDtaNascimento(Date PesDtaNascimento) {
        this.PesDtaNascimento = PesDtaNascimento;
    }

    public int getPesIsFuncionario() {
        return PesIsFuncionario;
    }

    public void setPesIsFuncionario(int PesIsFuncionario) {
        this.PesIsFuncionario = PesIsFuncionario;
    }

    public int getPesIsCliente() {
        return PesIsCliente;
    }

    public void setPesIsCliente(int PesIsCliente) {
        this.PesIsCliente = PesIsCliente;
    }

    public int getPesIsUsuario() {
        return PesIsUsuario;
    }

    public void setPesIsUsuario(int PesIsUsuario) {
        this.PesIsUsuario = PesIsUsuario;
    }

    public int getPesIsFornecedor() {
        return PesIsFornecedor;
    }

    public void setPesIsFornecedor(int PesIsFornecedor) {
        this.PesIsFornecedor = PesIsFornecedor;
    }

    public Date getPesDtaDelecao() {
        return PesDtaDelecao;
    }

    public void setPesDtaDelecao(Date PesDtaDelecao) {
        this.PesDtaDelecao = PesDtaDelecao;
    }

    public boolean load(int PesCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " WHERE PesCodigo = ?";
            ResultSet rs = DB.executeQuery(sql, new Object[]{PesCodigo});
            if (rs.next()) {
                this.setPesCPFCNPJ(rs.getString("PesCPFCNPJ"));
                this.setPesDtaNascimento(rs.getDate("PesDtaNascimento"));
                this.setPesEmlCodigo(PessoaEmail.getAll(this));
                this.setPesIsCliente(rs.getInt("PesIsCliente"));
                this.setPesIsFornecedor(rs.getInt("PesIsFornecedor"));
                this.setPesIsFuncionario(rs.getInt("PesIsFuncionario"));
                this.setPesIsUsuario(rs.getInt(rs.getInt("PesIsUsuario")));
                this.setPesNome(rs.getString("PesNome"));
                this.setPesRG(rs.getString("PesRG"));
                this.setPesSexo(rs.getInt("PesSexo"));
                this.setPesTipoPessoa(rs.getString("PesTipoPessoa"));
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Pessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static Pessoa[] listBusca() {
        ArrayList<Pessoa> list = new ArrayList<>();
        String sql = "select "
                + "PesNome,"
                + "PesCPFCNPJ,"
                + "PesEmlCodigo,"
                + "PesCodigo,"
                + "PesSexo,"
                + "PesRG,"
                + "PesTipoPessoa,"
                + "PesDtaNascimento,"
                + "PesIsFuncionario,"
                + "PesIsCliente,"
                + "PesIsUsuario,"
                + "PesIsFornecedor from pessoa;";
        try {
            ResultSet rs = DB.executeQuery(sql);
            while (rs.next()) {
                Pessoa p = new Pessoa();
                p.setPesCPFCNPJ(rs.getString("PesCPFCNPJ"));
                p.setPesNome(rs.getString("PesNome"));
                list.add(p);
            }
        } catch (Exception ex) {

        }
        return (Pessoa[]) list.toArray(new Pessoa[list.size()]);
    }

}
