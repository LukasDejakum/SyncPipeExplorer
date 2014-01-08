package finished;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class ContactChangedListener implements TableModelListener {

	private boolean changed = false;
	
	@Override
	public void tableChanged(TableModelEvent arg0) {
		changed=true;
	}
	public boolean contactsHaveChanged(){
		return changed;
	}
}
