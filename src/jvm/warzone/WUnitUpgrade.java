package warzone;


import java.util.*;

public class WUnitUpgrade extends WTechnology {
	
	private String unitType;
	private WUnitModel newModel;
	private String newImage;
	private boolean updateCurr; //this will only work for base characteristics, it won't affect traits with state (i.e. WResearchAbility)
	
	public WUnitUpgrade() {
		super(null, null, 0, 0);
	}
	
	
	public WUnitUpgrade(WViewer viewer1, String name1, int price1, int numTurns1, String unitType1, WUnitModel newModel1, String newImage1, boolean updateCurr1) {
		this(viewer1, name1, price1, numTurns1, unitType1, newModel1, newImage1);
		updateCurr = updateCurr1;
	}
	
	public WUnitUpgrade(WViewer viewer1, String name1, int price1, int numTurns1, String unitType1, WUnitModel newModel1, String newImage1) {
		super(viewer1, name1, price1, numTurns1);
		unitType = unitType1;
		newModel = newModel1;
		newImage = newImage1;
		updateCurr = false;
		
	}
	
	public WTechnology createNewInstance() {
		return new WUnitUpgrade();
	}
	
	public void setAs(WTechnology other1) {
		super.setAs(other1);
		WUnitUpgrade other = (WUnitUpgrade) other1;
		unitType = other.unitType;
		newModel = other.newModel;
		newImage = other.newImage;
		updateCurr = other.updateCurr;
	}
	
	public void DoTechUpgrade() {
		int currSide = viewer.whoseTurn();
		WResource database = viewer.getDatabase();
		database.addUnitType(unitType, newImage, newModel, currSide);
		if(updateCurr) {
			ArrayList units = viewer.getCurrentPlayer().getUnits();
			Iterator it = units.iterator();
			while(it.hasNext()) {
				WUnit wu = (WUnit) it.next();
				if(wu.getUnitName().equals(unitType)) {
					wu.UpgradeToModel(newModel); //only updates base characteristic (HP, attack, defend, movesTotal, movable)
				}
			}
		}
	}

}
