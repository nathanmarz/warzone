package warzone;

import java.awt.*;

import javax.swing.*;

public class WCanvas extends JPanel {
	public static final int GRID_BUFFER = 5;
	
	private int width;
	private int height;
	private int unitLength;
	private int gridLength;
	
	public WCanvas(int width1, int height1, WViewer viewer, WBoardNode[][] grid) {
		super();
		this.setLayout(null);
		this.setBackground(Color.white);
		width = width1;
		height = height1;
		Dimension canvasSize = Toolkit.getDefaultToolkit().getScreenSize();

		canvasSize.setSize(canvasSize.getWidth()-170, canvasSize.getHeight()-60);
		
		int pLength = (int) canvasSize.getWidth()/(width);
		int pLength2 = (int) canvasSize.getHeight()/(height);
		
		gridLength = Math.min(pLength, pLength2);
		unitLength = gridLength-2*GRID_BUFFER;
		
		for(int row=0;row<height;row++) {
			for(int col=0;col<width;col++) {
				WBoardNode n = new WBoardNode(viewer, row, col);
				n.setBounds(col*gridLength, row*gridLength, gridLength+1, gridLength+1);
				add(n,-1);
				grid[row][col] = n;
			}
		}
		
		
		setPreferredSize(new Dimension(width*gridLength+1, height*gridLength+1));
		
	}
	
	public int getUnitSize() {return unitLength; }
	public int getGridSize() {return gridLength; }
	public int getCWidth() {return width; }
	public int getCHeight() {return height; }
	
	public void addTerrain(WTerrain t, int row, int col) {
		int pRow = row*gridLength;
		int pCol = col*gridLength;
		t.setBounds(pCol, pRow, gridLength, gridLength);
		add(t);
	}
	
	public void moveUnit(WUnit unit, int row, int col) {
		int pRow = row*gridLength;
		int pCol = col*gridLength;
		unit.repaint();
		unit.setLocation(pCol, pRow);
		unit.repaint();
	}
}
