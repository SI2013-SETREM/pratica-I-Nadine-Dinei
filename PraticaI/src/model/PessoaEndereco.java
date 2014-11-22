/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.DB;

/**
 *
 * @author Nadine
 */
public class PessoaEndereco extends ModelTemplate {

    private Pessoa PesCodigo;
    private int PesEndCodigo;
    private String PesEndDescricao;
    private String PesEndCep;
    private Pais PaiCodigo;
    private Estado EstSigla;
    private Cidade CidCodigo;
    private String PesEndEndereco;
    private String PesEndComplemento;
    private String PesEndNumero;
    private String PesEndBairro;

    private String flag = DB.FLAG_INSERT;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Endereço";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Endereços";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "googleMap.png";
    /**
     * @see model.ModelTemplate#softDelete
     */
    public static String softDelete = "";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"Pessoa.PesCodigo", "PesEndCodigo"};

    public Pessoa getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(Pessoa PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public int getPesEndCodigo() {
        return PesEndCodigo;
    }

    public void setPesEndCodigo(int PesEndCodigo) {
        this.PesEndCodigo = PesEndCodigo;
    }

    public String getPesEndDescricao() {
        return PesEndDescricao;
    }

    public void setPesEndDescricao(String PesEndDescricao) {
        this.PesEndDescricao = PesEndDescricao;
    }

    public String getPesEndCep() {
        return PesEndCep;
    }

    public void setPesEndCep(String PesEndCep) {
        this.PesEndCep = PesEndCep;
    }

    public Pais getPaiCodigo() {
        return PaiCodigo;
    }

    public void setPaiCodigo(Pais PaiCodigo) {
        this.PaiCodigo = PaiCodigo;
    }

    public Estado getEstSigla() {
        return EstSigla;
    }

    public void setEstSigla(Estado EstSigla) {
        this.EstSigla = EstSigla;
    }

    public Cidade getCidCodigo() {
        return CidCodigo;
    }

    public void setCidCodigo(Cidade CidCodigo) {
        this.CidCodigo = CidCodigo;
    }

    public String getPesEndEndereco() {
        return PesEndEndereco;
    }

    public void setPesEndEndereco(String PesEndEndereco) {
        this.PesEndEndereco = PesEndEndereco;
    }

    public String getPesEndComplemento() {
        return PesEndComplemento;
    }

    public void setPesEndComplemento(String PesEndComplemento) {
        this.PesEndComplemento = PesEndComplemento;
    }

    public String getPesEndNumero() {
        return PesEndNumero;
    }

    public void setPesEndNumero(String PesEndNumero) {
        this.PesEndNumero = PesEndNumero;
    }

    public String getPesEndBairro() {
        return PesEndBairro;
    }

    public void setPesEndBairro(String PesEndBairro) {
        this.PesEndBairro = PesEndBairro;
    }
    
    public boolean insert() {
        try {
            setPesEndCodigo(Sequencial.getNextSequencial(PessoaEndereco.class.getName() + "_" + getPesCodigo().getPesCodigo()));
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " (PesCodigo, PesEndCodigo, PesEndDescricao, PesEndCep, PaiCodigo, EstSigla, CidCodigo, PesEndEndereco, PesEndComplemento, PesEndNumero, PesEndBairro)";
            sql += " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            DB.executeUpdate(sql, new Object[] {
                getPesCodigo().getPesCodigo(),
                getPesEndCodigo(),
                getPesEndDescricao(),
                getPesEndCep(),
                getPaiCodigo().getPaiCodigo(),
                getEstSigla().getEstSigla(),
                getCidCodigo().getCidCodigo(),
                getPesEndEndereco(),
                getPesEndComplemento(),
                getPesEndNumero(),
                getPesEndBairro(),
            });
            
            return true;
        } catch (SQLException ex) {
            Log.log(Pessoa.fncNome, Log.INT_INSERCAO, "Falha ao inserir o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }

    @Override
    public String toString() {
        String r = "";
        r += getPesEndEndereco();
        if (!r.equals(""))
            r += ", ";
        r += getPesEndNumero();
        if (!r.equals(""))
            r += ", ";
        r += getPesEndComplemento();
        
        return r;
    }

    
    public static ArrayList<PessoaEndereco> getAll(Pessoa PesCodigo) {
        ArrayList<PessoaEndereco> list = new ArrayList<>();
        String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(PessoaEndereco.class);
        sql += " WHERE PesCodigo = ?";
        try {
            ResultSet rs = DB.executeQuery(sql, new Object[]{PesCodigo.getPesCodigo()});
            while (rs.next()) {
                PessoaEndereco pe = new PessoaEndereco();
                pe.setPesEndCodigo(rs.getInt("PesEndCodigo"));
                pe.setPesEndDescricao(rs.getString("PesEndDescricao"));
                pe.setPesEndCep(rs.getString("PesEndCep"));
                Cidade cid = new Cidade();
                if (cid.load(rs.getInt("PaiCodigo"), rs.getString("EstSigla"), rs.getInt("CidCodigo"))) {
                    pe.setCidCodigo(cid);
                }
                pe.setPesEndEndereco(rs.getString("PesEndEndereco"));
                pe.setPesEndComplemento(rs.getString("PesEndComplemento"));
                pe.setPesEndNumero(rs.getString("PesEndNumero"));
                pe.setPesEndBairro(rs.getString("PesEndBairro"));
                list.add(pe);
            }
        } catch (SQLException ex) {
            Log.log(Pessoa.fncNome, Log.INT_OUTRA, "Falha ao buscar os " + prlTitle + " da " + Pessoa.sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return list;
    }
    
}
