import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

/** A custom JScrollPane that bubbles mouse wheel events up to the parent component. */
public class BubbleScrollPane extends JScrollPane {
	
	private static final long serialVersionUID = -7109180173608789154L;

	public BubbleScrollPane() {
		super();
		addMouseWheelListener(new PDMouseWheelListener());
	}
	
	public BubbleScrollPane(Component c) {
		super(c);
		addMouseWheelListener(new PDMouseWheelListener());
	}
	
	class PDMouseWheelListener implements MouseWheelListener {
		private JScrollBar bar;
		private int previousValue = 0;
		private JScrollPane parentScrollPane;
		
		private JScrollPane getParentScrollPane() {
			if (parentScrollPane == null) {
				Component parent = getParent();
				while (!(parent instanceof JScrollPane) && parent != null) {
					parent = parent.getParent();
				}
				parentScrollPane = (JScrollPane)parent;
			}
			return parentScrollPane;
		}
		
		public PDMouseWheelListener() {
			bar = BubbleScrollPane.this.getVerticalScrollBar();
		}
		
		public void mouseWheelMoved(MouseWheelEvent e) {
			JScrollPane parent = getParentScrollPane();
			if (parent != null) {
				//only dispatch if we have reached top/bottom on previous scroll
				if (e.getWheelRotation() < 0) {
					if (bar.getValue() == 0 && previousValue == 0) {
						parent.dispatchEvent(cloneEvent(e));
					}
				} else {
					if (bar.getValue() == getMax() && previousValue == getMax()) {
						parent.dispatchEvent(cloneEvent(e));
					}
				}
				previousValue = bar.getValue();
			} 
			
			//if parent scrollpane doesn't exist remove this as a listener
			else {
				BubbleScrollPane.this.removeMouseWheelListener(this);
			}
		}
		
		private int getMax() {
			return bar.getMaximum() - bar.getVisibleAmount();
		}
		
		private MouseWheelEvent cloneEvent(MouseWheelEvent e) {
			return new MouseWheelEvent(getParentScrollPane(), e.getID(), e.getWhen(),
					e.getModifiers(), 1, 1, e.getClickCount(), false,
					e.getScrollType(), e.getScrollAmount(), e.getWheelRotation());
		}
	}
}