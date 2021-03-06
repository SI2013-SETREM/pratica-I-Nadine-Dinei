package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.Cidade;
import model.ContaCapital;
import model.Estado;
import model.FechamentoCaixa;
import model.Lancamento;
import model.Log;
import model.NivelAcesso;
import model.Pais;
import model.Pessoa;
import model.Usuario;
import model.Venda;
import util.ImageSize;
import util.Util;

/**
 *
 * @author Nadine
 */
public class MainMenu extends javax.swing.JFrame {

    private Usuario UsuLogado = Usuario.UsuLogado;

    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        Locale.setDefault(new Locale("pt", "BR"));

        java.net.URL urlImage = Util.getImageUrl("logo.png", ImageSize.M);
        if (urlImage != null) {
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(urlImage));
        }

        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);

        String bemVindo = "Bem vind";
        switch (UsuLogado.getPesCodigo().getPesSexo()) {
            case Pessoa.SEXO_MASCULINO:
                bemVindo += "o";
                break;
            case Pessoa.SEXO_FEMININO:
                bemVindo += "a";
                break;
            case Pessoa.SEXO_NAOSABE:
            case Pessoa.SEXO_NAOESPECIFICADO:
                bemVindo += "o(a)";
                break;
        }
        lblBemVindo.setText(bemVindo + " " + UsuLogado.getPesCodigo().getPesNome());

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblRelogio.setText(util.Util.getFormattedDateTime());
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();

        lblImg.setIcon(new ImageIcon(util.Util.getImageUrl("logo_report.png", util.ImageSize.G)));
        
        btnEfetuarLancamentoEntrada.setIcon(new ImageIcon(util.Util.getImageUrl("moneyadd.png", util.ImageSize.M)));
        btnEfetuarLancamentoSaida.setIcon(new ImageIcon(util.Util.getImageUrl("moneydelete.png", util.ImageSize.M)));
        btnFecharCaixa.setIcon(new ImageIcon(util.Util.getImageUrl(FechamentoCaixa.iconTitle, util.ImageSize.M)));
//        btnContatos.setIcon(new ImageIcon(util.Util.getImageUrl("email.png", util.ImageSize.M)));
        btnSair.setIcon(new ImageIcon(util.Util.getImageUrl("doorout.png", util.ImageSize.M)));
        btnLancamento.setIcon(new ImageIcon(util.Util.getImageUrl("moneydollar.png", util.ImageSize.M)));
        btnPlanoContas.setIcon(new ImageIcon(util.Util.getImageUrl("category.png", util.ImageSize.M)));
        btnContasCapital.setIcon(new ImageIcon(util.Util.getImageUrl(ContaCapital.iconTitle, util.ImageSize.M)));
        btnFechaCaixa2.setIcon(new ImageIcon(util.Util.getImageUrl(FechamentoCaixa.iconTitle, util.ImageSize.M)));
        btnExportarFechamentos.setIcon(new ImageIcon(util.Util.getImageUrl("documentcheck.png", util.ImageSize.M)));
        btnVendas.setIcon(new ImageIcon(util.Util.getImageUrl(Venda.iconTitle, util.ImageSize.M)));
        btnClientes.setIcon(new ImageIcon(util.Util.getImageUrl("clientes.png", util.ImageSize.M)));
        btnProdutos.setIcon(new ImageIcon(util.Util.getImageUrl("produtos.png", util.ImageSize.M)));
        //       btnPessoas.setIcon(new ImageIcon(util.Util.getImageUrl("pessoas2.png", util.ImageSize.M)));
        //      btnFuncionarios.setIcon(new ImageIcon(util.Util.getImageUrl("funcionarios.png", util.ImageSize.M)));
        btnClientes1.setIcon(new ImageIcon(util.Util.getImageUrl("clientes.png", util.ImageSize.M)));
        btnUsuarios.setIcon(new ImageIcon(util.Util.getImageUrl("user.png", util.ImageSize.M)));
        btnPerfilUsuario.setIcon(new ImageIcon(util.Util.getImageUrl(NivelAcesso.iconTitle, util.ImageSize.M)));
//        btnContatos1.setIcon(new ImageIcon(util.Util.getImageUrl("email.png", util.ImageSize.M)));
        //       btnCargos.setIcon(new ImageIcon(util.Util.getImageUrl("briefcase.png", util.ImageSize.M)));
//        btnConfiguracoes.setIcon(new ImageIcon(util.Util.getImageUrl("tools.png", util.ImageSize.M)));
        btnLogs.setIcon(new ImageIcon(util.Util.getImageUrl(Log.iconTitle, util.ImageSize.M)));
        btnPaises.setIcon(new ImageIcon(util.Util.getImageUrl(Pais.iconTitle, util.ImageSize.M)));
        btnEstados.setIcon(new ImageIcon(util.Util.getImageUrl(Estado.iconTitle, util.ImageSize.M)));
        btnCidades.setIcon(new ImageIcon(util.Util.getImageUrl(Cidade.iconTitle, util.ImageSize.M)));

        btnContatos.setVisible(false);
        //    btnContatos1.setVisible(false);
        btnConfiguracoes.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedMenu = new javax.swing.JTabbedPane();
        pnlPrincipal = new javax.swing.JPanel();
        btnEfetuarLancamentoEntrada = new javax.swing.JButton();
        btnFecharCaixa = new javax.swing.JButton();
        btnContatos = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnEfetuarLancamentoSaida = new javax.swing.JButton();
        pnlFluxoDeCaixa = new javax.swing.JPanel();
        btnLancamento = new javax.swing.JButton();
        btnPlanoContas = new javax.swing.JButton();
        btnContasCapital = new javax.swing.JButton();
        btnFechaCaixa2 = new javax.swing.JButton();
        btnExportarFechamentos = new javax.swing.JButton();
        pnlVendas = new javax.swing.JPanel();
        btnVendas = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProdutos = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnUsuarios = new javax.swing.JButton();
        btnClientes1 = new javax.swing.JButton();
        btnPerfilUsuario = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnConfiguracoes = new javax.swing.JButton();
        btnLogs = new javax.swing.JButton();
        btnPaises = new javax.swing.JButton();
        btnEstados = new javax.swing.JButton();
        btnCidades = new javax.swing.JButton();
        lblBemVindo = new javax.swing.JLabel();
        lblRelogio = new javax.swing.JLabel();
        lblImg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlPrincipal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnEfetuarLancamentoEntrada.setBackground(new java.awt.Color(255, 255, 255));
        btnEfetuarLancamentoEntrada.setText("Registrar Entrada");
        btnEfetuarLancamentoEntrada.setBorderPainted(false);
        btnEfetuarLancamentoEntrada.setContentAreaFilled(false);
        btnEfetuarLancamentoEntrada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEfetuarLancamentoEntrada.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEfetuarLancamentoEntrada.setMaximumSize(new java.awt.Dimension(75, 75));
        btnEfetuarLancamentoEntrada.setMinimumSize(new java.awt.Dimension(75, 75));
        btnEfetuarLancamentoEntrada.setPreferredSize(new java.awt.Dimension(75, 75));
        btnEfetuarLancamentoEntrada.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEfetuarLancamentoEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEfetuarLancamentoEntradaActionPerformed(evt);
            }
        });

        btnFecharCaixa.setBackground(new java.awt.Color(255, 255, 255));
        btnFecharCaixa.setText("Fechar Período");
        btnFecharCaixa.setBorderPainted(false);
        btnFecharCaixa.setContentAreaFilled(false);
        btnFecharCaixa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFecharCaixa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFecharCaixa.setMaximumSize(new java.awt.Dimension(75, 75));
        btnFecharCaixa.setMinimumSize(new java.awt.Dimension(75, 75));
        btnFecharCaixa.setPreferredSize(new java.awt.Dimension(75, 75));
        btnFecharCaixa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFecharCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharCaixaActionPerformed(evt);
            }
        });

        btnContatos.setBackground(new java.awt.Color(255, 255, 255));
        btnContatos.setText("Contatos");
        btnContatos.setBorderPainted(false);
        btnContatos.setContentAreaFilled(false);
        btnContatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnContatos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnContatos.setMaximumSize(new java.awt.Dimension(75, 75));
        btnContatos.setMinimumSize(new java.awt.Dimension(75, 75));
        btnContatos.setPreferredSize(new java.awt.Dimension(75, 75));
        btnContatos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnContatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContatosActionPerformed(evt);
            }
        });

        btnSair.setBackground(new java.awt.Color(255, 255, 255));
        btnSair.setText("Sair");
        btnSair.setBorderPainted(false);
        btnSair.setContentAreaFilled(false);
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSair.setMaximumSize(new java.awt.Dimension(75, 75));
        btnSair.setMinimumSize(new java.awt.Dimension(75, 75));
        btnSair.setPreferredSize(new java.awt.Dimension(75, 75));
        btnSair.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnEfetuarLancamentoSaida.setBackground(new java.awt.Color(255, 255, 255));
        btnEfetuarLancamentoSaida.setText("Registrar Saída");
        btnEfetuarLancamentoSaida.setBorderPainted(false);
        btnEfetuarLancamentoSaida.setContentAreaFilled(false);
        btnEfetuarLancamentoSaida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEfetuarLancamentoSaida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEfetuarLancamentoSaida.setMaximumSize(new java.awt.Dimension(75, 75));
        btnEfetuarLancamentoSaida.setMinimumSize(new java.awt.Dimension(75, 75));
        btnEfetuarLancamentoSaida.setPreferredSize(new java.awt.Dimension(75, 75));
        btnEfetuarLancamentoSaida.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEfetuarLancamentoSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEfetuarLancamentoSaidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEfetuarLancamentoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEfetuarLancamentoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFecharCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnContatos, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(190, Short.MAX_VALUE))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEfetuarLancamentoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEfetuarLancamentoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFecharCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedMenu.addTab("Principal", pnlPrincipal);

        pnlFluxoDeCaixa.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnLancamento.setBackground(new java.awt.Color(255, 255, 255));
        btnLancamento.setText("Lançamentos");
        btnLancamento.setBorderPainted(false);
        btnLancamento.setContentAreaFilled(false);
        btnLancamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLancamento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLancamento.setMaximumSize(new java.awt.Dimension(75, 75));
        btnLancamento.setMinimumSize(new java.awt.Dimension(75, 75));
        btnLancamento.setPreferredSize(new java.awt.Dimension(75, 75));
        btnLancamento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLancamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLancamentoActionPerformed(evt);
            }
        });

        btnPlanoContas.setBackground(new java.awt.Color(255, 255, 255));
        btnPlanoContas.setText("Planos de Contas");
        btnPlanoContas.setBorderPainted(false);
        btnPlanoContas.setContentAreaFilled(false);
        btnPlanoContas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlanoContas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPlanoContas.setMaximumSize(new java.awt.Dimension(75, 75));
        btnPlanoContas.setMinimumSize(new java.awt.Dimension(75, 75));
        btnPlanoContas.setPreferredSize(new java.awt.Dimension(75, 75));
        btnPlanoContas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPlanoContas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlanoContasActionPerformed(evt);
            }
        });

        btnContasCapital.setBackground(new java.awt.Color(255, 255, 255));
        btnContasCapital.setText("Contas de Capital");
        btnContasCapital.setBorderPainted(false);
        btnContasCapital.setContentAreaFilled(false);
        btnContasCapital.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnContasCapital.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnContasCapital.setMaximumSize(new java.awt.Dimension(75, 75));
        btnContasCapital.setMinimumSize(new java.awt.Dimension(75, 75));
        btnContasCapital.setPreferredSize(new java.awt.Dimension(75, 75));
        btnContasCapital.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnContasCapital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContasCapitalActionPerformed(evt);
            }
        });

        btnFechaCaixa2.setBackground(new java.awt.Color(255, 255, 255));
        btnFechaCaixa2.setText("Fechar Período");
        btnFechaCaixa2.setBorderPainted(false);
        btnFechaCaixa2.setContentAreaFilled(false);
        btnFechaCaixa2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFechaCaixa2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFechaCaixa2.setMaximumSize(new java.awt.Dimension(75, 75));
        btnFechaCaixa2.setMinimumSize(new java.awt.Dimension(75, 75));
        btnFechaCaixa2.setPreferredSize(new java.awt.Dimension(75, 75));
        btnFechaCaixa2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFechaCaixa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFechaCaixa2ActionPerformed(evt);
            }
        });

        btnExportarFechamentos.setBackground(new java.awt.Color(255, 255, 255));
        btnExportarFechamentos.setText("Exportar Fechamentos");
        btnExportarFechamentos.setBorderPainted(false);
        btnExportarFechamentos.setContentAreaFilled(false);
        btnExportarFechamentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportarFechamentos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportarFechamentos.setMaximumSize(new java.awt.Dimension(75, 75));
        btnExportarFechamentos.setMinimumSize(new java.awt.Dimension(75, 75));
        btnExportarFechamentos.setPreferredSize(new java.awt.Dimension(75, 75));
        btnExportarFechamentos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExportarFechamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarFechamentosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFluxoDeCaixaLayout = new javax.swing.GroupLayout(pnlFluxoDeCaixa);
        pnlFluxoDeCaixa.setLayout(pnlFluxoDeCaixaLayout);
        pnlFluxoDeCaixaLayout.setHorizontalGroup(
            pnlFluxoDeCaixaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFluxoDeCaixaLayout.createSequentialGroup()
                .addComponent(btnLancamento, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPlanoContas, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnContasCapital, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFechaCaixa2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportarFechamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );
        pnlFluxoDeCaixaLayout.setVerticalGroup(
            pnlFluxoDeCaixaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFluxoDeCaixaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFluxoDeCaixaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExportarFechamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFechaCaixa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContasCapital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlanoContas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLancamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedMenu.addTab("Fluxo de Caixa", pnlFluxoDeCaixa);

        pnlVendas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnVendas.setBackground(new java.awt.Color(255, 255, 255));
        btnVendas.setText("Vendas");
        btnVendas.setBorderPainted(false);
        btnVendas.setContentAreaFilled(false);
        btnVendas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVendas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVendas.setMaximumSize(new java.awt.Dimension(75, 75));
        btnVendas.setMinimumSize(new java.awt.Dimension(75, 75));
        btnVendas.setPreferredSize(new java.awt.Dimension(75, 75));
        btnVendas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVendasActionPerformed(evt);
            }
        });

        btnClientes.setBackground(new java.awt.Color(255, 255, 255));
        btnClientes.setText("Clientes");
        btnClientes.setBorderPainted(false);
        btnClientes.setContentAreaFilled(false);
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClientes.setMaximumSize(new java.awt.Dimension(75, 75));
        btnClientes.setMinimumSize(new java.awt.Dimension(75, 75));
        btnClientes.setPreferredSize(new java.awt.Dimension(75, 75));
        btnClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnProdutos.setBackground(new java.awt.Color(255, 255, 255));
        btnProdutos.setText("Produtos");
        btnProdutos.setBorderPainted(false);
        btnProdutos.setContentAreaFilled(false);
        btnProdutos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProdutos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProdutos.setMaximumSize(new java.awt.Dimension(75, 75));
        btnProdutos.setMinimumSize(new java.awt.Dimension(75, 75));
        btnProdutos.setPreferredSize(new java.awt.Dimension(75, 75));
        btnProdutos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdutosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlVendasLayout = new javax.swing.GroupLayout(pnlVendas);
        pnlVendas.setLayout(pnlVendasLayout);
        pnlVendasLayout.setHorizontalGroup(
            pnlVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVendasLayout.createSequentialGroup()
                .addComponent(btnVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 493, Short.MAX_VALUE))
        );
        pnlVendasLayout.setVerticalGroup(
            pnlVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVendasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedMenu.addTab("Vendas", pnlVendas);

        btnUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        btnUsuarios.setText("Usuários");
        btnUsuarios.setBorderPainted(false);
        btnUsuarios.setContentAreaFilled(false);
        btnUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuarios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUsuarios.setMaximumSize(new java.awt.Dimension(75, 75));
        btnUsuarios.setMinimumSize(new java.awt.Dimension(75, 75));
        btnUsuarios.setPreferredSize(new java.awt.Dimension(75, 75));
        btnUsuarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });

        btnClientes1.setBackground(new java.awt.Color(255, 255, 255));
        btnClientes1.setText("Clientes");
        btnClientes1.setBorderPainted(false);
        btnClientes1.setContentAreaFilled(false);
        btnClientes1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClientes1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClientes1.setMaximumSize(new java.awt.Dimension(75, 75));
        btnClientes1.setMinimumSize(new java.awt.Dimension(75, 75));
        btnClientes1.setPreferredSize(new java.awt.Dimension(75, 75));
        btnClientes1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClientes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientes1ActionPerformed(evt);
            }
        });

        btnPerfilUsuario.setBackground(new java.awt.Color(255, 255, 255));
        btnPerfilUsuario.setText("Perfis de Usuários");
        btnPerfilUsuario.setBorderPainted(false);
        btnPerfilUsuario.setContentAreaFilled(false);
        btnPerfilUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPerfilUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPerfilUsuario.setMaximumSize(new java.awt.Dimension(75, 75));
        btnPerfilUsuario.setMinimumSize(new java.awt.Dimension(75, 75));
        btnPerfilUsuario.setPreferredSize(new java.awt.Dimension(75, 75));
        btnPerfilUsuario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPerfilUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerfilUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPerfilUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(433, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPerfilUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedMenu.addTab("Pessoas", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnConfiguracoes.setBackground(new java.awt.Color(255, 255, 255));
        btnConfiguracoes.setText("Configurações");
        btnConfiguracoes.setBorderPainted(false);
        btnConfiguracoes.setContentAreaFilled(false);
        btnConfiguracoes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfiguracoes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConfiguracoes.setMaximumSize(new java.awt.Dimension(75, 75));
        btnConfiguracoes.setMinimumSize(new java.awt.Dimension(75, 75));
        btnConfiguracoes.setPreferredSize(new java.awt.Dimension(75, 75));
        btnConfiguracoes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConfiguracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracoesActionPerformed(evt);
            }
        });

        btnLogs.setBackground(new java.awt.Color(255, 255, 255));
        btnLogs.setText("Logs");
        btnLogs.setBorderPainted(false);
        btnLogs.setContentAreaFilled(false);
        btnLogs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogs.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogs.setMaximumSize(new java.awt.Dimension(75, 75));
        btnLogs.setMinimumSize(new java.awt.Dimension(75, 75));
        btnLogs.setPreferredSize(new java.awt.Dimension(75, 75));
        btnLogs.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogsActionPerformed(evt);
            }
        });

        btnPaises.setBackground(new java.awt.Color(255, 255, 255));
        btnPaises.setText("Países");
        btnPaises.setBorderPainted(false);
        btnPaises.setContentAreaFilled(false);
        btnPaises.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPaises.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPaises.setMaximumSize(new java.awt.Dimension(75, 75));
        btnPaises.setMinimumSize(new java.awt.Dimension(75, 75));
        btnPaises.setPreferredSize(new java.awt.Dimension(75, 75));
        btnPaises.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPaises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaisesActionPerformed(evt);
            }
        });

        btnEstados.setBackground(new java.awt.Color(255, 255, 255));
        btnEstados.setText("Estados");
        btnEstados.setBorderPainted(false);
        btnEstados.setContentAreaFilled(false);
        btnEstados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstados.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEstados.setMaximumSize(new java.awt.Dimension(75, 75));
        btnEstados.setMinimumSize(new java.awt.Dimension(75, 75));
        btnEstados.setPreferredSize(new java.awt.Dimension(75, 75));
        btnEstados.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadosActionPerformed(evt);
            }
        });

        btnCidades.setBackground(new java.awt.Color(255, 255, 255));
        btnCidades.setText("Cidades");
        btnCidades.setBorderPainted(false);
        btnCidades.setContentAreaFilled(false);
        btnCidades.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCidades.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCidades.setMaximumSize(new java.awt.Dimension(75, 75));
        btnCidades.setMinimumSize(new java.awt.Dimension(75, 75));
        btnCidades.setPreferredSize(new java.awt.Dimension(75, 75));
        btnCidades.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCidadesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnConfiguracoes, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPaises, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCidades, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 266, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPaises, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLogs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConfiguracoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnEstados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedMenu.addTab("Configuração", jPanel2);

        lblBemVindo.setText("lblBemVindo");

        lblRelogio.setText("    ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedMenu)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblBemVindo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRelogio)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBemVindo)
                    .addComponent(lblRelogio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
                .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowOpened

    private void btnEfetuarLancamentoEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEfetuarLancamentoEntradaActionPerformed
        if (checkAcesso(Lancamento.fncNome, Usuario.IDX_INSERIR, true)) {
            FrmLancamento frm = new FrmLancamento();
            frm.loadInsert();
            frm.setEntrada();
            frm.setVisible(true);
        }
    }//GEN-LAST:event_btnEfetuarLancamentoEntradaActionPerformed

    private void btnFecharCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharCaixaActionPerformed
        FecharPeriodo lst = new FecharPeriodo();
        lst.setVisible(true);
    }//GEN-LAST:event_btnFecharCaixaActionPerformed

    private void btnContatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContatosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnContatosActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnLancamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLancamentoActionPerformed
        //openList(model.Lancamento.class, 600);
        LstLancamento lst = new LstLancamento();
        lst.setVisible(true);
    }//GEN-LAST:event_btnLancamentoActionPerformed

    private void btnPlanoContasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlanoContasActionPerformed
        openList(model.PlanoContas.class);
    }//GEN-LAST:event_btnPlanoContasActionPerformed

    private void btnContasCapitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContasCapitalActionPerformed
        openList(model.ContaCapital.class);
    }//GEN-LAST:event_btnContasCapitalActionPerformed

    private void btnFechaCaixa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFechaCaixa2ActionPerformed
        btnFecharCaixaActionPerformed(evt);
    }//GEN-LAST:event_btnFechaCaixa2ActionPerformed

    private void btnVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVendasActionPerformed
        openList(model.Venda.class);
    }//GEN-LAST:event_btnVendasActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        openList(model.Cliente.class);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdutosActionPerformed
        openList(model.Produto.class);
    }//GEN-LAST:event_btnProdutosActionPerformed

    private void btnPerfilUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilUsuarioActionPerformed
        openList(model.NivelAcesso.class);
    }//GEN-LAST:event_btnPerfilUsuarioActionPerformed

    private void btnClientes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientes1ActionPerformed
        btnClientesActionPerformed(evt);
    }//GEN-LAST:event_btnClientes1ActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        openList(model.Usuario.class);
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnConfiguracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracoesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfiguracoesActionPerformed

    private void btnLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogsActionPerformed
        openList(model.Log.class, 700);
    }//GEN-LAST:event_btnLogsActionPerformed

    private void btnPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaisesActionPerformed
//        reflection.ListJFrame list = new reflection.ListJFrame();
//        list.setClass(model.Pais.class);
//        list.initListComponents();
//        list.setVisible(true);
        openList(model.Pais.class);
    }//GEN-LAST:event_btnPaisesActionPerformed

    private void btnEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadosActionPerformed
//        reflection.ListJFrame list = new reflection.ListJFrame();
//        list.setClass(model.Estado.class);
//        list.initListComponents();
//        list.setVisible(true);
        openList(model.Estado.class);

    }//GEN-LAST:event_btnEstadosActionPerformed

    private void btnCidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCidadesActionPerformed
//        reflection.ListJFrame list = new reflection.ListJFrame();
//        list.setClass(model.Cidade.class);
//        list.initListComponents();
//        list.setVisible(true);
        openList(model.Cidade.class);
    }//GEN-LAST:event_btnCidadesActionPerformed

    private void btnEfetuarLancamentoSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEfetuarLancamentoSaidaActionPerformed
        if (checkAcesso(Lancamento.fncNome, Usuario.IDX_INSERIR, true)) {
            FrmLancamento frm = new FrmLancamento();
            frm.loadInsert();
            frm.setSaida();
            frm.setVisible(true);
        }
    }//GEN-LAST:event_btnEfetuarLancamentoSaidaActionPerformed

    private void btnExportarFechamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarFechamentosActionPerformed
        ExpFechamentoCaixa frm = new ExpFechamentoCaixa();
        frm.setVisible(true);
    }//GEN-LAST:event_btnExportarFechamentosActionPerformed

    private void openList(Class<? extends model.ModelTemplate> cls) {
        openList(cls, 0);
    }

    private void openList(Class<? extends model.ModelTemplate> cls, int width) {
        String FncNome = (String) reflection.ReflectionUtil.getAttibute(cls, "fncNome");

        if (checkAcesso(FncNome, Usuario.IDX_VISUALIZAR, true)) {
            reflection.ListJFrame list = new reflection.ListJFrame();
            if (width > 0) {
                list.setWidth(width);
            }
            list.setClass(cls);
            list.initListComponents();
//            list.setAlwaysOnTop(true);
            list.setVisible(true);
        }
    }

    private boolean checkAcesso(String FncNome, int idx_acesso, boolean showError) {
        boolean[] acessos;
        if (FncNome != null && !"".equals(FncNome)) {
            acessos = UsuLogado.verificaAcesso(FncNome);
        } else {
            acessos = new boolean[]{true, true, true, true};
        }
        if (showError && !acessos[idx_acesso]) {
            JOptionPane.showMessageDialog(this, "Usuário sem acesso ao objeto '" + FncNome + "'", "Sem acesso", JOptionPane.WARNING_MESSAGE);
        }
        return acessos[idx_acesso];
    }

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
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCidades;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnClientes1;
    private javax.swing.JButton btnConfiguracoes;
    private javax.swing.JButton btnContasCapital;
    private javax.swing.JButton btnContatos;
    private javax.swing.JButton btnEfetuarLancamentoEntrada;
    private javax.swing.JButton btnEfetuarLancamentoSaida;
    private javax.swing.JButton btnEstados;
    private javax.swing.JButton btnExportarFechamentos;
    private javax.swing.JButton btnFechaCaixa2;
    private javax.swing.JButton btnFecharCaixa;
    private javax.swing.JButton btnLancamento;
    private javax.swing.JButton btnLogs;
    private javax.swing.JButton btnPaises;
    private javax.swing.JButton btnPerfilUsuario;
    private javax.swing.JButton btnPlanoContas;
    private javax.swing.JButton btnProdutos;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVendas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedMenu;
    private javax.swing.JLabel lblBemVindo;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblRelogio;
    private javax.swing.JPanel pnlFluxoDeCaixa;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlVendas;
    // End of variables declaration//GEN-END:variables
}
