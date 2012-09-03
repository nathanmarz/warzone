package warzone;

import java.util.ArrayList;

public class WSpecialFactory extends WUnitCreator {
	private int factRow;
	private int factCol;
	protected WStoreItem unitToPlace;
	
	public WSpecialFactory(WViewer viewer1, ArrayList possibleUnits1) {
		super(viewer1, possibleUnits1);
	}
	
	public void DoSpecial(int row, int col) {
		if(SpecialAllowed(row,col)) {
			super.DoSpecial(row,col);
		}
	}
	
	protected boolean SpecialAllowed(int row, int col) {
		WUnit theFact = viewer.getGameBoard().UnitAtSpace(row,col);
		return theFact.getMoves(viewer.currTurn())>0;
			
	}
	
	protected WSpecialAbility getCopy() {
		return new WSpecialFactory(viewer, new ArrayList(possibleUnits));
	}
	
	protected void PlaceUnit(int row, int col, WStoreItem unitToPlace1) {
		factRow = row;
		factCol = col;
		unitToPlace = unitToPlace1;
		viewer.HighlightEmptyAround(row,col);
		viewer.SpecialSelect(this);
	}
	
	
	public void SpecialSelect(int row, int col) {
		viewer.UnHighlightSquaresAround(factRow, factCol);
		if(row==-1||col==-1) return;
		DoPlaceUnit(row,col);
		FinishedUnload();
		viewer.grid[row][col].doMouseEntered(); //quasi-hack, b/c this function guarentees that square was just clicked
	}
	
	protected void FinishedUnload() {
		viewer.getGameBoard().UnitAtSpace(factRow,factCol).useMove(viewer.currTurn());
	}
	
	protected void DoPlaceUnit(int row, int col) {
		int side = viewer.getGameBoard().UnitAtSpace(factRow, factCol).getSide();
		viewer.createUnit(unitToPlace.getUnit(), side, row, col);
		viewer.getPlayer(side).deltaResources(-1*unitToPlace.getPrice());
	}

}
