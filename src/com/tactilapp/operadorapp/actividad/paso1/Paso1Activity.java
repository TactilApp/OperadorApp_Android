package com.tactilapp.operadorapp.actividad.paso1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.flurry.android.FlurryAgent;
import com.tactilapp.operadorapp.R;
import com.tactilapp.operadorapp.Utils;
import com.tactilapp.operadorapp.actividad.AbstractActivity;
import com.tactilapp.operadorapp.actividad.paso2.Paso2Activity;

public class Paso1Activity extends AbstractActivity {

	private static final String TAG = "Paso1Activity";

	public static final int CARGAR_CONTACTO = 1;
	public static final int IR_AL_PASO2 = 2;

	private EditText numeroDeTelefono;
	private CompraDeLaAgenda compraDeLaAgenda;

	@Override
	protected int obtenerVista() {
		return R.layout.paso1;
	}

	@Override
	public void onBackPressed() {
		mostrarAvisoDeQueSeVaACerrarLaAplicacion();
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		numeroDeTelefono = (EditText) findViewById(R.id.paso1telefono);
		compraDeLaAgenda = new CompraDeLaAgenda(this);
		compraDeLaAgenda.inicializar(false);
	}

	public void irAlPaso2(final View view) {
		if (Utils.haMetidoAlgoValidoEnElCampo(this, numeroDeTelefono, 8,
				R.string.errorNumeroCorto, R.string.errorNumeroVacio)
				&& Utils.hayConexionAInternet(this)) {
			final Intent intent = new Intent(this, Paso2Activity.class);
			intent.putExtra("numero", numeroDeTelefono.getText().toString()
					.trim());
			startActivityForResult(intent, IR_AL_PASO2);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}
	}

	public void irABuscarUnContacto(final View view) {
		if (compraDeLaAgenda.tieneAccesoALaAgenda()) {
			mostrarLaAgenda();
		} else if (compraDeLaAgenda.seHaConfiguradoIAConExito()) {
			compraDeLaAgenda.comprarElAccesoALaAgenda();
		}
	}

	@Override
	public void onActivityResult(final int reqCode, final int resultCode,
			final Intent data) {

		if (compraDeLaAgenda.handleActivityResult(reqCode, resultCode, data)) {
			if (compraDeLaAgenda.tieneAccesoALaAgenda()) {
				Log.d(TAG, "Llegamos después de realizar la compra.");
				FlurryAgent.logEvent("Compra de la agenda realizada", true);
				mostrarLaAgenda();
			}
		} else {

			super.onActivityResult(reqCode, resultCode, data);
			switch (reqCode) {
			case IR_AL_PASO2:
				Log.d(TAG,
						"Llegamos después de volver desde la página del captcha.");
				if (resultCode == Activity.RESULT_OK) {
					finish();
				}
				break;
			case CARGAR_CONTACTO:
				Log.d(TAG, "Llegamos después de seleccionar un contacto.");
				if (resultCode == Activity.RESULT_OK) {
					cargarElContactoSeleccionadoDesdeLaAgenda(data);
				}
				break;
			}
		}
	}

	private void cargarElContactoSeleccionadoDesdeLaAgenda(final Intent data) {
		try {
			final Cursor cursor = managedQuery(data.getData(), null, null,
					null, null);
			if (cursor.moveToFirst()) {
				String numeroSeleccionado = null;

				final String tieneTelefono = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if ("1".equalsIgnoreCase(tieneTelefono)) {
					final String idContacto = cursor.getString(cursor
							.getColumnIndex(BaseColumns._ID));

					final Cursor telefonos = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + idContacto, null, null);
					if (telefonos.getCount() > 0 && telefonos.moveToFirst()) {
						numeroSeleccionado = telefonos.getString(telefonos
								.getColumnIndex("data1"));
					}

				}
				if (numeroSeleccionado != null
						&& !"".equals(numeroSeleccionado.trim())) {
					FlurryAgent.logEvent("Teléfono cargado desde agenda", true);
					numeroDeTelefono.setText(numeroSeleccionado);
				} else {
					numeroDeTelefono.setText("");
				}
			}
		} catch (final Exception excepcion) {
			numeroDeTelefono.setText("");
			excepcion.printStackTrace();
		}
	}

	private void mostrarLaAgenda() {
		FlurryAgent.logEvent("Mostrar agenda", true);
		final Intent intent = new Intent(Intent.ACTION_PICK,
				ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(intent, CARGAR_CONTACTO);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}
