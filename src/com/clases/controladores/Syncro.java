package com.clases.controladores;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log; 

public class Syncro implements ListenerDB {

	private final String namespace = "http://suarpe.com";
	private final String url = "http://192.168.1.51:80/ServicioClientes.asmx";
	Context context;

	public Syncro(Context c) {
		context = c;  
	}

	@Override
	public boolean ItemOrden(String placa, String norden, int codser,
			int cantd, int iva, int subtal, int total, String codtec) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void NuevoCliente(final String cc, final String nombre, final String dir,
			final String tel, final String mail, final String placa, final int marca, final int color,
			final int modelo, final int tipo) {
		
		final ProgressDialog pro = new ProgressDialog(context,
				android.R.style.Theme_Holo_Dialog_MinWidth);
		
		new AsyncTask<Void, String, Void>() {
			@Override
			protected void onPreExecute() {
				pro.setTitle("Guardando...");
				pro.setMessage("Espere Porfavor");
				pro.show();
			}

			@Override
			protected Void doInBackground(Void... params) {
				 
				try {
					SoapObject request = new SoapObject(namespace, "Registrar");
					request.addProperty("cc", cc);
					request.addProperty("nombre", nombre);
					request.addProperty("dir", dir);
					request.addProperty("tel", tel);
					request.addProperty("mail", mail);
					request.addProperty("placa", placa);
					request.addProperty("marca", marca);
					request.addProperty("color", color);
					request.addProperty("modelo", modelo);
					request.addProperty("tipo", tipo);  
					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					HttpTransportSE transportSE = new HttpTransportSE(url);
					transportSE.call("http://suarpe.com/Registrar", envelope);
					SoapPrimitive resultado = (SoapPrimitive) envelope.getResponse();
					Log.e("Error", resultado.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {  
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				pro.dismiss();
			}
		}.execute();

	}
}
