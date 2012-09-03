package warzone;

import java.util.ArrayList;

public class WSettlerSpecial extends WUnitCreator {

	/* (non-Javadoc)
	 * @see WUnitCreator#PlaceUnit(int, int, WStoreItem)
	 */
	public WSettlerSpecial(WViewer viewer1, ArrayList possibleUnits1) {
		super(viewer1, possibleUnits1);
	}
	
	public void DoSpecial(int row, int col) {
		if(viewer.getGameBoard().UnitAtSpace(row,col).getMoves(viewer.currTurn())>0) {
			super.DoSpecial(row,col);
		}
	}
	
	protected WSpecialAbility getCopy() {
		return new WSettlerSpecial(viewer, new ArrayList(possibleUnits));
	}
	
	protected void PlaceUnit(int row, int col, WStoreItem unitToPlace) {
		viewer.getGameBoard().UnitAtSpace(row,col).killThisUnit();
		viewer.createUnit(unitToPlace.getUnit(), viewer.whoseTurn(), row, col);
		viewer.getCurrentPlayer().deltaResources(-1*unitToPlace.getPrice());
		viewer.getGameBoard().UnitAtSpace(row,col).useAllMoves(viewer.currTurn());
	}

}
