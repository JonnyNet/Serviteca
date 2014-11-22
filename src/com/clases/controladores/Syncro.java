package com.clases.controladores;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.bd.modelos.Cliente;
import com.bd.modelos.Servicio;
import com.bd.modelos.Tecnico;
import com.bd.modelos.Vehiculo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class Syncro implements ListenerDB {

	private String namespace = "http://suarpe.com/";
	private String url = "http://server:80/ServicioClientes.asmx";

	Context context;
	ListenerServer listener;

	public Syncro(Context c, ListenerServer listener) {
		context = c;
		Preferencia();
		this.listener = listener;
	}

	private void Preferencia() {
		SharedPreferences bdsgl = context.getSharedPreferences("loginPrefs",
				Context.MODE_PRIVATE);
		boolean savesql = bdsgl.getBoolean("save", false);
		if (savesql) {
			url = bdsgl.getString("url", "");
			namespace = bdsgl.getString("namespace", "");
		}
	}

	@Override
	public void ItemOrden(final String placa, final long norden,
			final int codser, final int cantd, final int iva, final int subtal,
			final int total, final String codtec) {
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
			final String placa, final int marca, final String color,
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
			final String dir, final String tel, final String mail,
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
					SoapObject request = new SoapObject(namespace,
							"UpdateCliente");
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
					transportSE.call("http://suarpe.com/UpdateCliente",
							envelope);
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

	public void getAllClientes() {
		new AsyncTask<Void, Void, Cliente[]>() {

			@Override
			protected Cliente[] doInBackground(Void... params) {
				SoapObject request = new SoapObject(namespace, "getAllClientes");

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;

				envelope.setOutputSoapObject(request);

				HttpTransportSE transporte = new HttpTransportSE(url);
				try {
					transporte.call("http://suarpe.com/getAllClientes",
							envelope);

					SoapObject resSoap = (SoapObject) envelope.getResponse();

					Cliente[] clientes = new Cliente[resSoap.getPropertyCount()];

					for (int i = 0; i < clientes.length; i++) {
						SoapObject ic = (SoapObject) resSoap.getProperty(i);

						Cliente cli = new Cliente();
						cli.setId(Integer
								.parseInt(ic.getProperty(0).toString()));
						cli.setNombre(ic.getProperty(1).toString());
						cli.setDireccion(ic.getProperty(2).toString());
						cli.setTelefono(ic.getProperty(3).toString());
						cli.setEmail(ic.getProperty(4).toString());
						cli.setCodigo(ic.getProperty(5).toString());

						clientes[i] = cli;
					}
				} catch (Exception e) {

				}
				return null;
			}

			@Override
			protected void onPostExecute(Cliente[] result) {
				listener.sinkClientes(result);
			}

		}.execute();

	}
	
	public void getAllVehiculo() {
		new AsyncTask<Void, Void, Vehiculo[]>() {

			@Override
			protected Vehiculo[] doInBackground(Void... params) {
				SoapObject request = new SoapObject(namespace, "getAllVehiculo");

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;

				envelope.setOutputSoapObject(request);

				HttpTransportSE transporte = new HttpTransportSE(url);
				try {
					transporte.call("http://suarpe.com/getAllVehiculo",
							envelope);

					SoapObject resSoap = (SoapObject) envelope.getResponse();

					Vehiculo[] vehiculos = new Vehiculo[resSoap.getPropertyCount()];

					for (int i = 0; i < vehiculos.length; i++) {
						SoapObject ic = (SoapObject) resSoap.getProperty(i);

						Vehiculo cli = new Vehiculo();
						cli.setPlaca(ic.getProperty(0).toString());
						cli.setCodter(ic.getProperty(1).toString());
						cli.setCodmarca(Integer.parseInt(ic.getProperty(2).toString()));
						cli.setMarca(ic.getProperty(3).toString());
						cli.setColor(ic.getProperty(4).toString());
						
						vehiculos[i] = cli;
					}
				} catch (Exception e) {

				}
				return null;
			}

			@Override
			protected void onPostExecute(Vehiculo[] result) {
				listener.sinkVehiculos(result);
			}

		}.execute();

	}
	
	public void getAllServicios() {
		new AsyncTask<Void, Void, Servicio[]>() {

			@Override
			protected Servicio[] doInBackground(Void... params) {
				SoapObject request = new SoapObject(namespace, "getAllVehiculo");

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;

				envelope.setOutputSoapObject(request);

				HttpTransportSE transporte = new HttpTransportSE(url);
				try {
					transporte.call("http://suarpe.com/getAllVehiculo",
							envelope);

					SoapObject resSoap = (SoapObject) envelope.getResponse();

					Servicio[] ser = new Servicio[resSoap.getPropertyCount()];

					for (int i = 0; i < ser.length; i++) {
						SoapObject ic = (SoapObject) resSoap.getProperty(i);

						Servicio cli = new Servicio();
						cli.setCodigo(ic.getProperty(0).toString());
						cli.setNombre(ic.getProperty(1).toString());
						cli.setValor(Integer.parseInt(ic.getProperty(2).toString()));
						ser[i] = cli;
					}
				} catch (Exception e) {

				}
				return null;
			}

			@Override
			protected void onPostExecute(Servicio[] result) {
				listener.sinkServicios(result);
			}

		}.execute();

	}
	
	public void getAllTecnico() {
		new AsyncTask<Void, Void, Tecnico[]>() {

			@Override
			protected Tecnico[] doInBackground(Void... params) {
				SoapObject request = new SoapObject(namespace, "getAllVehiculo");

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;

				envelope.setOutputSoapObject(request);

				HttpTransportSE transporte = new HttpTransportSE(url);
				try {
					transporte.call("http://suarpe.com/getAllVehiculo",
							envelope);

					SoapObject resSoap = (SoapObject) envelope.getResponse();

					Tecnico[] tec = new Tecnico[resSoap.getPropertyCount()];

					for (int i = 0; i < tec.length; i++) {
						SoapObject ic = (SoapObject) resSoap.getProperty(i);

						Tecnico cli = new Tecnico();
						cli.setId(Integer.parseInt(ic.getProperty(0).toString()));
						cli.setNombre(ic.getProperty(1).toString());
						cli.setCodigo(ic.getProperty(2).toString());
						tec[i] = cli;
					}
				} catch (Exception e) {

				}
				return null;
			}

			@Override
			protected void onPostExecute(Tecnico[] result) {
				listener.sinkTecnicos(result);
			}

		}.execute();

	}
}
