
package util;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *  Classe com funções úteis
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public abstract class Util {
    
    public static void debug(String msg) {
        System.out.println(msg);
    }
    public static void debug(int msg) {
        debug(String.valueOf(msg));
    }
    
    public static void setMoneyField(final javax.swing.JTextField txtToFormat) {
        txtToFormat.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
//                DecimalFormat formatter = new DecimalFormat("###,###,###.00");
//                String txt = formatter.format(Double.parseDouble(txtToFormat.getText()));
                
//                String txt = String.format("%10.2f", txtToFormat.getText());
                
                String txt = txtToFormat.getText();
                String valTxt = txt.replaceAll("[^1-9,.]", "");
                if ("".equals(valTxt) || Integer.parseInt(valTxt) == 0) 
                    txt = "0,00";
                if (txt.indexOf(",") == -1)
                    txt += ",00";
                txtToFormat.setText("R$ " + txt);
            }
            @Override
            public void focusGained(FocusEvent e) {
                String txt = txtToFormat.getText().replaceAll("[^0-9,]", "");
                String valTxt = txt.replace(",", "");
                if (!"".equals(valTxt) && Integer.parseInt(valTxt) == 0) 
                    txt = "";
                txtToFormat.setText(txt);
                
            }
        });
        txtToFormat.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtToFormat.setText(txtToFormat.getText().replaceAll("[^0-9,]", ""));
            }
        });
    }
    
    public static void setLimitChars(final javax.swing.JTextField txtToLimit, final int charLimit) {
        txtToLimit.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (e.getKeyChar() == (char) java.awt.event.KeyEvent.VK_ENTER
                    || e.getKeyChar() == (char) java.awt.event.KeyEvent.VK_BACK_SPACE)
                    return;
                if (txtToLimit.getText().length() <= charLimit - 1) {
                    //ok
                } else {
                    e.setKeyChar((char) java.awt.event.KeyEvent.VK_CLEAR);
                }  
            }
            
//            @Override
//            public void keyPressed(java.awt.event.KeyEvent e) {
//                System.out.println("keyPressed: " + txtToLimit.getText());
//            }
//            
//            @Override
//            public void keyReleased(java.awt.event.KeyEvent e) {
//                System.out.println("keyReleased: " + txtToLimit.getText());
//            }
        });
    }
    
    public static java.net.URL getIconUrl(String imgName) {
        return Util.class.getResource("../" + Config.imageFolder + "/" + ImageSize.P + "/" + imgName);
    }
    public static java.net.URL getImageUrl(String imgName) {
        return Util.class.getResource("../" + Config.imageFolder + "/" + ImageSize.M + "/" + imgName);
    }
    public static java.net.URL getImageUrl(String imgName, String imgsize) {
        return Util.class.getResource("../" + Config.imageFolder + "/" + imgsize + "/" + imgName);
    }
}
