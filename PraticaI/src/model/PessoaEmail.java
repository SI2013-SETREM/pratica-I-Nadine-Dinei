package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;

/**
 *
 * @author Nadine
 */
public class PessoaEmail {

    private Pessoa PesCodigo;
    private int PesEmlCodigo;
    private String PesEmlEmail;

    public PessoaEmail() {
    }

    public Pessoa getPesCodigo() {
        return PesCodigo;
    }

    public void setPesCodigo(Pessoa PesCodigo) {
        this.PesCodigo = PesCodigo;
    }

    public int getPesEmlCodigo() {
        return PesEmlCodigo;
    }

    public void setPesEmlCodigo(int PesEmlCodigo) {
        this.PesEmlCodigo = PesEmlCodigo;
    }

    public String getPesEmlEmail() {
        return PesEmlEmail;
    }

    public void setPesEmlEmail(String PesEmlEmail) {
        this.PesEmlEmail = PesEmlEmail;
    }

    public void load(int PesEmlCodigo, int PesCod) {
        try {
            String sql ="select * from pessoaemail where PesCodigo=? and PesEmlCodigo=?;";
            ResultSet rs= DB.executeQuery(sql,new Object[]{PesCod,PesEmlCodigo});
            rs.next();
            this.setPesCodigo((Pessoa) rs.getObject("PesCodigo"));
            this.setPesEmlCodigo(rs.getInt("PesEmlCodigo"));
            this.setPesEmlEmail(rs.getString("PesEmlEmail"));
        } catch (Exception e) {
            Logger.getLogger(PessoaEmail.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static PessoaEmail[] getAll(Pessoa PesCodigo) {
        ArrayList<PessoaEmail> list = new ArrayList<>();
        String sql = "SELECT * FROM " + reflection.ReflectionUtil.getDBTableName(PessoaEmail.class);
        sql += " WHERE PesCodigo = ?";
        try {
            ResultSet rs = DB.executeQuery(sql, new Object[]{PesCodigo.getPesCodigo()});
            while (rs.next()) {
                PessoaEmail pe = new PessoaEmail();
                pe.setPesCodigo(PesCodigo);
                pe.setPesEmlCodigo(rs.getInt("PesEmlCodigo"));
                pe.setPesEmlEmail(rs.getString("PesEmlEmail"));
                list.add(pe);
            }
        } catch (Exception e) {
            Logger.getLogger(PessoaEmail.class.getName()).log(Level.SEVERE, null, e);
        }
        return (PessoaEmail[]) list.toArray(new PessoaEmail[list.size()]);
    }
}
