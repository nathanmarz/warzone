package warzone;

import java.util.*;

public class WPlayer {
	private int resources;
	private WViewer viewer;
	private int side;
	private int res_from_farm;
	private ArrayList myUnits;
	
	public WPlayer(WViewer viewer1, int side1, int startRes) {
		resources = startRes;
		viewer = viewer1;
		side = side1;
		res_from_farm = 3;
		viewer.changeResInsp(side, resources);
		myUnits = new ArrayList();
	}
	
	public ArrayList getUnits() {
		return (ArrayList) myUnits.clone();
	}
	
	public void farmUpdate() {
		deltaResources(res_from_farm);
	}
	
	public void FarmUpgrade(int newResFromFarm1) {
		res_from_farm = newResFromFarm1;
	}
	
	public void addUnit(WUnit wu) {
		myUnits.add(wu);
	}
	
	public void removeUnit(WUnit wu) {
		myUnits.remove(wu);
	}
	
	public String toString() {
		if(side==WUnit.RED_TEAM) return "Red Player";
		else
			return "Blue Player";
	}
	
	public void deltaResources(int change) {
		resources+=change;
		viewer.changeResInsp(side, resources);
	}
	
	public void TurnSwitchTo() {
		viewer.changeResInsp(side, resources);
		Iterator it = myUnits.iterator();
		while(it.hasNext()) {
			WUnit wu = (WUnit) it.next();
			wu.beginTurn();
		}
	}
	
	public int getResources() {return resources; }
}
