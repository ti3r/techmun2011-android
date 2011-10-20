package org.blanco.techmun.android.fragments;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import org.blanco.techmun.android.EventosActivity;
import org.blanco.techmun.android.R;
import org.blanco.techmun.android.misc.MesaListItemClickListener;
import org.blanco.techmun.android.misc.MesasCursorAdapter;
import org.blanco.techmun.entities.Mesa;
import org.blanco.techmun.entities.Mesas;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MesasListFragment extends Fragment 
		implements MesaListItemClickListener{

	ListView list = null;
	Uri mesasUri = null;
	ProgressBar progressBar = null;
	LoadTablesTask loadTablesTask = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    mesasUri = Uri.parse("content://org.blanco.techmun.android.mesasprovider"); 
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.mesas_fragment_main_layout, null);
		list = (ListView) result.findViewById(R.id.mesas_fragment_mesas_list);
		progressBar = (ProgressBar) result
				.findViewById(R.id.mesas_fragment_title_bar_progress_bar);
		loadTablesTask = new LoadTablesTask();
		loadTablesTask.execute(getActivity());
		return result;
	}
	
	/**
	 * Makes visible the progress bar on the title bar of the fragments layout.
	 * If the progress bar was not retrieved when the layout was inflated a 
	 * run time exception is thrown.
	 */
	public void showProgressBar() {
		if (progressBar == null){
			throw new RuntimeException("Unable to make visible the progress bar on the title. " +
					"The object is null, check the inflate logs");
		}
		else{
			progressBar.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * Makes invisible the progress bar on the title bar of the fragments layout.
	 * If the progress bar was not retrieved when the layout was inflated a 
	 * run time exception is thrown.
	 */
	public void hideProgressBar() {
		if (progressBar == null){
			throw new RuntimeException("Unable to make invisible the progress bar on the title. " +
					"The object is null, check the inflate logs");
		}
		else{
			progressBar.setVisibility(View.GONE);
		}
	}
	
	/**
	 * The Asynchronous task in charge of loading the available tables from the
	 * content provider and populate the list of the fragment.
	 * 
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	private class LoadTablesTask extends AsyncTask<Activity, Void, Mesas>{
		
		@Override
		protected void onPreExecute() {
			//Display the progress bar
			MesasListFragment.this.showProgressBar();
		}

		@Override
		protected Mesas doInBackground(Activity... params) {
			Activity ctx = null;
			if (params.length != 1){
				throw new RuntimeException("Activities passed for LoadTableTask" +
						" must be exactly one ");
			}
			ctx = params[0];
			Cursor cursor = ctx.getContentResolver()
					.query(mesasUri, null, null, null, null);
			Mesas result = new Mesas();
			if (cursor != null){
				int x = 0;
				while (cursor.moveToNext()){
					byte[] bytes = cursor.getBlob(x++);
					ObjectInputStream in;
					try {
						in = new ObjectInputStream(new ByteArrayInputStream(bytes));
						Object o = in.readObject();
						result.getMesas().add((Mesa) o);
					} catch (StreamCorruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				cursor.close();
			}
			return result ;
		}

		@Override
		protected void onPostExecute(Mesas result) {
			if (list != null){
				MesasCursorAdapter adapter = null;
				adapter = new MesasCursorAdapter(getActivity(),
						R.layout.mesas_list_item_layout,
						android.R.layout.simple_list_item_1,
						result.getMesas(),MesasListFragment.this);
				list.setAdapter(adapter);
			}
			//Hide the progress bar.
			MesasListFragment.this.hideProgressBar();
		}
		
		
		
	}

	public void MesaListItemClicked(Mesa mesa) {
		Bundle extras = new Bundle();
		extras.putSerializable("mesa", mesa);
		Intent i = new Intent(EventosActivity.ACTION_INTENT);
		i.putExtras(extras);
		startActivity(i);
	}
	
}
