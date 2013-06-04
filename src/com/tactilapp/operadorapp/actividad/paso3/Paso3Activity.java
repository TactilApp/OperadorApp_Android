package com.tactilapp.operadorapp.actividad.paso3;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.tactilapp.operadorapp.R;
import com.tactilapp.operadorapp.actividad.AbstractActivity;
import com.tactilapp.operadorapp.actividad.paso1.Paso1Activity;

public class Paso3Activity
		extends AbstractActivity {

	private TextView textoDeLaCompanhia;

	private String numero;
	private String nombreDeLaCompanhia;
	private String colorSuperior;
	private String colorInferior;

	@Override
	protected int obtenerVista() {
		return R.layout.paso3;
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		numero = getIntent().getStringExtra("numero");
		nombreDeLaCompanhia = getIntent().getStringExtra("companhia");
		colorSuperior = getIntent().getStringExtra("colorSuperior");
		colorInferior = getIntent().getStringExtra("colorInferior");

		textoDeLaCompanhia = (TextView) findViewById(R.id.companhia);
		textoDeLaCompanhia.setText(nombreDeLaCompanhia);

		final Map<String, String> nombreCompanhia =
				new HashMap<String, String>();
		nombreCompanhia.put("nombre", nombreDeLaCompanhia);
		FlurryAgent.logEvent("Compañía cargada", nombreCompanhia, true);

		final GradientDrawable degradado =
				new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
						new int[]{ obtenerElColorSuperior(),
								obtenerElColorInferior() });
		degradado.setCornerRadius(0f);
		textoDeLaCompanhia.setBackgroundDrawable(degradado);
	}

	private int obtenerElColorSuperior() {
		return (int) Long.parseLong("FF" + colorSuperior.substring(1), 16);
	}

	private int obtenerElColorInferior() {
		return (int) Long.parseLong("FF" + colorInferior.substring(1), 16);
	}

	public void irAlPaso1(final View view) {

		final Intent intent = new Intent(this, Paso1Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	public void irALlamar(final View view) {
		final Uri numeroDeTelefono = Uri.parse("tel:" + numero);
		startActivity(new Intent(Intent.ACTION_CALL, numeroDeTelefono));
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	@Override
	public void onBackPressed() {
		irAlPaso1(null);
	}

}