package com.servitek.vistas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.adapter.AutoCompleteUser;

public class RegistroUser extends Activity implements OnClickListener {

	private EditText nombre, cedula, direccion, celular, email, pass1, pass2;
	private AutoCompleteTextView user;
	private Button menu, eliminar, crear, editar;
	private ImageButton foto;
	private Spinner opt;
	private String[] tipos = { "Tipo de Cuenta", "Admin", "Estandar", "Tecnico" };
	private AutoCompleteUser adap;
	private Admin_BD db;
	private Intent camara;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crear);
		db = new Admin_BD(this);
		user = (AutoCompleteTextView) findViewById(R.id.nuser);
		nombre = (EditText) findViewById(R.id.nombre);
		cedula = (EditText) findViewById(R.id.cedula);
		direccion = (EditText) findViewById(R.id.dir);
		celular = (EditText) findViewById(R.id.tel);
		email = (EditText) findViewById(R.id.mail);
		pass1 = (EditText) findViewById(R.id.pass);
		pass2 = (EditText) findViewById(R.id.pass2);

		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(this);
		eliminar = (Button) findViewById(R.id.eliminar);
		eliminar.setOnClickListener(this);
		crear = (Button) findViewById(R.id.crear);
		crear.setOnClickListener(this);
		editar = (Button) findViewById(R.id.editar);
		editar.setOnClickListener(this);

		foto = (ImageButton) findViewById(R.id.foto);
		foto.setOnClickListener(this);
		opt = (Spinner) findViewById(R.id.tipo);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, tipos);
		opt.setAdapter(adapter);
		
		user.setThreshold(1);
		Cursor cursor = db.LoginAutoComplete("");
		adap = new AutoCompleteUser(getApplicationContext(), cursor, db);
		user.setAdapter(adap);
		user.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor c = db.Cursor2("Login", "user", user.getText()
						.toString());
				if (c.moveToFirst())
					LlenarCampos(c);
			}
		});

	}

	protected void LlenarCampos(Cursor c) {
		user.setFocusableInTouchMode(false);
		nombre.setText(c.getString(c.getColumnIndexOrThrow("nombre")));
		cedula.setText(c.getString(c.getColumnIndexOrThrow("cedula")));
		direccion.setText(c.getString(c.getColumnIndexOrThrow("direccion")));
		celular.setText(c.getString(c.getColumnIndexOrThrow("celular")));
		email.setText(c.getString(c.getColumnIndexOrThrow("email")));
		opt.setSelection(c.getInt(c.getColumnIndexOrThrow("tipo")));
		byte[] image = c.getBlob(c.getColumnIndexOrThrow("foto"));
		if (image != null)
			foto.setImageBitmap(Util.GetImage(image));
	}

	private void Registrar() {
		if (pass1.getText().toString().equals(pass2.getText().toString())) {
			if (!user.getText().toString().equals("")
					&& !nombre.getText().toString().equals("")
					&& !cedula.getText().toString().equals("")
					&& !direccion.getText().toString().equals("")
					&& !celular.getText().toString().equals("")
					&& !email.getText().toString().equals("")
					&& opt.getSelectedItemPosition() != 0) {

				db.User(user.getText().toString(), pass1.getText().toString(),
						nombre.getText().toString(), cedula.getText()
								.toString(), direccion.getText().toString(),
						celular.getText().toString(), email.getText()
								.toString(), opt.getSelectedItem().toString(),
						Util.GetBytes(((BitmapDrawable) foto.getDrawable())
								.getBitmap()));

			} else
				Util.MensajeCorto(this, "Llene Todos Los Campos");
		} else
			Util.MensajeCorto(this, "Contraseñas No Coinciden");
	}

	@Override
	public void onClick(View v) {
		if (v == menu) {
			Intent intent = new Intent(RegistroUser.this, Accion.class);
			startActivity(intent);
			finish();
		}

		if (v == crear | v == editar){
			Registrar();
			Reset();
		}

		if (v == eliminar) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"¿Desea eliminar este usuario?")
					.setTitle("Advertencia")
					.setCancelable(false)
					.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							})
					.setPositiveButton("Continuar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									if (!db.EliminarUser(user.getText().toString()))
										Util.MensajeCorto(RegistroUser.this, "No se puede eliminar este usuario");
									else
										Reset();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}
		if (v == foto)
			TomarFoto();

	}

	private void TomarFoto() {
		db.Cerrar();
		camara = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(camara, 0);
	}

	@Override
	protected void onActivityResult(int RequesCode, int ResultCode, Intent data) {
		super.onActivityResult(RequesCode, ResultCode, data);
		db.Escribir();
		if (ResultCode == Activity.RESULT_OK) {
			Bundle ext = data.getExtras();
			Bitmap bmt = (Bitmap) ext.get("data");
			foto.setImageBitmap(bmt);
		}
	}
	
	private void Reset() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

}
