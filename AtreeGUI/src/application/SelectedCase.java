package application;

public class SelectedCase {
	public static String selectedTestCase="";
	public static String prevSelectedTestCase="";
	static SelectedCase inst = new SelectedCase();
	private SelectedCase()
	{
		
	}
	
	public static SelectedCase getInstance()
	{
		if(inst == null)
		{
			inst = new SelectedCase();
		}
		return inst;
	}
	
	public static String getPrevSelectedTestCase() {
		return prevSelectedTestCase;
	}

	public static void setPrevSelectedTestCase(String prevSelectedTestCase) {
		SelectedCase.prevSelectedTestCase = prevSelectedTestCase;
	}

	public static String getSelectedTestCase() {
		return selectedTestCase;
	}

	public static void setSelectedTestCase(String selectedTestCase) {
		SelectedCase.selectedTestCase = selectedTestCase;
	}

}
