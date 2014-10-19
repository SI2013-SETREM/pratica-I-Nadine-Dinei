
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  Classe que faz o acesso ao banco de dados
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public abstract class DB {
    
    public static final String DB_DBMS  = "mysql";
    public static final String DB_HOST  = "localhost";
    public static final int    DB_PORT  = 3306;
    public static final String DB_NAME  = "pratica_i";
    public static final String DB_USER  = "root";
    public static final String DB_PASS  = "";
    
    private static Connection con;
    
    public static Connection getConnection() {
        if (con == null) {
            // Sempre dá o erro Class Not Found
//            try {
//                Class.forName("org." + DB_DBMS + ".jdbc.Driver");
//            } catch (ClassNotFoundException ex) {
//                System.err.println("Erro na Conexão: Class Not Found '" + ex.getMessage() + "'");
//            } catch (Exception ex) {
//                System.err.println("ERRO NA CONEXÃO: " + ex.getClass() + ", " + ex.getMessage());
//            }
            
            try {
                con = DriverManager.getConnection(getDsn(), DB_USER, DB_PASS);
                Util.debug(DB.class.getName() + " - " + getDsn());
            } catch (SQLException ex) {
                System.err.println("ERRO NO DSN: " + getDsn() + " - " + ex.getErrorCode() + " - " + ex.getMessage());
            }
            
//            try{
//                PreparedStatement pst = con.prepareStatement("SELECT * FROM marca");
//                ResultSet rs = pst.executeQuery();
//                while (rs.next()) {
//                    System.out.println(rs.getInt("codigo") + "\t" + rs.getString("descricao"));
//                }
//            } catch (SQLException ex) {
//                System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
//            }
        }
        return con;
    }
    
    public static ResultSet executeQuery(String sql) throws SQLException {
        Statement st = getConnection().createStatement();
        return st.executeQuery(sql);
    }
    
    public static ResultSet executeQuery(String sql, Object[] parms) throws SQLException {
        PreparedStatement st = getConnection().prepareStatement(sql);
        int count = 0;
        for (Object o : parms) {
            count++;
            // Como determinar o tipo da variável [http://stackoverflow.com/a/4964359/3136474]
            if (o instanceof String)
                st.setString(count, (String) o);
            else if (o instanceof Integer)
                st.setInt(count, (Integer) o);
            else if (o instanceof Double)
                st.setDouble(count, (Double) o);
            else if (o instanceof Float)
                st.setFloat(count, (Float) o);
        }
        return st.executeQuery();
    }
    
    public static int executeUpdate(String sql) throws SQLException {
        Statement st = getConnection().createStatement();
        return st.executeUpdate(sql);
    }
    
    /**
     *  Retorna o DSN da conexão
     * @return DSN
     */
    private static String getDsn() {
        String dsn = "jdbc:" + DB_DBMS + "://" + DB_HOST;
        if (DB_PORT != 0) {
            dsn += ":" + DB_PORT;
        }
        dsn += "/" + DB_NAME;
        dsn += "?useUnicode=true&characterEncoding=UTF-8";
//        dsn += "?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&connectionCollation=utf8_general_ci";
        return dsn;
    }
    
}
