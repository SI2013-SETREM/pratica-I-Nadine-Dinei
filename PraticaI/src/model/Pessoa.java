package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Timestamp;
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
    
    public static final String TIPO_FISICA = "F";
    public static final String TIPO_JURIDICA = "J";
    
    
    private int PesCodigo;
    private String PesNome;
    private PessoaEmail PesEmlCodigo;
    private int PesSexo;
    private String PesCPFCNPJ;
    private String PesRG;
    private String PesTipoPessoa;
    private Date PesDtaNascimento;
    private boolean PesIsFuncionario;
    private boolean PesIsCliente;
    private boolean PesIsUsuario;
    private boolean PesIsFornecedor;
    private Timestamp PesDtaDelecao;
    
    private PessoaEmail[] pessoaEmail = new PessoaEmail[0];
    private PessoaTelefone[] pessoaTelefone = new PessoaTelefone[0];
    private PessoaEndereco[] pessoaEndereco = new PessoaEndereco[0];
    
    private String flag = DB.FLAG_INSERT;
    
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
    public Pessoa(int PesCodigo) {
        this.load(PesCodigo);
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

    public boolean isFuncionario() {
        return PesIsFuncionario;
    }
    public boolean getPesIsFuncionario() {
        return PesIsFuncionario;
    }

    public void setPesIsFuncionario(boolean PesIsFuncionario) {
        this.PesIsFuncionario = PesIsFuncionario;
    }

    public boolean isCliente() {
        return PesIsCliente;
    }
    public boolean getPesIsCliente() {
        return PesIsCliente;
    }

    public void setPesIsCliente(boolean PesIsCliente) {
        this.PesIsCliente = PesIsCliente;
    }

    public boolean isUsuario() {
        return PesIsUsuario;
    }
    public boolean getPesIsUsuario() {
        return PesIsUsuario;
    }

    public void setPesIsUsuario(boolean PesIsUsuario) {
        this.PesIsUsuario = PesIsUsuario;
    }

    public boolean isFornecedor() {
        return PesIsFornecedor;
    }
    public boolean getPesIsFornecedor() {
        return PesIsFornecedor;
    }

    public void setPesIsFornecedor(boolean PesIsFornecedor) {
        this.PesIsFornecedor = PesIsFornecedor;
    }

    public Timestamp getPesDtaDelecao() {
        return PesDtaDelecao;
    }

    public void setPesDtaDelecao(Timestamp PesDtaDelecao) {
        this.PesDtaDelecao = PesDtaDelecao;
    }

    public PessoaEmail[] getPessoaEmail() {
        return pessoaEmail;
    }

    public void setPessoaEmail(PessoaEmail[] pessoaEmail) {
        this.pessoaEmail = pessoaEmail;
    }

    public PessoaTelefone[] getPessoaTelefone() {
        return pessoaTelefone;
    }

    public void setPessoaTelefone(PessoaTelefone[] pessoaTelefone) {
        this.pessoaTelefone = pessoaTelefone;
    }

    public PessoaEndereco[] getPessoaEndereco() {
        return pessoaEndereco;
    }

    public void setPessoaEndereco(PessoaEndereco[] pessoaEndereco) {
        this.pessoaEndereco = pessoaEndereco;
    }

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }

    
    
    public boolean load(int PesCodigo) {
        try {
            String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " WHERE PesCodigo = ?";
            
            ResultSet rs = DB.executeQuery(sql, new Object[]{PesCodigo});
            
            if (rs.next()) {
                this.fill(rs);
                return true;
            }
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_OUTRA, "Falha ao buscar a " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public Pessoa fill(ResultSet rs) throws SQLException {
        this.setPesCodigo(rs.getInt("PesCodigo"));
        this.setPesCPFCNPJ(rs.getString("PesCPFCNPJ"));
        this.setPesDtaNascimento(rs.getDate("PesDtaNascimento"));
        int PesEmlCodigo = rs.getInt("PesEmlCodigo");
        if (PesEmlCodigo != 0) {
            PessoaEmail pEm = new PessoaEmail();
            pEm.load(this.getPesCodigo(), PesEmlCodigo);
            this.setPesEmlCodigo(pEm);
        }
        this.setPesIsCliente(rs.getBoolean("PesIsCliente"));
        this.setPesIsFornecedor(rs.getBoolean("PesIsFornecedor"));
        this.setPesIsFuncionario(rs.getBoolean("PesIsFuncionario"));
        this.setPesIsUsuario(rs.getBoolean("PesIsUsuario"));
        this.setPesNome(rs.getString("PesNome"));
        this.setPesRG(rs.getString("PesRG"));
        this.setPesSexo(rs.getInt("PesSexo"));
        this.setPesTipoPessoa(rs.getString("PesTipoPessoa"));
        
        this.setFlag(DB.FLAG_UPDATE);
        
        return this;
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
            this.setPesCodigo(Sequencial.getNextSequencial(Pessoa.class));
            
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " (PesCodigo, PesNome, PesEmlCodigo, PesSexo, PesCPFCNPJ, PesRG, PesTipoPessoa, PesDtaNascimento, PesIsFuncionario, PesIsCliente, PesIsUsuario, PesIsFornecedor)";
            sql += " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            DB.executeUpdate(sql, new Object[]{
                getPesCodigo(),
                getPesNome(),
                (getPesEmlCodigo() == null) ? null : getPesEmlCodigo().getPesEmlCodigo(),
                getPesSexo(),
                getPesCPFCNPJ(),
                getPesRG(),
                getPesTipoPessoa(),
                getPesDtaNascimento(),
                getPesIsFuncionario(),
                getPesIsCliente(),
                getPesIsUsuario(),
                getPesIsFornecedor(),
            });
            
            flag = DB.FLAG_UPDATE;
            
            savePessoaEnderecos();
            
            savePessoaTelefones();
            
            savePessoaEmails();
            
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_INSERCAO, "Falha ao inserir a " + sngTitle + " '" + getPesNome() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    public boolean update() {
        try {
            String sql = "UPDATE " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " SET PesNome = ?, PesEmlCodigo = ?, PesSexo = ?, PesCPFCNPJ = ?, PesRG = ?, PesTipoPessoa = ?,";
            sql += " PesDtaNascimento = ?, PesIsFuncionario = ?, PesIsCliente = ?, PesIsUsuario = ?, PesIsFornecedor = ?";
            sql += " WHERE PesCodigo = ?";
            
            DB.executeUpdate(sql, new Object[]{
                getPesNome(),
                (getPesEmlCodigo() == null) ? null : getPesEmlCodigo().getPesEmlCodigo(),
                getPesSexo(),
                getPesCPFCNPJ(),
                getPesRG(),
                getPesTipoPessoa(),
                getPesDtaNascimento(),
                getPesIsFuncionario(),
                getPesIsCliente(),
                getPesIsUsuario(),
                getPesIsFornecedor(),
                getPesCodigo(),
            });
            
            // Exclui todos os dados filhos e cadastra de novo
            sql = "DELETE FROM " + reflection.ReflectionUtil.getDBTableName(PessoaEndereco.class);
            sql += " WHERE PesCodigo = ?";
            DB.executeUpdate(sql, new Object[]{getPesCodigo()});
            savePessoaEnderecos();
            
            sql = "DELETE FROM " + reflection.ReflectionUtil.getDBTableName(PessoaTelefone.class);
            sql += " WHERE PesCodigo = ?";
            DB.executeUpdate(sql, new Object[]{getPesCodigo()});
            savePessoaTelefones();
            
            sql = "DELETE FROM " + reflection.ReflectionUtil.getDBTableName(PessoaEmail.class);
            sql += " WHERE PesCodigo = ?";
            DB.executeUpdate(sql, new Object[]{getPesCodigo()});
            savePessoaEmails();
            
            return true;
        } catch (SQLException ex) {
            Log.log(fncNome, Log.INT_ALTERACAO, "Falha ao alterar a " + sngTitle + " '" + getPesNome() + "' [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    public void savePessoaEnderecos() {
        for (PessoaEndereco pe : getPessoaEndereco()) {
            pe.setPesCodigo(this);
            pe.insert();
        }
    }
    
    public void savePessoaTelefones() {
        for (PessoaTelefone pt : getPessoaTelefone()) {
            pt.setPesCodigo(this);
            pt.insert();
        }
    }
    
    public void savePessoaEmails() {
        for (PessoaEmail pe : getPessoaEmail()) {
            pe.setPesCodigo(this);
            pe.insert();
        }
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
