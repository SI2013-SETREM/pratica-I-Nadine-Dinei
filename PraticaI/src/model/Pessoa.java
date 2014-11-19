package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import util.DB;
import util.field.FilterField;
import util.field.FilterFieldText;

/**
 *
 * @author Nadine
 */
public class Pessoa extends ModelTemplate {
    
    public static final int SEXO_NAOSABE = 0;
    public static final int SEXO_MASCULINO = 1;
    public static final int SEXO_FEMININO = 2;
    public static final int SEXO_NAOESPECIFICADO = 9;
    
    private int PesCodigo;
    private String PesNome;
    private PessoaEmail PesEmlCodigo;
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
     * @see model.ModelTemplate#fncNome
     */
    public static String fncNome = "PESSOAS";
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

    public PessoaEmail getPesEmlCodigo() {
        return PesEmlCodigo;
    }

    public void setPesEmlCodigo(PessoaEmail PesEmlCodigo) {
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
            System.out.println(sql + PesCodigo);
            ResultSet rs = DB.executeQuery(sql, new Object[]{PesCodigo});
            if (rs.next()) {
                this.setPesCPFCNPJ(rs.getString("PesCPFCNPJ"));
                this.setPesDtaNascimento(rs.getDate("PesDtaNascimento"));
                int PesEmlCodigo = rs.getInt("PesEmlCodigo");
                if (PesEmlCodigo != 0) {
                    PessoaEmail pEm = new PessoaEmail();
                    pEm.load(PesCodigo, PesEmlCodigo);
                    this.setPesEmlCodigo(pEm);
                }
                this.setPesIsCliente(rs.getInt("PesIsCliente"));
                this.setPesIsFornecedor(rs.getInt("PesIsFornecedor"));
                this.setPesIsFuncionario(rs.getInt("PesIsFuncionario"));
                this.setPesIsUsuario(rs.getInt("PesIsUsuario"));
                this.setPesNome(rs.getString("PesNome"));
                this.setPesRG(rs.getString("PesRG"));
                this.setPesSexo(rs.getInt("PesSexo"));
                this.setPesTipoPessoa(rs.getString("PesTipoPessoa"));
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar a " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public static Pessoa[] listBusca() {
        return listBusca(true, true, true, true);
    }
    public static Pessoa[] listBusca(boolean isFuncionario, boolean isCliente, boolean isUsuario, boolean isFornecedor) {
        ArrayList<Pessoa> list = new ArrayList<>();
        String sql = "SELECT PesCodigo, PesNome, PesCPFCNPJ FROM pessoa";
        if (isFuncionario || isCliente || isUsuario || isFornecedor) {
            sql += " WHERE ";
            boolean isWhere = true;
            if (isFuncionario) {
                if (!isWhere)
                    sql += " OR ";
                sql += "PesIsFuncionario";
                isWhere = false;
            }
            if (isCliente) {
                if (!isWhere)
                    sql += " OR ";
                sql += "PesIsCliente";
                isWhere = false;
            }
            if (isUsuario) {
                if (!isWhere)
                    sql += " OR ";
                sql += "PesIsUsuario";
                isWhere = false;
            }
            if (isFornecedor) {
                if (!isWhere)
                    sql += " OR ";
                sql += "PesIsFornecedor";
                isWhere = false;
            }
        } else {
            sql += " WHERE 1=2"; //Não traz nada, não selecionou nenhum
        }
        System.out.println(sql);
        try {
            ResultSet rs = DB.executeQuery(sql);
            while (rs.next()) {
                Pessoa p = new Pessoa();
                p.setPesCodigo(rs.getInt("PesCodigo"));
                p.setPesNome(rs.getString("PesNome"));
                p.setPesCPFCNPJ(rs.getString("PesCPFCNPJ"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar as " + prlTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return (Pessoa[]) list.toArray(new Pessoa[list.size()]);
    }

}
