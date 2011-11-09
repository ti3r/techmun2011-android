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
package org.blanco.techmun.android;

import org.blanco.techmun.android.misc.AuthTask;
import org.blanco.techmun.android.misc.AuthTask.AuthTaskListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements AuthTaskListener {

	/** The intent action for this Activity as defined in the manifest */
	public static final String ACTION_INTENT = "org.blanco.techmun2011.LOGIN";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.login_layout);
		init();
		super.onCreate(savedInstanceState);
	}

	EditText edtPassword;
	EditText edtUser;
	
	public void init(){
		edtUser = (EditText) findViewById(R.id.login_layout_edt_user);
		edtPassword = (EditText) findViewById(R.id.login_layout_edt_password);
		Button btnOk = (Button) findViewById(R.id.login_layout_btn_accept);
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				login();	
			}
		});
		Button btnCancel = (Button) findViewById(R.id.login_layout_btn_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Button btnLogout = (Button) findViewById(R.id.login_layout_btn_logout);
		if (!PreferenceManager.getDefaultSharedPreferences(getBaseContext())
				.contains("user")){
			btnLogout.setVisibility(View.GONE);
		}
		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit()
				.remove("user").commit();
				setResult(RESULT_OK);
				finish();
			}
		});
	}
	
	ProgressDialog dialog = null;
	
	private void login(){
		dialog = ProgressDialog.show(this, 
				getString(R.string.auth_progress_dialog_title), 
				getString(R.string.auth_progress_dialog_cap),false);
		
		String password = edtPassword.getText().toString();
		String user = edtUser.getText().toString();
		AuthTask autenticate = new AuthTask(this);
		autenticate.execute(user,password);
	}

	@Override
	public void onAuthFinished(boolean valid) {
		dialog.dismiss();
		if (valid){
			Editor editor = 
			PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
			editor.putString("tec-ch-mun-2011-user", edtUser.getText().toString());
			editor.commit();
			Toast.makeText(getBaseContext(), getString(R.string.login_correct_msg), 
					Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK);
			PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit()
			.putString("user", edtUser.getText().toString()).commit();
			finish();
		}else{
			Toast.makeText(getBaseContext(), getString(R.string.login_failed_msg), 
					Toast.LENGTH_SHORT).show();
		}
	}
	
	
}
