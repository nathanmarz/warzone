package warzone;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

//position set by WCanvas
public class WBoardNode extends JComponent {
	public static final int NOTHING = 0;
	public static final int VALID = 1;
	public static final int INVALID = 2;
	
	private WViewer viewer;
	private int row;
	private int col;
	private boolean mouseOverMe;
	private int highLightType;
	
	public WBoardNode(WViewer viewer1, int row1, int col1) {
		viewer = viewer1;
		row = row1;
		col = col1;
		mouseOverMe = false;
		highLightType = NOTHING;
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				doMouseEntered();
				
				if(viewer.getMode()==WViewer.UNIT_MODE) {
					WGameBoard gb = viewer.getGameBoard();
					if(gb.UnitAtSpace(row,col)==null&&gb.TerrainAtSpace(row,col)==null) viewer.SpaceClicked(row, col,0);
				}
			}
			
			public void mouseExited(MouseEvent e) {
				mouseOverMe = false;
				repaint();
			}
			
			public void mousePressed(MouseEvent e) {
				int b;
				if(e.getButton()!=MouseEvent.BUTTON1 || e.isControlDown()) {
                                    b = 2;                                    
                                } else {
                                    b = 1;
                                }
				viewer.SpaceClicked(row, col,b);
			}
			
			
		
		});
		
	}
	
	public void doMouseEntered() {
		mouseOverMe = true;
		WGameBoard gameBoard = viewer.getGameBoard();
		viewer.setInspector(gameBoard.UnitAtSpace(row,col));
		repaint();
	}
	
	public int getHighLight() {return highLightType; }
	
	public void changeHighlight(int newType) {
		highLightType = newType;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int size = this.getHeight();
		g.setColor(Color.black);
		if(mouseOverMe) g.drawRect(0,0,size-1,size-1);
		if(highLightType!=NOTHING) {
			if(highLightType==VALID) g.setColor(Color.green);
			if(highLightType==INVALID) g.setColor(Color.gray);
			g.drawRect(1,1,size-3,size-3);
			g.drawRect(2,2,size-5,size-5);
		}
	}
}
