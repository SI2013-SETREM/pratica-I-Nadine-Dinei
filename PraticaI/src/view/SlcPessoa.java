/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import model.Pessoa;
import util.ImageSize;
import util.Util;

/**
 *
 * @author Nadine
 */
public class SlcPessoa extends javax.swing.JDialog {
    
    private Pessoa pessoa = null;
    public boolean isFuncionario = false;
    public boolean isCliente = false;
    public boolean isUsuario = false;
    public boolean isFornecedor = false;
    
    /**
     * Creates new form bscPessoa
     */
    public SlcPessoa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        java.net.URL urlImage = Util.getImageUrl(Pessoa.iconTitle, ImageSize.M);
        if (urlImage != null)
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(urlImage));
        
        initComponents();
        
        this.setLocationRelativeTo(null);
        
        btnSelecionar.setIcon(Util.getImageIconOk());
        btnCancelar.setIcon(Util.getImageIconCancel());
    }
    
    public Pessoa getPessoa() {
        return pessoa;
    }
    
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
    public void listar() {
        DefaultTableModel dados = (DefaultTableModel) tblPessoa.getModel();
        dados.setNumRows(0);
        for (Pessoa p : Pessoa.listBusca(isFuncionario, isCliente, isUsuario, isFornecedor)) {
            dados.addRow(new String[]{String.valueOf(p.getPesCodigo()), p.getPesNome(), p.getPesCPFCNPJ()});
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
        tblPessoa = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        btnSelecionar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tblPessoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PesCodigo", "Nome", "CPF/CNPJ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPessoa.getTableHeader().setReorderingAllowed(false);
        tblPessoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPessoaMouseClicked(evt);
            }
        });
        tblPessoa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblPessoaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblPessoa);
        if (tblPessoa.getColumnModel().getColumnCount() > 0) {
            tblPessoa.getColumnModel().getColumn(0).setMinWidth(0);
            tblPessoa.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblPessoa.getColumnModel().getColumn(0).setMaxWidth(0);
            tblPessoa.getColumnModel().getColumn(1).setPreferredWidth(290);
            tblPessoa.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSelecionar.setText("Selecionar");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSelecionar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        listar();
    }//GEN-LAST:event_formWindowOpened

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        pessoa = null;
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tblPessoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPessoaMouseClicked
        if (evt.getClickCount() > 1)
            btnSelecionarActionPerformed(null);
    }//GEN-LAST:event_tblPessoaMouseClicked

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        if (tblPessoa.getSelectedRow() != -1) {
            pessoa = new Pessoa();
            pessoa.load(Integer.parseInt(String.valueOf(tblPessoa.getValueAt(tblPessoa.getSelectedRow(), 0))));
            this.dispose();
        }
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void tblPessoaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblPessoaKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) 
            btnSelecionarActionPerformed(null);
    }//GEN-LAST:event_tblPessoaKeyPressed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SlcPessoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SlcPessoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SlcPessoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SlcPessoa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SlcPessoa dialog = new SlcPessoa(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPessoa;
    // End of variables declaration//GEN-END:variables
}
