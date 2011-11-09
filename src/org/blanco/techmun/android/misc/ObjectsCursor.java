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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.database.AbstractCursor;

public class ObjectsCursor extends AbstractCursor {

	private String[] colNames=new String[]{"object"};
	List<Object> objects = null;
	
	public ObjectsCursor(List<? extends Object> objects){
		this.objects = new ArrayList<Object>(5);
		if (objects != null){
			this.objects.addAll(objects);
		}
		
	}
	
	@Override
	public String[] getColumnNames() {
		return colNames;
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public double getDouble(int arg0) {
		return 0;
	}

	@Override
	public float getFloat(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShort(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getString(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNull(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] getBlob(int column) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(stream);
			out.writeObject(objects.get(column));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream.toByteArray();
	}
	
}
