package application;

public class AtreeFolder {
	
	String aTreeFolder;
	static AtreeFolder classObj = new AtreeFolder();

	private AtreeFolder()
	{		
		
	}
	
	public static AtreeFolder getInstance()
	{
		if(classObj == null)
		{
			classObj = new AtreeFolder();
		}
		return classObj;
	}
	
	public String getATreeFolder() {
		return aTreeFolder;
	}

	public void setATreeFolder(String aTreeFolder) {
		this.aTreeFolder = aTreeFolder;
	}
	
}
