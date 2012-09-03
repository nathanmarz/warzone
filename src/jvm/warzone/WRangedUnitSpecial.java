package warzone;

import java.util.ArrayList;

public abstract class WRangedUnitSpecial extends WRangeSelector {

	
	public WRangedUnitSpecial(WViewer viewer, int range1, ArrayList possible1) {
		super(viewer, range1, possible1);
	}
	
	public void DoSpecial(int row, int col) {
		if(viewer.getGameBoard().UnitAtSpace(row,col).getMoves(viewer.currTurn())>0) {
			super.DoSpecial(row,col);
		}
	}

}
