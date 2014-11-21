/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Cliente;
import model.ContaCapital;
import model.Lancamento;
import model.Pessoa;
import model.PlanoContas;
import model.Venda;
import util.DB;
import util.ImageSize;
import util.Util;
import util.field.ComboBoxItem;

/**
 *
 * @author Dinei A. Rockenbach
 */
public class FrmLancamento extends reflection.FormJDialog {
    
    public Lancamento lancamento = new Lancamento();
    private Pessoa pessoa = null;
    private Venda venda = null;
    
    private int LanTipo = Lancamento.TIPO_ENTRADA;
    
    private ImageIcon imgEntrada = new ImageIcon(util.Util.getImageUrl("moneyadd.png", ImageSize.M));
    private ImageIcon imgEntradaP = new ImageIcon(util.Util.getImageUrl("moneyadd.png", ImageSize.P));
    private ImageIcon imgSaida = new ImageIcon(util.Util.getImageUrl("moneydelete.png", ImageSize.M));
    private ImageIcon imgSaidaP = new ImageIcon(util.Util.getImageUrl("moneydelete.png", ImageSize.P));
    private ImageIcon imgTransferencia = new ImageIcon(util.Util.getImageUrl("companygenerosity.png", ImageSize.M));
    
    private ArrayList<ComboBoxItem> cboxItensContaCapital = new ArrayList<>();
    private ArrayList<ComboBoxItem> cboxItensPlanoContas = new ArrayList<>();
    
    private ArrayList<ContaCapital> ContasDeCapital = ContaCapital.getAll();
    private ArrayList<PlanoContas> PlanosDeContas = PlanoContas.getAllOrdered();
    
    public FrmLancamento() {
        initComponents();
        this.setTitle("Manutenção de " + Lancamento.sngTitle);
        ImageIcon icone = new ImageIcon(util.Util.getIconUrl(Lancamento.iconTitle));
        this.setLocationRelativeTo(null);
        this.setIconImage(icone.getImage());
        btnSalvar.setIcon(Util.getImageIconOk());
        btnCancelar.setIcon(Util.getImageIconCancel());
//        cmbContaCapital.setModel(new ComboBoxModel());
        util.Util.setMoneyField(txtLanValor);
        util.Util.setLimitChars(txtLanDescricao, 200);
        util.Util.setLimitChars(txtLanDocumento, 50);
        
        util.Util.setSlcButton(btnBuscaPessoa);
        util.Util.setSlcButton(btnBuscaVenda);
        
        this.setEntrada(); //Só por garantia
        
        ComboBoxItem sel = null;
        for (ContaCapital cc : ContasDeCapital) {
            ComboBoxItem cboxItem = new ComboBoxItem(cc.getCntCodigo(), cc.getCntNome());
            cboxItensContaCapital.add(cboxItem);
            if (cc.getCntPadrao())
                sel = cboxItem;
        }
        cmbContaCapital.setModel(new DefaultComboBoxModel((ComboBoxItem[]) cboxItensContaCapital.toArray(new ComboBoxItem[cboxItensContaCapital.size()])));
        if (sel != null)
            cmbContaCapital.setSelectedItem(sel);
        
        cboxItensPlanoContas.add(new ComboBoxItem(0, "Selecione")); //Empty Item
        for (PlanoContas pc : PlanosDeContas) {
            cboxItensPlanoContas.add(new ComboBoxItem(pc.getPlnCodigo(), pc.getPlnNome()));
        }
        cmbPlanoContas.setModel(new DefaultComboBoxModel((ComboBoxItem[]) cboxItensPlanoContas.toArray(new ComboBoxItem[cboxItensPlanoContas.size()])));
        
        chkEfetivadoActionPerformed(null);
    }
    
    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        if (getPessoa() != null)
            txtPessoa.setText(getPessoa().getPesNome());
//        if (pessoa != null && pessoa.isCliente()) {
//            lblVenda.setVisible(true);
//            txtVenda.setVisible(true);
//            btnBuscaVenda.setVisible(true);
//        } else {
//            venda = null;
//            lblVenda.setVisible(false);
//            txtVenda.setVisible(false);
//            btnBuscaVenda.setVisible(false);
//        }
    }
    public Venda getVenda() {
        return venda;
    }
    public void setVenda(Venda venda) {
        this.venda = venda;
        if (getVenda() != null)
            txtVenda.setText(venda.toString());
    }
    
    public void setEntrada() {
        LanTipo = Lancamento.TIPO_ENTRADA;
        
        lblImg.setIcon(imgEntrada);
        lblLanTipo.setText("Entrada");
        lblLanTipo.setForeground(Lancamento.COR_ENTRADA);
//        btnTrocaLanTipo.setText("Saída");
//        btnTrocaLanTipo.setText("");
//        btnTrocaLanTipo.setIcon(imgSaidaP);
    }
    public void setSaida() {
        LanTipo = Lancamento.TIPO_SAIDA;
        
        lblImg.setIcon(imgSaida);
        lblLanTipo.setText("Saída");
        lblLanTipo.setForeground(Lancamento.COR_SAIDA);
//        btnTrocaLanTipo.setText("Entrada");
//        btnTrocaLanTipo.setText("");
//        btnTrocaLanTipo.setIcon(imgEntradaP);
    }
    
    private void setDateNow() {
        txtLanDataHora.setText(util.Util.getFormattedDateTime(new java.util.Date()));
    }
    
    @Override
    public void loadInsert() {
        lancamento = new Lancamento();
        setPessoa(null);
        setDateNow();
        chkEfetivadoActionPerformed(null);
    }

    @Override
    public void loadUpdate() {
        setPessoa(null);
        if (lancamento.load((int) idCols[0], (int) idCols[1])) {
            if (lancamento.getPesCodigo() != null) {
                setPessoa(lancamento.getPesCodigo());
            }
            
            if (lancamento.getVenCodigo() != null) {
                setVenda(lancamento.getVenCodigo());
            }
            
            if (lancamento.getPlnCodigo() != null) {
                for (ComboBoxItem cboxItem : cboxItensPlanoContas) {
                    if (lancamento.getPlnCodigo().getPlnCodigo() == (int) cboxItem.getId()) {
                        cmbPlanoContas.setSelectedItem(cboxItem);
                    }
                }
            }
            cmbContaCapital.setEnabled(false);
            
            switch (lancamento.getLanTipo()) {
                case Lancamento.TIPO_ENTRADA:
                    this.setEntrada();
                    txtLanValor.setText(Util.getFormattedMoney(lancamento.getLanValorEntrada()));
                    break;
                case Lancamento.TIPO_SAIDA:
                    this.setSaida();
                    txtLanValor.setText(Util.getFormattedMoney(lancamento.getLanValorSaida()));
                    break;
                case Lancamento.TIPO_TRANSFERENCIA:
                    //@TODO
                    break;
            }
            txtLanDataHora.setText(Util.getFormattedDateTime(lancamento.getLanDataHora()));
            txtLanDescricao.setText(lancamento.getLanDescricao());
            txtLanDocumento.setText(lancamento.getLanDocumento());
            chkEfetivado.setSelected(lancamento.isLanEfetivado());
            
            chkEfetivadoActionPerformed(null);
        } else {
            JOptionPane.showMessageDialog(this, Lancamento.sngTitle + " não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            //Fechar form
        }
    }
    
    public void save() {
        lancamento.setLanTipo(LanTipo);
        if (lancamento.getFlag().equals(DB.FLAG_INSERT) && cmbContaCapital.getSelectedItem() != null) {
            for (ContaCapital cc : ContasDeCapital) {
                if ((int) ((ComboBoxItem) cmbContaCapital.getSelectedItem()).getId() == cc.getCntCodigo()) {
                    lancamento.setCntCodigo(cc);
                    lancamento.getCntCodigo().setFlag(DB.FLAG_UPDATE);
                }
            }
        }
        
        if (pessoa != null) {
            lancamento.setPesCodigo(pessoa);
        }
        
        if (venda != null) {
            lancamento.setVenCodigo(venda);
        }
        
        if (cmbPlanoContas.getSelectedItem() != null) {
            for (PlanoContas pln : PlanosDeContas) {
                if ((int) ((ComboBoxItem) cmbPlanoContas.getSelectedItem()).getId() == pln.getPlnCodigo())
                    lancamento.setPlnCodigo(pln);
            }
        }
        lancamento.setLanTipo(LanTipo);
        lancamento.setLanDataHora(Util.getTimestampFromString(txtLanDataHora.getText()));
        switch(lancamento.getLanTipo()) {
            case Lancamento.TIPO_ENTRADA:
                lancamento.setLanValorEntrada(Util.getMoneyFromText(txtLanValor.getText()));
                break;
            case Lancamento.TIPO_SAIDA:
                lancamento.setLanValorSaida(Util.getMoneyFromText(txtLanValor.getText()));
                break;
        }
        lancamento.setLanDescricao(txtLanDescricao.getText());
        lancamento.setLanDocumento(txtLanDocumento.getText());
        lancamento.setLanEfetivado(chkEfetivado.isSelected());
        if (lancamento.save())
            this.dispose();
        else
            JOptionPane.showMessageDialog(this, "Falha ao salvar o registro\r\n" + lancamento.errorDsc, "Erro ao salvar", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpLanTipo = new javax.swing.ButtonGroup();
        btnGrpLanEfetivado = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        cmbContaCapital = new javax.swing.JComboBox();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbPlanoContas = new javax.swing.JComboBox();
        txtLanDataHora = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtLanValor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtLanDescricao = new javax.swing.JTextField();
        txtLanDocumento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblLanTipo = new javax.swing.JLabel();
        lblImg = new javax.swing.JLabel();
        chkEfetivado = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        txtPessoa = new javax.swing.JTextField();
        btnBuscaPessoa = new javax.swing.JButton();
        lblVenda = new javax.swing.JLabel();
        txtVenda = new javax.swing.JTextField();
        btnBuscaVenda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Conta de Capital:");

        cmbContaCapital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbContaCapitalActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel2.setText("Plano de Contas:");

        cmbPlanoContas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPlanoContasActionPerformed(evt);
            }
        });

        try {
            txtLanDataHora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtLanDataHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLanDataHoraActionPerformed(evt);
            }
        });

        jLabel3.setText("Data/Hora:");

        jLabel4.setText("Valor:");

        txtLanValor.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtLanValor.setText("R$ 0,00");
        txtLanValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLanValorActionPerformed(evt);
            }
        });

        jLabel5.setText("Descrição:");

        txtLanDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLanDescricaoActionPerformed(evt);
            }
        });

        txtLanDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLanDocumentoActionPerformed(evt);
            }
        });

        jLabel6.setText("Doc. Vinculado:");

        lblLanTipo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblLanTipo.setText("Tipo");

        chkEfetivado.setSelected(true);
        chkEfetivado.setText("Efetivado");
        chkEfetivado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEfetivadoActionPerformed(evt);
            }
        });

        jLabel7.setText("Pessoa Relacionada:");

        txtPessoa.setEnabled(false);
        txtPessoa.setFocusable(false);

        btnBuscaPessoa.setText("...");
        btnBuscaPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaPessoaActionPerformed(evt);
            }
        });

        lblVenda.setText("Venda Relacionada:");

        txtVenda.setEnabled(false);
        txtVenda.setFocusable(false);

        btnBuscaVenda.setText("...");
        btnBuscaVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaVendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblLanTipo))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblVenda)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtLanValor, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(chkEfetivado))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtPessoa)
                                            .addComponent(txtVenda))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnBuscaVenda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnBuscaPessoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cmbContaCapital, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel3))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(263, 263, 263)
                                                .addComponent(jLabel6)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtLanDataHora, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                            .addComponent(txtLanDocumento)))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtLanDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(17, 17, 17)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbPlanoContas, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 7, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblLanTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscaPessoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVenda)
                    .addComponent(txtVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscaVenda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbContaCapital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(txtLanDataHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkEfetivado)
                    .addComponent(txtLanValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPlanoContas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLanDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(txtLanDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        this.save();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtLanValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLanValorActionPerformed
        this.save();
    }//GEN-LAST:event_txtLanValorActionPerformed

    private void txtLanDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLanDescricaoActionPerformed
        this.save();
    }//GEN-LAST:event_txtLanDescricaoActionPerformed

    private void txtLanDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLanDocumentoActionPerformed
        this.save();
    }//GEN-LAST:event_txtLanDocumentoActionPerformed

    private void txtLanDataHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLanDataHoraActionPerformed
        this.save();
    }//GEN-LAST:event_txtLanDataHoraActionPerformed

    private void cmbContaCapitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbContaCapitalActionPerformed
        
    }//GEN-LAST:event_cmbContaCapitalActionPerformed

    private void cmbPlanoContasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPlanoContasActionPerformed
        
    }//GEN-LAST:event_cmbPlanoContasActionPerformed

    private void chkEfetivadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEfetivadoActionPerformed
        if (chkEfetivado.isSelected()) {
            this.setDateNow();
            txtLanDataHora.setEnabled(false);
        } else {
            txtLanDataHora.setEnabled(true);
        }
    }//GEN-LAST:event_chkEfetivadoActionPerformed

    private void btnBuscaPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaPessoaActionPerformed
        SlcPessoa slc = new SlcPessoa(null, true);
        slc.isCliente = true;
        slc.isFuncionario = true;
        slc.isFornecedor = true;
        slc.setVisible(true);
        if (slc.getPessoa() != null) {
            setPessoa(slc.getPessoa());
        }
    }//GEN-LAST:event_btnBuscaPessoaActionPerformed

    private void btnBuscaVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaVendaActionPerformed
        SlcVenda slc = new SlcVenda(null, true);
        if (pessoa != null && pessoa.isCliente()) {
            slc.cliente = new Cliente(pessoa);
        }
        slc.setVisible(true);
        if (slc.getVenda() != null) {
            setVenda(slc.getVenda());
        }
    }//GEN-LAST:event_btnBuscaVendaActionPerformed

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
            java.util.logging.Logger.getLogger(FrmLancamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLancamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLancamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLancamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmLancamento dialog = new FrmLancamento();
                dialog.setModal(true);
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
    private javax.swing.JButton btnBuscaPessoa;
    private javax.swing.JButton btnBuscaVenda;
    private javax.swing.JButton btnCancelar;
    private javax.swing.ButtonGroup btnGrpLanEfetivado;
    private javax.swing.ButtonGroup btnGrpLanTipo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JCheckBox chkEfetivado;
    private javax.swing.JComboBox cmbContaCapital;
    private javax.swing.JComboBox cmbPlanoContas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblLanTipo;
    private javax.swing.JLabel lblVenda;
    private javax.swing.JFormattedTextField txtLanDataHora;
    private javax.swing.JTextField txtLanDescricao;
    private javax.swing.JTextField txtLanDocumento;
    private javax.swing.JTextField txtLanValor;
    private javax.swing.JTextField txtPessoa;
    private javax.swing.JTextField txtVenda;
    // End of variables declaration//GEN-END:variables
}
