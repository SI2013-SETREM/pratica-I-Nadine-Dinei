/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.Pessoa;
import model.PessoaTelefone;
import model.Produto;
import model.Venda;
import model.VendaProduto;

/**
 *
 * @author Nadine
 */
public class FrmVenda extends reflection.FormJFrame {

    private Venda venda = new Venda();
    private Cliente cliente;
    private PessoaTelefone[] pesTel;
    private ArrayList<VendaProduto> vendaProdutos = new ArrayList<>();

    /**
     * Creates new form FrmVenda
     */
    public FrmVenda() {
        initComponents();
        this.setTitle("Manutenção De " + Venda.sngTitle);
        ImageIcon icone = new ImageIcon(util.Util.getImageUrl(Venda.iconTitle, util.ImageSize.P));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(icone.getImage());
        btnSalvar.setIcon(new ImageIcon(util.Util.getImageUrl("tick.png", util.ImageSize.P)));
        btnCancelar.setIcon(new ImageIcon(util.Util.getImageUrl("cancel.png", util.ImageSize.P)));
        util.Util.setMoneyField(txtValor);
        util.Util.setMoneyField(txtDesconto);
        util.Util.setMoneyField(txtEntrada);
    }

    @Override
    public void loadUpdate() {
        cliente = new Cliente((int) idCols[0]);
        venda.load(cliente, (int) idCols[1]); //To change body of generated methods, choose Tools | Templates.
        txtDataOperacao.setText(String.valueOf(venda.getVenData()));
        txtDesconto.setText(util.Util.getFormattedMoney(venda.getVenDesconto()));
        txtEntrada.setText(util.Util.getFormattedMoney(venda.getVenEntrada()));
        txtValor.setText(util.Util.getFormattedMoney(venda.getVenValor()));
        fillCliente();
        //lblNumPedido.setText(venda.get);
        lblValorFinal.setText(util.Util.getFormattedMoney(venda.getVenValorFinal()));
        vendaProdutos = new ArrayList<>(Arrays.asList(venda.getVendaProduto()));
        fillTable();
    }

    private void fillCliente() {
        Pessoa pessoa = cliente.getPesCodigo();
        lblCliente.setText(pessoa.getPesNome());
        lblClienteCpf.setText(pessoa.getPesCPFCNPJ());
        String clienteTelefone = "";
        for (PessoaTelefone pTel : PessoaTelefone.getAll(pessoa)) {
            clienteTelefone = pTel.getPesFonTelefone();
            break;
        }
        lblClienteTelefone.setText(clienteTelefone);
    }

    private void fillTable() {
        DefaultTableModel dtm = (DefaultTableModel) tblProdutos.getModel();
        dtm.setNumRows(0);
        for (VendaProduto vp : vendaProdutos) {
            Object[] row = new Object[]{
                vp.getVenPrdNome(),
                util.Util.getFormattedMoney(vp.getVenPrdPreco()),
                vp.getVenPrdQuantidade(),
                vp.getVenPrdTotal(),
                null,
                vp.getVenPrdCodigo()
            };
            dtm.addRow(row);
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

        VendaTipo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblCliente = new javax.swing.JLabel();
        btnSelecionarCliente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtDataOperacao = new javax.swing.JFormattedTextField();
        ckbEfetivada = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        rdbAvista = new javax.swing.JRadioButton();
        rdbAprazo = new javax.swing.JRadioButton();
        lblClienteCpf = new javax.swing.JLabel();
        lblClienteTelefone = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnAddProduto = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        txtValor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDesconto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        lblValorFinal = new javax.swing.JLabel();
        lblNPARCELAS = new javax.swing.JLabel();
        txtNParcelas = new javax.swing.JTextField();
        lblEntrada = new javax.swing.JLabel();
        txtEntrada = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        lblNumPedido = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblCliente.setText("Selecione um cliente...");

        btnSelecionarCliente.setText("...");
        btnSelecionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarClienteActionPerformed(evt);
            }
        });

        jLabel1.setText("Data da Operação:");

        try {
            txtDataOperacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        ckbEfetivada.setSelected(true);
        ckbEfetivada.setText("Efetivada");

        jLabel5.setText("Tipo de pagamento:");

        VendaTipo.add(rdbAvista);
        rdbAvista.setSelected(true);
        rdbAvista.setText("À vista");
        rdbAvista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAvistaActionPerformed(evt);
            }
        });

        VendaTipo.add(rdbAprazo);
        rdbAprazo.setText("À prazo");
        rdbAprazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAprazoActionPerformed(evt);
            }
        });

        lblClienteCpf.setText(" ");

        lblClienteTelefone.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelecionarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataOperacao))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblClienteCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbAvista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbAprazo)
                        .addGap(0, 35, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblClienteTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckbEfetivada)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCliente)
                    .addComponent(btnSelecionarCliente)
                    .addComponent(jLabel1)
                    .addComponent(txtDataOperacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(rdbAprazo)
                    .addComponent(rdbAvista)
                    .addComponent(lblClienteCpf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckbEfetivada)
                    .addComponent(lblClienteTelefone))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "Preço", "Qtde", "Total", "Remover", "VenPrdCodigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProdutos);
        if (tblProdutos.getColumnModel().getColumnCount() > 0) {
            tblProdutos.getColumnModel().getColumn(0).setMinWidth(200);
            tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(4).setResizable(false);
            tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(50);
            tblProdutos.getColumnModel().getColumn(5).setMinWidth(0);
            tblProdutos.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblProdutos.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        btnCancelar.setText("Cancelar");

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnAddProduto.setText("Adicionar");
        btnAddProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProdutoActionPerformed(evt);
            }
        });

        jLabel9.setText("Produtos");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtValor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValor.setText("R$ 0,00");

        jLabel11.setText("Valor:");

        jLabel12.setText("Desconto:");

        txtDesconto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDesconto.setText("R$ 0,00");

        jLabel13.setText("Valor Final:");

        lblValorFinal.setText("R$ 0,00");

        lblNPARCELAS.setText("Nº Parcelas:");

        txtNParcelas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNParcelas.setText("0");

        lblEntrada.setText("Entrada:");

        txtEntrada.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtEntrada.setText("R$ 0,00");

        jLabel17.setText("Pedido:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblValorFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDesconto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtValor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEntrada)
                            .addComponent(lblNPARCELAS))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNParcelas)
                            .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(lblNumPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEntrada)
                            .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNPARCELAS)
                            .addComponent(txtNParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNumPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblValorFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Excluir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAddProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProduto)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdbAvistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAvistaActionPerformed
        // TODO add your handling code here:
        lblEntrada.setEnabled(false);
        lblNPARCELAS.setEnabled(false);
        txtEntrada.setEnabled(false);
        txtNParcelas.setEnabled(false);
    }//GEN-LAST:event_rdbAvistaActionPerformed

    private void rdbAprazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAprazoActionPerformed
        lblEntrada.setEnabled(true);
        lblNPARCELAS.setEnabled(true);
        txtEntrada.setEnabled(true);
        txtNParcelas.setEnabled(true);
    }//GEN-LAST:event_rdbAprazoActionPerformed

    private void btnSelecionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarClienteActionPerformed
        SlcPessoa slcpessoa = new SlcPessoa(this, true);
        slcpessoa.isCliente = true;
        slcpessoa.setVisible(true);
        if (slcpessoa.getPessoa() != null) {
            cliente = new Cliente(slcpessoa.getPessoa());
            fillCliente();
        }
    }//GEN-LAST:event_btnSelecionarClienteActionPerformed

    private void btnAddProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProdutoActionPerformed
        // TODO add your handling code here:
        VendaProduto p = new VendaProduto();
        vendaProdutos.add(p);


    }//GEN-LAST:event_btnAddProdutoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        venda = new Venda();
        venda.setCliCodigo(cliente);
        venda.setVendaProduto((VendaProduto[]) vendaProdutos.toArray(new VendaProduto[vendaProdutos.size()]));
        venda.save();
    }//GEN-LAST:event_btnSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup VendaTipo;
    private javax.swing.JButton btnAddProduto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSelecionarCliente;
    private javax.swing.JCheckBox ckbEfetivada;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblClienteCpf;
    private javax.swing.JLabel lblClienteTelefone;
    private javax.swing.JLabel lblEntrada;
    private javax.swing.JLabel lblNPARCELAS;
    private javax.swing.JLabel lblNumPedido;
    private javax.swing.JLabel lblValorFinal;
    private javax.swing.JRadioButton rdbAprazo;
    private javax.swing.JRadioButton rdbAvista;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JFormattedTextField txtDataOperacao;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtEntrada;
    private javax.swing.JTextField txtNParcelas;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
