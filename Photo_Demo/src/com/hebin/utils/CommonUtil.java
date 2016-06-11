package com.hebin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 通用类 常用的一些小工具
 * 
 * @author zwy
 * 
 */
public class CommonUtil {
	private static String TAG="CommonUtil";
	private static Dialog dialog;
	public static void showInfoDialog(Context context, String message) {
		showInfoDialog(context, message, "", "好", null);
	}
	


	/**
	 * 检查是否是邮箱
	 * 
	 * @param paramString
	 * @return
	 */
	public static boolean isValidEmail(String paramString) {

		String regex = "[a-zA-Z0-9_\\.]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
		if (paramString.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查是否为手机号码
	 * 
	 * @param paramString
	 * @return
	 */
	public static boolean isValidMobiNumber(String paramString) {
		String regex = "^1\\d{10}$";
		if (paramString.matches(regex)) {
			return true;
		}
		return false;
	}

	/**
	 * 弹出提示框
	 * 
	 * @param context
	 *            上下文
	 * @param message
	 *            要显示的消息
	 * @param titleStr
	 *            标题 默认为"提示"
	 * @param positiveStr
	 *            确定按钮 默认值为"确定"
	 * @param onClickListener
	 *            Dialog监听
	 */
	public static void showInfoDialog(Context context, String message, String titleStr, String positiveStr, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		localBuilder.show();
	}
	

	public static String getStringDate(int a) {
		Date currentTime = new Date();
		SimpleDateFormat formatter;
		String dateString = null;
		switch (a) {
		case 1:
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateString = formatter.format(currentTime);
			break;
		case 2:
			formatter=new SimpleDateFormat("yyyy-MM-dd");
			dateString=formatter.format(currentTime);
			break;
		default:
			break;
		}
		
		
		return dateString;
	}
	
	public static Bitmap getBitmapFromUri(Context context, Uri uri) {
		ContentResolver cr = context.getContentResolver();

		try {
			InputStream in = cr.openInputStream(uri);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			int mWidth = options.outWidth;
			int mHeight = options.outHeight;
			// 获取屏幕的宽高
			DisplayMetrics dm = new DisplayMetrics();
			dm = context.getResources().getDisplayMetrics();
			int screenWidth = dm.widthPixels;
			int screenHeight = dm.heightPixels;

			float scale = 1;
			if (mWidth > screenWidth || mHeight > screenHeight) {
				if (mWidth / mHeight > screenWidth / screenHeight) {
					scale = mWidth / (float) screenWidth;
				} else {
					scale = mHeight / (float) screenHeight;
				}
			}

			options = new BitmapFactory.Options();
			options.inSampleSize = Math.round(scale);

			in = cr.openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);

			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}
	//旋转图片
		public static Bitmap adjustPhotoRotation(Bitmap bm,
				final int orientationDegree) {

			Matrix m = new Matrix();
			m.setRotate(orientationDegree, (float) bm.getWidth() / 2,
					(float) bm.getHeight() / 2);
			float targetX, targetY;
			if (orientationDegree == 90) {
				targetX = bm.getHeight();
				targetY = 0;
			} else {
				targetX = bm.getHeight();
				targetY = bm.getWidth();
			}

			final float[] values = new float[9];
			m.getValues(values);

			float x1 = values[Matrix.MTRANS_X];
			float y1 = values[Matrix.MTRANS_Y];

			m.postTranslate(targetX - x1, targetY - y1);

			Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(),
					Bitmap.Config.ARGB_8888);

			Paint paint = new Paint();
			Canvas canvas = new Canvas(bm1);
			canvas.drawBitmap(bm, m, paint);
			return bm1;
		}
		/**
		 * 获取手机型号
		 * @return
		 */
		public static String getPhoneModel(){
			
			return android.os.Build.MODEL;
		}
		/**
		 * 三星手机进行旋转图片
		 * 
		 * @param mBitmap
		 * @return
		 */
		public static Bitmap MatrixImage(Bitmap mBitmap, int rotate ) {
			//if ("SM-N9008S".equals(CommonUtil.getPhoneModel()) || "Lenovo S90-t".equals(CommonUtils.getPhoneModel())) {	
				//Log.e("CommonUtil", "mBitmap.getWidth--->"+mBitmap.getWidth()+"mBitmap.getHeight--->"+mBitmap.getHeight());
				
			boolean isRotate = false; 
			if(mBitmap != null) {
				isRotate = mBitmap.getWidth() > mBitmap.getHeight();
				Log.e(TAG,"isRotate---->"+isRotate);
			}
			
			if(isRotate){
				Matrix matrix = new Matrix();
				matrix.reset();
				matrix.setRotate(rotate);
				mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
						mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
			} 
			
			
			/*if ("SM-N9008S".equals(CommonUtils.getPhoneModel()) || "Lenovo S90-t".equals(CommonUtils.getPhoneModel())) {			 
			//boolean isRotate = mBitmap.getWidth() > mBitmap.getHeight();
			//if(isRotate){
					Matrix matrix = new Matrix();
					matrix.reset();
					matrix.setRotate(rotate);
					mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
							mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
				 
			}*/
			return mBitmap;
		}

		/**
	     * 普通枚举
	     * 
	     * @author jiqinlin
	     *
	     */
	    public enum ResultStateEnum {
	    	failure, success;
	    }
}
