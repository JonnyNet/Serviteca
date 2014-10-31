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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class Syncro implements ListenerDB {

	private String namespace = "http://suarpe.com/";
	private String url = "http://192.168.1.51:80/ServicioClientes.asmx";
	
	Context context;

	public Syncro(Context c) {
		context = c;
		Preferencia();
	}
	
	private void Preferencia() {
		SharedPreferences bdsgl = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
		boolean savesql = bdsgl.getBoolean("save", false);
		if (savesql) {
			url = bdsgl.getString("url", "");
			namespace = bdsgl.getString("namespace", "");
		}
	}

	@Override
	public void ItemOrden(final String placa, final long norden, final int codser,
			final int cantd, final int iva, final int subtal, final int total, final String codtec) {
		final ProgressDialog pro = new ProgressDialog(context,
				android.R.style.Theme_Holo_Dialog_MinWidth);

		new AsyncTask<Void, String, Void>() {

			@Override
			protected void onPreExecute() {
				pro.setTitle("Guardando...");
				pro.setMessage("Espere Porfavor");
				pro.setCancelable(false);
				pro.show();
			}

			@Override
			protected Void doInBackground(Void... params) {

				try {
					SoapObject request = new SoapObject(namespace, "ItemOrden");
					request.addProperty("placa", placa);
					request.addProperty("norden", norden);
					request.addProperty("codser", codser);
					request.addProperty("cantd", cantd);
					request.addProperty("iva", iva);
					request.addProperty("subtal", subtal);
					request.addProperty("total", total);
					request.addProperty("codtec", codtec);

					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					HttpTransportSE transportSE = new HttpTransportSE(url);
					transportSE.call("http://suarpe.com/ItemOrden", envelope);
					SoapPrimitive resultado = (SoapPrimitive) envelope
							.getResponse();
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

	@Override
	public void NuevoCliente(final String cc, final String nombre,
			final String dir, final String tel, final String mail,
			final String placa, final int marca, final int color,
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
					SoapPrimitive resultado = (SoapPrimitive) envelope
							.getResponse();
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

	@Override
	public void UpdateCliente(final String cc, final String nombre,
			final String dir, final String tel, String dane, final String mail,
			final String placa) {

		final ProgressDialog pro = new ProgressDialog(context,
				android.R.style.Theme_Holo_Dialog_MinWidth);

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPreExecute() {
				pro.setTitle("Guardando...");
				pro.setMessage("Espere Porfavor");
				pro.show();
			}

			@Override
			protected Void doInBackground(Void... params) {
				try {
					SoapObject request = new SoapObject(namespace, "UpdateCliente");
					request.addProperty("cc", cc);
					request.addProperty("nombre", nombre);
					request.addProperty("dir", dir);
					request.addProperty("tel", tel);
					request.addProperty("mail", mail);
					request.addProperty("placa", placa);
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					HttpTransportSE transportSE = new HttpTransportSE(url);
					transportSE.call("http://suarpe.com/UpdateCliente", envelope);
					SoapPrimitive resultado = (SoapPrimitive) envelope
							.getResponse();
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
