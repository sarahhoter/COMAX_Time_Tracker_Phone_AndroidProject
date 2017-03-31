package com.binasystems.mtimereporter.api;

public interface Callback<T> {
	public void onSuccess(T result);
	public void onError(Exception error);		
}
