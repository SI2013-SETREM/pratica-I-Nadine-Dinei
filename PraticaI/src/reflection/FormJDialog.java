
package reflection;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

/**
 *  JDialog padrão de Formulários
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class FormJDialog extends javax.swing.JDialog {
    public String flag;
    public Object[] idCols;
    
    public void loadUpdate() { }
    public void loadInsert() { }
    
    //Construtores
    public FormJDialog() {
    }
    public FormJDialog(Frame owner) {
        super(owner);
    }
    public FormJDialog(Frame owner, boolean modal) {
        super(owner, modal);
    }
    public FormJDialog(Frame owner, String title) {
        super(owner, title);
    }
    public FormJDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
    }
    public FormJDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
    }
    public FormJDialog(Dialog owner) {
        super(owner);
    }
    public FormJDialog(Dialog owner, boolean modal) {
        super(owner, modal);
    }
    public FormJDialog(Dialog owner, String title) {
        super(owner, title);
    }
    public FormJDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
    }
    public FormJDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
    }
    public FormJDialog(Window owner) {
        super(owner);
    }
    public FormJDialog(Window owner, ModalityType modalityType) {
        super(owner, modalityType);
    }
    public FormJDialog(Window owner, String title) {
        super(owner, title);
    }
    public FormJDialog(Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
    }
    public FormJDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
    }
}
