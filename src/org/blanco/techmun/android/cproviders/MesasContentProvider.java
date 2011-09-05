package org.blanco.techmun.android.cproviders;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class MesasContentProvider extends ContentProvider {

	private String MESAS_REST_SERVICE_BSAE_URI = 
			"https://techmun2011.appspot.com/rest/mesas";
	DefaultHttpClient httpClient = new DefaultHttpClient();
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public String getType(Uri uri) {
		if (uri.toString().contains(MESAS_REST_SERVICE_BSAE_URI)){
			return "org.blanco.techmun.entities.Mesa";
		} else {			
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		HttpGet req = new HttpGet(MESAS_REST_SERVICE_BSAE_URI);
		Cursor result = new MatrixCursor(new String[]{"id","nombre","representante"});
		try {
			HttpResponse response = httpClient.execute(req);
			HttpEntity entity = response.getEntity();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
