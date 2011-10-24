package org.blanco.techmun.android.fragments;

import org.blanco.techmun.android.R;
import org.blanco.techmun.android.misc.ExpandAnimation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ComentariosPublishFragment extends Fragment {

	TextView header = null;
	boolean expanded = false;
	int prevHeight = 0;
	Button btnEnviar = null;
	Button btnCancelar = null;
	EditText edtComment = null;
	EditText edtNombre = null;
	EditText edtEmail = null;
	Dialog dialog = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.comentarios_publish_layout, null); 
		
		header = (TextView) v.findViewById(R.id.comentarios_publish_layout_header);
		header.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				animate();
			}
		});
		btnEnviar = (Button) v.findViewById(R.id.comentarios_publish_layout_btn_accept);
		btnEnviar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sendComment();
				animate();
			}
		});
		btnCancelar = (Button) v.findViewById(R.id.comentarios_publish_layout_btn_cancel);
		btnCancelar.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				clearFields();
				animate();
			}
		});
		edtComment = (EditText) v.findViewById(R.id.comentarios_publish_layout_edt_comentario);
		edtNombre = (EditText) v.findViewById(R.id.comentarios_publish_layout_edt_nombre);
		edtEmail = (EditText) v.findViewById(R.id.comentarios_publish_layout_edt_email);
		return v;
	}

	private void clearFields(){
		if (edtComment != null)
			edtComment.getText().clear();
		if (edtNombre != null)
			edtNombre.getText().clear();
		if (edtEmail != null)
			edtEmail.getText().clear();
	}
	
	private void sendComment(){
		dialog = ProgressDialog.show(getActivity(), "", 
				getString(R.string.comentario_sending));
		ComentarioSender sender = new ComentarioSender();
		sender.execute();
	}
	
	private void animate(){
		int endHeight = (expanded)? header.getHeight() : 400;
		expanded = !expanded;
		ExpandAnimation anim = new ExpandAnimation(getView(), getView().getHeight(), endHeight);
		getView().startAnimation(anim);
	}
	
	class ComentarioSender extends AsyncTask<Void, Void, Uri>{

		@Override
		protected Uri doInBackground(Void... params) {
			ContentResolver resolver = getActivity().getContentResolver();
			ContentValues values = new ContentValues(3);
			values.put("comentario", edtComment.getText().toString());
			values.put("nombre", edtNombre.getText().toString());
			values.put("email", edtEmail.getText().toString());
			Uri result =
					resolver.insert(Uri.parse("content://org.blanco.techmun.android.mesasprovider/comentarios/add"), values);
			return result;
		}

		@Override
		protected void onPostExecute(Uri result) {
			if (result != Uri.EMPTY){
				Toast.makeText(getActivity(), getString(R.string.comentario_sent), 500).show();
				clearFields();
			}
			dialog.dismiss();
			super.onPostExecute(result);
		}

	}
	
}
