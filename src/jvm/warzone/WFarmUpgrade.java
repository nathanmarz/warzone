package warzone;

public class WFarmUpgrade extends WTechnology {
	private int newFarmPower;
	
	public WFarmUpgrade() {
		super(null, null, 0, 0);
	}
	
	
	public WFarmUpgrade(WViewer viewer1, String name1, int price1, int numTurns1, int newFarmPower1) {
		super(viewer1, name1, price1, numTurns1);
		newFarmPower = newFarmPower1;
	}
	
	public WTechnology createNewInstance() {
		return new WFarmUpgrade();
	}
	
	public void setAs(WTechnology other) {
		super.setAs(other);
		newFarmPower = ((WFarmUpgrade) other).newFarmPower;
	}

	
	public void DoTechUpgrade() {
		WPlayer currPlayer = viewer.getCurrentPlayer();
		currPlayer.FarmUpgrade(newFarmPower);
	}

}
