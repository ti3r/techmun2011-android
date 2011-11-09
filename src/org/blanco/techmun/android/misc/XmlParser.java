/**
 * Tec ch mun 2011 for Android, is the android application used to 
 *  
 * review all the information that is generated during the event
 * Tec Ch Mun 2011 of the ITESM campus chihuahua.
 * You can use this application as an example of all the technologies
 * used in this app.
 * Copyright (C) 2011  Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Visit http://tec-ch-mun-2011.herokuapps.com
 */
package org.blanco.techmun.android.misc;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlParser {

	public static synchronized Document parseHttpEntity(HttpEntity entity)
			throws Exception{
		Document doc = null;
		String xmlString = null;
		try {
			xmlString = EntityUtils.toString(entity);
			DocumentBuilder docBuilder = DocumentBuilderFactory
				.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (ParseException e1) {
			throw new Exception("Error parsing the entity.",e1);
		} catch (IOException e1) {
			throw new Exception("Error while reading the entity.",e1);
		} catch (ParserConfigurationException e) {
			throw new Exception("Error in the parser configuration.",e);
		} catch (SAXException e) {
			throw new Exception("Error parsing the entity.",e);
		}
	}
	
	public static synchronized JSONArray parseJSONArrayFromHttpEntity(HttpEntity entity) 
			throws Exception{
		String jsonText = null;
		try {
			jsonText = EntityUtils.toString(entity);
			JSONArray result = new JSONArray(jsonText);
			return result;
		} catch (ParseException e) {
			throw new Exception("Error parsing the entity.",e);
		} catch (IOException e) {
			throw new Exception("Error reading the entity.",e);
		}
	}
	
	public static synchronized JSONObject parseJSONObjectFromHttpEntity(HttpEntity entity) 
			throws Exception{
		String jsonText = null;
		try {
			jsonText = EntityUtils.toString(entity);
			JSONObject result = new JSONObject(jsonText);
			return result;
		} catch (ParseException e) {
			throw new Exception("Error parsing the entity.",e);
		} catch (IOException e) {
			throw new Exception("Error reading the entity.",e);
		}
	}
	
}
