
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import model.Log;

/**
 *  Classe que faz o acesso ao banco de dados
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public abstract class DB {
    
    public static final String FLAG_UPDATE = "U";
    public static final String FLAG_INSERT = "I";
    public static final String FLAG_DELETE = "D";
    // Ainda não utilizadas
//    public static final String FLAG_LIST = "L";
//    public static final String FLAG_FILTER = "F";
//    public static final String FLAG_ORDER = "O";
    
    private static Connection con;
    
    public static Connection getConnection() {
        if (con == null) {
            // Sempre dá o erro Class Not Found
//            try {
//                Class.forName("org." + Config.DB_DBMS + ".jdbc.Driver");
//            } catch (ClassNotFoundException ex) {
//                System.err.println("Erro na Conexão: Class Not Found '" + ex.getMessage() + "'");
//            } catch (Exception ex) {
//                System.err.println("ERRO NA CONEXÃO: " + ex.getClass() + ", " + ex.getMessage());
//            }
            
            try {
                con = DriverManager.getConnection(getDsn(), Config.DB_USER, Config.DB_PASS);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Falha na conexão ao banco de dados: " + getDsn() + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", "Falha na conexão ao banco", JOptionPane.ERROR_MESSAGE);
            }
        }
        return con;
    }
    
    public static ResultSet executeQuery(String sql) throws SQLException {
        if (getConnection() != null) {
            Statement st = getConnection().createStatement();
            return st.executeQuery(sql);
        } else {
            return null;
        }
    }
    public static ResultSet executeQuery(String sql, Object[] parms) throws SQLException {
        if (getConnection() != null) {
            PreparedStatement st = getConnection().prepareStatement(sql);
            setParmsByType(st, parms);
            return st.executeQuery();
        } else {
            return null;
        }
    }
    
    public static int executeUpdate(String sql) throws SQLException {
        if (getConnection() != null) {
            Statement st = getConnection().createStatement();
            return st.executeUpdate(sql);
        } else {
            return 0;
        }
    }
    public static int executeUpdate(String sql, Object[] parms) throws SQLException {
        if (getConnection() != null) {
            PreparedStatement st = getConnection().prepareStatement(sql);
            setParmsByType(st, parms);
            return st.executeUpdate();
        } else {
            return 0;
        }
    }
    
    public static Object getColumnByType(ResultSet rs, String colLabel, Class<?> colType) {
        Object r = null;
        try {
            if (colType == String.class)
                r = rs.getString(colLabel);
            else if (colType == Integer.class || colType == int.class)
                r = rs.getInt(colLabel);
            else if (colType == Double.class || colType == double.class)
                r = rs.getDouble(colLabel);
            else if (colType == Float.class || colType == float.class)
                r = rs.getFloat(colLabel);
            else if (colType == java.sql.Date.class)
                r = rs.getDate(colLabel);
            else if (colType == java.sql.Time.class)
                r = rs.getTime(colLabel);
            else if (colType == java.sql.Timestamp.class)
                r = rs.getTimestamp(colLabel);
            else if (colType == Boolean.class || colType == boolean.class)
                r = rs.getBoolean(colLabel);
            else if (colType == Character.class || colType == char.class)
                r = rs.getString(colLabel);
            else if (model.ModelTemplate.class.isAssignableFrom(colType)) {
//                model.ModelTemplate mdl = (model.ModelTemplate) colType.newInstance();
//                String[] idColumn = (String[]) reflection.ReflectionUtil.getAttibute(colType, "idColumn");
                r = rs.getInt(colLabel);
//                r = null;
            }
        } catch (SQLException ex) {
            // Comentar esta linha:
            Log.log("CONFIGURACOES", Log.INT_OUTRA, "Erro tentando recuperar a coluna " + colLabel + " do tipo " + colType.getName() + " [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
            // Se der erro deve retornar null
        }
        return r;
    }
    public static String formatColumn(Object data) {
        String r = "";
        if (data instanceof String)
            r = (String) data;
        else if (data instanceof Integer)
            r = String.valueOf(data);
        else if (data instanceof Double)
            r = Util.getFormattedMoney((double) data);
            //r = String.valueOf(data);
        else if (data instanceof Float)
            r = String.valueOf(data);
        else if (data instanceof java.sql.Date)
            r = java.text.DateFormat.getDateInstance(Config.FORMAT_DATE).format((java.sql.Date) data);
        else if (data instanceof java.sql.Time)
            r = java.text.DateFormat.getTimeInstance(Config.FORMAT_TIME).format((java.sql.Time) data);
        else if (data instanceof java.sql.Timestamp)
            r = java.text.DateFormat.getDateTimeInstance(Config.FORMAT_DATE, Config.FORMAT_TIME).format((java.sql.Timestamp) data);
        else if (data instanceof Boolean)
            r = ((boolean) data) ? "Sim" : "Não"; //String.valueOf((boolean) data);
        else if (data instanceof Character)
            r = String.valueOf((char) data);
        return r;
    }
    
//    public static String getWhereByClass(Class<? extends model.ModelTemplate> cls) {
//        String sql = "";
//        
//        return sql;
//    }
    
    private static void setParmsByType(PreparedStatement st, Object[] parms) throws SQLException {
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
            else if (o instanceof java.sql.Date)
                st.setDate(count, (java.sql.Date) o);
            else if (o instanceof java.sql.Time)
                st.setTime(count, (java.sql.Time) o);
            else if (o instanceof java.sql.Timestamp)
                st.setTimestamp(count, (java.sql.Timestamp) o);
            else if (o instanceof Boolean)
                st.setBoolean(count, (boolean) o);
            else if (o instanceof Character)
                st.setString(count, String.valueOf((char) o));
            else if (o == null)
                st.setNull(count, java.sql.Types.INTEGER);
        }
    }
    
    /**
     *  Retorna o DSN da conexão
     * @return DSN
     */
    private static String getDsn() {
        String dsn = "jdbc:" + Config.DB_DBMS + "://" + Config.DB_HOST;
        if (Config.DB_PORT != 0) {
            dsn += ":" + Config.DB_PORT;
        }
        dsn += "/" + Config.DB_NAME;
//        dsn += "?useUnicode=true&characterEncoding=UTF-8";
//        dsn += "?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&connectionCollation=utf8_general_ci";
        return dsn;
    }
    
}
