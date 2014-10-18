
package model;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class Cidade {

    private Estado estado;
    private int CidCodigo;
    private String CidNome;
    
    public static String[] idColumn = {"Estado.Pais.PaiCodigo", "Estado.EstSigla", "CidCodigo"};
    
    public static String[][] listTableFields = {
        {"País", "Estado", "Nome"}, //Rótulo da coluna
        {"Estado.Pais.PaiNome", "Estado.EstNome", "CidNome"}, //Nome do campo no banco / atributo do model
    };

    public Cidade() {
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getCidCodigo() {
        return CidCodigo;
    }

    public void setCidCodigo(int CidCodigo) {
        this.CidCodigo = CidCodigo;
    }

    public String getCidNome() {
        return CidNome;
    }

    public void setCidNome(String CidNome) {
        this.CidNome = CidNome;
    }

    public static String[] getIdColumn() {
        return idColumn;
    }

    public static void setIdColumn(String[] idColumn) {
        Cidade.idColumn = idColumn;
    }

    public static String[][] getListTableFields() {
        return listTableFields;
    }

    public static void setListTableFields(String[][] listTableFields) {
        Cidade.listTableFields = listTableFields;
    }
    
    
}
