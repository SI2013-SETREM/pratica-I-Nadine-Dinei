package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @see http://www.java2s.com/Tutorial/Java/0240__Swing/LimitJTextFieldinputtoamaximumlength.htm
 */
class JTextFieldLimit extends PlainDocument {
    
    private int limit;
    
    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }
    
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) 
            return;
        if ((getLength() + str.length()) <= limit) 
            super.insertString(offset, str, attr);
    }
}
