
package reflection;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    private String sngTitle;
    private String prlTitle;
    private String softDelete;
    ArrayList<String> idColumnListed = new ArrayList<>();
    ArrayList<String> idColumnHidden = new ArrayList<>();
    private Object[][] listTableFields;
    private FilterField[] listFilterFields;
    
    private int width = 500;
    private int height = 300;
    private final int btnColWidth = 30;
    
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
        // Título singular
        if (ReflectionUtil.isAttributeExists(cls, "sngTitle")) 
            sngTitle = (String) ReflectionUtil.getAttibute(cls, "sngTitle");
        // Título plural
        if (ReflectionUtil.isAttributeExists(cls, "prlTitle")) 
            prlTitle = (String) ReflectionUtil.getAttibute(cls, "prlTitle");
        // A tabela usa Soft Delete? Se sim, qual é o campo?
        if (ReflectionUtil.isAttributeExists(cls, "softDelete")) 
            softDelete = (String) ReflectionUtil.getAttibute(cls, "softDelete");
        
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
    public void initListComponents() {
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        if (prlTitle != null)
            setTitle("Lista de " + prlTitle);
        else
            setTitle("Listagem de Dados");
        
        try {
            
            // Recupera os dados do banco
            listData();
            
            // Monta os elementos visuais
            javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
            scrollPane.setViewportView(tblList);
            
            // Monitora as teclas pressionadas na tabela principal
            tblList.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    switch (evt.getKeyCode()) {
                        case java.awt.event.KeyEvent.VK_DELETE:
                            deleteRow(tblList.getSelectedRow());
                            break;
                    }
                }
            });
            // Monitora os cliques na tabela principal
            tblList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int col = tblList.columnAtPoint(e.getPoint());
                    int row = tblList.rowAtPoint(e.getPoint());
//                    System.out.println(col + " - " + dltColumn + " - " + updColumn);
                    if (col == dltColumn) {
                        deleteRow(row);
                    } else if (col == updColumn) {
                        updateRow(row);
                    } else if (e.getClickCount() > 1) {
                        updateRow(row);
                    }
                }
            });
            
            // Monitorador do evento Refresh
            java.awt.event.ActionListener alRefresh = new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    listData();
                }
            };
            // Monitorador do evento Add
            java.awt.event.ActionListener alAdd = new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertRow();
                }
            };
            
            JButton btnFilter = new JButton();
            btnFilter.setText("Filtrar");
            btnFilter.addActionListener(alRefresh);
            btnFilter.setIcon(iconFilter);
//            btnFilter.setBorderPainted(false); //Aparentemente, não funciona
//            btnFilter.setOpaque(true);
            
            JButton btnAdd = new JButton();
            btnAdd.setText("Adicionar");
            btnAdd.addActionListener(alAdd);
            btnAdd.setIcon(iconInsert);
            
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
                .addContainerGap()
                    ;
            pgContainer = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            
            ParallelGroup pgMainTable = layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, width, GroupLayout.PREFERRED_SIZE)
                .addComponent(scrollPane, 0, width, Short.MAX_VALUE)
                    ;
            
            
            if (allowInsert) {
                ParallelGroup pgAddButton = layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        ;
                pgContainer.addGroup(pgAddButton);
            }
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
            //sgMain.addContainerGap(15, Short.MAX_VALUE);
            sgMain.addContainerGap();
            pgMain.addGroup(sgMain);
            
            layout.setHorizontalGroup(pgMain);
            
            
            /// VERTICAL ///
            pgMain = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            sgMain = layout.createSequentialGroup();
            sgMain.addContainerGap(20, 20);
            
            ParallelGroup pgButtons = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            if (allowInsert) 
                pgButtons.addComponent(btnAdd);
            
            if (listFilterFields.length > 0) {
                ParallelGroup pgFilters = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
                for (int i = 0; i < listFilterFields.length; i++) {
                    pgFilters
                        .addComponent(listFilterFields[i].getJComponent(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(listFilterFields[i].getJLabel())
                            ;
                }
                pgButtons.addComponent(btnFilter);
                sgMain.addGroup(pgFilters)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                    .addComponent(btnFilter)
//                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        ;
            }
            sgMain.addGroup(pgButtons)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    ;
            // Tabela principal
            sgMain.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, height, Short.MAX_VALUE);
            sgMain.addContainerGap();
            pgMain.addGroup(sgMain);
            
            layout.setVerticalGroup(pgMain);
            
            pack();
            
            // Needs to be called after pack() [http://stackoverflow.com/a/2442614/3136474]
            this.setLocationRelativeTo(null);
            
        } catch (Exception ex) {
            Logger.getLogger(ListJFrame.class.getName()).log(Level.SEVERE, null, ex);
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
            if (softDelete != null)
                sql += " WHERE " + softDelete + " IS NULL";
            else
                sql += " WHERE 1=1"; //Sempre tem WHERE na SQL, aí é só concatenar AND
            
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
                    } else if (clsType == java.sql.Date.class) {
                        //@TODO
                        sql += operator;
                        filterValues.add(filterField.getSQLValue());
                    } else if (clsType == java.sql.Time.class) {
                        //@TODO
                        
                    } else if (clsType == java.sql.Timestamp.class) {
                        //@TODO
                        
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
//            lblBtnUpdate.addMouseListener(new MouseAdapter(){
//                public void mouseClicked(MouseEvent e) {
//                    System.out.println("Mouse clicked (# of clicks: " + e.getClickCount() + ")");
//                }
//            });

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
                    String clsAttr = idCol;
                    
                    String[] join = column.split("\\."); //Verifica se o campo veio de Join
//                    System.out.println("Hue" + join.length + " " + column);
                    if (join.length == 1) {
                        clsType = ReflectionUtil.getAttributeType(cls, column);
                        column = mainTable + "_" + column;
                    } else {
                        Class clsInJoin = Class.forName("model." + join[join.length-2]);
                        clsAttr = join[join.length-1];
                        clsType = ReflectionUtil.getAttributeType(clsInJoin, clsAttr);
                        String tblAlias = null;
                        for (Join jJoin : joins) {
                            if (clsInJoin == jJoin.getRightClass()) {
                                tblAlias = jJoin.getRightAlias();
                            }
                        }
                        if (tblAlias != null) 
                            column = tblAlias + "_" + clsAttr;
                        else
                            column = mainTable + "_" + clsAttr;
                    }
                    if (clsType.getPackage() == Package.getPackage("model")) // Veio por Join
                        clsType = ReflectionUtil.getAttributeType(clsType, clsAttr);
                    //System.out.println("COL HIDE: " + column + ", " + clsType);
                    row[count] = DB.getColumnByType(rs, column, clsType);
                    count++;
                }
                for (Object[] col : listTableFields) {
                    Class<?> clsType = Object.class;
                    String column = null;
                    String clsAttr = null;
                    if (col[1] instanceof String) {
                        column = (String) col[1];
                        clsAttr = column;
                        String[] join = column.split("\\."); //Verifica se o campo veio de Join
                        
                        if (join.length == 1) {
                            clsType = ReflectionUtil.getAttributeType(cls, column);
                            column = mainTable + "_" + column;
                        } else {
                            Class clsInJoin = Class.forName("model." + join[join.length-2]);
                            clsAttr = join[join.length-1];
                            clsType = ReflectionUtil.getAttributeType(clsInJoin, clsAttr);
                            
                            String tblAlias = null;
                            for (Join jJoin : joins) {
                                if (clsInJoin == jJoin.getRightClass()) {
                                    tblAlias = jJoin.getRightAlias();
                                }
                            }
                            if (tblAlias != null) 
                                column = tblAlias + "_" + clsAttr;
                            else
                                column = mainTable + "_" + clsAttr;
                        }
                    } else if (col[1] instanceof Join) {
                        Join join = (Join) col[1];
                        clsType = ReflectionUtil.getAttributeType(join.getRightClass(), join.getRightField());
                        clsAttr = join.getRightField();
                        column = join.getRightAlias() + "_" + clsAttr;
                    }
                    if (clsType.getPackage() == Package.getPackage("model")) // Veio por Join
                        clsType = ReflectionUtil.getAttributeType(clsType, clsAttr);
                    row[count] = DB.getColumnByType(rs, column, clsType);
                    row[count] = DB.formatColumn(row[count]);
                    count++;
                }
                
//                String o = "LISTJFRAME ROW [";
//                for (Object ob : row)
//                    o += String.valueOf(ob) + ",";
//                o += "]";
//                System.out.println(o);
                
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
                tcm.getColumn(updColumn).setMinWidth(btnColWidth);
//                tcm.getColumn(updColumn).setPreferredWidth(btnColWidth);
                tcm.getColumn(updColumn).setMaxWidth(btnColWidth);
                tcm.getColumn(updColumn).setCellRenderer(new UpdateCellRenderer());
                tcm.getColumn(updColumn).setCellEditor(null);
            }
            if (allowDelete) {
                tcm.getColumn(dltColumn).setResizable(false);
                tcm.getColumn(dltColumn).setMinWidth(btnColWidth);
//                tcm.getColumn(dltColumn).setPreferredWidth(btnColWidth);
                tcm.getColumn(dltColumn).setMaxWidth(btnColWidth);
                tcm.getColumn(dltColumn).setCellRenderer(new DeleteCellRenderer());
                tcm.getColumn(dltColumn).setCellEditor(null);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ListJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void deleteRow(int row) {
//        String o = "idColumn: [";
//        for (Object hue : idColumn) 
//            o += String.valueOf(hue) + ", ";
//        o += "]";
//        o += "\r\n idColumnHidden: [";
//        for (Object hue : idColumnHidden) 
//            o += String.valueOf(hue) + ", ";
//        o += "]";
//        o += "\r\n idColumnListed: [";
//        for (Object hue : idColumnListed) 
//            o += String.valueOf(hue) + ", ";
//        o += "]";
//        System.out.println(o);
        
        // Pergunta ao usuário se ele deseja realmente cancelar.
        String msg = "";
        msg += "Deseja realmente excluir o ";
        // Precisa identificar o sexo do registro, se for implementar isto (o Estado, a Cidade)
//        if (sngTitle != null) 
//            msg += sngTitle;
//        else
            msg += "registro";
        msg += " [";
        for (int iTblColumn = 0; iTblColumn < listTableFields.length; iTblColumn++) {
            msg += tblList.getValueAt(row, iTblColumn+idColumnHidden.size()) + ", ";
        }
        msg = msg.substring(0, (msg.length()-2)); //Tira a última vírgula
        msg += "] ?";
        
        int option = JOptionPane.showConfirmDialog(this, msg, "Deseja excluir?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, iconDelete);
        if (option != JOptionPane.YES_OPTION)
            return;
        
        String sql = "";
        if (softDelete != null)
            sql += "UPDATE " + mainTable + " SET " + softDelete + " = NOW() WHERE 1=1";
        else
            sql += "DELETE FROM " + mainTable + " WHERE 1=1";
        
        for (String field : idColumn) 
            sql += " AND " + field + " = ?";
        
        Object[] idCols = getIdCols(row);
        
//        Object[] idCols = new Object[idColumn.length]; //Cria um array que armazenará os campos chave da tabela (em ordem)
//        int colIdHidden = 0; //É definido aqui fora porque as colunas ocultas sempre estarão em ordem, então não precisa navegar por todas elas a cada loop
//        // Busca os campos de chave
//        for (int col = 0; col < idColumn.length; col++) {
//            sql += " AND " + idColumn[col] + " = ?";
//            
//            boolean isFound = false;
//            for (; colIdHidden < idColumnHidden.size(); colIdHidden++) { //Passa por todos os campos chave ocultos
//                if (idColumn[col].equals(idColumnHidden.get(colIdHidden))) {
//                    idCols[col] = tblList.getValueAt(row, col);
//                    isFound = true;
//                    break;
//                }
//            }
//            if (!isFound) { //Se não encontrou nas colunas ocultas, está nas exibidas
//                for (int iListed = 0; iListed < idColumnListed.size(); iListed++) { //Passa por todos os campos chave exibidos
//                    if (idColumn[col].equals(idColumnListed.get(iListed))) { //É este campo que estamos procurando?
//                        for (int iTblField = 0; iTblField < listTableFields.length; iTblField++) { //Navega nas colunas da tabela
//                            if (idColumnListed.get(iListed).equals((String) listTableFields[iTblField][1])) { //Esta coluna contêm o campo que estamos procurando?
//                                idCols[col] = tblList.getValueAt(row, iTblField+idColumnHidden.size());
//                                isFound = true;
//                                break;
//                            }
//                        }
//                        if (isFound)
//                            break;
//                    }
//                }
//            }
//        }
        
        String o = "[";
        for (Object colcol : idCols) 
            o += String.valueOf(colcol) + ", ";
        o += "]";
        System.out.println("Linha " + row + " excluída: " + o);
        System.out.println("SQL: " + sql);
        
        try {
            DB.executeUpdate(sql, idCols);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Falha ao excluir o registro!\r\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE, iconDelete);
        }
        
        //@TODO: Talvez seria melhor simplesmente excluir aquela linha da tabela, não?
        listData();
    }
    
    /**
     *  Abre o formulário para alterar o registro
     * @param row Linha da tabela do registro a ser atualizado
     */
    private void updateRow(int row) {
        this.openForm(DB.FLAG_UPDATE, row);
    }
    
    /**
     *  Abre o formulário para inserir um novo registro
     */
    private void insertRow() {
        this.openForm(DB.FLAG_INSERT);
    }
    
    /**
     *  Abre o formulário vinculado à listagem
     * @param flag Uma das constantes DB.FLAG_XXX
     */
    private void openForm(String flag) {
        openForm(flag, 0);
    }
    /**
     *  Abre o formulário vinculado à listagem
     * @param flag Uma das constantes DB.FLAG_XXX
     * @param row Linha da tabela caso deva carregar algum registro
     */
    private void openForm(String flag, int row) {
        String formName = "view.Frm" + cls.getSimpleName();
        try {
            Class<?> frmClass = Class.forName(formName);
            if (FormJFrame.class.isAssignableFrom(frmClass)) {
                FormJFrame form = (FormJFrame) Class.forName(formName).newInstance();
                form.flag = flag;
                switch (flag) {
                    case DB.FLAG_UPDATE:
                        form.idCols = getIdCols(row);
                        form.loadUpdate();
                        break;
                    case DB.FLAG_INSERT:
                        form.loadInsert();
                        break;
                }
                form.setVisible(true);
            } else if (FormJDialog.class.isAssignableFrom(frmClass)) {
                FormJDialog form = (FormJDialog) Class.forName(formName).newInstance();
                form.setModal(true);
                form.flag = flag;
                switch (flag) {
                    case DB.FLAG_UPDATE:
                        form.idCols = getIdCols(row);
                        form.loadUpdate();
                        break;
                    case DB.FLAG_INSERT:
                        form.loadInsert();
                        break;
                }
                form.setVisible(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(ListJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Object[] getIdCols(int row) {
        Object[] idCols = new Object[idColumn.length]; //Cria um array que armazenará os campos chave da tabela (em ordem)
        int colIdHidden = 0; //É definido aqui fora porque as colunas ocultas sempre estarão em ordem, então não precisa navegar por todas elas a cada loop
        // Busca os campos de chave
        for (int col = 0; col < idColumn.length; col++) {
            boolean isFound = false;
            for (; colIdHidden < idColumnHidden.size(); colIdHidden++) { //Passa por todos os campos chave ocultos
                if (idColumn[col].equals(idColumnHidden.get(colIdHidden))) {
                    idCols[col] = tblList.getValueAt(row, col);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) { //Se não encontrou nas colunas ocultas, está nas exibidas
                for (int iListed = 0; iListed < idColumnListed.size(); iListed++) { //Passa por todos os campos chave exibidos
                    if (idColumn[col].equals(idColumnListed.get(iListed))) { //É este campo que estamos procurando?
                        for (int iTblField = 0; iTblField < listTableFields.length; iTblField++) { //Navega nas colunas da tabela
                            if (idColumnListed.get(iListed).equals((String) listTableFields[iTblField][1])) { //Esta coluna contêm o campo que estamos procurando?
                                idCols[col] = tblList.getValueAt(row, iTblField+idColumnHidden.size());
                                isFound = true;
                                break;
                            }
                        }
                        if (isFound)
                            break;
                    }
                }
            }
        }
        return idCols;
    }
}

class DeleteCellRenderer extends DefaultTableCellRenderer {
    public DeleteCellRenderer() {
        this.setIcon(ListJFrame.iconDelete);
        this.setHorizontalAlignment(SwingConstants.CENTER);
//        this.addMouseListener(new MouseAdapter(){
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("Mouse clicked (# of clicks: " + e.getClickCount() + ")");
//            }
//        });
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