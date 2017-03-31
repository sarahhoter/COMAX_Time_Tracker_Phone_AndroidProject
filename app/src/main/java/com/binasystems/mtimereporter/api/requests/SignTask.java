package com.binasystems.mtimereporter.api.requests;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.binasystems.mtimereporter.TimeTrackerApplication;
import com.binasystems.mtimereporter.R;
import com.binasystems.mtimereporter.dialog.ExceptionDialog;
import com.binasystems.mtimereporter.dialog.WaitDialog;

import java.io.ByteArrayOutputStream;

public class SignTask extends BaseAsyncTask<String>{

	Context context;
	WaitDialog waitDialog;
	Dialog alert;
	Bitmap bitmap=null;

	private static final long REFUND_CERTIFIDATE = 11;
	private static final long CERTIFICATE_LOGIN_MEGA = 13;
	public SignTask(Context context,Bitmap bitmap) {
		super(context);

		this.context=context;
		this.bitmap=bitmap;



	}

	@Override
	protected void onPreExecute() {

		waitDialog=new WaitDialog(context);
		waitDialog.show();

	};

	@Override
	protected String doInBackground(String... params) {


		UniRequest ur = new UniRequest("Mobile/Signature.aspx", "execute");



			ur.addLine("Lk",UniRequest.LkC);
			ur.addLine("SwSQL",  UniRequest.SwSQL);
			ur.addLine("UserC", UniRequest.UserC);
			ur.addLine("Company", TimeTrackerApplication.getInstance().getBranch().getC());
			ur.addLine("MlayDoc_MsofonC", String.valueOf(TimeTrackerApplication.getInstance().getMlayDoc_MsofonC()));
			switch ((int)TimeTrackerApplication.getInstance().getDoc()) {
				case (int)REFUND_CERTIFIDATE: {
					ur.addLine("DocType", "271");
				}break;
				case (int)CERTIFICATE_LOGIN_MEGA: {
					ur.addLine("DocType", "270");
				}break;
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if(bitmap!=null) {
				bitmap.compress(
					Bitmap.CompressFormat.JPEG, 50, baos); // bm is the
			// bitmap object
			byte[] b = baos.toByteArray();

				ur.addLine("ImgSign",resizeBase64Image (Base64.encodeToString(b, Base64.DEFAULT)));
			}
			else ur.addLine("ImgSign", "");


			try {
				return  PostRequest.executeRequestAsStringResult(ur);

			} catch (Exception e) {
				e.printStackTrace();
				publishProgress(new Runnable() {

					@Override
					public void run() {
						alert = new ExceptionDialog(context, context.getResources()
								.getString(R.string.request_fail),
								null,"ok");
						alert.show();
					}
				});
				onError(context, e);

			}
			return null;
	}

	public String resizeBase64Image(String base64image){
		byte [] encodeByte=Base64.decode(base64image.getBytes(),Base64.DEFAULT);
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inPurgeable = true;
		Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length,options);


		if(image.getHeight() <= 400 && image.getWidth() <= 400){
			return base64image;
		}
		image = Bitmap.createScaledBitmap(image, 300, 300, false);

		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG,100, baos);

		byte [] b=baos.toByteArray();
		System.gc();
		return Base64.encodeToString(b, Base64.NO_WRAP);

	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);


//		Dialog alert = null;
//
//		if (result==null || result.trim().isEmpty()) {
//
//			alert = new ExceptionDialog(context, context.getResources()
//					.getString(R.string.request_fail),
//					null,"ok");
//			alert.show();
//
//		} else {
//			JSONObject response = null;
//			try {
//				response = new JSONObject(result);
//
//				if (response.getBoolean("Result")) {
//					String errorMessage;
//					try {
//						errorMessage = response.getString("Msg");
//						if(errorMessage.equals("Ok")) {
//							alert = new ExceptionDialog(context, context.getResources()
//									.getString(R.string.the_image_received),
//									null, "ok");
//							alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
//								@Override
//								public void onDismiss(DialogInterface dialog) {
//									Intent intent = new Intent(context, ProductsActivity.class);
//									context.startActivity(intent);
//
//
//								}
//							});
//							alert.show();
//						}
//						else{
//							alert = new ExceptionDialog(context, context.getResources()
//									.getString(R.string.request_fail),
//									null, "ok");
//
//							alert.show();
//						}
//					} catch (Exception e) {
//						errorMessage = context.getString(R.string.request_fail);
//					}
//
//				}
//				else{
//					alert = new ExceptionDialog(context, context.getResources()
//							.getString(R.string.request_fail),
//							null, "ok");
//
//					alert.show();
//				}
//			}catch (JSONException e){}
//			//Log.d("result", result);
//
//
//
//
//
//		}
//
//
	waitDialog.dismiss();
	}



}
