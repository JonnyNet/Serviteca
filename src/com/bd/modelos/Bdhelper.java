package com.bd.modelos;

import com.clases.controladores.Admin_BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Bdhelper extends SQLiteOpenHelper {

	private static final String BD_NAME = "servitek.sqlite";
	private static final int BD_VERSION = 1;
	Context c;

	public Bdhelper(Context context) {
		super(context, BD_NAME, null, BD_VERSION);
		c = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(Admin_BD.sql0);
			db.execSQL(Admin_BD.sql1);
			db.execSQL(Admin_BD.sql2);
			db.execSQL(Admin_BD.sql3);
			db.execSQL(Admin_BD.sql5);
			db.execSQL(Admin_BD.sql7);
			db.execSQL(Admin_BD.sql8);
			db.execSQL(Admin_BD.sql9);
			db.execSQL(Admin_BD.sql10);
			db.execSQL(Admin_BD.sql11);


			CargarTipos(db);
			CargarMarcas(db);
			CargarTecicos(db);
			CargarServicios(db);
			CargarLoginPorDefecto(db);
		} catch (Exception e) {
			Log.d("bd", e + "");
			e.printStackTrace();
		}

	}

	private void CargarLoginPorDefecto(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Login (user,pass,nombre,cedula,Direccion,Celular,Email,Tipo,Foto) VALUES ('Admin','admin','Admin','Admin','Admin','Admin','Admin',1,null) ");
	}

	private void CargarServicios(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Servicios (codser,nomser,codcue,valser,ivaser,tasacomis,codins,concesion) VALUES ('0','Servicios','A','0','0','0','B','c') ");
	}

	private void CargarTecicos(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Tecnicos (codtec,nomtec,dirtec,teltec,email) VALUES ('0','Tecnico','tecnico','tecnico','Tecnico')");
	}

	private void CargarMarcas(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Mov_Marcas (_id, nombre) VALUES (100, 'Marca')");
	}

	private void CargarTipos(SQLiteDatabase db) {
		db.execSQL("INSERT INTO Mov_Clases (_id ,Nomclase) VALUES (200, 'Tipo')");
		db.execSQL("INSERT INTO Mov_Clases (Nomclase) VALUES ('Camion')");
		db.execSQL("INSERT INTO Mov_Clases (Nomclase) VALUES ('Automovil')");
		db.execSQL("INSERT INTO Mov_Clases (Nomclase) VALUES ('Camioneta')");
		db.execSQL("INSERT INTO Mov_Clases (Nomclase) VALUES ('Buseta')");
		db.execSQL("INSERT INTO Mov_Clases (Nomclase) VALUES ('Moto')");
		db.execSQL("INSERT INTO Mov_Clases (Nomclase) VALUES ('Taxi')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
