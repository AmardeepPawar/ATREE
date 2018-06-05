package application;

import java.io.File;
import java.util.ArrayList;

public class ReadFolder {
	    static ReadFolder obj =new ReadFolder();
	    private ReadFolder()
	    {
	    	
	    }
	    public static ReadFolder getInstance()
	    {
	    	if(obj == null)
	    	{
	    		obj =new ReadFolder();
	    	}
			return obj;	    	
	    }
		public ArrayList<String> artyList;
		public ArrayList<String> readFileList(String folderName){
			try
			{
			File dir = new File(folderName);
			String[] list = dir.list();	
			artyList = new ArrayList<String>();
			String fileregex = ".+\\.atry$";
			for ( String flist : list)
			{		 			
				if (flist.matches(fileregex))
				{
					artyList.add(flist);
				}			
			}
			}
			catch(Exception c)
			{
				System.out.println(c);
			}
			return artyList;
		}
	}


