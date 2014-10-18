
package reflection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import util.DB;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class ListJFrame extends javax.swing.JFrame {
    
    private javax.swing.JTable tblList = new javax.swing.JTable();
    private Class<?> cls; //Ex.: Estado.class
    private String mainTable;
    
    private int width = 500;
    private int height = 300;
    
//    private ArrayList<Field> lstIdFields = new ArrayList<Field>();
    
    // Atributos que vem da classe
    private FilterField[] listFilterFields;
    private String[][] listTableFields;
    
    /**
     *  Define a classe para a qual se está fazendo a listagem
     * @param cls 
     */
    public void setClass(Class<?> cls) {
        this.cls = cls;
        this.mainTable = ReflectionUtil.getDBTableName(cls);
    }
    
    public FilterField[] getListFilterFields() {
        if (listFilterFields == null)
            listFilterFields = (FilterField[]) ReflectionUtil.getAttibute(cls, "listFilterFields");
        return listFilterFields;
    }

    public String[][] getListTableFields() {
        if (listTableFields == null)
            listTableFields = (String[][]) ReflectionUtil.getAttibute(cls, "listTableFields");
        return listTableFields;
    }
    
    /**
     * Método que deve ser chamado pelo filho
     */
    protected void initListComponents() {
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        String title = null;
        if (ReflectionUtil.isAttributeExists(cls, "prlTitle"))
            title = (String) ReflectionUtil.getAttibute(cls, "prlTitle");
        if (title != null)
            setTitle("Lista de " + title);
        else
            setTitle("ALERTA! Falta o atributo 'prlTitle' em " + cls.getName());
        
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
//            String[] idColumns = (String[]) ReflectionUtil.getAttibute(cls, "idColumn");
//            for (String field : idColumns) {
//                lstIdFields.add(cls.getDeclaredField(field)); //throw NoSuchFieldException
//            }
            
            listFilterFields = getListFilterFields();
            listTableFields = getListTableFields();
            
            // Recupera os dados do banco
            tblList.setModel(new javax.swing.table.DefaultTableModel(
                    null,
                    listTableFields[0]
            ));
            
            listData();
            
            // Monta os elementos visuais
            javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
            scrollPane.setViewportView(tblList);
            
            java.awt.event.ActionListener alRefresh = new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    listData();
                }
            };
            
            javax.swing.JButton btnFilter = new javax.swing.JButton();
            btnFilter.setText("Filtrar");
            btnFilter.addActionListener(alRefresh);
            
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            
            ParallelGroup pgMain;
            SequentialGroup sgMain;
            ParallelGroup pgContainer;
            
            /// HORIZONTAL ///
            pgMain = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            sgMain = layout.createSequentialGroup()
                .addContainerGap();
            pgContainer = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            
            ParallelGroup pgMainTable = layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, width, GroupLayout.PREFERRED_SIZE)
                    ;
            
            // Filtros
            if (listFilterFields.length > 0) {
                ParallelGroup pgFilterButton = layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        ;
                
                SequentialGroup sgFilters = layout.createSequentialGroup();
                for (int i = 0; i < listFilterFields.length; i++) {
                    if (listFilterFields[i].getJComponent() instanceof javax.swing.JTextField) {
                        ((javax.swing.JTextField) listFilterFields[i].getJComponent()).addActionListener(alRefresh);
                    }
                    sgFilters
                        .addGap(6, 6, 6)
                        .addComponent(listFilterFields[i].getJLabel())
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(listFilterFields[i].getJComponent(), GroupLayout.PREFERRED_SIZE, listFilterFields[i].getWidth(), GroupLayout.PREFERRED_SIZE)
                            ;
                }
                
                pgMainTable.addGroup(sgFilters);
                pgFilterButton.addGroup(pgMainTable);
                pgContainer.addGroup(pgFilterButton);
            } else {
                pgContainer.addGroup(pgMainTable);
            }
            
            sgMain.addGroup(pgContainer);
            sgMain.addContainerGap(15, Short.MAX_VALUE);
            pgMain.addGroup(sgMain);
            
            layout.setHorizontalGroup(pgMain);
            
            
            /// VERTICAL ///
            pgMain = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            sgMain = layout.createSequentialGroup();
            sgMain.addContainerGap(20, Short.MAX_VALUE);
            
            if (listFilterFields.length > 0) {
                ParallelGroup pgFilters = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
                for (int i = 0; i < listFilterFields.length; i++) {
                    pgFilters
                        .addComponent(listFilterFields[i].getJComponent(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(listFilterFields[i].getJLabel())
                            ;
                }
                sgMain.addGroup(pgFilters)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnFilter)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        ;
            }
            // Tabela principal
            sgMain.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE);
            sgMain.addContainerGap();
            pgMain.addGroup(sgMain);
            
            layout.setVerticalGroup(pgMain);
            
            pack();
            
            // Needs to be called after pack() [http://stackoverflow.com/a/2442614/3136474]
            this.setLocationRelativeTo(null);
            
        } catch (Exception ex) {
            Logger.getLogger(LstTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     *  Busca os dados do banco e lista-os na tabela
     */
    private void listData() {
        
        try {
        
            // Recupera informações da classe
            listFilterFields = getListFilterFields();
            listTableFields = getListTableFields();

            // Recupera os dados do banco
            String sql = "";
            sql += "SELECT ";

            ArrayList<Object[]> joins = new ArrayList<>();

            for (String column : listTableFields[1]) {
                String[] join = column.split("\\.");
                if (join.length == 1)
                    sql += mainTable + "." + column;
                else {
                    Class clsInJoin = cls;
                    int c = 0;
                    String fieldToSelect = "";
                    for (String tbl : join) {
                        c++;
                        if (c == join.length) {
                            fieldToSelect = tbl;
                        } else {
                            Class clsJoin = Class.forName("model." + tbl);
                            joins.add(new Object[]{clsJoin, ReflectionUtil.getDBTableName(clsJoin), clsInJoin, ReflectionUtil.getDBTableName(clsInJoin)});
                            clsInJoin = clsJoin;
                        }
                    }
                    Object[] last = joins.get(joins.size()-1);
                    sql += (String) last[1] + "." + fieldToSelect;
                }
                sql += ",";
            }
            sql = sql.substring(0, (sql.length()-1)); //Tira a última vírgula
            sql += " FROM " + mainTable;

            //@TODO Fazer loop nos campos de filtros para verificar se algum deles precisa de um join

            for (Object[] tbl : joins) {
                sql += " LEFT OUTER JOIN " + (String) tbl[1] + " ON (";
                String[] idColumn = (String[]) ReflectionUtil.getAttibute((Class) tbl[0], "idColumn");
                for (String col : idColumn) {
                    sql += (String) tbl[1] + "." + col + "=" + (String) tbl[3] + "." + col + " AND ";
                }
                sql = sql.substring(0, (sql.length()-5)); //Tira o último AND
                sql += ")";
            }

            // Filtros
            boolean isWhere = false;

            ArrayList<Object> filterValues = new ArrayList<>();
            for (int i = 0; i < listFilterFields.length; i++) {
                if (!listFilterFields[i].isEmpty()) {
                    if (!isWhere) {
                        sql += " WHERE ";
                        isWhere = true;
                    } else
                        sql += " AND ";

                    Class<?> clsJoin = cls;
                    String column = "";
                    String[] join = {};
    //                if (listFilterFields[i] instanceof FilterFieldDynamicCombo) {
    //                    FilterFieldDynamicCombo dcbox = (FilterFieldDynamicCombo) listFilterFields[i];
    //                    clsJoin = dcbox.getClass();
    //                    String joinTable = ReflectionUtil.getDBTableName(clsJoin);
    //                    
    //                    join = new String[] {joinTable};
    //                } else {
                        column = listFilterFields[i].getField();
                        join = column.split("\\."); //Verifica se o campo veio de Join
    //                }
                    if (join.length <= 1)
                        sql += mainTable + "." + column;
                    else {
                        clsJoin = Class.forName("model." + join[join.length-2]);
                        sql += ReflectionUtil.getDBTableName(clsJoin) + "." + join[join.length-1];
                    }
                    sql += " ";
                    boolean isOperator = false;
                    String operator = "=";
                    if (ReflectionUtil.isAttributeExists(listFilterFields[i], "operator")) {
                        operator = (String) ReflectionUtil.getAttibute(listFilterFields[i], "operator", true);
                        isOperator = true;
                    }
                    Class<?> clsType = ReflectionUtil.getAttributeType(clsJoin, column);
                    if (clsType == null && listFilterFields[i] instanceof FilterFieldDynamicCombo) {
                        clsJoin = ((FilterFieldDynamicCombo) listFilterFields[i]).getDynamicClass();
                        clsType = ReflectionUtil.getAttributeType(clsJoin, column);
                        System.out.println(clsJoin.getName() + " . " + column);
                    }
                    if (clsType == String.class) {
                        if (!isOperator) {
                            sql += "LIKE";
                            filterValues.add("%" + listFilterFields[i].getSQLValue() + "%");
                        } else {
                            sql += operator;
                            if ("LIKE".equals(operator.toUpperCase()))
                                filterValues.add("%" + listFilterFields[i].getSQLValue() + "%");
                            else 
                                filterValues.add(listFilterFields[i].getSQLValue());
                        }
                    } else if (clsType == Integer.class || clsType == int.class) {
                        sql += operator;
                        filterValues.add(Integer.parseInt(listFilterFields[i].getSQLValue()));
                    } else if (clsType == Double.class) {
                        sql += operator;
                        filterValues.add(Double.parseDouble(listFilterFields[i].getSQLValue()));
                    } else if (clsType == Float.class) {
                        sql += operator;
                        filterValues.add(Float.parseFloat(listFilterFields[i].getSQLValue()));
                    }
                    sql += " ?";
                }
            }

            System.out.println(this.getClass().getName() +  " - SQL:" + sql); //Debug

            ResultSet rs;
            if (filterValues.size() > 0) 
                rs = DB.executeQuery(sql, filterValues.toArray());
            else
                rs = DB.executeQuery(sql);

            DefaultTableModel dtm = (DefaultTableModel) tblList.getModel();
            dtm.setNumRows(0);

            while (rs.next()) {
                Object[] row = new Object[listTableFields[1].length]; //Linha da tabela
                int count = 0;
                for (String col : listTableFields[1]) {
                    String column = col;
                    String[] join = column.split("\\."); //Verifica se o campo veio de Join

                    Class<?> clsType = Object.class;
                    if (join.length == 1)
                        clsType = ReflectionUtil.getAttributeType(cls, column);
                    else {
                        column = join[join.length-1];
                        Class clsInJoin = Class.forName("model." + join[join.length-2]);
                        clsType = ReflectionUtil.getAttributeType(clsInJoin, column);
                    }
                    if (clsType == String.class)
                        row[count] = rs.getString(column);
                    else if (clsType == Integer.class || clsType == int.class)
                        row[count] = rs.getInt(column);
                    else if (clsType == Double.class)
                        row[count] = rs.getDouble(column);
                    else if (clsType == Float.class)
                        row[count] = rs.getFloat(column);

                    count++;
                }
                dtm.addRow(row);
            }
        
        } catch (Exception ex) {
            Logger.getLogger(ListJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
