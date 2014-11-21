
package util;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
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
    
    public static java.sql.Timestamp getNow() {
        return new java.sql.Timestamp(new java.util.Date().getTime());
    }
    
    public static String getFormattedDate() {
        return getFormattedDate(Calendar.getInstance());
    }
    public static String getFormattedDate(java.sql.Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(date.getTime());
        return getFormattedDate(cl);
    }
    public static String getFormattedDate(Calendar cl) {
        String data = String.format("%02d", cl.get(Calendar.DAY_OF_MONTH)) + "/";
        data += String.format("%02d", cl.get(Calendar.MONTH)+1) + "/";
        data += String.valueOf(cl.get(Calendar.YEAR));
        return data;
    }
    
    public static String getFormattedTime() {
        return getFormattedTime(Calendar.getInstance());
    }
    public static String getFormattedTime(Calendar cl) {
        String hora = String.format("%02d", cl.get(Calendar.HOUR_OF_DAY)) + ":";
        hora += String.format("%02d", cl.get(Calendar.MINUTE)) + ":";
        hora += String.format("%02d", cl.get(Calendar.SECOND));
        return hora;
    }
    
    public static String getFormattedDateTime() {
        return Util.getFormattedDateTime(Calendar.getInstance());
    }
    public static String getFormattedDateTime(java.util.Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        return Util.getFormattedDateTime(cl);
    }
    public static String getFormattedDateTime(Calendar cl) {
        return getFormattedDate(cl) + " " + getFormattedTime(cl);
    }
    
    public static java.sql.Date getDateFromString(String strDate) {
        return java.sql.Date.valueOf(getDateStr(strDate, java.sql.Date.class));
    }
    public static java.sql.Timestamp getTimestampFromString(String strDate) {
        return java.sql.Timestamp.valueOf(getDateStr(strDate, java.sql.Timestamp.class));
    }
    private static String getDateStr(String strDate, Class<?> type) {
        String[] dateTimeParts = strDate.split("\\s");
        String[] dateParts = dateTimeParts[0].split("/");
        String dateStr = "";
        if (dateParts.length == 3) 
            dateStr += dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
        if (type == java.sql.Time.class || type == java.sql.Timestamp.class) {
            if (dateTimeParts.length == 2) 
                dateStr += " " + dateTimeParts[1];
            else
                dateStr += " 00:00:00";
        }
        return dateStr;
    }
    
    public static double getMoneyFromText(String txtText) {
        String valTxt = txtText.replaceAll("[^0-9,.]", "");
//        System.out.println(valTxt + " " + txtText.trim().equals(","));
        if (!"".equals(valTxt) && !",".equals(txtText) && !".".equals(txtText)) {
            valTxt = valTxt.replace(".", "").replace(",", ".");
//            System.out.println(valTxt);
            return Double.parseDouble(valTxt);
        } else {
            return 0;
        }
    }
    public static String getFormattedMoney(String txtText) {
        String valTxt = txtText.replaceAll("[^0-9,.]", "");
        if (!"".equals(valTxt)) {
            valTxt = valTxt.replace(".", "").replace(",", ".");
            return getFormattedMoney(Double.parseDouble(valTxt));
        } else {
            return getFormattedMoney(0);
        }
    }
    public static String getFormattedMoney(double valor) {
        java.text.NumberFormat formatter = java.text.NumberFormat.getCurrencyInstance();
        String txt = formatter.format(valor);
        
        return txt;
    }
    
    public static void setMoneyField(final javax.swing.JTextField txtToFormat) {
        txtToFormat.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                txtToFormat.setText(getFormattedMoney(txtToFormat.getText()));
            }
            @Override
            public void focusGained(FocusEvent e) {
                String txt = txtToFormat.getText().replaceAll("[^0-9,]", "");
                txtToFormat.setText(txt);
                txtToFormat.selectAll();
            }
        });
        txtToFormat.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    if (e.getKeyChar() == ',' || e.getKeyChar() == '.') {
                        if (e.getKeyChar() == '.') {
                            e.setKeyCode(KeyEvent.VK_COMMA);
                            e.setKeyChar(',');
                        }
                        if (txtToFormat.getText().indexOf(",") != -1)
                            e.consume();
                    } else {
                        e.consume();
                    }
                }
            }
        });
    }
    
    public static void setLimitChars(final javax.swing.JTextField txtToLimit, final int charLimit) {
        txtToLimit.setDocument(new JTextFieldLimit(charLimit));
    }
    
    public static void setSlcButton(final javax.swing.JButton btn) {
        btn.setText("...");
    }
    
    public static String strRepeat(String s, int n) {
        return new String(new char[n]).replace("\0", s);
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
    
    public static javax.swing.ImageIcon getImageIconOk() {
        return new javax.swing.ImageIcon(getIconUrl("tick.png"));
    }
    public static javax.swing.ImageIcon getImageIconCancel() {
        return new javax.swing.ImageIcon(getIconUrl("cancel.png"));
    }
}
