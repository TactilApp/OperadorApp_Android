package com.tactilapp.operadorapp.actividad.paso1;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tactilapp.operadorapp.Constantes;
import com.tactilapp.operadorapp.R;
import com.tactilapp.operadorapp.Utils;
import com.tactilapp.operadorapp.iabilling.IabHelper;
import com.tactilapp.operadorapp.iabilling.IabResult;
import com.tactilapp.operadorapp.iabilling.Inventory;
import com.tactilapp.operadorapp.iabilling.Purchase;

public class CompraDeLaAgenda implements Serializable {

	private static final long serialVersionUID = 6240132470950034660L;

	private static final String TAG = "CompraDeLaAgenda";

	private Activity actividad;
	private boolean configuracionCorrectaDeIABilling = false;
	private boolean tieneCompradoElAccesoALaAgenda = false;
	private String idDelServicioDeIABillingParaLaAgenda;
	private String payloadDelServicioDeIABillingParaLaAgenda;

	IabHelper helperDeIABilling;

	public CompraDeLaAgenda(final Activity actividad) {
		super();
		this.actividad = actividad;
	}

	public void inicializar(final boolean depuracion) {
		Log.d(TAG, "Creamos el helper de acceso al servicio de IA Billing.");
		crearElHelperDeIA(depuracion);
		inicializarElHelperDeIA();
	}

	private void crearElHelperDeIA(final boolean depuracion) {
		final String claveIABilling = actividad
				.getString(R.string.clave_ia_billing);
		if (!"ejemplo".equalsIgnoreCase(claveIABilling)) {
			helperDeIABilling = new IabHelper(actividad, claveIABilling);
			helperDeIABilling.enableDebugLogging(depuracion);
			idDelServicioDeIABillingParaLaAgenda = actividad
					.getString(R.string.id_agenda_ia_billing);
			payloadDelServicioDeIABillingParaLaAgenda = actividad
					.getString(R.string.payload_agenda_ia_billing);
		}
	}

	private void inicializarElHelperDeIA() {
		helperDeIABilling
				.startSetup(new IabHelper.OnIabSetupFinishedListener() {
					public void onIabSetupFinished(IabResult result) {
						if (!result.isSuccess()) {
							return;
						}

						helperDeIABilling
								.queryInventoryAsync(listenerParaComprobarSiYaSeHaCompradoElAccesoALaAgenda);
					}
				});
	}

	private IabHelper.QueryInventoryFinishedListener listenerParaComprobarSiYaSeHaCompradoElAccesoALaAgenda = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(final IabResult result,
				final Inventory inventory) {
			if (result.isFailure()) {
				return;
			}

			configuracionCorrectaDeIABilling = true;
			actividad.findViewById(R.id.paso1botonagenda).setVisibility(
					View.VISIBLE);
			final Purchase compraDeLaAgenda = inventory
					.getPurchase(idDelServicioDeIABillingParaLaAgenda);
			tieneCompradoElAccesoALaAgenda = (compraDeLaAgenda != null && Utils
					.comprobarSiLaCompraYaHaSidoRealizada(
							payloadDelServicioDeIABillingParaLaAgenda,
							compraDeLaAgenda));
		}
	};

	private IabHelper.OnIabPurchaseFinishedListener listenerParaComprobarSiLaCompraSeHaRealizadoBien = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(final IabResult resultado,
				final Purchase compra) {
			Log.d(TAG, "Compra terminada: " + resultado + ", purchase: "
					+ compra);
			if (resultado.isFailure()) {
				tieneCompradoElAccesoALaAgenda = false;

				Toast.makeText(
						actividad,
						actividad
								.getString(R.string.paso1_compra_agenda_correcta)
								+ resultado, Toast.LENGTH_LONG).show();
				return;
			} else if (!Utils.comprobarSiLaCompraYaHaSidoRealizada(
					payloadDelServicioDeIABillingParaLaAgenda, compra)) {
				Toast.makeText(actividad,
						R.string.paso1_compra_error_autenticacion,
						Toast.LENGTH_LONG).show();
				return;
			} else if (compra.getSku().equals(
					idDelServicioDeIABillingParaLaAgenda)) {
				Toast.makeText(actividad,
						R.string.paso1_compra_agenda_correcta,
						Toast.LENGTH_LONG).show();
				tieneCompradoElAccesoALaAgenda = true;
			}
		}
	};

	public boolean tieneAccesoALaAgenda() {
		return configuracionCorrectaDeIABilling
				&& tieneCompradoElAccesoALaAgenda;
	}

	public boolean seHaConfiguradoIAConExito() {
		return configuracionCorrectaDeIABilling;
	}

	public void comprarElAccesoALaAgenda() {
		helperDeIABilling.launchPurchaseFlow(actividad,
				idDelServicioDeIABillingParaLaAgenda,
				Constantes.IA_AGENDA_PETICION,
				listenerParaComprobarSiLaCompraSeHaRealizadoBien,
				payloadDelServicioDeIABillingParaLaAgenda);
	}

	public boolean handleActivityResult(final int reqCode,
			final int resultCode, final Intent data) {
		return helperDeIABilling
				.handleActivityResult(reqCode, resultCode, data);
	}

}
