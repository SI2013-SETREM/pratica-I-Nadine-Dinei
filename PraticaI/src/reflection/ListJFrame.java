
package reflection;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import util.DB;

/**
 *
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public abstract class ListJFrame extends javax.swing.JFrame {
    
    private javax.swing.JTable tblList = new javax.swing.JTable();
    private Class<?> cls; //Ex.: Estado.class
    private ArrayList<JTextField> txtFilters = new ArrayList<JTextField>();
    private ArrayList<JLabel> txtFilterLabels = new ArrayList<JLabel>();
//    private ArrayList<Field> lstIdFields = new ArrayList<Field>();
    
    // Atributos que vem da classe
    private Object[][] listFilterFields;
    private String[][] listTableFields;
    
    
    /**
     *  Define a classe para a qual se está fazendo a listagem
     * @param cls 
     */
    public void setClass(Class<?> cls) {
        this.cls = cls;
    }
    
    public Object[][] getListFilterFields() {
        if (listFilterFields == null)
            listFilterFields = (Object[][]) ReflectionUtil.getStaticAttibute(cls, "listFilterFields");
        return listFilterFields;
    }

    public String[][] getListTableFields() {
        if (listTableFields == null)
            listTableFields = (String[][]) ReflectionUtil.getStaticAttibute(cls, "listTableFields");
        return listTableFields;
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
//            String[] idColumns = (String[]) ReflectionUtil.getStaticAttibute(cls, "idColumn");
//            for (String field : idColumns) {
//                lstIdFields.add(cls.getDeclaredField(field)); //throw NoSuchFieldException
//            }
            
            listFilterFields = getListFilterFields();
            listTableFields = getListTableFields();
            
            for (Object[] field : listFilterFields) {
                JLabel lbl = new JLabel();
                lbl.setText((String) field[0]);
                txtFilterLabels.add(lbl);
                
                JTextField txt = new JTextField();
                txt.setText(null);
                txtFilters.add(txt);
            }
            
            
            // Recupera os dados do banco
            tblList.setModel(new javax.swing.table.DefaultTableModel(
                    null,
                    listTableFields[0]
            ));
            
            listData();
            
            // Monta os elementos visuais
            javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
            scrollPane.setViewportView(tblList);
            
            javax.swing.JButton btnFilter = new javax.swing.JButton();
            btnFilter.setText("Filtrar");
            btnFilter.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        listData();
                    } catch (SQLException ex) {
                        Logger.getLogger(ListJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
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
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                    ;
            
            // Filtros
            if (txtFilters.size() > 0) {
                ParallelGroup pgFilterButton = layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        ;
                
                SequentialGroup sgFilters = layout.createSequentialGroup();
                for (int i = 0; i < txtFilters.size(); i++) {
                    sgFilters
                        .addGap(6, 6, 6)
                        .addComponent(txtFilterLabels.get(i))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFilters.get(i), GroupLayout.PREFERRED_SIZE, (int) listFilterFields[i][2], GroupLayout.PREFERRED_SIZE)
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
            
            if (txtFilters.size() > 0) {
                ParallelGroup pgFilters = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
                for (int i = 0; i < txtFilters.size(); i++) {
                    pgFilters
                        .addComponent(txtFilters.get(i), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtFilterLabels.get(i))
                            ;
                }
                sgMain.addGroup(pgFilters)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnFilter)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        ;
            }
            // Tabela principal
            sgMain.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE);
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
    
    private void listData() throws SQLException {
        
        // Recupera informações da classe
        listFilterFields = getListFilterFields();
        listTableFields = getListTableFields();
        
        // Recupera os dados do banco
        String sql = "";
        sql += "SELECT ";
        for (String col : listTableFields[1]) {
            sql += "t." + col + ",";
        }
        sql = sql.substring(0, (sql.length()-1));
        sql += " FROM " + ReflectionUtil.getDBTableName(cls) + " t";

        boolean isWhere = false;

        ArrayList<Object> filterValues = new ArrayList<>();
        for (int i = 0; i < txtFilters.size(); i++) {
            if (!"".equals(txtFilters.get(i).getText())) {
                String field = (String) listFilterFields[i][1];
                if (!isWhere) {
                    sql += " WHERE ";
                    isWhere = true;
                } else
                    sql += " AND ";
                sql += "t." + field;

                boolean isOperator = false;
                if (listFilterFields[i].length >= 4) {
                    sql += " " + listFilterFields[i][3] + " ";
                    isOperator = true;
                }
                Class<?> clsType = ReflectionUtil.getAttributeType(cls, field);
                if (clsType == String.class) {
                    if (!isOperator) {
                        sql += " LIKE ";
                        filterValues.add("%" + txtFilters.get(i).getText() + "%");
                    } else {
                        if ("LIKE".equals(String.valueOf(listFilterFields[i][3]).toUpperCase()))
                            filterValues.add("%" + txtFilters.get(i).getText() + "%");
                        else 
                            filterValues.add(txtFilters.get(i).getText());
                    }
                } else if (clsType == Integer.class) {
                    if (!isOperator)
                        sql += " = ";
                    filterValues.add(Integer.parseInt(txtFilters.get(i).getText()));
                } else if (clsType == Double.class) {
                    if (!isOperator)
                        sql += " = ";
                    filterValues.add(Double.parseDouble(txtFilters.get(i).getText()));
                } else if (clsType == Float.class) {
                    if (!isOperator)
                        sql += " = ";
                    filterValues.add(Float.parseFloat(txtFilters.get(i).getText()));
                }
                sql += "?";
            }
        }

        System.out.println("SQL:" + sql); //Debug

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
            dtm.addRow(row);
        }
        
    }
    
}
