package com.umbrella.sg16drawabletest.xmlparser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class CitiesParser {
	public List<String> getCities(String xml) {
		List<String> res = new ArrayList<String>();
		res.add("«Î—°‘Ò≥« –");
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(xml));
			int eventType = parser.getEventType();
			while(eventType!=XmlPullParser.END_DOCUMENT) {
				switch(eventType) {
				case XmlPullParser.START_TAG:
					String tagName = parser.getName();
					if("string".equals(tagName)) {
						String city = parser.nextText();
						res.add(city.split(",")[0]);
					}
					break;
				case XmlPullParser.END_TAG:
					break;
					default:
						break;
				}
				eventType = parser.next();
			}
		} catch(Exception e) {
			
		}
		return res;
	}
	
	public Map<String, String> getCitiesMap(String xml) {
		return null;
	}
}
