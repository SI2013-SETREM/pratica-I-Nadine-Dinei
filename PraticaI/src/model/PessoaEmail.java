package model;

import java.sql.ResultSet;
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
        }
    }
}
