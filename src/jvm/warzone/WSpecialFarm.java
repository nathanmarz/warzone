package warzone;

public class WSpecialFarm extends WSpecialAbility {

	public WSpecialFarm(WViewer viewer1) {
		super(viewer1);
	}
	
	protected WSpecialAbility getCopy() {
		return this;
	}
	
	public void DoSpecial(int row, int col) {}
	
	public void DoTurnEnter() {
		viewer.getCurrentPlayer().farmUpdate();
	}

}
