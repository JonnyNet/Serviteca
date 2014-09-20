package com.clases.controladores;

import java.sql.Connection;

import com.bd.modelos.BDsqlServer;
import android.os.AsyncTask;


public class SqlProgres extends AsyncTask<String, Integer, Void> {

	private BDsqlServer sql;
	private Connection com;
	Listener escuchador;

	public SqlProgres(Connection c, BDsqlServer bd, Listener listener) {
		sql = bd;
		com = c;
		escuchador = listener;
	}

	@Override
	protected Void doInBackground(String... query) {
		boolean aux = sql.Insertar(query[0], com);
		escuchador.Exito(aux);
		return null;
	}
}
