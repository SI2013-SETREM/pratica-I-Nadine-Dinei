/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.Lancamento;
import model.Log;
import model.Usuario;
import reflection.ListJFrame;
import util.DB;
import util.ImageSize;
import util.Util;

/**
 *
 * @author Dinei A. Rockenbach
 */
public class LstLancamento extends javax.swing.JFrame {
    
    public static final ImageIcon iconEfetivar = new ImageIcon(Util.getIconUrl("accept.png"));
    public static final ImageIcon iconEfetivarInativo = new ImageIcon(Util.getIconUrl("accept_gray.png"));
    public static final ImageIcon iconQuestion = new ImageIcon(Util.getImageUrl("question.png", ImageSize.M));
    
    public static final int colLanDataHora = 0;
    public static final int colLanContaCapital = 1;
    public static final int colLanPlanoContas = 2;
    public static final int colLanDescricao = 3;
    public static final int colLanValorSaida = 4;
    public static final int colLanValorEntrada = 5;
    public static final int colBtnEfetivar = 6;
    public static final int colCntCodigo = 7;
    public static final int colLanCodigo = 8;
    public static final int colLanEfetivado = 9;
    
    /**
     * Creates new form LstLancamento
     */
    public LstLancamento() {
        java.net.URL urlImage = Util.getImageUrl("money.png", ImageSize.M);
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
                if (col == colBtnEfetivar) {
                    efetivar(row);
                }
//                } else if (e.getClickCount() > 1) {
//                    updateRow(row);
            }
        });
        
        // Filtros default
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_MONTH, -3);
        txtLanDataHoraFrom.setText(Util.getFormattedDate(cl));
        cl.add(Calendar.DAY_OF_MONTH, +3);
        txtLanDataHoraTo.setText(Util.getFormattedDate(cl));
        
        listar();
    }
    
    public void listar() {
        ResultSet rs = Lancamento.getList(0, 0, txtLanDescricao.getText(), null, null, null);
        
        DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
        dtm.setNumRows(0);
        try {
            while (rs.next()) {
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
                dtm.addRow(row);
            }
        } catch (SQLException ex) {
            Log.log(Lancamento.fncNome, Log.INT_OUTRA, "Falha na listagem de lançamentos [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
            Logger.getLogger(LstLancamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void efetivar(int row) {
        if (!(boolean) tbl.getValueAt(row, colLanEfetivado)) {
            int opt = JOptionPane.showConfirmDialog(rootPane, "Deseja realmente efetivar o lançamento:", "Efetivar lançamento", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, iconQuestion);
            if (opt == JOptionPane.YES_OPTION) {
                Lancamento lan = new Lancamento();
                lan.load((int) tbl.getValueAt(row, colCntCodigo), (int) tbl.getValueAt(row, colLanCodigo));
                lan.setLanEfetivado(true);
                lan.save();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Data", "Conta de Capital", "Plano de Contas", "Descrição", "Saída", "Entrada", "", "CntCodigo", "LanCodigo", "LanEfetivado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
            tbl.getColumnModel().getColumn(6).setPreferredWidth(30);
            tbl.getColumnModel().getColumn(7).setMinWidth(0);
            tbl.getColumnModel().getColumn(7).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(7).setMaxWidth(0);
            tbl.getColumnModel().getColumn(8).setMinWidth(0);
            tbl.getColumnModel().getColumn(8).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(8).setMaxWidth(0);
            tbl.getColumnModel().getColumn(9).setMinWidth(0);
            tbl.getColumnModel().getColumn(9).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(9).setMaxWidth(0);
        }

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

        jLabel5.setText("Até");

        try {
            txtLanDataHoraTo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        boolean LanEfetivado = (boolean) table.getValueAt(row, LstLancamento.colLanEfetivado);
        System.out.println(row + "," + column  + " = " + table.getValueAt(row, column) + "; ");
        System.out.println(table.getValueAt(row, LstLancamento.colLanEfetivado));
        switch (column) {
            case LstLancamento.colLanValorSaida:
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
                break;
            case LstLancamento.colLanValorEntrada:
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
                break;
            case LstLancamento.colBtnEfetivar:
                this.setHorizontalAlignment(SwingConstants.CENTER);
                this.setText(null);
                if (LanEfetivado)
                    this.setIcon(LstLancamento.iconEfetivarInativo);
                else
                    this.setIcon(LstLancamento.iconEfetivar);
                break;
        }
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