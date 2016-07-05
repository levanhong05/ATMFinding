package lvhong.tim.atm.adapter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import lvhong.tim.atm.data.PlaceArray;

public class PlaceHandler extends DefaultHandler{
	public static PlaceArray itemList;
	public boolean current = false;
	public String currentValue = null;
	
	@Override
	public void startElement(String uri, String localName, String qName,	Attributes attributes) throws SAXException {
		current = true;
		if (localName.equals("kml")){
			itemList = new PlaceArray();
		} 
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)	throws SAXException {
		current = false;
		if(localName.equals("name")){
			itemList.setListPlaceName(currentValue);
		}else if(localName.equals("longitude"))		{
			itemList.setListLngPoint(currentValue);
		}else if(localName.equals("latitude")){
			itemList.setListLatPoint(currentValue);
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)throws SAXException {
		if(current){
			currentValue = new String(ch, start, length);
			current=false;
		}
	}
}

