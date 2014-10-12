
package reflection;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public abstract class ListJFrame extends javax.swing.JFrame {
    
    private javax.swing.JTable tbl;
    private Class<?> cls; //Ex.: Estado.class
    private ArrayList<Field> lstIdFields = new ArrayList<Field>();
    
    /**
     *  Define a classe para a qual se está fazendo a listagem
     * @param cls 
     */
    public void setClass(Class<?> cls) {
        this.cls = cls;
    }
    
    /**
     * Método que deve ser chamado pelo filho
     */
    protected void initListComponents() {
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        try {
            
            //Debug
//            System.out.println(cls.getName());
//            for (Field fld : cls.getDeclaredFields()) {
//                System.out.println("Coluna: " + fld.getName() + " - " + fld.toString());
//            }
//            for (String col : cols[0]) {
//                System.out.println("Coluna: " + col);
//            }
//            Object[] o = {new String("as"), 10, 10.5};
//            DB.executeQuery("", o);
            
            
            // Recupera informações da classe
            //Ex.: {"PaiCodigo","EstSigla"}
            String[] idColumns = (String[]) ReflectionUtil.getStaticAttibute(cls, "idColumn");
            for (String field : idColumns) {
                lstIdFields.add(cls.getDeclaredField(field)); //throw NoSuchFieldException
            }
            
            //Ex.: { {"Nome", "Sigla"},{"PaiNome", "PaiAlfa2"} }
            String[][] cols = (String[][]) ReflectionUtil.getStaticAttibute(cls, "listColumns");
//            String[][] cols = (String[][]) ReflectionUtil.getStaticMethod(cls, "getListColumns")
            
            // Recupera os dados do banco
            String sql = "";
            sql += "SELECT ";
            for (String col : cols[1]) {
                sql += col + ",";
            }
            sql = sql.substring(0, (sql.length()-1));
            sql += " FROM " + ReflectionUtil.getDBTableName(cls);
            
            System.out.println("SQL:" + sql); //Debug
            
            for (Field fld : cls.getDeclaredFields()) {
                System.out.println("Coluna: " + fld.getName() + " - " + fld.toString());
            }
            
            ResultSet rs = DB.executeQuery(sql);
            
            ArrayList<Object[]> data = new ArrayList<>(); //Dados que irão na tabela
            while (rs.next()) {
                Object[] row = new Object[cols[1].length]; //Linha da tabela
                int count = 0;
                for (String col : cols[1]) {
                    Class<?> clsType = ReflectionUtil.getAttributeType(cls, col);
                    if (clsType == String.class)
                        row[count] = rs.getString(col);
                    else if (clsType == Integer.class)
                        row[count] = rs.getInt(col);
                    else if (clsType == Double.class)
                        row[count] = rs.getDouble(col);
                    else if (clsType == Float.class)
                        row[count] = rs.getFloat(col);
                    count++;
                }
                data.add(row);
            }
            
//            System.out.println("data: {");
//            for (Object[] row : data) {
//                System.out.print("{");
//                for (Object o : row) {
//                    System.out.print(o + ",");
//                }
//                System.out.println("},");
//            }
//            System.out.println("}");
            
            Object[][] tblData = new Object[data.size()][cols[1].length];
            data.toArray(tblData);
            
            // Monta os elementos visuais
            javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
            
            tbl = new javax.swing.JTable();
            tbl.setModel(new javax.swing.table.DefaultTableModel(
                    tblData,
                    cols[0]
            ));
            scrollPane.setViewportView(tbl);
            
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addContainerGap(102, Short.MAX_VALUE))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(20, Short.MAX_VALUE)
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
            
            pack();
            
            // Needs to be called after pack() [http://stackoverflow.com/a/2442614/3136474]
            this.setLocationRelativeTo(null);
            
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(LstTemplate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LstTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
