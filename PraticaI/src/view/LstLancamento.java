/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.ContaCapital;
import model.Lancamento;
import model.Log;
import model.PlanoContas;
import model.Usuario;
import reflection.ListJFrame;
import util.DB;
import util.ImageSize;
import util.Util;
import util.field.ComboBoxItem;

/**
 *
 * @author Dinei A. Rockenbach
 */
public class LstLancamento extends javax.swing.JFrame {
    
    public static final ImageIcon iconEfetivar = new ImageIcon(Util.getIconUrl("accept.png"));
    public static final ImageIcon iconEfetivarInativo = new ImageIcon(Util.getIconUrl("accept_gray.png"));
    public static final ImageIcon iconEstornar = new ImageIcon(Util.getIconUrl("cross.png"));
    public static final ImageIcon iconEstornarInativo = new ImageIcon(Util.getIconUrl("cross_gray.png"));
    public static final ImageIcon iconQuestion = new ImageIcon(Util.getImageUrl("question.png", ImageSize.M));
    
    public static final int colLanDataHora = 0;
    public static final int colLanContaCapital = 1;
    public static final int colLanPlanoContas = 2;
    public static final int colLanDescricao = 3;
    public static final int colLanValorSaida = 4;
    public static final int colLanValorEntrada = 5;
    public static final int colBtnEfetivar = 6;
    public static final int colBtnEstornar = 7;
    public static final int colCntCodigo = 8;
    public static final int colLanCodigo = 9;
    public static final int colLanEfetivado = 10;
    public static final int colLanEstornado = 11;
    
    public static int rowTotal = 0;
    
    private ArrayList<ComboBoxItem> cboxItensContaCapital = new ArrayList<>();
    private ArrayList<ComboBoxItem> cboxItensPlanoContas = new ArrayList<>();
    
    private ArrayList<ContaCapital> ContasDeCapital = ContaCapital.getAll();
    private ArrayList<PlanoContas> PlanosDeContas = PlanoContas.getAllOrdered();
    
    /**
     * Creates new form LstLancamento
     */
    public LstLancamento() {
        java.net.URL urlImage = Util.getImageUrl(Lancamento.iconTitle, ImageSize.M);
        if (urlImage != null)
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(urlImage));
        
        initComponents();
        
        this.setLocationRelativeTo(null);
        
        btnFiltrar.setIcon(new ImageIcon(Util.getIconUrl("filter.png")));
        btnAddEntrada.setIcon(new ImageIcon(Util.getIconUrl("moneyadd.png")));
        btnAddSaida.setIcon(new ImageIcon(Util.getIconUrl("moneydelete.png")));
        
        boolean[] acessos = Usuario.UsuLogado.verificaAcesso(Lancamento.fncNome);
        if (!acessos[Usuario.IDX_INSERIR]) {
            //JOptionPane.showMessageDialog(this, "Usuário sem acesso ao objeto '" + Lancamento.fncNome + "'", "Sem acesso", JOptionPane.WARNING_MESSAGE);
            btnAddEntrada.setVisible(false);
            btnAddSaida.setVisible(false);
        }
        
        for (Enumeration<TableColumn> tblCol = tbl.getColumnModel().getColumns(); tblCol.hasMoreElements();)
            tblCol.nextElement().setCellRenderer(new LancamentoCellRenderer());
        
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = tbl.columnAtPoint(e.getPoint());
                int row = tbl.rowAtPoint(e.getPoint());
                if (row != rowTotal) {
                    switch (col) {
                        case colBtnEfetivar:
                            efetivar(row);
                            break;
                        case colBtnEstornar:
                            estornar(row);
                            break;
                        default:
                            if (e.getClickCount() > 1)
                                updateRow(row);
                            break;
                    }
                }
            }
        });
        
        // Preenche os filtros
        cboxItensContaCapital.add(new ComboBoxItem(0, "")); //Empty Item
        for (ContaCapital cc : ContasDeCapital) 
            cboxItensContaCapital.add(new ComboBoxItem(cc.getCntCodigo(), cc.getCntNome()));
        cmbContaCapital.setModel(new DefaultComboBoxModel((ComboBoxItem[]) cboxItensContaCapital.toArray(new ComboBoxItem[cboxItensContaCapital.size()])));
        
        cboxItensPlanoContas.add(new ComboBoxItem(0, "")); //Empty Item
        for (PlanoContas pc : PlanosDeContas)
            cboxItensPlanoContas.add(new ComboBoxItem(pc.getPlnCodigo(), pc.getPlnNome()));
        cmbPlanoContas.setModel(new DefaultComboBoxModel((ComboBoxItem[]) cboxItensPlanoContas.toArray(new ComboBoxItem[cboxItensPlanoContas.size()])));
        
        // Filtros default
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_MONTH, -3);
        txtLanDataHoraFrom.setText(Util.getFormattedDate(cl));
        cl.add(Calendar.DAY_OF_MONTH, +3);
        txtLanDataHoraTo.setText(Util.getFormattedDate(cl));
        
        listar();
    }
    
    public void listar() {
        int CntCodigo = 0;
        if (cmbContaCapital.getSelectedItem() != null) {
            for (ContaCapital cc : ContasDeCapital) {
                if ((int) ((ComboBoxItem) cmbContaCapital.getSelectedItem()).getId() == cc.getCntCodigo())
                    CntCodigo = cc.getCntCodigo();
            }
        }
        
        int PlnCodigo = 0;
        if (cmbPlanoContas.getSelectedItem() != null) {
            for (PlanoContas pln : PlanosDeContas) {
                if ((int) ((ComboBoxItem) cmbPlanoContas.getSelectedItem()).getId() == pln.getPlnCodigo())
                    PlnCodigo = pln.getPlnCodigo();
            }
        }
        
        ResultSet rs = Lancamento.getList(CntCodigo, PlnCodigo, txtLanDescricao.getText(), Util.getTimestampFromString(txtLanDataHoraFrom.getText() + " 00:00:00"), Util.getTimestampFromString(txtLanDataHoraTo.getText() + " 23:59:59"), null);
        
        DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
        dtm.setNumRows(0);
        try {
            double totalSaida = 0;
            double totalEntrada = 0;
            
            if (rs.next()) {
                do { //invertido porque ele já deu um next no if
                    Object[] row = new Object[tbl.getColumnCount()];
                    row[colLanDataHora] = DB.formatColumn(rs.getTimestamp("LanDataHora"));
                    row[colLanContaCapital] = rs.getString("CntNome");
                    row[colLanPlanoContas] = rs.getString("PlnNome");
                    row[colLanDescricao] = rs.getString("LanDescricao");
                    row[colLanValorSaida] = Util.getFormattedMoney(rs.getDouble("LanValorSaida"));
                    row[colLanValorEntrada] = Util.getFormattedMoney(rs.getDouble("LanValorEntrada"));
                    row[colBtnEfetivar] = null;
                    row[colCntCodigo] = rs.getInt("CntCodigo");
                    row[colLanCodigo] = rs.getInt("LanCodigo");
                    row[colLanEfetivado] = rs.getBoolean("LanEfetivado");
                    row[colLanEstornado] = rs.getBoolean("LanEstornado");
                    dtm.addRow(row);
                    
                    totalSaida += rs.getDouble("LanValorSaida");
                    totalEntrada += rs.getDouble("LanValorEntrada");
                } while (rs.next());
                Object[] row = new Object[tbl.getColumnCount()];
                row[colLanDataHora] = null;
                row[colLanContaCapital] = null;
                row[colLanPlanoContas] = null;
                row[colLanDescricao] = "Total:";
                row[colLanValorSaida] = Util.getFormattedMoney(totalSaida);
                row[colLanValorEntrada] = Util.getFormattedMoney(totalEntrada);
                row[colBtnEfetivar] = null;
                row[colCntCodigo] = null;
                row[colLanCodigo] = null;
                row[colLanEfetivado] = null;
                row[colLanEstornado] = null;
                dtm.addRow(row);
                
                rowTotal = dtm.getRowCount()-1;
            }
        } catch (SQLException ex) {
            Log.log(Lancamento.fncNome, Log.INT_OUTRA, "Falha na listagem de lançamentos [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
            Logger.getLogger(LstLancamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateRow(int row) {
        boolean LanEfetivado = (boolean) tbl.getValueAt(row, colLanEfetivado);
        boolean LanEstornado = (boolean) tbl.getValueAt(row, colLanEstornado);
        if (!LanEfetivado && !LanEstornado) {
            FrmLancamento frm = new FrmLancamento();
            frm.idCols = new Object[]{(int) tbl.getValueAt(row, colCntCodigo), (int) tbl.getValueAt(row, colLanCodigo)};
            frm.loadUpdate();
            frm.setVisible(true);
        }
    }
    
    public void efetivar(int row) {
        if (!(boolean) tbl.getValueAt(row, colLanEfetivado)) {
            int opt = JOptionPane.showConfirmDialog(rootPane, 
                    "Deseja realmente efetivar o lançamento: '" + (String) tbl.getValueAt(row, colLanDescricao) + "'?\r\n"
                    + "Esta opção é irreversível.", 
                    "Efetivar lançamento", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    iconQuestion);
            if (opt == JOptionPane.YES_OPTION) {
                Lancamento lan = new Lancamento();
                lan.load((int) tbl.getValueAt(row, colCntCodigo), (int) tbl.getValueAt(row, colLanCodigo));
                lan.efetivar();
                listar();
            }
        }
    }
    
    public void estornar(int row) {
        if (!(boolean) tbl.getValueAt(row, colLanEstornado)) {
            int opt = JOptionPane.showConfirmDialog(rootPane, 
                    "Deseja realmente estornar o lançamento: '" + (String) tbl.getValueAt(row, colLanDescricao) + "'?\r\n"
                    + "Esta opção é irreversível.", 
                    "Estornar lançamento", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    iconQuestion);
            if (opt == JOptionPane.YES_OPTION) {
                Lancamento lan = new Lancamento();
                lan.load((int) tbl.getValueAt(row, colCntCodigo), (int) tbl.getValueAt(row, colLanCodigo));
                lan.estornar();
                listar();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmbContaCapital = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cmbPlanoContas = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtLanDescricao = new javax.swing.JTextField();
        btnFiltrar = new javax.swing.JButton();
        btnAddEntrada = new javax.swing.JButton();
        btnAddSaida = new javax.swing.JButton();
        txtLanDataHoraFrom = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtLanDataHoraTo = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Conta de Capital");

        jLabel2.setText("Plano de Contas");

        jLabel3.setText("Descrição");

        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        btnAddEntrada.setText("Lançar Entrada");
        btnAddEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEntradaActionPerformed(evt);
            }
        });

        btnAddSaida.setText("Lançar Saída");
        btnAddSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSaidaActionPerformed(evt);
            }
        });

        try {
            txtLanDataHoraFrom.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtLanDataHoraFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLanDataHoraFromActionPerformed(evt);
            }
        });

        jLabel4.setText("De:");

        jLabel5.setText("até");

        try {
            txtLanDataHoraTo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Data", "Conta de Capital", "Plano de Contas", "Descrição", "Saída", "Entrada", "Efetivar", "Estornar", "CntCodigo", "LanCodigo", "LanEfetivado", "LanEstornado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(0).setMinWidth(100);
            tbl.getColumnModel().getColumn(0).setPreferredWidth(100);
            tbl.getColumnModel().getColumn(6).setResizable(false);
            tbl.getColumnModel().getColumn(6).setPreferredWidth(35);
            tbl.getColumnModel().getColumn(7).setResizable(false);
            tbl.getColumnModel().getColumn(7).setPreferredWidth(35);
            tbl.getColumnModel().getColumn(8).setMinWidth(0);
            tbl.getColumnModel().getColumn(8).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(8).setMaxWidth(0);
            tbl.getColumnModel().getColumn(9).setMinWidth(0);
            tbl.getColumnModel().getColumn(9).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(9).setMaxWidth(0);
            tbl.getColumnModel().getColumn(10).setMinWidth(0);
            tbl.getColumnModel().getColumn(10).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(10).setMaxWidth(0);
            tbl.getColumnModel().getColumn(11).setMinWidth(0);
            tbl.getColumnModel().getColumn(11).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbContaCapital, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLanDescricao)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbPlanoContas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtLanDataHoraFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLanDataHoraTo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 149, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbContaCapital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cmbPlanoContas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtLanDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLanDataHoraFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtLanDataHoraTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFiltrar)
                    .addComponent(btnAddEntrada)
                    .addComponent(btnAddSaida))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        listar();
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnAddEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEntradaActionPerformed
        FrmLancamento frm = new FrmLancamento();
        frm.loadInsert();
        frm.setEntrada();
        frm.setVisible(true);
    }//GEN-LAST:event_btnAddEntradaActionPerformed

    private void btnAddSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSaidaActionPerformed
        FrmLancamento frm = new FrmLancamento();
        frm.loadInsert();
        frm.setSaida();
        frm.setVisible(true);
    }//GEN-LAST:event_btnAddSaidaActionPerformed

    private void txtLanDataHoraFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLanDataHoraFromActionPerformed
        listar();
    }//GEN-LAST:event_txtLanDataHoraFromActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LstLancamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LstLancamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LstLancamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LstLancamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LstLancamento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddEntrada;
    private javax.swing.JButton btnAddSaida;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JComboBox cmbContaCapital;
    private javax.swing.JComboBox cmbPlanoContas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl;
    private javax.swing.JFormattedTextField txtLanDataHoraFrom;
    private javax.swing.JFormattedTextField txtLanDataHoraTo;
    private javax.swing.JTextField txtLanDescricao;
    // End of variables declaration//GEN-END:variables
}

class LancamentoCellRenderer extends DefaultTableCellRenderer {
    
    public LancamentoCellRenderer() {
    }
    
    @Override
    public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (row == LstLancamento.rowTotal) {
            Map attributes = this.getFont().getAttributes();
            attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
            this.setFont(new Font(attributes));
            switch (column) {
                case LstLancamento.colLanDescricao:
                    this.setHorizontalAlignment(SwingConstants.LEFT);
                    this.setForeground(Color.black);
                    break;
                case LstLancamento.colLanValorSaida:
                    this.setHorizontalAlignment(SwingConstants.RIGHT);
                    if (isSelected)
                        this.setForeground(Lancamento.COR_SAIDA_SEL);
                    else
                        this.setForeground(Lancamento.COR_SAIDA);
                    break;
                case LstLancamento.colLanValorEntrada:
                    this.setHorizontalAlignment(SwingConstants.RIGHT);
                    if (isSelected)
                        this.setForeground(Lancamento.COR_ENTRADA_SEL);
                    else
                        this.setForeground(Lancamento.COR_ENTRADA);
                    break;
                default:
                    this.setIcon(null);
                    this.setForeground(Color.black);
                    break;
            }
        } else {
            boolean LanEfetivado = (boolean) table.getValueAt(row, LstLancamento.colLanEfetivado);
            boolean LanEstornado = (boolean) table.getValueAt(row, LstLancamento.colLanEstornado);

            Map attributes = this.getFont().getAttributes();
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            Font fontEstornado = new Font(attributes);

            switch (column) {
                case LstLancamento.colLanDataHora:
                case LstLancamento.colLanContaCapital:
                case LstLancamento.colLanPlanoContas:
                case LstLancamento.colLanDescricao:
                    this.setHorizontalAlignment(SwingConstants.LEFT);
                    if (LanEstornado) {
                        this.setFont(fontEstornado);
                        this.setForeground(Lancamento.COR_ESTORNADO);
                    } else {
                        if (LanEfetivado)
                            this.setForeground(Color.black);
                        else
                            if (isSelected)
                                this.setForeground(Lancamento.COR_INATIVO_SEL);
                            else
                                this.setForeground(Lancamento.COR_INATIVO);
                    }
                    break;
                case LstLancamento.colLanValorSaida:
                    this.setHorizontalAlignment(SwingConstants.RIGHT);
                    if (LanEstornado) {
                        this.setFont(fontEstornado);
                        this.setForeground(Lancamento.COR_ESTORNADO);
                    } else {
                        if (LanEfetivado)
                            if (isSelected)
                                this.setForeground(Lancamento.COR_SAIDA_SEL);
                            else
                                this.setForeground(Lancamento.COR_SAIDA);
                        else
                            if (isSelected)
                                this.setForeground(Lancamento.COR_INATIVO_SEL);
                            else
                                this.setForeground(Lancamento.COR_INATIVO);
                    }
                    break;
                case LstLancamento.colLanValorEntrada:
                    this.setHorizontalAlignment(SwingConstants.RIGHT);
                    if (LanEstornado) {
                        this.setFont(fontEstornado);
                        this.setForeground(Lancamento.COR_ESTORNADO);
                    } else {
                        if (LanEfetivado)
                            if (isSelected)
                                this.setForeground(Lancamento.COR_ENTRADA_SEL);
                            else
                                this.setForeground(Lancamento.COR_ENTRADA);
                        else
                            if (isSelected)
                                this.setForeground(Lancamento.COR_INATIVO_SEL);
                            else
                                this.setForeground(Lancamento.COR_INATIVO);
                    }
                    break;
                case LstLancamento.colBtnEfetivar:
                    this.setHorizontalAlignment(SwingConstants.CENTER);
                    this.setText(null);
                    if (LanEfetivado || LanEstornado)
                        this.setIcon(LstLancamento.iconEfetivarInativo);
                    else
                        this.setIcon(LstLancamento.iconEfetivar);
                    break;
                case LstLancamento.colBtnEstornar:
                    this.setHorizontalAlignment(SwingConstants.CENTER);
                    this.setText(null);
                    if (LanEstornado)
                        this.setIcon(LstLancamento.iconEstornarInativo);
                    else
                        this.setIcon(LstLancamento.iconEstornar);
                    break;
            }
        }
        return this;
    }
}
