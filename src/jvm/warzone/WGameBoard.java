package warzone;

public class WGameBoard {
	//accessed using row,col
	private WUnit[][] gameBoard;
	private WTerrain[][] terrain;
	private WCanvas canvas;
	
	public WGameBoard(WCanvas canvas1) {
		gameBoard = new WUnit[canvas1.getCHeight()][canvas1.getCWidth()];
		terrain = new WTerrain[canvas1.getCHeight()][canvas1.getCWidth()];
		canvas = canvas1;
		for(int i=0;i<canvas.getCWidth();i++) {
			for(int j=0;j<canvas.getCHeight();j++) {
				gameBoard[j][i] = null;
				terrain[j][i] = null;
			}
		}
	}
	
	public void moveUnit(int rowOrig, int colOrig, int rowNew, int colNew) {
		WUnit wu = gameBoard[rowOrig][colOrig];
		gameBoard[rowNew][colNew] = gameBoard[rowOrig][colOrig];
		gameBoard[rowOrig][colOrig] = null;
		canvas.moveUnit(wu, rowNew, colNew);
	}
	
	public void removeUnit(WUnit wu) {
		WCoordinate up = UnitPos(wu);
		removeUnit(up.getRow(), up.getCol());
	}
	
	public WCoordinate UnitPos(WUnit wu) {
		for(int row=0;row<canvas.getCHeight();row++) {
			for(int col=0;col<canvas.getCWidth();col++) {
				if(wu==gameBoard[row][col]) return new WCoordinate(row,col);
			}
		}
		return null;
	}
	
	public void addUnit(WUnit wu, int row, int col) {
		gameBoard[row][col] = wu;
		canvas.add(wu,0);
		canvas.moveUnit(wu, row, col);
	}
	
	public void removeUnit(int row, int col) {
		WUnit wu = gameBoard[row][col];
		gameBoard[row][col] = null;
		canvas.remove(wu);
		canvas.repaint(wu.getBounds());
	}
	
	public void removeTerrain(int row, int col) {
		WTerrain t = terrain[row][col];
		if(t!=null) {
			terrain[row][col] = null;
			canvas.remove(t);
			canvas.repaint(t.getBounds());
		}
	}
	
	public void addTerrain(WTerrain t, int row, int col) {
		terrain[row][col] = t;
		canvas.add(t);
		canvas.addTerrain(t, row, col);
	}
	
	public boolean IsEmptySpace(int row, int col) {
		return gameBoard[row][col]==null&&terrain[row][col]==null;
	}
	
	public WTerrain TerrainAtSpace(int row, int col) {
		return terrain[row][col]; //returns null if no terrain
	}
	
	public WUnit UnitAtSpace(int row, int col) {
		return gameBoard[row][col]; //returns null if not unit
	}

}
