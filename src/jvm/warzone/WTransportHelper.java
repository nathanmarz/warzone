package warzone;

import java.util.ArrayList;

public class WTransportHelper extends WSpecialFactory {
	private WTransportAbility mother;
	
	public WTransportHelper(WViewer viewer1, ArrayList possibleUnits1, WTransportAbility mother1) {
		super(viewer1, possibleUnits1);
		mother = mother1;
	}
	
	
	protected void DoPlaceUnit(int row, int col) {
		mother.DoPlaceUnit(row,col,unitToPlace.getPrice()-1);
	}
	
	protected void FinishedUnload() {}
	
	protected boolean SpecialAllowed(int row, int col) {
		return true;
	}
}
