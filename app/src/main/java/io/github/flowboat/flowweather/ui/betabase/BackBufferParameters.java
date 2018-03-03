package io.github.flowboat.flowweather.ui.betabase;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.opengl.GLES20;

public class BackBufferParameters extends TextureParameters {
	private static final String PRESET = "p";

	private String preset;

	public BackBufferParameters() {
		super(GLES20.GL_NEAREST,
				GLES20.GL_NEAREST,
				GLES20.GL_CLAMP_TO_EDGE,
				GLES20.GL_CLAMP_TO_EDGE);
	}

	public void setPreset(String preset) {
		this.preset = preset;
	}

	@Override
	public String toString() {
		String params = super.toString();
		if (preset != null) {
			if (params.length() < 1) {
				params = HEADER;
			}
			params += PRESET + ASSIGN + preset + SEPARATOR;
		}
		return params;
	}

	void reset() {
		set(GLES20.GL_NEAREST,
				GLES20.GL_NEAREST,
				GLES20.GL_CLAMP_TO_EDGE,
				GLES20.GL_CLAMP_TO_EDGE);
		preset = null;
	}

	boolean setPresetBitmap(int width, int height) {
		if (preset == null) {
			return false;
		}

//		Bitmap tile = ShaderEditorApp.db.getTextureBitmap(preset);
		Bitmap tile = null;
		if (tile == null) {
			return false;
		}

		Bitmap background = Bitmap.createBitmap(
				width,
				height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(background);

		int tw = tile.getWidth();
		int th = tile.getHeight();
		int scaledHeight = Math.round((float) width / tw * th);
		tile = Bitmap.createScaledBitmap(
				tile,
				width,
				scaledHeight,
				true);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(new BitmapShader(tile,
				Shader.TileMode.CLAMP,
				Shader.TileMode.MIRROR));
		canvas.drawPaint(paint);

		setBitmap(background);

		return true;
	}

	@Override
	protected void parseParameter(String name, String value) {
		if (PRESET.equals(name)) {
			preset = value;
		} else {
			super.parseParameter(name, value);
		}
	}
}