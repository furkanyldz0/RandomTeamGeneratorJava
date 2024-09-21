import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class CustomFocusListener implements FocusListener{
	private JTextField textField;
	private String defaultText;
	
	@Override
	public void focusGained(FocusEvent e) {
		if(textField.getText().equals(defaultText)) {
			textField.setText("");
			textField.setForeground(null);
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(textField.getText().equals("")) {
			textField.setText(defaultText);
			textField.setForeground(Color.gray);
		}		
	}
	
	public void setFocusListener(JTextField textField, String defaultText) {
		this.defaultText = defaultText;
		this.textField = textField;
		textField.addFocusListener(this);
		textField.setForeground(Color.gray);
	}

}
