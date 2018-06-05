package caseDesigner;

public class ActiveModule {
	String activeModId;
	
	public String getActiveModId() {
		return activeModId;
	}

	public void setActiveModId(String activeModId) {
		this.activeModId = activeModId;
	}

	static ActiveModule clsObj = new ActiveModule();

	private ActiveModule() {
	}

	public static ActiveModule getInstance() {
		if (clsObj == null) {
			clsObj = new ActiveModule();
		}
		return clsObj;
	}
}
