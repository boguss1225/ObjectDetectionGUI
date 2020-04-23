package tfgui.controller.view;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * A {@link JTextField} that skips all non-digit keys. The user is only able to enter numbers.
 * 
 * @author Michi Gysel <michi@scythe.ch>
 * 
 */
public class JNumberTextField extends JTextField {
    private static final long serialVersionUID = 1L;

    @Override
    public void processKeyEvent(KeyEvent ev) {
        if (Character.isDigit(ev.getKeyChar())) {
            super.processKeyEvent(ev);
        }
        ev.consume();
        return;
    }

    /**
     * As the user is not even able to enter a dot ("."), only integers (whole numbers) may be entered.
     */
    public Long getNumber() {
        Long result = null;
        String text = getText();
        if (text != null && !"".equals(text)) {
            result = Long.valueOf(text);
        }
        return result;
    }
}