package com.binasystems.mtimereporter.api.requests;


import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.binasystems.mtimereporter.dialog.ExceptionDialog;
import com.binasystems.mtimereporter.R;

public abstract class BaseAsyncTask<Result> extends AsyncTask<String, Runnable, Result>{
	protected Context context = null;
	public static interface CallbackListener<Result>{
		public void onSuccessResult(Result result);
		public void onError(Throwable error);
	}
	
	protected CallbackListener<Result> mListener;
	
	protected Throwable mError;
	
	public void setCallback(CallbackListener<Result> listener){
		this.mListener = listener;
	}
	
	public boolean hasError(){
		return mError != null;
	}
	
	@Override
	protected void onProgressUpdate(Runnable... values) {
		super.onProgressUpdate(values);
		if(values != null && values.length > 0){
			for(Runnable v: values){
				v.run();
			}
		}
	}
	public BaseAsyncTask(Context context){
		this.context = context;
	}
	protected void postDataBackgroundHandleResult(Result result){}
	
	/**
	 * This method called from background thread, when some errors occurred during server call.
	 * Note, this method not run in the main(GUI) thread. To show error dialog, for example You can use 
	 * <p><code>		
	 * 	Handler handler = new Handler(Looper.getMainLooper());
	 * 	handler.post(new Runnable() {
	 * 		public void run() {
	 * 			Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
	 * 		}
	 * 	});
	 * 	</code>
	 * 
	 * @param mContext
	 * 		mContext provided from child async task or null
	 * 
	 * @param e
	 *  	exception with error. It can store info about server error status line. 
	 */
	protected void onError(final Context context, Exception e){

		// Show default error message dialog
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				Dialog alert = new ExceptionDialog(context,
						context.getResources()
								.getString(R.string.request_fail),
//						mContext.getResources().getString(R.string.request_fail),
						context.getString(R.string.error),"");

				alert.show();
			}
		});
	}
	
	protected void showRequestFailExceptionDialog(final Context context){
		if(context != null){
			// Run un gui thread
			Handler handler = new Handler(Looper.getMainLooper());
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					Dialog alert = new ExceptionDialog(context,
							context.getResources()
									.getString(R.string.request_fail),
//							mContext.getResources().getString(R.string.request_fail),
							context.getString(R.string.error),"");

					alert.show();
				}
			});
		}
	}
}
