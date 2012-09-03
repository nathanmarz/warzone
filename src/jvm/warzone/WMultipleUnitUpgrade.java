package warzone;

import java.util.*;

public class WMultipleUnitUpgrade extends WTechnology {
	private ArrayList upgrades;
	
	public WMultipleUnitUpgrade() {
		super(null,null,0,0);
	}
	
	public WMultipleUnitUpgrade(WViewer viewer1, String name1, int price1, int numTurns1, ArrayList upgrades1) {
		super(viewer1, name1, price1, numTurns1);
		upgrades = upgrades1;
		
	}
	
	public WTechnology createNewInstance() {
		// TODO Auto-generated method stub
		return new WMultipleUnitUpgrade();
	}
	
	public void setAs(WTechnology other) {
		super.setAs(other);
		upgrades = ((WMultipleUnitUpgrade)other).upgrades;
	}

	
	public void DoTechUpgrade() {
		Iterator it = upgrades.iterator();
		while(it.hasNext()) {
			WUnitUpgrade wuu = (WUnitUpgrade) it.next();
			wuu.DoTechUpgrade();
		}

	}

}
