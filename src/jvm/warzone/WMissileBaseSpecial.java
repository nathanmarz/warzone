package warzone;

import java.util.ArrayList;

public class WMissileBaseSpecial extends WRangeSelector {
	private int miss_cost;
	
	public WMissileBaseSpecial(WViewer viewer, int range1, ArrayList possible1, int cost) {
		super(viewer, range1, possible1);
		miss_cost = cost;
	}
	
	public void DoSpecial(int row, int col) {
		if(viewer.getCurrentPlayer().getResources()>=miss_cost&&viewer.getGameBoard().UnitAtSpace(row,col).getMoves(viewer.currTurn())>0) {
			super.DoSpecial(row,col);
		}
	}
	
	protected WSpecialAbility getCopy() {
		return new WMissileBaseSpecial(viewer, range, new ArrayList(possible), miss_cost);
	}
	
	protected void DoAbility(int row, int col) {
		if(row==-1||col==-1) return;
		viewer.getCurrentPlayer().deltaResources(-1*miss_cost);
		viewer.getGameBoard().UnitAtSpace(myRow,myCol).useMove(viewer.currTurn());
		WUnit toDie = viewer.getGameBoard().UnitAtSpace(row,col);
		if(toDie!=null) toDie.killThisUnit();
		viewer.getGameBoard().removeTerrain(row,col);
		viewer.grid[row][col].doMouseEntered();

	}

}
