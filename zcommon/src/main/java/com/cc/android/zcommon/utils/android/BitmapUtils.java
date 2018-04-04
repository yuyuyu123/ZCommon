package com.cc.android.zcommon.utils.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 位图工具箱
 */
public class BitmapUtils {
	/**
	 * 缩放处理
	 * @param bitmap 原图
	 * @param scaling 缩放比例
	 * @return 缩放后的图片
	 */
	public static Bitmap scale(Bitmap bitmap, float scaling) {
		Matrix matrix = new Matrix();
		matrix.postScale(scaling, scaling);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	/**
	 * 缩放处理
	 * @param bitmap 原图
	 * @param newWidth 新的宽度
	 * @return
	 */
	public static Bitmap scaleByWidth(Bitmap bitmap, int newWidth) {
		return scale(bitmap, (float) newWidth / bitmap.getWidth());
	}

	/**
	 * 缩放处理
	 * @param bitmap 原图
	 * @param newHeight 新的高度
	 * @return
	 */
	public static Bitmap scaleByHeight(Bitmap bitmap, int newHeight) {
		return scale(bitmap, (float) newHeight / bitmap.getHeight());
	}

	/**
	 * 水平翻转处理
	 * @param bitmap 原图
	 * @return 水平翻转后的图片
	 */
	public static Bitmap reverseByHorizontal(Bitmap bitmap){
		Matrix matrix = new Matrix();
		matrix.preScale(-1, 1);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
	}

	/**
	 * 垂直翻转处理
	 * @param bitmap 原图
	 * @return 垂直翻转后的图片
	 */
	public static Bitmap reverseByVertical(Bitmap bitmap){
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
	}

	/**
	 * 将给定资源ID的Drawable转换成Bitmap
	 * @param context 上下文
	 * @param resId Drawable资源文件的ID
	 * @return 新的Bitmap
	 */
	public static Bitmap drawableToBitmap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 圆角处理
	 * @param bitmap 原图片
	 * @param pixels 角度，度数越大圆角越大
	 * @return 转换成圆角后的图片
	 */
	public static Bitmap roundCorner(Bitmap bitmap, float pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  //创建一个同原图一样大小的矩形，用于把原图绘制到这个矩形上
		RectF rectF = new RectF(rect);  //创建一个精度更高的矩形，用于画出圆角效果
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0); //涂上黑色全透明的底色
		paint.setColor(0xff424242);  //设置画笔的颜色为不透明的灰色
		canvas.drawRoundRect(rectF, pixels, pixels, paint); //用给给定的画笔把给定的矩形以给定的圆角的度数画到画布
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint); //用画笔paint将原图bitmap根据新的矩形重新绘制
		return output;
	}

	/**
	 * 倒影处理
	 * @param bitmap 原图
	 * @param reflectionSpacing 原图与倒影之间的间距
	 * @return 加上倒影后的图片
	 */
	public static Bitmap reflection(Bitmap bitmap, int reflectionSpacing, int reflectionHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		/* 获取倒影图片，并创建一张宽度与原图相同，但高度等于原图的高度加上间距加上倒影的高度的图片，并创建画布。画布分为上中下三部分，上：是原图；中：是原图与倒影的间距；下：是倒影 */
		Bitmap reflectionImage = reverseByVertical(bitmap);//
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, height + reflectionSpacing + reflectionHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);

		/* 将原图画到画布的上半部分，将倒影画到画布的下半部分，倒影与画布顶部的间距是原图的高度加上原图与倒影之间的间距 */
		canvas.drawBitmap(bitmap, 0, 0, null);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionSpacing, null);

		/* 将倒影改成半透明，创建画笔，并设置画笔的渐变从半透明的白色到全透明的白色，然后再倒影上面画半透明效果 */
		Paint paint = new Paint();
		paint.setShader(new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionSpacing, 0x70ffffff, 0x00ffffff, TileMode.CLAMP));
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height+reflectionSpacing, width, bitmapWithReflection.getHeight() + reflectionSpacing, paint);

		return bitmapWithReflection;
	}

	/**
	 * 倒影处理
	 * @param bitmap 原图
	 * @return 加上倒影后的图片
	 */
	public static Bitmap reflection(Bitmap bitmap) {
		return reflection(bitmap, 4, bitmap.getHeight()/2);
	}

	/**
	 * 旋转处理
	 * @param bitmap 原图
	 * @param angle 旋转角度
	 * @param px 旋转中心点在X轴的坐标
	 * @param py 旋转中心点在Y轴的坐标
	 * @return 旋转后的图片
	 */
	public static Bitmap rotate(Bitmap bitmap, float angle, float px, float py){
		Matrix matrix = new Matrix();
		matrix.postRotate(angle, px, py);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
	}

	/**
	 * 旋转后处理
	 * @param bitmap 原图
	 * @param angle 旋转角度
	 * @return 旋转后的图片
	 */
	public static Bitmap rotate(Bitmap bitmap, float angle){
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
	}

	/**
	 * 饱和度处理
	 * @param bitmap 原图
	 * @param saturationValue 新的饱和度值
	 * @return 改变了饱和度值之后的图片
	 */
	public static Bitmap saturation(Bitmap bitmap, int saturationValue){
		//计算出符合要求的饱和度值
		float newSaturationValue = saturationValue * 1.0F / 127;
		//创建一个颜色矩阵
		ColorMatrix saturationColorMatrix = new ColorMatrix();
		//设置饱和度值
		saturationColorMatrix.setSaturation(newSaturationValue);
		//创建一个画笔并设置其颜色过滤器
		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(saturationColorMatrix));
		//创建一个新的图片并创建画布
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		//将原图使用给定的画笔画到画布上
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return newBitmap;
	}

	/**
	 * 亮度处理
	 * @param bitmap 原图
	 * @param lumValue 新的亮度值
	 * @return 改变了亮度值之后的图片
	 */
	public static Bitmap lum(Bitmap bitmap, int lumValue){
		//计算出符合要求的亮度值
		float newlumValue = lumValue * 1.0F / 127;
		//创建一个颜色矩阵
		ColorMatrix lumColorMatrix = new ColorMatrix();
		//设置亮度值
		lumColorMatrix.setScale(newlumValue, newlumValue, newlumValue, 1);
		//创建一个画笔并设置其颜色过滤器
		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(lumColorMatrix));
		//创建一个新的图片并创建画布
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		//将原图使用给定的画笔画到画布上
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return newBitmap;
	}

	/**
	 * 色相处理
	 * @param bitmap 原图
	 * @param hueValue 新的色相值
	 * @return 改变了色相值之后的图片
	 */
	public static Bitmap hue(Bitmap bitmap, int hueValue){
		//计算出符合要求的色相值
		float newHueValue = (hueValue - 127) * 1.0F / 127 * 180;
		//创建一个颜色矩阵
		ColorMatrix hueColorMatrix = new ColorMatrix();
		// 控制让红色区在色轮上旋转的角度
		hueColorMatrix.setRotate(0, newHueValue);
		// 控制让绿红色区在色轮上旋转的角度
		hueColorMatrix.setRotate(1, newHueValue);
		// 控制让蓝色区在色轮上旋转的角度
		hueColorMatrix.setRotate(2, newHueValue);
		//创建一个画笔并设置其颜色过滤器
		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(hueColorMatrix));
		//创建一个新的图片并创建画布
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		//将原图使用给定的画笔画到画布上
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return newBitmap;
	}

	/**
	 * 亮度、色相、饱和度处理
	 * @param bitmap 原图
	 * @param lumValue 亮度值
	 * @param hueValue 色相值
	 * @param saturationValue 饱和度值
	 * @return 亮度、色相、饱和度处理后的图片
	 */
	public static Bitmap lumAndHueAndSaturation(Bitmap bitmap, int lumValue, int hueValue, int saturationValue){
		//计算出符合要求的饱和度值
		float newSaturationValue = saturationValue * 1.0F / 127;
		//计算出符合要求的亮度值
		float newlumValue = lumValue * 1.0F / 127;
		//计算出符合要求的色相值
		float newHueValue = (hueValue - 127) * 1.0F / 127 * 180;

		//创建一个颜色矩阵并设置其饱和度
		ColorMatrix colorMatrix = new ColorMatrix();

		//设置饱和度值
		colorMatrix.setSaturation(newSaturationValue);
		//设置亮度值
		colorMatrix.setScale(newlumValue, newlumValue, newlumValue, 1);
		// 控制让红色区在色轮上旋转的角度
		colorMatrix.setRotate(0, newHueValue);
		// 控制让绿红色区在色轮上旋转的角度
		colorMatrix.setRotate(1, newHueValue);
		// 控制让蓝色区在色轮上旋转的角度
		colorMatrix.setRotate(2, newHueValue);

		//创建一个画笔并设置其颜色过滤器
		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
		//创建一个新的图片并创建画布
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		//将原图使用给定的画笔画到画布上
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return newBitmap;
	}

	/**
	 * 怀旧效果处理
	 * @param bitmap 原图
	 * @return 怀旧效果处理后的图片
	 */
	public static Bitmap nostalgic(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
		int pixColor = 0;
		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int newR = 0;
		int newG = 0;
		int newB = 0;
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < height; i++) {
			for (int k = 0; k < width; k++) {
				pixColor = pixels[width * i + k];
				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
				newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
				newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
				int newColor = Color.argb(255, newR > 255 ? 255 : newR, newG > 255 ? 255 : newG, newB > 255 ? 255
						: newB);
				pixels[width * i + k] = newColor;
			}
		}
		newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return newBitmap;
	}

	/**
	 * 模糊效果处理
	 * @param bitmap 原图
	 * @return 模糊效果处理后的图片
	 */
	public static Bitmap blur(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int newColor = 0;

		int[][] colors = new int[9][3];
		for (int i = 1, length = width - 1; i < length; i++) {
			for (int k = 1, len = height - 1; k < len; k++) {
				for (int m = 0; m < 9; m++) {
					int s = 0;
					int p = 0;
					switch (m) {
					case 0:
						s = i - 1;
						p = k - 1;
						break;
					case 1:
						s = i;
						p = k - 1;
						break;
					case 2:
						s = i + 1;
						p = k - 1;
						break;
					case 3:
						s = i + 1;
						p = k;
						break;
					case 4:
						s = i + 1;
						p = k + 1;
						break;
					case 5:
						s = i;
						p = k + 1;
						break;
					case 6:
						s = i - 1;
						p = k + 1;
						break;
					case 7:
						s = i - 1;
						p = k;
						break;
					case 8:
						s = i;
						p = k;
					}
					pixColor = bitmap.getPixel(s, p);
					colors[m][0] = Color.red(pixColor);
					colors[m][1] = Color.green(pixColor);
					colors[m][2] = Color.blue(pixColor);
				}

				for (int m = 0; m < 9; m++) {
					newR += colors[m][0];
					newG += colors[m][1];
					newB += colors[m][2];
				}

				newR = (int) (newR / 9F);
				newG = (int) (newG / 9F);
				newB = (int) (newB / 9F);

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				newColor = Color.argb(255, newR, newG, newB);
				newBitmap.setPixel(i, k, newColor);

				newR = 0;
				newG = 0;
				newB = 0;
			}
		}
		return newBitmap;
	}

	/**
	 * 柔化效果处理
	 * @param bitmap 原图
	 * @return 柔化效果处理后的图片
	 */
	public static Bitmap soften(Bitmap bitmap) {
		// 高斯矩阵
		int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int delta = 16; // 值越小图片会越亮，越大则越暗

		int idx = 0;
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				idx = 0;
				for (int m = -1; m <= 1; m++) {
					for (int n = -1; n <= 1; n++) {
						pixColor = pixels[(i + m) * width + k + n];
						pixR = Color.red(pixColor);
						pixG = Color.green(pixColor);
						pixB = Color.blue(pixColor);

						newR = newR + (pixR * gauss[idx]);
						newG = newG + (pixG * gauss[idx]);
						newB = newB + (pixB * gauss[idx]);
						idx++;
					}
				}

				newR /= delta;
				newG /= delta;
				newB /= delta;

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				pixels[i * width + k] = Color.argb(255, newR, newG, newB);

				newR = 0;
				newG = 0;
				newB = 0;
			}
		}

		newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return newBitmap;
	}

	/**
	 * 光照效果处理
	 * @param bitmap 原图
	 * @param centerX 光源在X轴的位置
	 * @param centerY 光源在Y轴的位置
	 * @return 光照效果处理后的图片
	 */
	public static Bitmap sunshine(Bitmap bitmap, int centerX, int centerY) {
		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;
		int radius = Math.min(centerX, centerY);

		final float strength = 150F; // 光照强度 100~150
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		int pos = 0;
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				pos = i * width + k;
				pixColor = pixels[pos];

				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);

				newR = pixR;
				newG = pixG;
				newB = pixB;

				// 计算当前点到光照中心的距离，平面座标系中求两点之间的距离
				int distance = (int) (Math.pow((centerY - i), 2) + Math.pow(centerX - k, 2));
				if (distance < radius * radius) {
					// 按照距离大小计算增加的光照值
					int result = (int) (strength * (1.0 - Math.sqrt(distance) / radius));
					newR = pixR + result;
					newG = pixG + result;
					newB = pixB + result;
				}

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				pixels[pos] = Color.argb(255, newR, newG, newB);
			}
		}

		newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return newBitmap;
	}

	/**
	 * 底片效果处理
	 * @param bitmap 原图
	 * @return 底片效果处理后的图片
	 */
	public static Bitmap film(Bitmap bitmap) {
		// RGBA的最大值
		final int MAX_VALUE = 255;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		int pos = 0;
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				pos = i * width + k;
				pixColor = pixels[pos];

				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);

				newR = MAX_VALUE - pixR;
				newG = MAX_VALUE - pixG;
				newB = MAX_VALUE - pixB;

				newR = Math.min(MAX_VALUE, Math.max(0, newR));
				newG = Math.min(MAX_VALUE, Math.max(0, newG));
				newB = Math.min(MAX_VALUE, Math.max(0, newB));

				pixels[pos] = Color.argb(MAX_VALUE, newR, newG, newB);
			}
		}

		newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return newBitmap;
	}

	/**
	 * 锐化效果处理
	 * @param bitmap 原图
	 * @return 锐化效果处理后的图片
	 */
	public static Bitmap sharpen(Bitmap bitmap) {
		// 拉普拉斯矩阵
		int[] laplacian = new int[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 };

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int idx = 0;
		float alpha = 0.3F;
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				idx = 0;
				for (int m = -1; m <= 1; m++) {
					for (int n = -1; n <= 1; n++) {
						pixColor = pixels[(i + n) * width + k + m];
						pixR = Color.red(pixColor);
						pixG = Color.green(pixColor);
						pixB = Color.blue(pixColor);

						newR = newR + (int) (pixR * laplacian[idx] * alpha);
						newG = newG + (int) (pixG * laplacian[idx] * alpha);
						newB = newB + (int) (pixB * laplacian[idx] * alpha);
						idx++;
					}
				}

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				pixels[i * width + k] = Color.argb(255, newR, newG, newB);
				newR = 0;
				newG = 0;
				newB = 0;
			}
		}

		newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return newBitmap;
	}

	/**
	 * 浮雕效果处理
	 * @param bitmap 原图
	 * @return 浮雕效果处理后的图片
	 */
	public static Bitmap emboss(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		int pos = 0;
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				pos = i * width + k;
				pixColor = pixels[pos];

				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);

				pixColor = pixels[pos + 1];
				newR = Color.red(pixColor) - pixR + 127;
				newG = Color.green(pixColor) - pixG + 127;
				newB = Color.blue(pixColor) - pixB + 127;

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				pixels[pos] = Color.argb(255, newR, newG, newB);
			}
		}

		newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return newBitmap;
	}

	/**
	 * 素描效果处理
	 * @param bmp 原图
	 * @return 素描效果处理后的图片
	 */
	public static Bitmap sketch(Bitmap bmp) {
		//		int pos, row, col, clr;
		//		int width = bmp.getWidth();
		//		int height = bmp.getHeight();
		//		int[] pixSrc = new int[width * height];
		//		int[] pixNvt = new int[width * height];
		//		// 先对图象的像素处理成灰度颜色后再取反
		//		bmp.getPixels(pixSrc, 0, width, 0, 0, width, height);
		//
		//		for (row = 0; row < height; row++) {
		//			for (col = 0; col < width; col++) {
		//				pos = row * width + col;
		//				pixSrc[pos] = (Color.red(pixSrc[pos]) + Color.green(pixSrc[pos]) + Color.blue(pixSrc[pos])) / 3;
		//				pixNvt[pos] = 255 - pixSrc[pos];
		//			}
		//		}
		//
		//		// 对取反的像素进行高斯模糊, 强度可以设置，暂定为5.0
		//		gaussGray(pixNvt, 5.0, 5.0, width, height);
		//
		//		// 灰度颜色和模糊后像素进行差值运算
		//		for (row = 0; row < height; row++) {
		//			for (col = 0; col < width; col++) {
		//				pos = row * width + col;
		//
		//				clr = pixSrc[pos] << 8;
		//				clr /= 256 - pixNvt[pos];
		//				clr = Math.min(clr, 255);
		//
		//				pixSrc[pos] = Color.rgb(clr, clr, clr);
		//			}
		//		}
		//		bmp.setPixels(pixSrc, 0, width, 0, 0, width, height);
		//		return bmp;
		return null;
	}

	//	private static int gaussGray(int[] psrc, double horz, double vert, int width, int height) {
	//		int[] dst, src;
	//		double[] n_p, n_m, d_p, d_m, bd_p, bd_m;
	//		double[] val_p, val_m;
	//		int i, j, t, k, row, col, terms;
	//		int[] initial_p, initial_m;
	//		double std_dev;
	//		int row_stride = width;
	//		int max_len = Math.max(width, height);
	//		int sp_p_idx, sp_m_idx, vp_idx, vm_idx;
	//
	//		val_p = new double[max_len];
	//		val_m = new double[max_len];
	//
	//		n_p = new double[5];
	//		n_m = new double[5];
	//		d_p = new double[5];
	//		d_m = new double[5];
	//		bd_p = new double[5];
	//		bd_m = new double[5];
	//
	//		src = new int[max_len];
	//		dst = new int[max_len];
	//
	//		initial_p = new int[4];
	//		initial_m = new int[4];
	//
	//		// 垂直方向
	//		if (vert > 0.0) {
	//			vert = Math.abs(vert) + 1.0;
	//			std_dev = Math.sqrt(-(vert * vert) / (2 * Math.log(1.0 / 255.0)));
	//
	//			// 初试化常量
	//			findConstants(n_p, n_m, d_p, d_m, bd_p, bd_m, std_dev);
	//
	//			for (col = 0; col < width; col++) {
	//				for (k = 0; k < max_len; k++) {
	//					val_m[k] = val_p[k] = 0;
	//				}
	//
	//				for (t = 0; t < height; t++) {
	//					src[t] = psrc[t * row_stride + col];
	//				}
	//
	//				sp_p_idx = 0;
	//				sp_m_idx = height - 1;
	//				vp_idx = 0;
	//				vm_idx = height - 1;
	//
	//				initial_p[0] = src[0];
	//				initial_m[0] = src[height - 1];
	//
	//				for (row = 0; row < height; row++) {
	//					terms = (row < 4) ? row : 4;
	//
	//					for (i = 0; i <= terms; i++) {
	//						val_p[vp_idx] += n_p[i] * src[sp_p_idx - i] - d_p[i] * val_p[vp_idx - i];
	//						val_m[vm_idx] += n_m[i] * src[sp_m_idx + i] - d_m[i] * val_m[vm_idx + i];
	//					}
	//					for (j = i; j <= 4; j++) {
	//						val_p[vp_idx] += (n_p[j] - bd_p[j]) * initial_p[0];
	//						val_m[vm_idx] += (n_m[j] - bd_m[j]) * initial_m[0];
	//					}
	//
	//					sp_p_idx++;
	//					sp_m_idx--;
	//					vp_idx++;
	//					vm_idx--;
	//				}
	//
	//				transferGaussPixels(val_p, val_m, dst, 1, height);
	//
	//				for (t = 0; t < height; t++) {
	//					psrc[t * row_stride + col] = dst[t];
	//				}
	//			}
	//		}
	//
	//		// 水平方向
	//		if (horz > 0.0) {
	//			horz = Math.abs(horz) + 1.0;
	//
	//			if (horz != vert) {
	//				std_dev = Math.sqrt(-(horz * horz) / (2 * Math.log(1.0 / 255.0)));
	//
	//				// 初试化常量
	//				findConstants(n_p, n_m, d_p, d_m, bd_p, bd_m, std_dev);
	//			}
	//
	//			for (row = 0; row < height; row++) {
	//				for (k = 0; k < max_len; k++) {
	//					val_m[k] = val_p[k] = 0;
	//				}
	//
	//				for (t = 0; t < width; t++) {
	//					src[t] = psrc[row * row_stride + t];
	//				}
	//
	//				sp_p_idx = 0;
	//				sp_m_idx = width - 1;
	//				vp_idx = 0;
	//				vm_idx = width - 1;
	//
	//				initial_p[0] = src[0];
	//				initial_m[0] = src[width - 1];
	//
	//				for (col = 0; col < width; col++) {
	//					terms = (col < 4) ? col : 4;
	//
	//					for (i = 0; i <= terms; i++) {
	//						val_p[vp_idx] += n_p[i] * src[sp_p_idx - i] - d_p[i] * val_p[vp_idx - i];
	//						val_m[vm_idx] += n_m[i] * src[sp_m_idx + i] - d_m[i] * val_m[vm_idx + i];
	//					}
	//					for (j = i; j <= 4; j++) {
	//						val_p[vp_idx] += (n_p[j] - bd_p[j]) * initial_p[0];
	//						val_m[vm_idx] += (n_m[j] - bd_m[j]) * initial_m[0];
	//					}
	//
	//					sp_p_idx++;
	//					sp_m_idx--;
	//					vp_idx++;
	//					vm_idx--;
	//				}
	//
	//				transferGaussPixels(val_p, val_m, dst, 1, width);
	//
	//				for (t = 0; t < width; t++) {
	//					psrc[row * row_stride + t] = dst[t];
	//				}
	//			}
	//		}
	//
	//		return 0;
	//	}

	//	private static void findConstants(double[] n_p, double[] n_m, double[] d_p, double[] d_m, double[] bd_p,
	//			double[] bd_m, double std_dev) {
	//		double div = Math.sqrt(2 * 3.141593) * std_dev;
	//		double x0 = -1.783 / std_dev;
	//		double x1 = -1.723 / std_dev;
	//		double x2 = 0.6318 / std_dev;
	//		double x3 = 1.997 / std_dev;
	//		double x4 = 1.6803 / div;
	//		double x5 = 3.735 / div;
	//		double x6 = -0.6803 / div;
	//		double x7 = -0.2598 / div;
	//		int i;
	//
	//		n_p[0] = x4 + x6;
	//		n_p[1] = (Math.exp(x1) * (x7 * Math.sin(x3) - (x6 + 2 * x4) * Math.cos(x3)) + Math.exp(x0)
	//				* (x5 * Math.sin(x2) - (2 * x6 + x4) * Math.cos(x2)));
	//		n_p[2] = (2
	//				* Math.exp(x0 + x1)
	//				* ((x4 + x6) * Math.cos(x3) * Math.cos(x2) - x5 * Math.cos(x3) * Math.sin(x2) - x7 * Math.cos(x2)
	//						* Math.sin(x3)) + x6 * Math.exp(2 * x0) + x4 * Math.exp(2 * x1));
	//		n_p[3] = (Math.exp(x1 + 2 * x0) * (x7 * Math.sin(x3) - x6 * Math.cos(x3)) + Math.exp(x0 + 2 * x1)
	//				* (x5 * Math.sin(x2) - x4 * Math.cos(x2)));
	//		n_p[4] = 0.0;
	//
	//		d_p[0] = 0.0;
	//		d_p[1] = -2 * Math.exp(x1) * Math.cos(x3) - 2 * Math.exp(x0) * Math.cos(x2);
	//		d_p[2] = 4 * Math.cos(x3) * Math.cos(x2) * Math.exp(x0 + x1) + Math.exp(2 * x1) + Math.exp(2 * x0);
	//		d_p[3] = -2 * Math.cos(x2) * Math.exp(x0 + 2 * x1) - 2 * Math.cos(x3) * Math.exp(x1 + 2 * x0);
	//		d_p[4] = Math.exp(2 * x0 + 2 * x1);
	//
	//		for (i = 0; i <= 4; i++) {
	//			d_m[i] = d_p[i];
	//		}
	//
	//		n_m[0] = 0.0;
	//		for (i = 1; i <= 4; i++) {
	//			n_m[i] = n_p[i] - d_p[i] * n_p[0];
	//		}
	//
	//		double sum_n_p, sum_n_m, sum_d;
	//		double a, b;
	//
	//		sum_n_p = 0.0;
	//		sum_n_m = 0.0;
	//		sum_d = 0.0;
	//
	//		for (i = 0; i <= 4; i++) {
	//			sum_n_p += n_p[i];
	//			sum_n_m += n_m[i];
	//			sum_d += d_p[i];
	//		}
	//
	//		a = sum_n_p / (1.0 + sum_d);
	//		b = sum_n_m / (1.0 + sum_d);
	//
	//		for (i = 0; i <= 4; i++) {
	//			bd_p[i] = d_p[i] * a;
	//			bd_m[i] = d_m[i] * b;
	//		}
	//	}

	//	private static void transferGaussPixels(double[] src1, double[] src2, int[] dest, int bytes, int width) {
	//		int i, j, k, b;
	//		int bend = bytes * width;
	//		double sum;
	//
	//		i = j = k = 0;
	//		for (b = 0; b < bend; b++) {
	//			sum = src1[i++] + src2[j++];
	//
	//			if (sum > 255)
	//				sum = 255;
	//			else if (sum < 0)
	//				sum = 0;
	//
	//			dest[k++] = (int) sum;
	//		}
	//	}

	/**
	 * 将YUV格式的图片的源数据从横屏模式转为竖屏模式，注意：将源图片的宽高互换一下就是新图片的宽高
	 * @param sourceData YUV格式的图片的源数据
	 * @param width 源图片的宽
	 * @param height 源图片的高
	 * @return
	 */
	public static final byte[] yuvLandscapeToPortrait(byte[] sourceData, int width, int height){
		byte[] rotatedData = new byte[sourceData.length];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++)
				rotatedData[x * height + height - y - 1] = sourceData[x + y * width];
		}
		return rotatedData;
	}

	/**
	 * 获取缩略图
	 * @param imageFilePath 图片路径
	 * @param thumbnailWidth 缩略图的高度
	 * @param thumbnailHeight 缩略图的宽度
	 * @return
	 */
	public Bitmap getThumbnail(String imageFilePath, int thumbnailWidth, int thumbnailHeight) {
		/* 首先读取原始图片的尺寸 */
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//当此属性为true时，将只返回图片的尺寸信息，不返回图片数据，并且数据会保存在Options中
		BitmapFactory.decodeFile(imageFilePath,options);//因为在此之前将options的inJustDecodeBounds属性设置为true了所以返回的是null
		int sourceImageWidth = options.outWidth;//获取原始图片的宽
		int sourceImageHeight = options.outHeight;//获取原始图片的高

		/* 计算缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可 */
		int scaling = 1;//1：表示不缩放
		if (sourceImageWidth > sourceImageHeight && sourceImageWidth > thumbnailWidth) {//如果宽度大的话根据宽度固定大小缩放
			scaling = (options.outWidth / thumbnailWidth);
		} else if (sourceImageWidth < sourceImageHeight && sourceImageHeight > thumbnailHeight) {//如果高度高的话根据宽度固定大小缩放
			scaling = (options.outHeight / thumbnailHeight);
		}
		if (scaling <= 0){
			scaling = 1;
		}
		options.inSampleSize = scaling;//设置缩放比例

		/* 按之前计算的缩放比例重新读入图片，注意此时需要把options的inJustDecodeBounds设置为false了 */
		options.inJustDecodeBounds = false;//还原为false
		return BitmapFactory.decodeFile(imageFilePath, options);
	}

	/**
	 * Drawable转换成Bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		if(drawable != null){
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE  ? Config.ARGB_8888 : Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		}else{
			return null;
		}
	}

	public void decodeYUV(int[] out, byte[] fg, int width, int height)throws NullPointerException, IllegalArgumentException {
		int sz = width * height;
		if (out == null)
			throw new NullPointerException("buffer out is null");
		if (out.length < sz)
			throw new IllegalArgumentException("buffer out size " + out.length
					+ " < minimum " + sz);
		if (fg == null)
			throw new NullPointerException("buffer 'fg' is null");
		if (fg.length < sz)
			throw new IllegalArgumentException("buffer fg size " + fg.length
					+ " < minimum " + sz * 3 / 2);
		int i, j;
		int Y, Cr = 0, Cb = 0;
		for (j = 0; j < height; j++) {
			int pixPtr = j * width;
			final int jDiv2 = j >> 1;
		for (i = 0; i < width; i++) {
			Y = fg[pixPtr];
			if (Y < 0)
				Y += 255;
			if ((i & 0x1) != 1) {
				final int cOff = sz + jDiv2 * width + (i >> 1) * 2;
				Cb = fg[cOff];
				if (Cb < 0)
					Cb += 127;
				else
					Cb -= 128;
				Cr = fg[cOff + 1];
				if (Cr < 0)
					Cr += 127;
				else
					Cr -= 128;
			}
			int R = Y + Cr + (Cr >> 2) + (Cr >> 3) + (Cr >> 5);
			if (R < 0)
				R = 0;
			else if (R > 255)
				R = 255;
			int G = Y - (Cb >> 2) + (Cb >> 4) + (Cb >> 5) - (Cr >> 1)
					+ (Cr >> 3) + (Cr >> 4) + (Cr >> 5);
			if (G < 0)
				G = 0;
			else if (G > 255)
				G = 255;
			int B = Y + Cb + (Cb >> 1) + (Cb >> 2) + (Cb >> 6);
			if (B < 0)
				B = 0;
			else if (B > 255)
				B = 255;
			out[pixPtr++] = 0xff000000 + (B << 16) + (G << 8) + R;
		}
		}

	}

	/***
	 * 根据Uri转换成Bitmap
	 * @param context
	 * @param uri
	 * @return
	 */
	public static Bitmap uriToBitmap(Context context, Uri uri){
		InputStream inputStream = null;
		try {
			inputStream = context.getContentResolver().openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(inputStream != null) inputStream.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 压缩图片到指定文件
	 * @param bmp
	 * @param path
	 * @return
	 */
	public static boolean compressBitmap2file(Bitmap bmp, String path){
		bmp = compressScale(bmp);
		Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
		int quality = 80;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(path);
			return bmp.compress(format, quality, stream);
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(stream != null) stream.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 图片按比例大小压缩方法
	 *
	 * @param image （根据Bitmap图片压缩）
	 * @return
	 */
	public static Bitmap compressScale(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

		// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
		if (baos.toByteArray().length / 1024 > 1024) {
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		 float hh = 800f;// 这里设置高度为800f
		 float ww = 480f;// 这里设置宽度为480f
//		float hh = 512f;
//		float ww = 512f;
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be; // 设置缩放比例
		// newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565

		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

//        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩

		return bitmap;
	}
}
