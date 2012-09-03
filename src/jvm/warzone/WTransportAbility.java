package warzone;

import java.util.*;

public class WTransportAbility extends WSpecialAbility {
	private ArrayList validUnits; //arraylists of Strings
	private ArrayList invalidUnits;
	private int maxAmt;
	private ArrayList storedUnits;
	
	//accepts what's in validUnits or not in invalidUnits (ignores ArrayList if it's null)
	public WTransportAbility(WViewer viewer1, ArrayList validUnits1, ArrayList invalidUnits1, int fillAmt1) {
		super(viewer1);
		validUnits = validUnits1;
		invalidUnits = invalidUnits1;
		maxAmt = fillAmt1;
		storedUnits = new ArrayList();
	}
	
	public void DoSpecial(int row, int col) {
		if(storedUnits.size()==0) return;
		ArrayList hack = new ArrayList();
		for(int i=0;i<storedUnits.size();i++) {
			hack.add(new WStoreItem(((WUnit)storedUnits.get(i)).getName(), i+1));
		}
		WTransportHelper hp = new WTransportHelper(viewer, hack, this);
		hp.DoSpecial(row,col);
	}
	
	public void UnitKilled(WUnit owner) {
		killUnitsWithin();
	}
	
	public void addUnitIntoTransport(WUnit wu) {
		storedUnits.add(wu);
		viewer.EndUnitTurn(wu);
		viewer.getGameBoard().removeUnit(wu);
	}
	
	public void killUnitsWithin() {
		Iterator it = storedUnits.iterator();
		while(it.hasNext()) {
			WUnit wu = (WUnit) it.next();
			wu.killFromTransport();
		}
	}
	
	public boolean AcceptsUnitType(String unitType) {
		if(storedUnits.size()>=maxAmt) return false;
		boolean ret = false;	
		if(invalidUnits!=null) {
			if(invalidUnits.contains(unitType)) {
				ret = false;
			} else {
				ret = true;
			}
		}
		
		if(validUnits!=null) {
			if(validUnits.contains(unitType)) {
				ret = true;
			}
		}	
		return ret;
		
	}

	protected WSpecialAbility getCopy() {
		return new WTransportAbility(viewer, validUnits, invalidUnits, maxAmt);
	}
	
	public void DoPlaceUnit(int row, int col, int pos) {
		WUnit wu = (WUnit) storedUnits.remove(pos);
		viewer.getGameBoard().addUnit(wu,row,col);
		wu.useAllMoves(viewer.currTurn());
	}

}
