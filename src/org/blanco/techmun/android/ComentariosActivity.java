package org.blanco.techmun.android;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.blanco.techmun.android.cproviders.TechMunContentProvider;
import org.blanco.techmun.android.misc.ComentariosListAdapter;
import org.blanco.techmun.entities.Comentario;
import org.blanco.techmun.entities.Comentarios;
import org.blanco.techmun.entities.Evento;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


/**
 * The activity in charge of handling all the operations
 * concerning comentario objects for a specific event
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class ComentariosActivity extends ListActivity {

	private Evento evento = null;
	ProgressBar progress = null;
	Button btnRefresh = null;
	Button btnMore = null;
	ComentariosLoader loader = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.comentarios_list_layout);
		initComponents();		
	}
		
	@Override
	protected void onStart() {
		loader = new ComentariosLoader();
		evento = new Evento();
		evento.setId(1L); evento.setEvento("Evento de prueba"); evento.setFecha(new Date());
		evento.setMesaId(1L);
		fetchComments();
		super.onStart();
	}

	private void initComponents(){
		progress = (ProgressBar) findViewById(R.id.comentarios_list_layout_title_bar_progress_bar);
		btnRefresh = (Button) findViewById(R.id.comentarios_list_layout_refresh_button);
		btnRefresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fetchComments();
			}
		});
		btnMore = (Button) findViewById(R.id.comentarios_list_layout_more_button);
		btnMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fetchMoreComments();
			}
		});
		Log.i("Techmun2011", "Comentarios activity - finish initComponents");
	}
	
	public void fetchComments(){
		if (loader != null && loader.getStatus().equals(AsyncTask.Status.RUNNING)){
			loader.cancel(true);
		}
		loader = new ComentariosLoader();
		ComentariosLoaderParam param = new ComentariosLoaderParam();
		param.action = ComentariosLoaderParam.NEW_SEARCH;
		param.evento = evento;
		loader.execute(param);
	}
	
	public void fetchMoreComments(){
		if (loader != null && loader.getStatus().equals(AsyncTask.Status.RUNNING)){
			loader.cancel(true);
		}
		loader = new ComentariosLoader();
		ComentariosLoaderParam params = new ComentariosLoaderParam();
		params.evento = evento;
		params.action = ComentariosLoaderParam.MORE_COMMENTS;
		params.comentario = new Comentario();
		loader.execute(params);
	}
	
	private void setFetchingStatus(){
		if (progress != null){
			progress.setVisibility(View.VISIBLE);
		}
		btnRefresh.setEnabled(false);
		btnMore.setEnabled(false);
	}
	
	private void hideFetchingStatus(){
		if (progress != null){
			progress.setVisibility(View.GONE);
		}
		btnRefresh.setEnabled(true);
		btnMore.setEnabled(true);
	}
	
	/**
	 * The class in charge of loading the Comentarios object from the content
	 * provider and populate the list.
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	private class ComentariosLoader extends AsyncTask<ComentariosLoaderParam, 
			Void, ComentariosLoaderResult>{

		@Override
		protected void onPreExecute() {
			setFetchingStatus();
		}

		@Override
		protected ComentariosLoaderResult doInBackground(ComentariosLoaderParam... parameter) {
			if (parameter.length != 1){
				throw new RuntimeException("Can only load comments for one parameter." +
						" Params Length: "+parameter.length);
			}
			Comentarios result = new Comentarios();
			
			Evento evento = parameter[0].evento;
			Cursor c = null;
			if (parameter[0].action == ComentariosLoaderParam.NEW_SEARCH){
				c = getContentResolver().query(
					Uri.parse(TechMunContentProvider.CONTENT_BASE_URI+"/"+evento.getMesaId()
							+"/eventos/"+evento.getId()+"/comentarios"), 
					null, null, null, null);
			}else{
				Comentario lastComent = parameter[0].comentario;
				//c = getContentResolver().query(
				//		Uri.parse(TechMunContentProvider.CONTENT_BASE_URI+"/"+evento.getMesaId()
				//		+"/eventos/"+evento.getId()+"/comentarios/"+lastComent.getId()+"/mas"), 
				//		null, null, null, null);
				c = new MatrixCursor(new String[]{"id","eventoId","comentario"},1);
				List<Object> row = new ArrayList<Object>();
				row.add(1L); row.add(1L); row.add("Comentario Mas de Prueba");
				((MatrixCursor)c).addRow(row);
			}
			if (c != null){
				while (c.moveToNext()){
					Comentario comentario = new Comentario();
					comentario.setId(c.getLong(c.getColumnIndex("id")));
					comentario.setEventoId(c.getLong(c.getColumnIndex("eventoId")));
					comentario.setComentario(c.getString(c.getColumnIndex("comentario")));
					result.addComentario(comentario);
				}
				c.close();
			}
			ComentariosLoaderResult results = new ComentariosLoaderResult();
			results.comentarios = result;
			results.action = parameter[0].action;
			return results;
		}

		@Override
		protected void onPostExecute(ComentariosLoaderResult result) {
			if (result.action == ComentariosLoaderParam.NEW_SEARCH){
				ComentariosListAdapter adapter = new ComentariosListAdapter(result.comentarios);
				ComentariosActivity.this.setListAdapter(adapter);
			}else if (ComentariosActivity.this.getListAdapter() != null){
				ComentariosListAdapter adapter =
						((ComentariosListAdapter)ComentariosActivity.this.getListAdapter());
				adapter.addComentarios(result.comentarios);
				adapter.notifyDataSetChanged();
			}
			hideFetchingStatus();
		}

		@Override
		protected void onCancelled() {
			hideFetchingStatus();
			super.onCancelled();
		}

		
	}
	
	/**
	 * A private class that will hold the parameters for the Comentarios Loader
	 * in order to know if a new search, a research or a more action should 
	 * be executed and how the results should be treated.
	 *  
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	private class ComentariosLoaderParam{
		
		static final int NEW_SEARCH = 0;
		static final int MORE_COMMENTS = 1;
		
		protected Evento evento = null;
		protected Comentario comentario = null;
		protected int action = -1;
	}
	
	/**
	 * Private class that will hold the results for the comentarios loader
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	private class ComentariosLoaderResult{
		protected int action = -1;
		protected Comentarios comentarios = null;
	}
	
}
