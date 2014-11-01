
package reflection;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

/**
 *  JFrame padrão de Formulários
 * @author Dinei A. Rockenbach
 * @author Nadine Anderle
 */
public class FormJFrame extends javax.swing.JFrame {
    public String flag;
    public Object[] idCols;
    
    public void loadUpdate() { }
    public void loadInsert() { }
    
    //Construtores
    public FormJFrame() throws HeadlessException {
    }
    public FormJFrame(GraphicsConfiguration gc) {
        super(gc);
    }
    public FormJFrame(String title) throws HeadlessException {
        super(title);
    }
    public FormJFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }
}
