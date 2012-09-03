package warzone;

import java.awt.*;

import javax.swing.*;

public class WTerrain extends JComponent {
	public static final int TREE = 1;
	public static final int BOULDER = 2;
	public static final String TREE_NAME = "tree";
	public static final String BOULDER_NAME = "boulder";
	
	private int type;
	private Image myPic;
	private String name;
	
	public String getTerrainName() {return name; }
	
	public WTerrain(int type1, WResource database) {
		type = type1;
		myPic = null;
		
		if(type==TREE) {
			myPic = database.getTerrainPic(TREE_NAME);
			name = TREE_NAME;
		} else if(type==BOULDER) {
			myPic = database.getTerrainPic(BOULDER_NAME);
			name = BOULDER_NAME;
		}
		
	}
	
	public int getType() {return type; }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(myPic, WCanvas.GRID_BUFFER, WCanvas.GRID_BUFFER, this);
	}
}
