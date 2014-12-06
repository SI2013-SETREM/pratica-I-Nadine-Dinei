
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.font.TextAttribute;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import model.FechamentoCaixa;
import model.Lancamento;
import model.Log;
import model.Usuario;
import util.DB;
import util.ImageSize;
import util.Util;
import util.field.ComboBoxItem;

/**
 *
 * @author Dinei A. Rockenbach
 */
public class FecharPeriodo extends javax.swing.JFrame {
    
//    public static final ImageIcon iconEfetivar = new ImageIcon(Util.getIconUrl("accept.png"));
//    public static final ImageIcon iconEfetivarInativo = new ImageIcon(Util.getIconUrl("accept_gray.png"));
//    public static final ImageIcon iconEstornar = new ImageIcon(Util.getIconUrl("cross.png"));
//    public static final ImageIcon iconEstornarInativo = new ImageIcon(Util.getIconUrl("cross_gray.png"));
    public static final ImageIcon iconSuccess = new ImageIcon(Util.getImageUrl("accept.png", ImageSize.M));
    
    public static final int colLanDataHora = 0;
//    public static final int colLanContaCapital = 1;
    public static final int colLanPlanoContas = 1;
    public static final int colLanPessoa = 2;
    public static final int colLanVenda = 3;
    public static final int colLanDescricao = 4;
    public static final int colLanValorSaida = 5;
    public static final int colLanValorEntrada = 6;
//    public static final int colBtnEfetivar = 7;
//    public static final int colBtnEstornar = 8;
    public static final int colCntCodigo = 7;
    public static final int colLanCodigo = 8;
    public static final int colLanEfetivado = 9;
    public static final int colLanEstornado = 10;
    
    public static int rowTotal = 0;
    
    private ContaCapital contaCapital = null;
    
    Timestamp LanDataHoraFrom = null;
    Timestamp LanDataHoraTo = util.Util.getNow();
    
    private ArrayList<ComboBoxItem> cboxItensContaCapital = new ArrayList<>();
    private ArrayList<ContaCapital> ContasDeCapital = ContaCapital.getAll();
    
    /**
     * Creates new form LstLancamento
     */
    public FecharPeriodo() {
        java.net.URL urlImage = Util.getImageUrl(FechamentoCaixa.iconTitle, ImageSize.M);
        if (urlImage != null)
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(urlImage));
        
        initComponents();
        
        this.setLocationRelativeTo(null);
        
        btnFecharPeriodo.setIcon(new ImageIcon(Util.getIconUrl("accept.png")));
        
        boolean[] acessos = Usuario.UsuLogado.verificaAcesso(FechamentoCaixa.fncNome);
        if (!acessos[Usuario.IDX_INSERIR]) {
            JOptionPane.showMessageDialog(this, "Usuário sem acesso ao objeto '" + FechamentoCaixa.fncNome + "'", "Sem acesso", JOptionPane.WARNING_MESSAGE);
            dispose();
        }
        
        for (Enumeration<TableColumn> tblCol = tbl.getColumnModel().getColumns(); tblCol.hasMoreElements();)
            tblCol.nextElement().setCellRenderer(new FecharPeriodoCellRenderer());
        
//        tbl.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int col = tbl.columnAtPoint(e.getPoint());
//                int row = tbl.rowAtPoint(e.getPoint());
//                if (row != rowTotal) {
//                    switch (col) {
//                        case colBtnEfetivar:
//                            efetivar(row);
//                            break;
//                        case colBtnEstornar:
//                            estornar(row);
//                            break;
//                        default:
//                            if (e.getClickCount() > 1)
//                                updateRow(row);
//                            break;
//                    }
//                }
//            }
//        });
        
        // Preenche os filtros
        for (ContaCapital cc : ContasDeCapital) 
            cboxItensContaCapital.add(new ComboBoxItem(cc, cc.getCntNome()));
        cmbContaCapital.setModel(new DefaultComboBoxModel((ComboBoxItem[]) cboxItensContaCapital.toArray(new ComboBoxItem[cboxItensContaCapital.size()])));
        
        if (ContasDeCapital.size() > 0) {
            setContaCapital(ContasDeCapital.get(0));
        }
        listar();
    }

    public ContaCapital getContaCapital() {
        return contaCapital;
    }

    public void setContaCapital(ContaCapital contaCapital) {
        this.contaCapital = contaCapital;
        contaCapitalSelected();
    }
    
    public void listar() {
        ContaCapital contaCapital = null;
        if (cmbContaCapital.getSelectedItem() != null) {
            for (ContaCapital cc : ContasDeCapital) {
                if ((ContaCapital) ((ComboBoxItem) cmbContaCapital.getSelectedItem()).getId() == cc)
                    contaCapital = cc;
            }
        }
        
        ResultSet rs = Lancamento.getList(contaCapital, LanDataHoraFrom, LanDataHoraTo);
        
        DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
        dtm.setNumRows(0);
        try {
            double totalSaida = 0;
            double totalEntrada = 0;
            
            if (rs.next()) {
                do { //invertido porque ele já deu um next no if
                    Object[] row = new Object[tbl.getColumnCount()];
                    row[colLanDataHora] = DB.formatColumn(rs.getTimestamp("LanDataHora"));
                    row[colLanPlanoContas] = rs.getString("PlnNome");
                    row[colLanPessoa] = rs.getString("PesNome");
                    
                    row[colLanVenda] = "";
                    int VenCodigo = rs.getInt("VenCodigo");
                    if (VenCodigo != 0) {
                        java.sql.Date VenData = rs.getDate("VenData");
                        if (VenData != null)
                            row[colLanVenda] += util.Util.getFormattedDate(VenData) + " - ";
                        row[colLanVenda] += util.Util.getFormattedMoney(rs.getDouble("VenValorFinal"));
                    }
                    
                    row[colLanDescricao] = rs.getString("LanDescricao");
                    row[colLanValorSaida] = Util.getFormattedMoney(rs.getDouble("LanValorSaida"));
                    row[colLanValorEntrada] = Util.getFormattedMoney(rs.getDouble("LanValorEntrada"));
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
//                row[colLanContaCapital] = null;
                row[colLanPlanoContas] = null;
                row[colLanPessoa] = null;
                row[colLanVenda] = null;
                row[colLanDescricao] = "Total:";
                row[colLanValorSaida] = Util.getFormattedMoney(totalSaida);
                row[colLanValorEntrada] = Util.getFormattedMoney(totalEntrada);
                row[colCntCodigo] = null;
                row[colLanCodigo] = null;
                row[colLanEfetivado] = null;
                row[colLanEstornado] = null;
                dtm.addRow(row);
                
                rowTotal = dtm.getRowCount()-1;
            }
        } catch (SQLException ex) {
            Log.log(Lancamento.fncNome, Log.INT_OUTRA, "Falha na listagem de lançamentos [" + ex.getErrorCode() + " - " + ex.getMessage() + "]", Log.NV_ERRO);
            Logger.getLogger(FecharPeriodo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void contaCapitalSelected() {
        ContaCapital cc = getContaCapital();
        if (cc != null) {
            pnlDadosConta.setVisible(true);
            txtCntBncNumero.setText(cc.getCntBncNumero());
            txtCntBncAgencia.setText(cc.getCntBncAgencia());
            txtCntBncTitular.setText(cc.getCntBncTitular());
            txtCntPadrao.setText(cc.getCntPadrao() ? "Sim" : "Não");
            txtCntSaldo.setText(util.Util.getFormattedMoney(cc.getCntSaldo()));
            FechamentoCaixa fchCx = FechamentoCaixa.getLast(contaCapital);
            if (fchCx == null) {
                txtUltFechamento.setText("");
                LanDataHoraFrom = null;
                txtPeriodo.setText("Lançamentos da conta registrados até " + util.Util.getFormattedDateTime(LanDataHoraTo));
            } else {
                txtUltFechamento.setText(util.Util.getFormattedDateTime(fchCx.getFchDataHora()));
                LanDataHoraFrom = fchCx.getFchDataHora();
                txtPeriodo.setText("Lançamentos da conta no período de " + util.Util.getFormattedDateTime(LanDataHoraFrom) + " a " + util.Util.getFormattedDateTime(LanDataHoraTo));
            }
            
            listar();
        } else {
            pnlDadosConta.setVisible(false);
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

        rdgStatus = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        cmbContaCapital = new javax.swing.JComboBox();
        btnFecharPeriodo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        txtPeriodo = new javax.swing.JLabel();
        pnlDadosConta = new javax.swing.JPanel();
        lblDadosConta = new javax.swing.JLabel();
        txtCntBncNumero = new javax.swing.JTextField();
        lblDadosConta3 = new javax.swing.JLabel();
        txtCntPadrao = new javax.swing.JTextField();
        lblDadosConta1 = new javax.swing.JLabel();
        txtCntBncAgencia = new javax.swing.JTextField();
        lblDadosConta2 = new javax.swing.JLabel();
        txtCntBncTitular = new javax.swing.JTextField();
        lblDadosConta4 = new javax.swing.JLabel();
        txtCntSaldo = new javax.swing.JTextField();
        lblDadosConta5 = new javax.swing.JLabel();
        txtUltFechamento = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Conta de Capital");

        cmbContaCapital.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbContaCapitalItemStateChanged(evt);
            }
        });

        btnFecharPeriodo.setText("Fechar Período");
        btnFecharPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharPeriodoActionPerformed(evt);
            }
        });

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Data", "Plano de Contas", "Pessoa", "Venda", "Descrição", "Saída", "Entrada", "CntCodigo", "LanCodigo", "LanEfetivado", "LanEstornado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(0).setMinWidth(100);
            tbl.getColumnModel().getColumn(0).setPreferredWidth(100);
            tbl.getColumnModel().getColumn(7).setMinWidth(0);
            tbl.getColumnModel().getColumn(7).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(7).setMaxWidth(0);
            tbl.getColumnModel().getColumn(8).setMinWidth(0);
            tbl.getColumnModel().getColumn(8).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(8).setMaxWidth(0);
            tbl.getColumnModel().getColumn(9).setMinWidth(0);
            tbl.getColumnModel().getColumn(9).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(9).setMaxWidth(0);
            tbl.getColumnModel().getColumn(10).setMinWidth(0);
            tbl.getColumnModel().getColumn(10).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        txtPeriodo.setText("Lançamentos da conta no período de");

        lblDadosConta.setText("Número da Conta:");

        txtCntBncNumero.setEnabled(false);

        lblDadosConta3.setText("Conta Padrão?");

        txtCntPadrao.setEnabled(false);

        lblDadosConta1.setText("Agência:");

        txtCntBncAgencia.setEnabled(false);

        lblDadosConta2.setText("Titular:");

        txtCntBncTitular.setEnabled(false);

        lblDadosConta4.setText("Saldo Atual:");

        txtCntSaldo.setEnabled(false);

        lblDadosConta5.setText("Último Fechamento:");

        txtUltFechamento.setEnabled(false);

        javax.swing.GroupLayout pnlDadosContaLayout = new javax.swing.GroupLayout(pnlDadosConta);
        pnlDadosConta.setLayout(pnlDadosContaLayout);
        pnlDadosContaLayout.setHorizontalGroup(
            pnlDadosContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDadosContaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDadosContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDadosContaLayout.createSequentialGroup()
                        .addComponent(lblDadosConta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCntBncNumero, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                    .addGroup(pnlDadosContaLayout.createSequentialGroup()
                        .addComponent(lblDadosConta3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCntPadrao)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDadosContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDadosContaLayout.createSequentialGroup()
                        .addComponent(lblDadosConta4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCntSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDadosContaLayout.createSequentialGroup()
                        .addComponent(lblDadosConta1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCntBncAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlDadosContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlDadosContaLayout.createSequentialGroup()
                        .addComponent(lblDadosConta5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUltFechamento))
                    .addGroup(pnlDadosContaLayout.createSequentialGroup()
                        .addComponent(lblDadosConta2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCntBncTitular, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlDadosContaLayout.setVerticalGroup(
            pnlDadosContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDadosContaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDadosContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDadosContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDadosConta)
                        .addComponent(txtCntBncNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCntBncTitular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDadosConta1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDadosContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCntBncAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDadosConta2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDadosContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDadosConta3)
                    .addComponent(txtCntPadrao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDadosConta4)
                    .addComponent(txtCntSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDadosConta5)
                    .addComponent(txtUltFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnFecharPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbContaCapital, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtPeriodo)
                            .addComponent(pnlDadosConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 95, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbContaCapital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDadosConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPeriodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFecharPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharPeriodoActionPerformed
        FechamentoCaixa fch = new FechamentoCaixa();
        fch.setCntCodigo(contaCapital);
        fch.setUsuCodigo(Usuario.UsuLogado);
        fch.setFchDataHora(LanDataHoraTo);
        fch.setFchSaldo(contaCapital.getCntSaldo());
        if (fch.save()) {
            JOptionPane.showMessageDialog(rootPane, "Período fechado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE, iconSuccess);
            LanDataHoraFrom = util.Util.getNow();
            LanDataHoraTo = util.Util.getNow();
            contaCapitalSelected();
        }
    }//GEN-LAST:event_btnFecharPeriodoActionPerformed

    private void cmbContaCapitalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbContaCapitalItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            ContaCapital contaCapital = (ContaCapital) ((ComboBoxItem) cmbContaCapital.getSelectedItem()).getId();
            setContaCapital(contaCapital);
        }
    }//GEN-LAST:event_cmbContaCapitalItemStateChanged

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
            java.util.logging.Logger.getLogger(FecharPeriodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FecharPeriodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FecharPeriodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FecharPeriodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FecharPeriodo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFecharPeriodo;
    private javax.swing.JComboBox cmbContaCapital;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDadosConta;
    private javax.swing.JLabel lblDadosConta1;
    private javax.swing.JLabel lblDadosConta2;
    private javax.swing.JLabel lblDadosConta3;
    private javax.swing.JLabel lblDadosConta4;
    private javax.swing.JLabel lblDadosConta5;
    private javax.swing.JPanel pnlDadosConta;
    private javax.swing.ButtonGroup rdgStatus;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtCntBncAgencia;
    private javax.swing.JTextField txtCntBncNumero;
    private javax.swing.JTextField txtCntBncTitular;
    private javax.swing.JTextField txtCntPadrao;
    private javax.swing.JTextField txtCntSaldo;
    private javax.swing.JLabel txtPeriodo;
    private javax.swing.JTextField txtUltFechamento;
    // End of variables declaration//GEN-END:variables
}

class FecharPeriodoCellRenderer extends DefaultTableCellRenderer {
    
    public FecharPeriodoCellRenderer() {
    }
    
    @Override
    public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (row == FecharPeriodo.rowTotal) {
            Map attributes = this.getFont().getAttributes();
            attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
            this.setFont(new Font(attributes));
            switch (column) {
                case FecharPeriodo.colLanDescricao:
                    this.setHorizontalAlignment(SwingConstants.LEFT);
                    this.setForeground(Color.black);
                    break;
                case FecharPeriodo.colLanValorSaida:
                    this.setHorizontalAlignment(SwingConstants.RIGHT);
                    if (isSelected)
                        this.setForeground(Lancamento.COR_SAIDA_SEL);
                    else
                        this.setForeground(Lancamento.COR_SAIDA);
                    break;
                case FecharPeriodo.colLanValorEntrada:
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
            boolean LanEfetivado = (boolean) table.getValueAt(row, FecharPeriodo.colLanEfetivado);
            boolean LanEstornado = (boolean) table.getValueAt(row, FecharPeriodo.colLanEstornado);

            Map attributes = this.getFont().getAttributes();
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            Font fontEstornado = new Font(attributes);

            switch (column) {
                case FecharPeriodo.colLanDataHora:
                case FecharPeriodo.colLanPlanoContas:
                case FecharPeriodo.colLanPessoa:
                case FecharPeriodo.colLanVenda:
                case FecharPeriodo.colLanDescricao:
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
                case FecharPeriodo.colLanValorSaida:
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
                case FecharPeriodo.colLanValorEntrada:
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
            }
        }
        return this;
    }
}
