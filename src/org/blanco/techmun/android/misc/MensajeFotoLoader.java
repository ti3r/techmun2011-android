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

import org.blanco.techmun.android.R;
import org.blanco.techmun.entities.Mensaje;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class MensajeFotoLoader extends AsyncTask<Void, Void, Bitmap>{

	private Mensaje mensjae;
	private ImageView view = null;
	private MensajeFootLoaderExtraPostExecute listener = null;
	
	public MensajeFotoLoader(Mensaje mensaje, ImageView view){
		this(mensaje,view,null);
	}
	
	public MensajeFotoLoader(Mensaje mensaje, ImageView view, 
			MensajeFootLoaderExtraPostExecute listener){
		this.mensjae = mensaje;
		this.view = view;
		this.listener = listener;
	}

	@Override
	protected Bitmap doInBackground(Void... arg0) {
		//Foto is now encoded on the encoded_foto field all we need to do is
		//to decode that foto using Base 64 algorithm
		Bitmap result = null;
		if (this.mensjae.getFoto_encoded() != null &&
				!this.mensjae.getFoto_encoded().equals("")){
			try{
				byte bytes[] = Base64Coder.decode(this.mensjae.getFoto_encoded().toCharArray());
				result = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			}catch(Exception e){
				Log.d("techmun", "Problems decoding Mensaje foto: Mensaje Id"+mensjae.getId());
			}
		}
		return result;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null && view != null){
			//set the current image to the drawable
			view.setImageBitmap(result);
			mensjae.setFoto(result);
			//MensajesFetcher.tryToSaveFotosOnCache(view.getContext(), mensajeId);
		}else if (view != null){
			//set the default value
			BitmapDrawable drawable = (BitmapDrawable) view.getContext()
					.getResources().getDrawable(R.drawable.icon); 
			view.setImageDrawable(drawable);
			mensjae.setFoto(drawable.getBitmap());
		}
		if (listener != null){
			listener.executeAfterOnPostExecute(result);
		}
		super.onPostExecute(result);
	}
	
	public interface MensajeFootLoaderExtraPostExecute{
		public void executeAfterOnPostExecute(Bitmap bitmap);
	}
	
}