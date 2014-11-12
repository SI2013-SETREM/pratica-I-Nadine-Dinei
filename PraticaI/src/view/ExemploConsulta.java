/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lancamento;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import util.DB;

//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import net.sf.jasperreports.engine.JRResultSetDataSource;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.view.JasperViewer;
//import util.DB;
/**
 *
 * @author Nadine
 */
public class ExemploConsulta extends javax.swing.JFrame {

    /**
     * Creates new form ConsultaPaises
     */
    public ExemploConsulta() {
        initComponents();
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
        txtFiltro = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Sigla Pais 2 Digitos:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtFiltro.setText("jTextField1");
        getContentPane().add(txtFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        jButton1.setText("Gerar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {

//            ResultSet rs = DB.executeQuery("SELECT PaiCodigo,PaiAlfa3,PaiAlfa2,PaiBacenIbge,PaiISO3166,PaiNome "
//                    + "FROM `pais` where paicodigo=?", new Object[]{Integer.parseInt(txtFiltro.getText())});
            Map<String, Object> param = new HashMap<String, Object>();    //passa os parametros que foram criados no IReports
            param.put("IMAGEM", util.Util.getImageUrl("logo_report.png", util.ImageSize.G));//param.put("NOME_PARAMETRO_JASPER","CAMINHO DA IMAGEM");
            param.put("Periodo", "01/01/2014 a 31/12/2014");
            // ResultSet rs = DB.executeQuery("SELECT PaiCodigo,PaiAlfa3,PaiAlfa2,PaiBacenIbge,PaiISO3166,PaiNome "
            //       + "FROM `pais`");
            ResultSet rs = Lancamento.getLancamentosReport(Timestamp.valueOf("2014-01-01 00:00:00"), Timestamp.valueOf("2014-12-31 00:00:00"));
            JRResultSetDataSource relatRes = new JRResultSetDataSource(rs);
            JasperPrint p = JasperFillManager.fillReport("reports/rptLancamentosPorPeriodo.jasper", param, relatRes);
            JasperViewer jv = new JasperViewer(p);
            jv.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(ExemploConsulta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ExemploConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExemploConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExemploConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExemploConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExemploConsulta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
