package application;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import application.beans.ModulesGroupPropertyBean;

public class SetModuleGroupProperties {
	static DocumentBuilderFactory factory;
	static String modulesFolderMapFile = ConfigVariables.MODTYNFLDMAP_XMLFILE;
	static File inputFile = new File(modulesFolderMapFile);
	static DocumentBuilder builder;
	static Document doc;
	static SetModuleGroupProperties obj = new SetModuleGroupProperties();
	String modGrpId;
	
	private SetModuleGroupProperties()
	{
		
	}
	
	public static SetModuleGroupProperties getInstance()
	{
		if(obj == null)
		{
			obj = new SetModuleGroupProperties();
		}
		return obj;		
	}
	
	public void setModuleGrpProp()
	{
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		KeyValueMapping keyValMap = KeyValueMapping.getInstance();
		Map<String,ModulesGroupPropertyBean> modGrpMap = keyValMap.getModulesGroupPropertyBean();
		modGrpMap.clear();			
		Attr moduleTypeAttr;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(inputFile);
			doc.getDocumentElement().normalize();
			Node rootElement = doc.getDocumentElement();
			NodeList moduleTypeList = rootElement.getChildNodes();
			for(int i=0;i<moduleTypeList.getLength();i++)
			{				
				if(moduleTypeList.item(i).getNodeType() == Node.ELEMENT_NODE)
				{
			       ModulesGroupPropertyBean modGrpProp = new ModulesGroupPropertyBean();
				   Node ModuleType = moduleTypeList.item(i);
				   moduleTypeAttr=(Attr) ModuleType.getAttributes().getNamedItem("id");
				   modGrpId= moduleTypeAttr.getValue();
				   modGrpProp.setModuleGrpId(moduleTypeAttr.getValue());
				   moduleTypeAttr=(Attr) ModuleType.getAttributes().getNamedItem("name");
				   modGrpProp.setModuleGrpName(moduleTypeAttr.getValue());
				   moduleTypeAttr=(Attr) ModuleType.getAttributes().getNamedItem("folderName");
				   modGrpProp.setModuleGrpFldr(moduleTypeAttr.getValue());
				   modGrpMap.put(modGrpId, modGrpProp);
				}
			}		

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
