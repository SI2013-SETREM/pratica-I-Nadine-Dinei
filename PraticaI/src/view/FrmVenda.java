/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import model.Pessoa;
import model.PessoaTelefone;
import model.Produto;
import model.Sequencial;
import model.Venda;
import model.VendaProduto;

/**
 *
 * @author Nadine
 */
public class FrmVenda extends reflection.FormJFrame {

    private Venda venda;
    private Cliente cliente;
    private PessoaTelefone[] pesTel;
    private ArrayList<VendaProduto> vendaProdutos = new ArrayList<>();

    public final static int colBtnRemover = 4;
    public final static ImageIcon iconRemover = new ImageIcon(util.Util.getIconUrl("delete.png"));
    
    /**
     * Creates new form FrmVenda
     */
    public FrmVenda() {
        initComponents();
        this.setTitle("Manutenção de " + Venda.sngTitle);
        ImageIcon icone = new ImageIcon(util.Util.getImageUrl(Venda.iconTitle, util.ImageSize.P));
        this.setIconImage(icone.getImage());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        btnSalvar.setIcon(new ImageIcon(util.Util.getImageUrl("tick.png", util.ImageSize.P)));
        btnCancelar.setIcon(new ImageIcon(util.Util.getImageUrl("cancel.png", util.ImageSize.P)));
        util.Util.setMoneyField(txtValor);
        util.Util.setMoneyField(txtDesconto);
        util.Util.setMoneyField(txtEntrada);
        util.Util.setMoneyField(txtValorFinal);
        rdbAvistaActionPerformed(null);
        txtDataOperacao.setText(util.Util.getFormattedDateTime());
        
        tblProdutos.getColumnModel().getColumn(colBtnRemover).setCellRenderer(new VendaCellRenderer());
        tblProdutos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = tblProdutos.columnAtPoint(e.getPoint());
                int row = tblProdutos.rowAtPoint(e.getPoint());
                if (col == colBtnRemover) {
                    vendaProdutos.remove(row);
                    fillTable();
                }
            }
        });
        
    }
    
    private void carregaValores() {
        double valorFinal = ((util.Util.getMoneyFromText(txtValor.getText())) - (util.Util.getMoneyFromText(txtDesconto.getText())));
        txtValorFinal.setText(util.Util.getFormattedMoney(valorFinal));
    }
    
    @Override
    public void loadInsert() {
        txtPedido.setText(String.valueOf(Sequencial.getNextSequencial(Venda.class.getSimpleName() + "_Pedido", false)));
    }
    
    @Override
    public void loadUpdate() {
        venda = new Venda();
        cliente = new Cliente((int) idCols[0]);
        venda.load(cliente, (int) idCols[1]); //To change body of generated methods, choose Tools | Templates.
        txtDataOperacao.setText(util.Util.getFormattedDate(venda.getVenData()));
        txtDesconto.setText(util.Util.getFormattedMoney(venda.getVenDesconto()));
        txtEntrada.setText(util.Util.getFormattedMoney(venda.getVenEntrada()));
        txtValor.setText(util.Util.getFormattedMoney(venda.getVenValor()));
        fillCliente();
        txtPedido.setText(String.valueOf(venda.getVenPedNumero()));
        txtValorFinal.setText(util.Util.getFormattedMoney(venda.getVenValorFinal()));
        if (venda.getVendaProduto() != null) {
            vendaProdutos = new ArrayList<>(Arrays.asList(venda.getVendaProduto()));
        }
        fillTable();
        if (venda.getVenTipo() == Venda.TIPO_PRAZO) {
            rdbAprazo.setSelected(true);
            txtNParcelasFocusLost(null);
            rdbAprazoActionPerformed(null);
        } else {
            rdbAvista.setSelected(true);
            rdbAvistaActionPerformed(null);
        }
        if (venda.isVenEfetivada() == true) {
            chkEfetivada.setSelected(true);
            chkEfetivada.setEnabled(false);
            txtDataOperacao.setEnabled(false);
            txtDesconto.setEnabled(false);
            txtEntrada.setEnabled(false);
            txtValor.setEnabled(false);
            rdbAprazo.setEnabled(false);
            rdbAvista.setEnabled(false);
            btnSelecionarCliente.setEnabled(false);
            tblProdutos.setEnabled(false);
            btnAddProduto.setEnabled(false);
            btnSalvar.setEnabled(false);
            txtNParcelas.setEditable(false);
            txtValorParcela.setEnabled(false);
            tblProdutos.getColumnModel().getColumn(FrmVenda.colBtnRemover).setResizable(false);
            tblProdutos.getColumnModel().getColumn(FrmVenda.colBtnRemover).setMinWidth(0);
            tblProdutos.getColumnModel().getColumn(FrmVenda.colBtnRemover).setPreferredWidth(0);
            tblProdutos.getColumnModel().getColumn(FrmVenda.colBtnRemover).setMaxWidth(0);
        } else {
            chkEfetivada.setSelected(false);
        }
    }

    private void fillCliente() {
        Pessoa pessoa = cliente.getPesCodigo();
//        if (pessoa != null) {
        lblCliente.setText(pessoa.getPesNome());
        lblClienteCpf.setText(pessoa.getPesCPFCNPJ());
        String clienteTelefone = "";
        for (PessoaTelefone pTel : PessoaTelefone.getAll(pessoa)) {
            clienteTelefone = pTel.getPesFonTelefone();
            break;
        }
        lblClienteTelefone.setText(clienteTelefone);
//        }
    }

    private void fillTable() {
        double total = 0;
        DefaultTableModel dtm = (DefaultTableModel) tblProdutos.getModel();
        dtm.setNumRows(0);
        for (VendaProduto vp : vendaProdutos) {
            Object[] row = new Object[]{
                vp.getVenPrdNome(),
                util.Util.getFormattedMoney(vp.getVenPrdPreco()),
                vp.getVenPrdQuantidade(),
                util.Util.getFormattedMoney(vp.getVenPrdTotal()),
                null,
                vp.getVenPrdCodigo()
            };
            dtm.addRow(row);
            total += vp.getVenPrdTotal();
        }
        txtValor.setText(util.Util.getFormattedMoney(total));
        carregaValores();
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
        chkEfetivada = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        rdbAvista = new javax.swing.JRadioButton();
        rdbAprazo = new javax.swing.JRadioButton();
        lblClienteCpf = new javax.swing.JLabel();
        lblClienteTelefone = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
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
        lblNPARCELAS = new javax.swing.JLabel();
        txtNParcelas = new javax.swing.JTextField();
        lblEntrada = new javax.swing.JLabel();
        txtEntrada = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtValorFinal = new javax.swing.JTextField();
        lblNPARCELAS1 = new javax.swing.JLabel();
        txtValorParcela = new javax.swing.JTextField();
        txtPedido = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        chkEfetivada.setSelected(true);
        chkEfetivada.setText("Efetivada");

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

        jLabel2.setText("Nome:");

        jLabel3.setText("CPF/CNPJ:");

        jLabel4.setText("Telefone:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelecionarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataOperacao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClienteCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblClienteTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkEfetivada, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbAvista)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdbAprazo)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCliente)
                    .addComponent(btnSelecionarCliente)
                    .addComponent(jLabel1)
                    .addComponent(txtDataOperacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(rdbAprazo)
                    .addComponent(rdbAvista)
                    .addComponent(lblClienteCpf)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkEfetivada)
                    .addComponent(lblClienteTelefone)
                    .addComponent(jLabel4))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

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
        txtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorFocusLost(evt);
            }
        });
        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });

        jLabel11.setText("Valor:");

        jLabel12.setText("Desconto:");

        txtDesconto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDesconto.setText("R$ 0,00");
        txtDesconto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontoFocusLost(evt);
            }
        });
        txtDesconto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescontoActionPerformed(evt);
            }
        });

        jLabel13.setText("Valor Final:");

        lblNPARCELAS.setText("Nº Parcelas:");

        txtNParcelas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNParcelas.setText("0");
        txtNParcelas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNParcelasFocusLost(evt);
            }
        });

        lblEntrada.setText("Entrada:");

        txtEntrada.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtEntrada.setText("R$ 0,00");

        jLabel17.setText("Pedido:");

        txtValorFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValorFinal.setText("R$ 0,00");
        txtValorFinal.setEnabled(false);
        txtValorFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorFinalActionPerformed(evt);
            }
        });

        lblNPARCELAS1.setText("Valor da Parcela:");

        txtValorParcela.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValorParcela.setText("R$ 0,00");
        txtValorParcela.setEnabled(false);

        txtPedido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPedido.setText("0");
        txtPedido.setEnabled(false);
        txtPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPedidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtValorFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblNPARCELAS1))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtDesconto, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtValor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblEntrada)
                                    .addComponent(lblNPARCELAS))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNParcelas)
                            .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(70, 70, 70))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEntrada)
                            .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNPARCELAS)
                            .addComponent(txtNParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNPARCELAS1)
                            .addComponent(txtValorParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtValorFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(71, 71, 71))))
        );

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
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(btnAddProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 426, Short.MAX_VALUE)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 286, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar)
                            .addComponent(btnSalvar))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdbAvistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAvistaActionPerformed
        lblEntrada.setEnabled(false);
        lblNPARCELAS.setEnabled(false);
        txtEntrada.setEnabled(false);
        txtNParcelas.setEnabled(false);
        txtValorParcela.setEnabled(false);
    }//GEN-LAST:event_rdbAvistaActionPerformed

    private void rdbAprazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAprazoActionPerformed
        lblEntrada.setEnabled(true);
        lblNPARCELAS.setEnabled(true);
        txtEntrada.setEnabled(true);
        txtNParcelas.setEnabled(true);
        txtValorParcela.setEnabled(false);
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
        SlcVendaProduto vp = new SlcVendaProduto(this, true);
        vp.setVenda(venda);
        vp.setVisible(true);
        if (vp.getVp() != null) {
            VendaProduto p = vp.getVp();
            vendaProdutos.add(p);
            fillTable();
        }
    }//GEN-LAST:event_btnAddProdutoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (venda == null)
            venda = new Venda();
        venda.setCliCodigo(cliente);
        venda.setVendaProduto((VendaProduto[]) vendaProdutos.toArray(new VendaProduto[vendaProdutos.size()]));
        venda.setVenData(util.Util.getDateFromString(txtDataOperacao.getText()));
        venda.setVenDesconto(util.Util.getMoneyFromText(txtDesconto.getText()));
        venda.setVenEntrada(util.Util.getMoneyFromText(txtEntrada.getText()));
        venda.setVenEfetivada(chkEfetivada.isSelected());
        venda.setVenParcelas(Integer.parseInt(txtNParcelas.getText()));
        if (rdbAprazo.isSelected() == true) {
            venda.setVenTipo(Venda.TIPO_PRAZO);
        } else if (rdbAvista.isSelected() == true) {
            venda.setVenTipo(Venda.TIPO_VISTA);
        }
        venda.setVenValor(util.Util.getMoneyFromText(txtValor.getText()));
        venda.setVenValorFinal(util.Util.getMoneyFromText(txtValorFinal.getText()));
        venda.save();
        dispose();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusLost
        carregaValores();
    }//GEN-LAST:event_txtValorFocusLost

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorActionPerformed

    private void txtValorFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorFinalActionPerformed
    }//GEN-LAST:event_txtValorFinalActionPerformed

    private void txtDescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescontoActionPerformed

    private void txtNParcelasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNParcelasFocusLost
        // TODO add your handling code here:
        double vPar = ((util.Util.getMoneyFromText(txtValorFinal.getText())) - (util.Util.getMoneyFromText(txtEntrada.getText()) / (Integer.parseInt(txtNParcelas.getText()))));
        txtValorParcela.setText(String.valueOf(vPar));
    }//GEN-LAST:event_txtNParcelasFocusLost

    private void txtPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPedidoActionPerformed

    private void txtDescontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusLost
        carregaValores();
    }//GEN-LAST:event_txtDescontoFocusLost

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
    private javax.swing.JCheckBox chkEfetivada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JLabel lblNPARCELAS1;
    private javax.swing.JRadioButton rdbAprazo;
    private javax.swing.JRadioButton rdbAvista;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JFormattedTextField txtDataOperacao;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtEntrada;
    private javax.swing.JTextField txtNParcelas;
    private javax.swing.JTextField txtPedido;
    private javax.swing.JTextField txtValor;
    private javax.swing.JTextField txtValorFinal;
    private javax.swing.JTextField txtValorParcela;
    // End of variables declaration//GEN-END:variables
}



class VendaCellRenderer extends DefaultTableCellRenderer {
    
    public VendaCellRenderer() {
    }
    
    @Override
    public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column == FrmVenda.colBtnRemover) {
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setText(null);
            this.setIcon(FrmVenda.iconRemover);
        }
        return this;
    }
}
