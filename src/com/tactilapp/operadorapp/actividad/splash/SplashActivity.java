package com.tactilapp.operadorapp.actividad.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tactilapp.operadorapp.Constantes;
import com.tactilapp.operadorapp.R;
import com.tactilapp.operadorapp.actividad.paso1.Paso1Activity;

public class SplashActivity
		extends Activity {

	private Activity actividad;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		actividad = this;

		new Handler().postDelayed(new Runnable() {
			public void run() {
				actividad.startActivity(new Intent(actividad,
						Paso1Activity.class));
				actividad.finish();
				actividad.overridePendingTransition(R.anim.fade_in,
						R.anim.fade_out);
			}
		}, Constantes.TIEMPO_SPLASH);

	}

}