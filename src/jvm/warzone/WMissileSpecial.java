package warzone;


import java.util.ArrayList;

public class WMissileSpecial extends WRangedUnitSpecial {

	public WMissileSpecial(WViewer viewer, int range1, ArrayList possible1) {
		super(viewer, range1, possible1);
	}
	
	protected WSpecialAbility getCopy() {
		return new WMissileSpecial(viewer, range, new ArrayList(possible));
	}
	
	protected void DoAbility(int row, int col) {
		if(row==-1||col==-1) return;
		WUnit toDie = viewer.getGameBoard().UnitAtSpace(row,col);
		if(toDie!=null) toDie.killThisUnit();
		viewer.getGameBoard().removeTerrain(row,col);
		viewer.grid[row][col].doMouseEntered();
		WUnit wu = viewer.getGameBoard().UnitAtSpace(myRow, myCol);
		wu.killThisUnit();
	}

}
