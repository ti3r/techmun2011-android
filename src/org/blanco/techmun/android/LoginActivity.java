package org.blanco.techmun.android;

import org.blanco.techmun.android.misc.AuthTask;
import org.blanco.techmun.android.misc.AuthTask.AuthTaskListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
			Intent intent = new Intent();
			intent.putExtra("usuario", edtUser.getText().toString());
			setResult(RESULT_OK, intent);
			finish();
		}else{
			Toast.makeText(getBaseContext(), getString(R.string.login_failed_msg), 
					Toast.LENGTH_SHORT).show();
		}
	}
	
	
}
