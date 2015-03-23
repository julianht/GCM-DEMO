package com.androidbegin.gcmdemouam01;

import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class GCMMainActivity extends Activity {
	
	//Encabezado de la aplicación para el registro de logs	
	String TAG = "GCM DEMO UAM";
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Se realizan las validaciones de la aplicación en el servicio GCM
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		// Botón para registrar el dispositivo
		Button regbtn = (Button) findViewById(R.id.register);
		
		regbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String regId = GCMRegistrar.getRegistrationId(GCMMainActivity.this);
				
				AlertDialog alertDialog = new AlertDialog.Builder(GCMMainActivity.this).create();
				
				if(regId.equals(""))
				{
					//Se obtiene el senderID para registrarlo
					GCMRegistrar.register(GCMMainActivity.this,
										  GCMIntentService.SENDER_ID);
					
					alertDialog.setMessage("Registrando dispositivo");
					Log.i(TAG, "Registrando dispositivo");
				}
				else
				{
					alertDialog.setMessage("El dispositivo ya se encuentra registrado");
					Log.i(TAG, "El dispositivo ya se encuentra registrado");
				}
				
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			        }
			    });
				alertDialog.show();
			}
		});

		
		//Botón para la consulta del id del dispositivo
		Button btnConsultarId = (Button)findViewById(R.id.consultarIdRegistro);
		
		btnConsultarId.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String regId = GCMRegistrar.getRegistrationId(GCMMainActivity.this);
				
				if(regId.equals(""))
				{
					AlertDialog alertDialog = new AlertDialog.Builder(GCMMainActivity.this).create();
					alertDialog.setMessage("Dispositivo no registrado");
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) {
				        }
				    });
					alertDialog.show();
				}
				else
				{
					TextView txtIdRegistro = (TextView)findViewById(R.id.registerid);
					txtIdRegistro.setText(regId);
				}
			}
		});
	}
}
