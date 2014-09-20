package com.clases.controladores;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.servitek.vistas.Vehiculo;


public class Util {
	
	//////////////////////////////////////Fecha dia 
	
	public static String facha() {
		Calendar hoy = Calendar.getInstance();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
		String c = formato.format(hoy.getTime()); 
		return c;
	}
	
	
	/////////////////////////////////////Color del carro
	
	public static void GetColor(final Bitmap bitmap) {
		Thread color = new Thread(new Runnable() {
			@Override
			public void run() {
				if (bitmap == null)
					throw new NullPointerException();

				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				int size = width * height;
				int pixels[] = new int[size];

				Bitmap bitmap2 = bitmap.copy(Bitmap.Config.ARGB_4444, false);

				bitmap2.getPixels(pixels, 0, width, 0, 0, width, height);

				final List<HashMap<Integer, Integer>> colorMap = new ArrayList<HashMap<Integer, Integer>>();
				colorMap.add(new HashMap<Integer, Integer>());
				colorMap.add(new HashMap<Integer, Integer>());
				colorMap.add(new HashMap<Integer, Integer>());

				int color = 0;
				int r = 0;
				int g = 0;
				int b = 0;
				Integer rC, gC, bC;
				for (int i = 0; i < pixels.length; i++) {
					color = pixels[i];

					r = Color.red(color);
					g = Color.green(color);
					b = Color.blue(color);

					rC = colorMap.get(0).get(r);
					if (rC == null)
						rC = 0;
					colorMap.get(0).put(r, ++rC);

					gC = colorMap.get(1).get(g);
					if (gC == null)
						gC = 0;
					colorMap.get(1).put(g, ++gC);

					bC = colorMap.get(2).get(b);
					if (bC == null)
						bC = 0;
					colorMap.get(2).put(b, ++bC);
				}

				int[] rgb = new int[3];
				for (int i = 0; i < 3; i++) {
					int max = 0;
					int val = 0;
					for (Map.Entry<Integer, Integer> entry : colorMap.get(i).entrySet()) {
						if (entry.getValue() > max) {
							max = entry.getValue();
							val = entry.getKey();
						}
					}
					rgb[i] = val;
				}

				Integer c = Color.rgb(rgb[0], rgb[1], rgb[2]);
				Message msg = new Message();
				msg.obj = c;
				Vehiculo.puente.sendMessage(msg);
				
			}
		});
		color.start();
	}
	
	/////////////////////////////////// Mensajes 
	
	public static void MensajeCorto(Context context, String mensaje) {
		Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
		TextView v = (TextView) toast.getView().findViewById(
				android.R.id.message);
		if (v != null)
			v.setGravity(Gravity.CENTER);
		v.setTextColor(Color.rgb(225, 216, 79));
		toast.show();
	}
	
	
	public static void MensajeLargo(Context context, String mensaje) {
		Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_LONG);
		TextView v = (TextView) toast.getView().findViewById(
				android.R.id.message);
		if (v != null)
			v.setGravity(Gravity.CENTER);
		v.setTextColor(Color.rgb(225, 216, 79));
		toast.show();
	}
	
	////////////////////////////////////////internet
	
	public static boolean Internet(Context context) {
		boolean bConectado = false;
		ConnectivityManager connec = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] redes = connec.getAllNetworkInfo();
		for (int i = 0; i < 2; i++) {
			if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
				bConectado = true;
			}
		}
		return bConectado;
	}
	
	//////////////////////////////////img
	
	public static byte[] GetBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, stream);
		return stream.toByteArray();
	}

	public static Bitmap GetImage(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}
	
}
