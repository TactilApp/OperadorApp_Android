package com.tactilapp.operadorapp.post;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.google.gson.Gson;
import com.tactilapp.operadorapp.Utils;

public abstract class AbstractEnvioDePost<E> {

	protected String url;

	@SuppressWarnings("rawtypes")
	protected abstract Class obtenerClaseAGenerar();

	public AbstractEnvioDePost(String url) {
		super();
		this.url = url;
	}

	@SuppressWarnings("unchecked")
	protected E enviarPeticion(final String cookie,
			final List<NameValuePair> informacionAEnviar) {
		return (E) new Gson().fromJson(
				generarPeticion(cookie, informacionAEnviar),
				obtenerClaseAGenerar());
	}

	protected String generarPeticion(final String cookie,
			final List<NameValuePair> informacionAEnviar) {
		final DefaultHttpClient cliente = new DefaultHttpClient();
		try {
			final HttpPost peticion = new HttpPost(url);
			peticion.setEntity(new UrlEncodedFormEntity(informacionAEnviar,
					HTTP.UTF_8));
			peticion.setHeader("Cookie", cookie);

			final HttpResponse respuesta = cliente.execute(peticion);
			final HttpEntity contenidoDeLaRespuesta = respuesta.getEntity();

			if (contenidoDeLaRespuesta != null) {
				return Utils.obtenerElContenido(contenidoDeLaRespuesta);
			}
		} catch (final Exception excepcion) {
			Log.e(getClass().getSimpleName(), "Error para la URL " + url,
					excepcion);
		} finally {
			if (cliente != null) {
				cliente.getConnectionManager().shutdown();
			}
		}
		return null;
	}
}
