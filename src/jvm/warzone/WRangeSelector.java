package warzone;

import java.util.*;

public abstract class WRangeSelector extends WSpecialAbility {
	protected int range;
	protected ArrayList possible;
	protected int myRow;
	protected int myCol;
	
	public WRangeSelector(WViewer viewer, int range1, ArrayList possible1) {
		super(viewer);
		range = range1;
		possible = possible1;
	}
	
	
	
	public void DoSpecial(int row, int col) {
		myRow = row;
		myCol = col;
		viewer.HighlightRangeAround(row,col,range, possible);
		viewer.ChooseSquareForSpecial(this);
	}
	
	public void SpecialSelect(int row, int col) {
		viewer.UnHighlightSquaresAround(myRow,myCol,range);
		DoAbility(row, col);
	}
	
	protected abstract void DoAbility(int row, int col);

}
