package com.clases.controladores;

import com.bd.modelos.Bdhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Admin_BD {

	private static final String Tabla_Cliente = "Clientes";
	private static final String Tabla_Movil = "Vehiculos";
	private static final String Tabla_Tecnicos = "Tecnicos";
	private static final String Tabla_Servicios = "Servicios";
	private static final String Tabla_Productos = "Productos";
	private static final String Tabla_Marcas = "Mov_Marcas";
	private static final String Tabla_Colores = "Mov_Colores";
	private static final String Tabla_Clases = "Mov_Clases";
	private static final String Tabla_Imagen = "Mov_Imagenes";
	private static final String Tabla_Maeorden = "Mov_Maeorden";
	private static final String Tabla_Detaorden = "Mov_Detaorden";
	private static final String Tabla_Login = "Login";

	public static final String sql0 = "CREATE TABLE " + Tabla_Cliente + " ( "
			+ "Codter varchar primary key not null , "
			+ "Nomter	Varchar not null, " + "Dirter	Varchar null, "
			+ "Telter	Varchar null, " + "Coddane varchar null, "
			+ "Email	varchar null ," + "syncro	Integer  not null, "
			+ "fecha_ingreso TIMESTAMP NOT NULL DEFAULT current_timestamp )";

	public static final String sql1 = "CREATE TABLE " + Tabla_Movil + " ( "
			+ "placa  Varchar primary key not null , "
			+ "Codter	Varchar not null, " + "Codmarca Char not null, "
			+ "Codcolor Integer not null, " + "Modelo	Integer  null, "
			+ "Codclase Char not null," + "syncro	Integer  not null, "
			+ "fecha_ingreso TIMESTAMP NOT NULL DEFAULT current_timestamp )";

	public static final String sql2 = "CREATE TABLE " + Tabla_Tecnicos + " ( "
			+ "_id  integer primary key autoincrement, "
			+ "codtec Varchar  not null , " + "nomtec Varchar not null, "
			+ "dirtec Varchar not null, " + "teltec	Varchar not null, "
			+ "email Varchar not null, " + "foto BLOB null, "
			+ "fecha_ingreso TIMESTAMP NOT NULL DEFAULT current_timestamp )";

	public static final String sql3 = "CREATE TABLE " + Tabla_Servicios + " ( "
			+ "_id  integer primary key autoincrement, "
			+ "codser char  not null , " + "nomser varchar  not null, "
			+ "codcue char null, " + "valser Integer not null, "
			+ "ivaser	Integer not null, " + "tasacomis	Integer not null, "
			+ "codins	char  null, " + "concesion char null )";

	public static final String sql4 = "CREATE TABLE " + Tabla_Productos + " ( "
			+ "codins	char  primary key not null , "
			+ "Nomins  Varchar  not null, " + "codinterfase char not null,"
			+ "precio_publico Integer not null, " + "ivains	Integer not null )";

	public static final String sql5 = "CREATE TABLE " + Tabla_Marcas + " ( "
			+ "_id  integer primary key autoincrement, "
			+ "codmarca  Varchar not null, " + "nombre	Varchar not null)";

	public static final String sql6 = "CREATE TABLE " + Tabla_Colores + " ( "
			+ "codcolor  char primary key not null , "
			+ "Nomcolor	Varchar not null)";

	public static final String sql7 = "CREATE TABLE " + Tabla_Clases + " ( "
			+ "_id  integer primary key autoincrement, "
			+ "codclase  char  not null , " + "Nomclase	Varchar not null)";

	public static final String sql8 = "CREATE TABLE " + Tabla_Imagen + " ( "
			+ "placa  Varchar primary key not null , "
			+ "bitmap1 BLOB not null," + "bitmap2 BLOB not null,"
			+ "bitmap3 BLOB not null)";

	public static final String sql9 = "CREATE TABLE " + Tabla_Maeorden + " ( "
			+ "norde Integer primary key autoincrement, "
			+ "placa varchar not null , " + "subtal Integer  not null, "
			+ "iva	Integer null," + "valfact Integer  not null, "
			+ "estado char not null, " + "syncro  Integer not null,"
			+ "numfact char not null, "
			+ "fecha  TIMESTAMP NOT NULL DEFAULT current_timestamp )";

	public static final String sql10 = "CREATE TABLE " + Tabla_Detaorden
			+ " ( " + "_id  integer primary key autoincrement, "
			+ "placa varchar  not null , " + "norde	Integer not null, "
			+ "codser char  not null , " + "cantd Integer null , "
			+ "iva	Integer null, " + "subtal Integer not null, "
			+ "total Integer not null, " + "codtec Varchar not null,"
			+ "estado	char null," + "syncro Integer null,"
			+ "fecha  TIMESTAMP NOT NULL DEFAULT current_timestamp)";

	public static final String sql11 = "CREATE TABLE " + Tabla_Login + " ( "
			+ "user  Varchar primary key, " + "pass varchar  not null , "
			+ "nombre	Varchar not null, " + "cedula Varchar  not null , "
			+ "direccion Varchar null , " + "celular	Varchar null, "
			+ "email Varchar not null, " + "tipo Integer not null, "
			+ "foto BLOB null,"
			+ "fecha  TIMESTAMP NOT NULL DEFAULT current_timestamp)";

	private Bdhelper helper;
	private SQLiteDatabase bd;
	public static final String tag = "mytag";
	private ListenerDB syncro;

	// constructor de la classe
	public Admin_BD(Context c) {
		helper = new Bdhelper(c);
		bd = helper.getWritableDatabase();
		syncro = new Syncro(c);
	}

	// contenedores de valores para el registro del vehiculo y cliente en la
	// classe vehiculo

	private ContentValues ContenedorCliente(String cc, String nombre,
			String dir, String tel, String coddane, String mail) {
		ContentValues valores = new ContentValues();
		valores.put("Codter", cc);
		valores.put("Nomter", nombre);
		valores.put("Dirter", dir);
		valores.put("Telter", tel);
		valores.put("coddane", coddane);
		valores.put("email", mail);
		valores.put("syncro", 0);
		return valores;

	}

	private ContentValues ContenedorMovil(String placa, String cc, int marca,
			int color, int modelo, int tipo) {
		ContentValues valores = new ContentValues();
		valores.put("placa", placa);
		valores.put("Codter", cc);
		valores.put("Codmarca", CodigoNombre("Mov_Marcas", marca));
		valores.put("Codcolor", color);
		valores.put("Modelo", modelo);
		valores.put("Codclase", CodigoNombre("Mov_Clases", tipo));
		valores.put("syncro", 0);
		return valores;
	}

	// registro de vehiculo y cleinte

	private long InsertarCliente(String cc, String nombre, String dir,
			String tel, String coddane, String mail) {
		ContentValues datos = ContenedorCliente(cc, nombre, dir, tel, coddane,
				mail);
		long v = bd.insert(Tabla_Cliente, null, datos);
		return v;
	}

	private long InsertarMovil(String placa, String cc, int marca, int color,
			int modelo, int tipo) {
		ContentValues datos = ContenedorMovil(placa, cc, marca, color, modelo,
				tipo);
		long v = bd.insert(Tabla_Movil, null, datos);
		return v;
	}

	public void RegistrarVehiculo(String cc, String nombre, String dir,
			String tel, String coddane, String mail, String placa, int marca,
			int color, int modelo, int tipo, byte[] image, byte[] image2,
			byte[] image3) {
		syncro.NuevoCliente(cc, nombre, dir, tel, mail, placa, marca, color, modelo, tipo);
		InsertarMovil(placa, cc, marca, color, modelo, tipo);
		FotoCarro(placa, image, image2, image3);
		InsertarCliente(cc, nombre, dir, tel, coddane, mail);

	}

	// editar datos del cliente

	public long EditarCliente(String cc, String nombre, String dir, String tel,
			String dane, String email, String placa) {
		Cursor c = BuscarCliente(cc);
		if (c.moveToFirst()) {
			return bd.update(Tabla_Cliente,
					ContenedorCliente(cc, nombre, dir, tel, dane, email),
					"Codter =? ", new String[] { cc });
		} else {
			Log.i(tag, placa);
			ContentValues valor = new ContentValues();
			valor.put("Codter", cc);
			bd.update(Tabla_Movil, valor, "placa =?", new String[] { placa });
			return InsertarCliente(cc, nombre, dir, tel, dane, email);
		}
	}

	// query para guardar la foto del vehiculo y recuperarla

	private long FotoCarro(String placa, byte[] image, byte[] image2,
			byte[] image3) {
		return bd.insert(Tabla_Imagen, null,
				createContentValues(placa, image, image2, image3));
	}

	private ContentValues createContentValues(String placa, byte[] image,
			byte[] image2, byte[] image3) {
		ContentValues cv = new ContentValues();
		cv.put("placa", placa);
		cv.put("bitmap1", image);
		cv.put("bitmap2", image2);
		cv.put("bitmap3", image3);
		return cv;
	}

	public Cursor BuscarImagen(String placa) {
		Cursor c = bd.query(Tabla_Imagen, new String[] { "placa", "bitmap1",
				"bitmap2", "bitmap3" }, "placa =?", new String[] { placa },
				null, null, null);
		c.moveToFirst();
		return c; // c.getBlob(2);
	}

	// buscar nombre y id de los servicios, tecnicos, marca, tipo

	public String CodigoNombre(String tabla, int id) {
		Cursor c = bd.rawQuery("SELECT * FROM " + tabla + " WHERE _id =?",
				new String[] { id + "" });
		c.moveToFirst();
		return c.getString(1);
	}

	public String NombreTecnico(int id) {
		Cursor c = bd.rawQuery("SELECT * FROM Tecnicos WHERE _id =?",
				new String[] { id + "" });
		c.moveToFirst();
		return c.getString(c.getColumnIndexOrThrow("codtec"));
	}

	public long CodigoId(String tabla, String columna, String codigo) {
		Cursor c = bd.rawQuery("SELECT * FROM " + tabla + " WHERE " + columna
				+ " = " + codigo, null);
		if (c.moveToFirst() && c != null) {
			return c.getLong(0);
		}
		return 0;
	}

	// busqueda de cliente , vehiculo y actuar si no existen

	public Cursor BuscarPlaca(String placa) {
		Cursor c = bd.rawQuery("SELECT * FROM Vehiculos WHERE placa =?",
				new String[] { placa });
		c.moveToFirst();
		return c;
	}

	public Cursor BuscarCliente(String cc) {
		String[] columnas = { "rowid", "Codter", "Nomter", "Dirter", "Telter",
				"coddane", "email" };
		Cursor c = bd.query(Tabla_Cliente, columnas, "Codter =?",
				new String[] { cc }, null, null, null);
		c.moveToFirst();
		return c;
	}

	// query versatil para recuperar cursor de datos requeridos

	public Cursor Cursor(String id, String columna, String tabla) {
		Cursor c = bd.rawQuery("SELECT " + id + " AS _id, " + columna
				+ " FROM " + tabla, null);
		c.moveToFirst();
		return c;
	}

	public Cursor Cursor2(String tabla, String columna, String dato) {
		Cursor c = bd.rawQuery("SELECT * FROM " + tabla + " WHERE " + columna
				+ " =?", new String[] { dato });
		c.moveToFirst();
		return c;
	}

	// cursor para autocompletetextview

	public Cursor AutoComplete(String textSearch) {
		Cursor c = bd.rawQuery(
				"SELECT rowid  AS _id, placa AS item  FROM  Vehiculos WHERE placa LIKE '"
						+ textSearch + "%' ", null);
		c.moveToFirst();
		return c;
	}

	public Cursor LoginAutoComplete(String textSearch) {
		Cursor c = bd.rawQuery(
				"SELECT rowid  AS _id, user AS item  FROM  Login WHERE user LIKE '"
						+ textSearch + "%' ", null);
		c.moveToFirst();
		return c;
	}

	public Cursor TecnicoAutoComplete(String textSearch) {
		Cursor c = bd
				.rawQuery(
						"SELECT _id  AS _id, nomtec AS item  FROM  Tecnicos  WHERE nomtec !=?  AND  nomtec  LIKE '"
								+ textSearch + "%' ",
						new String[] { "Tecnico" });
		c.moveToFirst();
		return c;
	}

	public Cursor ServicioAutoComplete(String textSearch) {
		Cursor c = bd
				.rawQuery(
						"SELECT rowid  AS _id, nomser AS item  FROM  Servicios  WHERE nomser !=?  AND  nomser  LIKE '"
								+ textSearch + "%' ",
						new String[] { "Servicios" });
		c.moveToFirst();
		return c;
	}

	// retorna el precio del servicio requerido por la id

	public Cursor BuscarServicio(String columna, String id) {
		Cursor c = bd.rawQuery(" SELECT * FROM Servicios WHERE " + columna
				+ " =? ", new String[] { id });
		c.moveToFirst();
		return c;
	}

	// ////login

	public Cursor Login(String user, String password) {
		Cursor c = bd.rawQuery(" SELECT * FROM Login WHERE user =? ",
				new String[] { user });
		return c;
	}

	// en esta parte van los metos para los codigos consecutivos de las ordenes
	// de compra

	public void InicioConsecutivo(int init) {
		ContentValues c = new ContentValues();
		c.put("numorde", init);
		bd.insert(Tabla_Maeorden, null, c);
	}

	public long BuscarOrden(String placa, String fecha) {
		Cursor c = bd
				.rawQuery(
						" SELECT norde AS _id  FROM Mov_Maeorden WHERE placa =?  AND  estado =? AND fecha  LIKE '"
								+ fecha + "%' ", new String[] { placa, "A", });
		if (c.moveToFirst()) {
			return c.getLong(0);
		} else {
			return -1;
		}
	}

	public Cursor GetDetalles(long norde) {
		return bd
				.rawQuery(
						"SELECT codser,cantd,iva,subtal,total FROM Mov_Detaorden WHERE norde =?",
						new String[] { norde + "" });
	}

	public int OrdeneCompra(String placa, int cod, String[] datos) {
		if (cod == 0) {
			cod = NextOrden();
			InsertItemDetalle(placa, cod, datos);
			InsertMaestroDetalles(placa, Integer.parseInt(datos[4]),
					Integer.parseInt(datos[5]), Integer.parseInt(datos[6]));
			return cod;
		} else {
			InsertItemDetalle(placa, cod, datos);
			ActualizarMaestro(cod, Integer.parseInt(datos[4]),
					Integer.parseInt(datos[5]), Integer.parseInt(datos[6]));
			return 0;
		}
	}

	public long ActualizarMaestro(int cod, int subtal, int iva, int total) {
		Cursor c = bd
				.rawQuery(
						"SELECT  subtal, valfact, iva  FROM  Mov_Maeorden   WHERE norde =?",
						new String[] { cod + "" });
		c.moveToFirst();
		int ivap = c.getInt(c.getColumnIndexOrThrow("iva")) + iva;
		int subp = c.getInt(c.getColumnIndexOrThrow("subtal")) + subtal;
		int talp = c.getInt(c.getColumnIndexOrThrow("valfact")) + total;

		Log.i("valores de las columnas", ivap + "  " + subp + "  " + talp);
		ContentValues valores = new ContentValues();
		valores.put("subtal", subp);
		valores.put("iva", ivap);
		valores.put("valfact", talp);

		bd.update(Tabla_Maeorden, valores, "norde = " + cod, null);
		return 0;
	}

	private long InsertItemDetalle(String placa, int id, String[] datos) {
		ContentValues d = ContenedorDetalles(placa, id, datos);
		return bd.insert(Tabla_Detaorden, null, d);
	}

	private long InsertMaestroDetalles(String placa, int subtal, int iva,
			int valfact) {
		ContentValues r = ContenedorMaestro(placa, subtal, iva, valfact);
		return bd.insert(Tabla_Maeorden, null, r);
	}

	private ContentValues ContenedorMaestro(String placa, int subtal, int iva,
			int valfact) {
		ContentValues d = new ContentValues();
		d.put("placa", placa);
		d.put("subtal", subtal);
		d.put("iva", iva);
		d.put("valfact", valfact);
		d.put("estado", "A");
		d.put("syncro", 0);
		d.put("numfact", 0);
		return d;
	}

	private ContentValues ContenedorDetalles(String placa, int id,
			String[] datos) {
		ContentValues d = new ContentValues();
		d.put("placa", placa);
		d.put("norde", id);
		d.put("codser", datos[0]);
		d.put("cantd", datos[2]);
		d.put("iva", datos[5]);
		d.put("subtal", datos[4]);
		d.put("total", datos[6]);
		d.put("codtec", datos[7]);
		d.put("estado", "A");
		d.put("syncro", 0);
		return d;
	}

	public int NextOrden() {
		Cursor c = bd.rawQuery("SELECT MAX(norde) FROM Mov_Maeorden", null);
		c.moveToFirst();
		c.getInt(0);
		return c.getInt(0) + 1;
	}

	// ////////////historial
	public Cursor BuscarOrdenDia(String fecha) {
		Cursor c = bd.rawQuery(
				"SELECT norde AS _id , * FROM Mov_Maeorden  WHERE fecha  LIKE '"
						+ fecha + "%'  ORDER BY estado", null);
		return c;
	}

	public Cursor BuscarPorFecha(String fechai, String fechaf) {
		Cursor c = bd.rawQuery(
				"SELECT norde AS _id , * FROM Mov_Maeorden  WHERE fecha  BETWEEN  '"
						+ fechai + "00:00:00'  AND  '" + fechaf + "23:59:59'",
				null);
		return c;
	}

	public Cursor BuscarPorTecnico(int id, String fechai, String fechaf) {
		String tecnico = NombreTecnico(id);
		Cursor c = bd.rawQuery(
				"SELECT norde AS _id , * FROM Mov_Detaorden  WHERE codtec = "
						+ tecnico + " AND  fecha  BETWEEN  '" + fechai
						+ "00:00:00'  AND  '" + fechaf + "23:59:59'", null);
		return c;

	}

	// /////// Registrar actualizar y eliminar usuario login

	public void User(String user, String pass, String nombre, String cedula,
			String direccion, String celular, String email, int tipo,
			byte[] image) {

		Cursor c = Cursor2("Login", "user", user);
		if (c.moveToFirst()) {
			ActualizarUser(user, pass, nombre, cedula, direccion, celular,
					email, tipo, image);
		} else
			RegistrarUser(user, pass, nombre, cedula, direccion, celular,
					email, tipo, image);
	}

	private void RegistrarUser(String user, String pass, String nombre,
			String cedula, String direccion, String celular, String email,
			int tipo, byte[] image) {
		ContentValues v = ContenedorUser(user, pass, nombre, cedula, direccion,
				celular, email, tipo, image);

		bd.insert("Login", null, v);
	}

	private ContentValues ContenedorUser(String user, String pass,
			String nombre, String cedula, String direccion, String celular,
			String email, int tipo, byte[] image) {
		ContentValues valores = new ContentValues();
		valores.put("user", user);
		valores.put("pass", pass);
		valores.put("nombre", nombre);
		valores.put("cedula", cedula);
		valores.put("direccion", direccion);
		valores.put("celular", celular);
		valores.put("email", email);
		valores.put("tipo", tipo);
		valores.put("foto", image);
		return valores;
	}

	private void ActualizarUser(String user, String pass, String nombre,
			String cedula, String direccion, String celular, String email,
			int tipo, byte[] image) {
		ContentValues v = ContenedorUser(user, pass, nombre, cedula, direccion,
				celular, email, tipo, image);

		bd.update("Login", v, "user=?", new String[] { user });
	}

	public boolean EliminarUser(String user) {
		Cursor c = Cursor2("Login", "user", user);
		if (c.getCount() > 1) {
			bd.delete("Login", "WHERE user =? ", new String[] { user });
			return true;
		}
		return false;

	}

	// /////////////////////////////////////////////////metodos sincronizacion
	// SELECT * FROM test WHERE date BETWEEN "11/1/2011" AND "11/8/2011";
	// norde AS _id ,norde, placa, syncro, fecha AS item
	// con servidor sql server

	public Cursor SyncroTabla(String tabla) {
		Cursor c = bd.rawQuery("SELECT * FROM " + tabla + " WHERE syncro = 0",
				null);
		return c;
	}

	// ///////////////////////////insertar elminar servicio editarv

	public void Servicio(String nombre, String codigo, int valor, int iva,
			int comision) {
		Cursor c = Cursor2(Tabla_Servicios, "nomser", nombre);
		if (c.moveToFirst()) {
			UpdateServicio(nombre, codigo, valor, iva, comision);
		} else {
			InsertServicio(nombre, codigo, valor, iva, comision);
		}

	}

	private void InsertServicio(String nombre, String codigo, int valor,
			int iva, int comision) {
		ContentValues valores = ContenedorServicio(nombre, codigo, valor, iva,
				comision);
		bd.insert(Tabla_Servicios, null, valores);

	}

	private void UpdateServicio(String nombre, String codigo, int valor,
			int iva, int comision) {
		ContentValues valores = ContenedorServicio(nombre, codigo, valor, iva,
				comision);

		bd.update(Tabla_Servicios, valores, "codser=?", new String[] { codigo });

	}

	private ContentValues ContenedorServicio(String nombre, String codigo,
			int valor, int iva, int comision) {
		ContentValues v = new ContentValues();
		v.put("codser", codigo);
		v.put("nomser", nombre);
		v.put("valser", valor);
		v.put("ivaser", iva);
		v.put("tasacomis", comision);
		return v;
	}

	public void EliminarServicio(String codigo) {
		bd.delete(Tabla_Servicios, "codser=?", new String[] { codigo });
	}

	// //////////// Inseryar y eliminar tecnico

	public void Tecnicos(String nombre, String cedula, String direccion,
			String telefono, String email, byte[] foto) {

		Cursor c = Cursor2(Tabla_Tecnicos, "codtec", cedula);

		if (c.getCount() > 1)
			UpdateTecnico(nombre, cedula, direccion, telefono, email, foto);
		else
			InsertTecnico(nombre, cedula, direccion, telefono, email, foto);

	}

	private void UpdateTecnico(String nombre, String cedula, String direccion,
			String telefono, String email, byte[] foto) {
		ContentValues values = ContenedorTecnico(nombre, cedula, direccion,
				telefono, email, foto);
		bd.update(Tabla_Tecnicos, values, "codte c=?", new String[] { cedula });

	}

	private void InsertTecnico(String nombre, String cedula, String direccion,
			String telefono, String email, byte[] foto) {
		ContentValues r = ContenedorTecnico(nombre, cedula, direccion,
				telefono, email, foto);
		bd.insert(Tabla_Tecnicos, null, r);
	}

	private ContentValues ContenedorTecnico(String nombre, String cedula,
			String direccion, String telefono, String email, byte[] foto) {
		ContentValues valores = new ContentValues();
		valores.put("codtec", cedula);
		valores.put("nomtec", nombre);
		valores.put("dirtec", direccion);
		valores.put("teltec", telefono);
		valores.put("email", email);
		valores.put("foto", foto);
		return valores;
	}

	public void EliminarTecnico(String cedula) {
		bd.delete(Tabla_Tecnicos, " codtec =? ", new String[] { cedula });
	}

	// metodos para apertura y cierre de la base de datos

	public void Escribir() {
		bd = helper.getWritableDatabase();
	}

	public void Cerrar() {
		bd.close();
	}

	public void Leer() {
		bd = helper.getReadableDatabase();
	}

}
