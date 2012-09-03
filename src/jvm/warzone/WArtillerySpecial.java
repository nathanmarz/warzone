package warzone;

import java.util.ArrayList;

public class WArtillerySpecial extends WRangedUnitSpecial {

	public WArtillerySpecial(WViewer viewer, int range1, ArrayList possible1) {
		super(viewer, range1, possible1);
	}
	
	protected WSpecialAbility getCopy() {
		return new WArtillerySpecial(viewer, range, new ArrayList(possible));
	}
	
	protected void DoAbility(int row, int col) {
		if(row==-1||col==-1) return;
		viewer.getGameBoard().UnitAtSpace(row,col).changeHP(-1);
		viewer.grid[row][col].doMouseEntered();
		WUnit wu = viewer.getGameBoard().UnitAtSpace(myRow, myCol);
		wu.useMove(viewer.currTurn(), false);
	}

}
