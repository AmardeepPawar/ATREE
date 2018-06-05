package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfigurationFileClass {
	static String propertyValue;
	static Properties property = new Properties();
	static ReadConfigurationFileClass configFileObj= new ReadConfigurationFileClass();

	private ReadConfigurationFileClass()
	{		
		try (InputStream inputFile = new FileInputStream(new File("AtreeConfiguration.properties"))){
			
			try {
				property.load(inputFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static ReadConfigurationFileClass getConfigFileObj()
	{		
		if(configFileObj == null)
		{
			configFileObj = new ReadConfigurationFileClass();
		}
		return configFileObj;		
	}
	
	public String getPropertyValue(String KeyValue) {
		propertyValue=property.getProperty(KeyValue);
		return propertyValue;
	}
}
