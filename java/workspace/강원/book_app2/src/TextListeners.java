import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class TextListeners implements DocumentListener{

	abstract void update(DocumentEvent e);
		
	
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		update(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		update(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		update(e);
	}
	
	

}
