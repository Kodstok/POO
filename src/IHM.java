import java.awt.*;

import javax.swing.JFrame;


public class IHM {
	public void debut()
	{
		Frame frame = new JFrame();
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		frame.setSize(height,width);
		frame.setVisible(true);

	}
}
