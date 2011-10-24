package org.blanco.techmun.android.misc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class AuthTask extends AsyncTask<String, Void, Boolean>{

	AuthTaskListener listener = null;
	
	public AuthTask(AuthTaskListener listener){
		this.listener = listener;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		if (params.length != 2){
			throw new RuntimeException("Parameters for AuthTask should be exactly 2. " +
					"String user, String password");
		}
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] pbytes = digest.digest(params[1].getBytes());
			String md5password = new String(pbytes);
			HttpPost post = new HttpPost("http://tec-ch-mun-2011.herokuapp.com/application/autenticarUsuario");
			List<BasicNameValuePair> parameters = 
					new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair("user", params[0]));
			parameters.add(new BasicNameValuePair("md5password", md5password));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters);
			post.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);
			boolean result = false;
			switch (response.getStatusLine().getStatusCode()) {
			case 200:
				Log.i("techmun", "Authentication request returned OK");
				result = true;
				break;
			default:
				Log.i("techmun", "Authentication request returned: "
						+response.getStatusLine().getStatusCode());
				result = false;
			}
			//client.close();
			return result;
		} catch (NoSuchAlgorithmException e) {
			Log.e("techmun", "Error geting md5 instance",e);
		} catch (UnsupportedEncodingException e) {
			Log.e("techmun", "Error preparing entity for autentication",e);
		} catch (IOException e) {
			Log.e("techmun", "Error sending autentication form",e);
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (this.listener != null){
			this.listener.onAuthFinished(result);
		}
		super.onPostExecute(result);
	}
	
	public interface AuthTaskListener{
		public void onAuthFinished(boolean valid);
	}
	
}
