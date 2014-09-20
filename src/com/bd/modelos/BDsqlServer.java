package com.bd.modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.util.Log;

public class BDsqlServer {

	/*private static final String url = "jdbc:jtds:sqlserver://Serviteka.mssql.somee.com:1433/Serviteka";
	private static final String user = "1047389512_SQLLogin_1";
	
		"Data Source=tcp:s03.winhost.com;Initial Catalog=DB_68219_serviteka;User ID=DB_68219_serviteka_user;Password=******;Integrated Security=False;"
	private static final String password = "qikxi5vux3";*/
	private final String url;
	private final String usuario;
	private final String password;

	public BDsqlServer(String direccion,String user, String pass) {
		url = direccion;
		usuario = user;
		password = pass;
	}

	public Connection Conectar() {
		Connection com = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
			com = DriverManager.getConnection(url, usuario, password);
			return com;
		} catch (SQLException e) {
			Log.e("SQLException", e + "");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Log.e("ClassNotFoundException", e + "");
			e.printStackTrace();
		} catch (InstantiationException e) {
			Log.e("InstantiationException", e + "");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e("IllegalAccessException", e + "");
			e.printStackTrace();
		}
		return com;
	}

	public boolean Insertar(String stsql, Connection com) {
		Statement st;
		if (com != null) {
			try {
				st = com.createStatement();
				return  st.execute(stsql);
			} catch (SQLException e) {
				Log.i("Coneccion", e + "");
			}
			return false;
		}
		return false;
	}

	public ResultSet Consultar(String stsql, Connection com) {
		Statement st;
		ResultSet rs = null;
		if (com != null) {
			try {
				st = com.createStatement();
				rs = st.executeQuery(stsql);
				com.close();
			} catch (SQLException e) {
				Log.i("Coneccion", e + "");
				return rs;
			}
		}
		return rs;
	}
}
