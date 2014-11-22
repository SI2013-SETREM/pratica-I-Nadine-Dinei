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
public class PessoaTelefone extends ModelTemplate {

    private Pessoa PesCodigo;
    private int PesFonCodigo;
    private String PesFonDescricao;
    private String PesFonTelefone;

    private String flag = DB.FLAG_INSERT;

    /**
     * @see model.ModelTemplate#sngTitle
     */
    public static String sngTitle = "Telefone";
    /**
     * @see model.ModelTemplate#prlTitle
     */
    public static String prlTitle = "Telefones";
    /**
     * @see model.ModelTemplate#iconTitle
     */
    public static String iconTitle = "cartaoVisitas.png";
    /**
     * @see model.ModelTemplate#softDelete
     */
    public static String softDelete = "";
    /**
     * @see model.ModelTemplate#idColumn
     */
    public static String[] idColumn = {"PesCodigo", "PesFonCodigo"};

    public Pessoa getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(Pessoa PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public int getPesFonCodigo() {
        return PesFonCodigo;
    }

    public void setPesFonCodigo(int PesFonCodigo) {
        this.PesFonCodigo = PesFonCodigo;
    }

    public String getPesFonDescricao() {
        return PesFonDescricao;
    }

    public void setPesFonDescricao(String PesFonDescricao) {
        this.PesFonDescricao = PesFonDescricao;
    }

    public String getPesFonTelefone() {
        return PesFonTelefone;
    }

    public void setPesFonTelefone(String PesFonTelefone) {
        this.PesFonTelefone = PesFonTelefone;
    }

    public static ArrayList<PessoaTelefone> getAll(Pessoa pes) {
        ArrayList<PessoaTelefone> list = new ArrayList<>();
        String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(PessoaTelefone.class);
        sql += " WHERE PesCodigo = ?";
        try {
            ResultSet rs = DB.executeQuery(sql, new Object[]{pes.getPesCodigo()});
            while (rs.next()) {
                PessoaTelefone pe = new PessoaTelefone();
                pe.setPesCodigo(pes);
                pe.setPesFonCodigo(rs.getInt("PesFonCodigo"));
                pe.setPesFonDescricao(rs.getString("PesFonDescricao"));
                pe.setPesFonTelefone(rs.getString("PesFonTelefone"));
                list.add(pe);
            }
        } catch (SQLException ex) {
           Log.log(Pessoa.fncNome, Log.INT_OUTRA, "Falha ao buscar o " + sngTitle + " [" + ex.getErrorCode()+ " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return list;
//        return (PessoaTelefone[]) list.toArray(new PessoaTelefone[list.size()]);
    }
    
    public boolean insert() {
        try {
            setPesFonCodigo(Sequencial.getNextSequencial(PessoaTelefone.class.getName() + "_" + getPesCodigo().getPesCodigo()));
            String sql = "INSERT INTO " + reflection.ReflectionUtil.getDBTableName(this);
            sql += " (PesCodigo, PesFonCodigo, PesFonDescricao, PesFonTelefone)";
            sql += " VALUES (?, ?, ?, ?)";
            
            DB.executeUpdate(sql, new Object[] {
                getPesCodigo().getPesCodigo(),
                getPesFonCodigo(),
                getPesFonDescricao(),
                getPesFonTelefone(),
            });
            
            return true;
        } catch (SQLException ex) {
            Log.log(Pessoa.fncNome, Log.INT_INSERCAO, "Falha ao inserir o " + sngTitle + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
        }
        return false;
    }
    
    

}
