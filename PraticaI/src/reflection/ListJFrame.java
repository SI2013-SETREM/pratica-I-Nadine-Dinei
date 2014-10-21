
package reflection;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.ModelTemplate;
import util.DB;
import util.ImageSize;
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
//    {
//        private static final long serialVersionUID = 1L;
//
//        @Override
//        public Class getColumnClass(int column) {
//            return getValueAt(0, column).getClass();
//        }
//
//        @Override
//        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//            Component comp = super.prepareRenderer(renderer, row, column);
//            JLabel jc = (JLabel) comp;
//            if (column == this.getColumnCount()-2) {
////                if (isRowSelected(row) && isColumnSelected(column)) {
////                    jc.setIcon(infoIcon);
////                } else if (isRowSelected(row) && !isColumnSelected(column)) {
////                    jc.setIcon(warnIcon);
////                } else {
////                    jc.setIcon(jc.getIcon());
////                }
//            }
//            return comp;
//        }
//    };
    private Class<? extends ModelTemplate> cls; //Ex.: Estado.class
    // Atributos que vem da classe
    private String mainTable;
    private String[] idColumn;
    ArrayList<String> idColumnListed = new ArrayList<>();
    ArrayList<String> idColumnHidden = new ArrayList<>();
    private Object[][] listTableFields;
    private FilterField[] listFilterFields;
    
    private int width = 500;
    private int height = 300;
    
    private boolean allowInsert = true;
    private boolean allowUpdate = true;
    private boolean allowDelete = true;
    private int updColumn = 0;
    private int dltColumn = 0;
    
    public static ImageIcon iconFilter = new javax.swing.ImageIcon(Util.getIconUrl("filter.png"));
    public static ImageIcon iconInsert = new javax.swing.ImageIcon(Util.getIconUrl("add.png"));
    public static ImageIcon iconUpdate = new javax.swing.ImageIcon(Util.getIconUrl("pencil.png"));
    public static ImageIcon iconDelete = new javax.swing.ImageIcon(Util.getIconUrl("delete.png"));
    
//    private ArrayList<Field> lstIdFields = new ArrayList<Field>();
    
    
    /**
     *  Define a classe para a qual se está fazendo a listagem
     * @param cls 
     */
    public void setClass(Class<? extends ModelTemplate> cls) {
        this.cls = cls;
        this.mainTable = ReflectionUtil.getDBTableName(cls);
        this.idColumn = (String[]) ReflectionUtil.getAttibute(cls, "idColumn");
        this.listTableFields = (Object[][]) ReflectionUtil.getAttibute(cls, "listTableFields");
        this.listFilterFields = (FilterField[]) ReflectionUtil.getAttibute(cls, "listFilterFields");
        for (String idCol : idColumn) {
            boolean isSelected = false;
            for (Object[] field : listTableFields) {
                if (field[1] == idCol) {
                    idColumnListed.add(idCol);
                    isSelected = true;
                    break;
                }
            }
            if (!isSelected)
                idColumnHidden.add(idCol);
        }
        
        // A classe permite inserir registros?
        if (ReflectionUtil.isAttributeExists(cls, "allowInsert")) 
            allowInsert = (boolean) ReflectionUtil.getAttibute(cls, "allowInsert");
        // A classe permite alterar registros?
        if (ReflectionUtil.isAttributeExists(cls, "allowUpdate")) 
            allowUpdate = (boolean) ReflectionUtil.getAttibute(cls, "allowUpdate");
        // A classe permite excluir registros?
        if (ReflectionUtil.isAttributeExists(cls, "allowDelete")) 
            allowDelete = (boolean) ReflectionUtil.getAttibute(cls, "allowDelete");
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
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
            
            JButton btnFilter = new JButton();
            btnFilter.setText("Filtrar");
            btnFilter.addActionListener(alRefresh);
            btnFilter.setIcon(iconFilter);
//            btnFilter.setBorderPainted(false); //Aparentemente, não funciona
//            btnFilter.setOpaque(true);
            
            if (ReflectionUtil.isAttributeExists(cls, "iconTitle")) {
                String iconTitle = (String) ReflectionUtil.getAttibute(cls, "iconTitle");
                if (iconTitle != null && !"".equals(iconTitle)) {
                    java.net.URL urlImage = Util.getImageUrl(iconTitle, ImageSize.M);
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
            
            ArrayList<Join> joins = new ArrayList<>();
            ArrayList<String> lstSelectFields = new ArrayList<>();
            
            // Seleciona todos os campos ID que não estão na listagem
            for (String idCol : idColumnHidden) {
                String[] fieldParts = idCol.split("\\.");
                if (fieldParts.length == 1)
                    lstSelectFields.add(mainTable + "." + idCol + " AS " + mainTable + "_" + idCol);
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
                            boolean isJoinExists = false;
                            for (Join join : joins) {
                                if (clsJoin == join.getRightClass()) {
                                    isJoinExists = true;
                                    break;
                                }
                            }
                            if (!isJoinExists) {
                                Join join = new LeftOuterJoin(clsInJoin, clsJoin);
                                if (clsInJoin == cls) 
                                    join.setLeftAlias(mainTable);
                                joins.add(join);
                            }
                        }
                    }
                    Join lastJoin = joins.get(joins.size()-1);
                    lstSelectFields.add(lastJoin.getRightAlias() + "." + fieldToSelect + " AS " + lastJoin.getRightAlias() + "_" + fieldToSelect);
                }
            }
            
            // Recupera informações da classe
            int tblColCount = idColumnHidden.size() + listTableFields.length; //Quantas colunas vai ter o form?
            if (allowUpdate) { //Coluna de Editar
                updColumn = tblColCount;
                tblColCount++;
            }
            if (allowDelete) { //Coluna de Excluir 
                dltColumn = tblColCount;
                tblColCount++;
            }
            String[] colNamesTable = new String[tblColCount]; //Colunas
            for (int i = 0; i < idColumnHidden.size(); i++) 
                colNamesTable[i] = idColumnHidden.get(i); //Título das colunas de ID (ficarão ocultas)

            int iColNames = idColumnHidden.size();
            // Seleciona os campos da listagem e já faz a lista de JOIN
            for (Object[] field : listTableFields) {
                colNamesTable[iColNames] = (String) field[0];
                if (field[1] instanceof String) {
                    String[] fieldParts = ((String) field[1]).split("\\.");
                    if (fieldParts.length == 1)
                        lstSelectFields.add(mainTable + "." + field[1] + " AS " + mainTable + "_" + field[1]);
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
                                boolean isJoinExists = false;
                                for (Join join : joins) {
                                    if (clsJoin == join.getRightClass()) {
                                        isJoinExists = true;
                                        break;
                                    }
                                }
                                if (!isJoinExists) {
                                    Join join = new LeftOuterJoin(clsInJoin, clsJoin);
                                    if (clsInJoin == cls) 
                                        join.setLeftAlias(mainTable);
                                    joins.add(join);
                                }
                                clsInJoin = clsJoin;
                            }
                        }
                        Join lastJoin = joins.get(joins.size()-1);
                        lstSelectFields.add(lastJoin.getRightAlias() + "." + fieldToSelect + " AS " + lastJoin.getRightAlias() + "_" + fieldToSelect);
                    }
                } else if (field[1] instanceof Join) {
                    Join join = (Join) field[1];
                    if (join.getLeftClass() == cls) 
                        join.setLeftAlias(mainTable);
                    joins.add(join);
                    lstSelectFields.add(join.getRightAlias() + "." + join.getRightField() + " AS " + join.getRightAlias() + "_" + join.getRightField());
                }
                iColNames++;
            }
            
            // Recupera os dados do banco
            String sql = "";
            sql += "SELECT ";
            for (String slcField : lstSelectFields) 
                sql += slcField + ",";
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
            sql += " WHERE 1=1"; //Sempre tem WHERE na SQL, aí é só concatenar ADD
            
            ArrayList<Object> filterValues = new ArrayList<>();
            for (FilterField filterField : listFilterFields) {
                if (!filterField.isEmpty()) {
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
            
            JLabel lblBtnUpdate = new JLabel(iconDelete, SwingConstants.CENTER);
            lblBtnUpdate.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Mouse clicked (# of clicks: " + e.getClickCount() + ")");
                }
            });

            if (allowUpdate) {
                colNamesTable[updColumn] = "";
            }
            if (allowDelete) {
                colNamesTable[dltColumn] = "";
            }
            
            final boolean[] dtmCanEdit = new boolean[colNamesTable.length];
            for (int i = 0; i < dtmCanEdit.length; i++) 
                dtmCanEdit[i] = false;
            
            DefaultTableModel dtm = new javax.swing.table.DefaultTableModel(
                    null,
                    colNamesTable
            ) {
                boolean[] canEdit = dtmCanEdit;
                
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
                
//                @Override
//                public Class getColumnClass(int column) {
//                    if (column == (this.getColumnCount()-2)) return JLabel.class;
//                    if (column == (this.getColumnCount()-1)) return JLabel.class;
//                    return Object.class;
//                }
            };
            dtm.setNumRows(0);
            
            while (rs.next()) {
                Object[] row = new Object[colNamesTable.length]; //Linha da tabela
                int count = 0;
                for (String idCol : idColumnHidden) {
                    Class<?> clsType = Object.class;
                    String column = idCol;
                    
                    String[] join = column.split("\\."); //Verifica se o campo veio de Join

                    if (join.length == 1) {
                        clsType = ReflectionUtil.getAttributeType(cls, column);
                        column = mainTable + "_" + column;
                    } else {
                        Class clsInJoin = Class.forName("model." + join[join.length-2]);
                        column = join[join.length-1];
                        clsType = ReflectionUtil.getAttributeType(clsInJoin, column);
                        String tblAlias = null;
                        for (Join jJoin : joins) {
                            if (clsInJoin == jJoin.getRightClass()) {
                                tblAlias = jJoin.getRightAlias();
                            }
                        }
                        if (tblAlias != null) 
                            column = tblAlias + "_" + column;
                    }
                    row[count] = DB.getColumnByType(rs, column, clsType);
                    count++;
                }
                for (Object[] col : listTableFields) {
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
                            column = join[join.length-1];
                            clsType = ReflectionUtil.getAttributeType(clsInJoin, column);
                            System.out.println("");
                            String tblAlias = null;
                            for (Join jJoin : joins) {
                                if (clsInJoin == jJoin.getRightClass()) {
                                    tblAlias = jJoin.getRightAlias();
                                }
                            }
                            if (tblAlias != null) 
                                column = tblAlias + "_" + column;

                        }
                    } else if (col[1] instanceof Join) {
                        Join join = (Join) col[1];
                        clsType = ReflectionUtil.getAttributeType(join.getRightClass(), join.getRightField());
                        column = join.getRightAlias() + "_" + join.getRightField();
                    }
                    row[count] = DB.getColumnByType(rs, column, clsType);
                    count++;
                }
                if (allowUpdate) {
                    row[updColumn] = lblBtnUpdate;
                }
                dtm.addRow(row);
            }
            
            tblList.setModel(dtm);
            tblList.getTableHeader().setReorderingAllowed(false);
            TableColumnModel tcm = tblList.getColumnModel();
            
            for (int i = 0; i < idColumnHidden.size(); i++) {
                tcm.getColumn(i).setMinWidth(0);
                tcm.getColumn(i).setPreferredWidth(0);
                tcm.getColumn(i).setMaxWidth(0);
            }
            if (allowUpdate) {
                tcm.getColumn(updColumn).setResizable(false);
                tcm.getColumn(updColumn).setPreferredWidth(5);
                tcm.getColumn(updColumn).setCellRenderer(new UpdateCellRenderer());
                tcm.getColumn(updColumn).setCellEditor(null);
            }
            if (allowDelete) {
                tcm.getColumn(dltColumn).setResizable(false);
                tcm.getColumn(dltColumn).setPreferredWidth(5);
                tcm.getColumn(dltColumn).setCellRenderer(new DeleteCellRenderer());
                tcm.getColumn(dltColumn).setCellEditor(null);
            }
            
            tblList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //System.out.println("Mouse clicked (# of clicks: " + e.getClickCount() + ")");
                    int col = tblList.columnAtPoint(e.getPoint());
                    int row = tblList.rowAtPoint(e.getPoint());
                    if (col == dltColumn) {
                        deleteRow(row);
                    }
                    if (col == updColumn) {
                        updateRow(row);
                    }
                }
            });
            
        } catch (Exception ex) {
            Logger.getLogger(ListJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void deleteRow(int row) {
        Object[] idCols = new Object[idColumn.length];
        int col = 0;
        for (String idCol : idColumnHidden) {
            idCols[col] = tblList.getValueAt(row, col);
            col++;
        }
        for (Object[] tblCol : listTableFields) {
            
        }
        System.out.println("Linha col 0: " + tblList.getValueAt(row, 0));
        System.out.println("Linha excluída: " + row);
    }
    
    private void updateRow(int row) {
        System.out.println("Linha atualizada: " + row);
    }
}

class DeleteCellRenderer extends DefaultTableCellRenderer {
    public DeleteCellRenderer() {
        this.setIcon(ListJFrame.iconDelete);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked (# of clicks: " + e.getClickCount() + ")");
            }
        });
    }

    @Override
    public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setText(null);
        return this;
    }
}

class UpdateCellRenderer extends DefaultTableCellRenderer {
    public UpdateCellRenderer() {
        this.setIcon(ListJFrame.iconUpdate);
        this.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setText(null);
        return this;
    }
}