package com.tactilapp.operadorapp.componente;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class TextViewQueSeAdaptaAUnaLinea extends TextView {

	public TextViewQueSeAdaptaAUnaLinea(final Context contexto) {
		super(contexto);
		inicializar();
	}

	public TextViewQueSeAdaptaAUnaLinea(final Context contexto,
			final AttributeSet atributos) {
		super(contexto, atributos);
		inicializar();
	}

	public TextViewQueSeAdaptaAUnaLinea(final Context contexto,
			AttributeSet atributos, int defStyle) {
		super(contexto, atributos, defStyle);
		inicializar();
	}

	private void inicializar() {
		setSingleLine();
		setEllipsize(TruncateAt.END);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final Layout layout = getLayout();
		if (layout != null) {
			final int numeroDeLineas = layout.getLineCount();
			if (numeroDeLineas > 0) {
				final int numeroDeCaracteresFueraDeLaAnchura = layout
						.getEllipsisCount(numeroDeLineas - 1);
				if (numeroDeCaracteresFueraDeLaAnchura > 0) {
					final float tamanhoDelTexto = getTextSize();
					setTextSize(TypedValue.COMPLEX_UNIT_PX, (tamanhoDelTexto - 10));

					super.onMeasure(widthMeasureSpec, heightMeasureSpec);
				}
			}
		}
	}


}