package warzone;

public abstract class WSpecialAbility {
	protected WViewer viewer;
	private String description;
	
	public WSpecialAbility(WViewer viewer1) {
		viewer = viewer1;
		description = "";
		
	}
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	//row,col is where it is invoked
	public abstract void DoSpecial(int row, int col);
	
	public WSpecialAbility getClone() {
		WSpecialAbility ret = getCopy();
		ret.setDescription(getDescription());
		return ret;
	}
	
	public void UnitKilled(WUnit specialOwner) { }
	
	protected abstract WSpecialAbility getCopy();
	
	public void DoTurnEnter() {}
	public void SpecialSelect(int row, int col) {}
}
