
package reflection;

import java.awt.Toolkit;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import model.ModelTemplate;
import util.DB;
import util.Util;
import util.field.FilterField;
import util.field.FilterFieldDynamicCombo;
import util.sql.Join;
import util.sql.LeftOuterJoin;

/**
 *  JFrame padrão de listagem
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class ListJFrame extends javax.swing.JFrame {
    
    private javax.swing.JTable tblList = new javax.swing.JTable();
    private Class<? extends ModelTemplate> cls; //Ex.: Estado.class
    private String mainTable;
    
    private int width = 500;
    private int height = 300;
    
//    private ArrayList<Field> lstIdFields = new ArrayList<Field>();
    
    // Atributos que vem da classe
    private FilterField[] listFilterFields;
    private Object[][] listTableFields;
    
    /**
     *  Define a classe para a qual se está fazendo a listagem
     * @param cls 
     */
    public void setClass(Class<? extends ModelTemplate> cls) {
        this.cls = cls;
        this.mainTable = ReflectionUtil.getDBTableName(cls);
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public FilterField[] getListFilterFields() {
        if (listFilterFields == null)
            listFilterFields = (FilterField[]) ReflectionUtil.getAttibute(cls, "listFilterFields");
        return listFilterFields;
    }
    public Object[][] getListTableFields() {
        if (listTableFields == null)
            listTableFields = (Object[][]) ReflectionUtil.getAttibute(cls, "listTableFields");
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
            setTitle("Listagem de Dados");
        
        try {
            
            listFilterFields = getListFilterFields();
            
            // Recupera os dados do banco
            listData();
            
            // Monta os elementos visuais
            javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
            scrollPane.setViewportView(tblList);
            
            java.awt.event.ActionListener alRefresh = new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    listData();
                }
            };
            
            javax.swing.JButton btnFilter = new javax.swing.JButton();
            btnFilter.setText("Filtrar");
            btnFilter.addActionListener(alRefresh);
            btnFilter.setIcon(new javax.swing.ImageIcon(Util.getIconUrl("filter.png")));
            
            if (ReflectionUtil.isAttributeExists(cls, "iconTitle")) {
                String iconTitle = (String) ReflectionUtil.getAttibute(cls, "iconTitle");
                if (iconTitle != null && !"".equals(iconTitle)) {
                    java.net.URL urlImage = Util.getIconUrl(iconTitle);
                    if (urlImage != null)
                        this.setIconImage(Toolkit.getDefaultToolkit().getImage(urlImage));
                }
            }
            
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
            Object[][] lstTblFields = getListTableFields();
            String[] colNamesTable = new String[lstTblFields.length];
            
            // Recupera os dados do banco
            String sql = "";
            sql += "SELECT ";

            ArrayList<Join> joins = new ArrayList<>();

            // Lista as colunas que deve selecionar e já faz a lista de JOIN
            int i = 0;
            for (Object[] field : lstTblFields) {
                colNamesTable[i] = (String) field[0];
                if (field[1] instanceof String) {
                    String[] fieldParts = ((String) field[1]).split("\\.");
                    if (fieldParts.length == 1)
                        sql += mainTable + "." + field[1] + " AS " + mainTable + "_" + field[1];
                    else {
                        Class clsInJoin = cls;
                        int c = 0;
                        String fieldToSelect = "";
                        for (String tbl : fieldParts) {
                            c++;
                            if (c == fieldParts.length) {
                                fieldToSelect = tbl;
                            } else {
                                Class clsJoin = Class.forName("model." + tbl);
                                Join join = new LeftOuterJoin(clsInJoin, clsJoin);
                                if (clsInJoin == cls) 
                                    join.setLeftAlias(mainTable);
                                joins.add(join);
                                clsInJoin = clsJoin;
                            }
                        }
                        Join lastJoin = joins.get(joins.size()-1);
                        sql += lastJoin.getRightAlias() + "." + fieldToSelect + " AS " + lastJoin.getRightAlias() + "_" + fieldToSelect;
                    }
                } else if (field[1] instanceof Join) {
                    Join join = (Join) field[1];
                    if (join.getLeftClass() == cls) 
                        join.setLeftAlias(mainTable);
                    sql += join.getRightAlias() + "." + join.getRightField() + " AS " + join.getRightAlias() + "_" + join.getRightField();
                    joins.add(join);
                }
                sql += ",";
                i++;
            }
            sql = sql.substring(0, (sql.length()-1)); //Tira a última vírgula
            sql += " FROM " + mainTable;
            
            // Verifica os JOIN necessários pelos campos de filtro
            for (FilterField ff : listFilterFields) {
                String[] fieldParts = ff.getField().split("\\.");
                if (fieldParts.length > 1) {
                    Class clsInJoin = cls;
                    int c = 0;
                    for (String part : fieldParts) {
                        c++;
                        if (c < fieldParts.length) {
                            Class clsJoin = Class.forName("model." + part);
                            boolean isJoinExists = false;
                            for (Join join : joins) {
                                if (clsJoin == join.getRightClass()) {
                                    isJoinExists = true;
                                    break;
                                }
                            }
                            if (!isJoinExists) {
                                joins.add(new LeftOuterJoin(clsInJoin, clsJoin));
                            }
                            clsInJoin = clsJoin;
                        }
                    }
                }
            }

            // Faz os JOIN na SQL
            for (Join join : joins) {
                sql += " " + join.getJoinSQL();
            }
            
            // Filtros
            boolean isWhere = false;

            ArrayList<Object> filterValues = new ArrayList<>();
            for (FilterField filterField : listFilterFields) {
                if (!filterField.isEmpty()) {
                    if (!isWhere) {
                        sql += " WHERE ";
                        isWhere = true;
                    } else
                        sql += " AND ";
                    Class<?> clsJoin = cls;
                    String column = filterField.getField();
                    String[] fieldParts = column.split("\\."); //Verifica se o campo veio de Join
                    String classAttr = column;
                    if (fieldParts.length <= 1)
                        sql += mainTable + "." + column;
                    else {
                        classAttr = fieldParts[fieldParts.length-1];
                        clsJoin = Class.forName("model." + fieldParts[fieldParts.length-2]);
                        String tblAlias = null;
                        for (Join join : joins) {
                            if (clsJoin == join.getRightClass()) {
                                tblAlias = join.getRightAlias();
                            }
                        }
                        if (tblAlias == null) 
                            sql += classAttr;
                        else
                            sql += tblAlias + "." + classAttr;
                    }
                    sql += " ";
                    boolean isOperator = false;
                    String operator = "=";
                    if (ReflectionUtil.isAttributeExists(filterField, "operator")) {
                        operator = (String) ReflectionUtil.getAttibute(filterField, "operator", true);
                        isOperator = true;
                    }
                    Class<?> clsType = ReflectionUtil.getAttributeType(clsJoin, classAttr);
                    if (filterField instanceof FilterFieldDynamicCombo) {
                        clsJoin = ((FilterFieldDynamicCombo) filterField).getDynamicClass();
                        clsType = ReflectionUtil.getAttributeType(clsJoin, classAttr);
                    }
                    if (clsType == String.class) {
                        if (!isOperator) {
                            sql += "LIKE";
                            filterValues.add("%" + filterField.getSQLValue() + "%");
                        } else {
                            sql += operator;
                            if ("LIKE".equals(operator.toUpperCase())) {
                                filterValues.add("%" + filterField.getSQLValue() + "%");
                            } else {
                                filterValues.add(filterField.getSQLValue());
                            }
                        }
                    } else if (clsType == Integer.class || clsType == int.class) {
                        sql += operator;
                        filterValues.add(Integer.parseInt(filterField.getSQLValue()));
                    } else if (clsType == Double.class) {
                        sql += operator;
                        filterValues.add(Double.parseDouble(filterField.getSQLValue()));
                    } else if (clsType == Float.class) {
                        sql += operator;
                        filterValues.add(Float.parseFloat(filterField.getSQLValue()));
                    }
                    sql += " ?";
                }
            }

            Util.debug(this.getClass().getName() + " - SQL:" + sql);

            ResultSet rs;
            if (filterValues.size() > 0) 
                rs = DB.executeQuery(sql, filterValues.toArray());
            else
                rs = DB.executeQuery(sql);
            
            DefaultTableModel dtm = new javax.swing.table.DefaultTableModel(
                    null,
                    colNamesTable
            );
            dtm.setNumRows(0);

            while (rs.next()) {
                Object[] row = new Object[lstTblFields.length]; //Linha da tabela
                int count = 0;
                for (Object[] col : lstTblFields) {
                    Class<?> clsType = Object.class;
                    String column = null;
                    if (col[1] instanceof String) {
                        column = (String) col[1];
                        String[] join = column.split("\\."); //Verifica se o campo veio de Join
                        
                        if (join.length == 1) {
                            clsType = ReflectionUtil.getAttributeType(cls, column);
                            column = mainTable + "_" + column;
                        } else {
                            Class clsInJoin = Class.forName("model." + join[join.length-2]);
                            clsType = ReflectionUtil.getAttributeType(clsInJoin, join[join.length-1]);
                            column = ReflectionUtil.getDBTableName(clsInJoin) + "_" + join[join.length-1];
                        }
                    } else if (col[1] instanceof Join) {
                        Join join = (Join) col[1];
                        clsType = ReflectionUtil.getAttributeType(join.getRightClass(), join.getRightField());
                        column = join.getRightAlias() + "_" + join.getRightField();
                    }
                    System.out.println(column);
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
            
            tblList.setModel(dtm);
        
        } catch (Exception ex) {
            Logger.getLogger(ListJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
