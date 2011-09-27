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
