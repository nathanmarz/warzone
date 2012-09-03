package warzone;

import javax.swing.JFrame;

public class Warzone {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Warzone");
		frame.setContentPane(new WViewer(16,12));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
