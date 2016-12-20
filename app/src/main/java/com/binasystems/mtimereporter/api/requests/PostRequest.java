package com.binasystems.mtimereporter.api.requests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

import android.util.Log;

import com.binasystems.mtimereporter.api.requests.UniRequest.MyHttpPostTransfer;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.gson.Gson;

/**
 * Class used for sending requests via HttpPost variables <br>
 * methods: <br>
 * (String) executeRequest(HttpPost)<br>
 * 
 * @author eugeniu
 * 
 */

public class PostRequest {
	static final String DEFAULT_ERROR_MESSAGE = "Sorry, try again";
	static public String error;
	
	static final boolean SHOW_LOGS = true;
	
//	static HttpPost post;
//	static String data;
	
	static HttpTransport mHttpTransport;

	/**
	 * Gets the decrypted data from server.
	 * 
	 * @param pst
	 *            HttpPost variable
	 * @return The decrypted string answer from server.
	 */
//	static public String executeRequestOld(HttpPost pst) {
//		
//		String entryName = "PostRequest::execution "+pst.getURI();
//		
//		if(OptProfiler.ENABLE_PROF_LOGS){
//			OptProfiler.logStartEntry(entryName);
//		}
//		
//		if(OptProfiler.NP_GOOGLE){
//			data = executeRequest2(pst);
//			
//		} else{
//			httpRec http_rec = new httpRec();
//			Thread httprec = new Thread(http_rec);
//			post = pst;
//			try {
//				httprec.start();
//				httprec.join();
//			} catch (Exception e) {
//				Log.d("atf", "eror line 26");
//			}			
//		}
//		
//		String result = null; 
//		if(data != null)
//			result = Encrypter.decrypt(data);
//		
//		
//		if(OptProfiler.ENABLE_PROF_LOGS){
//			OptProfiler.logEndEntry(entryName);
//		}
//		
//		return result;
//	}
	
	/**
	 * Gets the decrypted data from server.
	 * 
	 * @param ur
	 *            {@link UniRequest} variable
	 * @return The decrypted string answer from server.
	 */	
	public static String executeRequestAsStringResult(UniRequest ur) throws Exception{
		if(ur != null){			
			return executePostHttpRequest(ur.getUrl(), 
					ur.getHttpHeadersMap(), 
					ur.getHttpParamsMap());
		}
		
		return null;
	}
	
	public static <T> T executeRequest(UniRequest ur, Class<T> clazz) throws Exception{
		
		String resData = executeRequestAsStringResult(ur);
		if(resData != null){
			Gson parser = new Gson();
			return parser.fromJson(resData, clazz);
		}
		
		return null;
	}
	
	static String mapToString(HashMap<String, Object> map){
		if(map != null){
			StringBuilder res = new StringBuilder();
			res.append("{");
			for(Entry<String, Object> e: map.entrySet()){
				res.append(e.getKey())
				.append("=")
				.append(e.getValue())
				.append(" ");
			}
			res.append("}");
			
			return res.toString();
		}
		return "null";
	}
	
	static String executePostHttpRequest(String serverUrl, HashMap<String, Object> headersMap, HashMap<String, Object> paramsMap) throws Exception{
		if(serverUrl != null){
			HttpTransport transport = getHttpTransport();
			HttpRequestFactory factory = transport.createRequestFactory();

			try {
				if(SHOW_LOGS){
					Log.d("atf", "call url: " + serverUrl);
					Log.d("atf", "\theaders: "+mapToString(headersMap));
					Log.d("atf", "\tparams: "+mapToString(paramsMap));
				}
				
				// url
				GenericUrl url = new GenericUrl(serverUrl);

				// content
				if(paramsMap == null) 
					paramsMap = new HashMap<String, Object>();
				
				encryptParams(paramsMap);
				
				UrlEncodedContent content = new UrlEncodedContent(paramsMap);
				
				// create request
				HttpRequest httpPostRequest = factory.buildPostRequest(url, content);
				
				// add headers
				if(headersMap != null){
					HttpHeaders headers = new HttpHeaders();
					headers.putAll(headersMap);
					httpPostRequest.setHeaders(headers);
				}
				
				com.google.api.client.http.HttpResponse response = httpPostRequest.execute();
				int statusCode = response.getStatusCode();
				Log.d("atf", "status code = " + statusCode);
				
				if(statusCode == 200){
					String stringResponse = Encrypter.decrypt(response.parseAsString());
					
					if(SHOW_LOGS)
						Log.d("Response: ", stringResponse);
					
					return stringResponse; 
				}
				
			} catch (IOException e) {
//				e.printStackTrace();
				Log.e("atf", "", e);
				error = DEFAULT_ERROR_MESSAGE;
			}
		}
		return null;		
	}

	public static String executeNotEncrypted(String serverUrl, HashMap<String, Object> headersMap, HashMap<String, Object> paramsMap) throws Exception{
		if(serverUrl != null){
			HttpTransport transport = getHttpTransport();
			HttpRequestFactory factory = transport.createRequestFactory();

			try {
				if(SHOW_LOGS){
					Log.d("atf", "call url: " + serverUrl);
					Log.d("atf", "\theaders: "+mapToString(headersMap));
					Log.d("atf", "\tparams: "+mapToString(paramsMap));
				}

				// url
				GenericUrl url = new GenericUrl(serverUrl);

				// content
				if(paramsMap == null)
					paramsMap = new HashMap<String, Object>();

				UrlEncodedContent content = new UrlEncodedContent(paramsMap);

				// create request
				HttpRequest httpPostRequest = factory.buildPostRequest(url, content);

				// add headers
				if(headersMap != null){
					HttpHeaders headers = new HttpHeaders();
					headers.putAll(headersMap);
					httpPostRequest.setHeaders(headers);
				}

				com.google.api.client.http.HttpResponse response = httpPostRequest.execute();
				int statusCode = response.getStatusCode();
				Log.d("atf", "status code = " + statusCode);

				if(statusCode == 200){
					String stringResponse = Encrypter.decrypt(response.parseAsString());

					if(SHOW_LOGS)
						Log.d("Response: ", stringResponse);

					return stringResponse;
				}

			} catch (IOException e) {
//				e.printStackTrace();
				Log.e("atf", "", e);
				error = DEFAULT_ERROR_MESSAGE;
			}
		}
		return null;
	}
	
	private static void encryptParams(HashMap<String, Object > params){
		if(params != null){
			for(String key: params.keySet()){
				Object value = params.get(key);
//				if(value instanceof String)
				if(value != null)
					params.put(key, Encrypter.encrypt((String) value));
			}
		}		
	}
	
	public static JSONObject executeRequestAsJsonResult(UniRequest request) throws Exception{
		String serverResponse = executeRequestAsStringResult(request);
		if(serverResponse != null){
			JSONObject result = new JSONObject(serverResponse);
			return result;			
		}
		
		return null;
	}
	
	private static HttpTransport getHttpTransport(){		
		if(mHttpTransport == null){
			synchronized(PostRequest.class)
			{
				if(mHttpTransport == null){
					mHttpTransport = AndroidHttp.newCompatibleTransport();
				}
			}
		}
		
		return mHttpTransport;
//		return AndroidHttp.newCompatibleTransport();
	}

	/**
	 * This method is deprecated
	 * Use method {@link #executeRequestAsJsonResult(UniRequest)}
	 * or {@link #executeRequestAsStringResult(UniRequest)}
	 * @param post
	 * @return
	 */
	@Deprecated
	public static String executeRequest(HttpPost post) {
		
		if(post instanceof MyHttpPostTransfer){
			MyHttpPostTransfer myPost = (MyHttpPostTransfer) post;
			try {
				return executePostHttpRequest(myPost.getURI().toString(), 
						myPost.getHeadersAsMap(), 
						myPost.getParamsAsMap());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/*
	private static String executeRequest2(HttpPost pst){
		HttpTransport transport = getHttpTransport();
		HttpRequestFactory factory = transport.createRequestFactory();
		//HttpContent content = new Requ
		try {
			GenericUrl urlFromPost = new GenericUrl(pst.getURI());
			HttpHeaders headersFormHttpPost = getHeadersFormApache(pst.getAllHeaders());
			MultipartContent mc = new MultipartContent();
			UrlEncodedContent uec = new UrlEncodedContent(mc);
			
//			ByteArrayContent bc = new ByteArrayContent("json", array)
//			HttpContent content = null;
//			InputStreamContent contentFromPost = new InputStreamContent("application/json", pst.getEntity().getContent());
			UrlEncodedContent content = new UrlEncodedContent(getParamsFromApache(pst));
			HttpRequest request = factory.buildPostRequest(urlFromPost, content);
			request.setHeaders(headersFormHttpPost);
			com.google.api.client.http.HttpResponse response = request.execute();
			
			
			int responseCode = response.getStatusCode();
			Log.d("atf", "status code = " + responseCode);
			
			if(responseCode == 200){
				return response.parseAsString();
			}
			
		} catch (IOException e){
			error = DEFAULT_ERROR_MESSAGE;
			e.printStackTrace();
			Log.d("atf", e.getMessage());
		}
		
		return null;
	}
	*/

//	static private class httpRec implements Runnable {
//		@Override
//		public void run() {
//			try {
//				
//				HttpParams params = new BasicHttpParams();
//				//params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//				 
//				HttpConnectionParams.setTcpNoDelay(params, true);
//				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//				HttpClient client = new DefaultHttpClient(params);
//				HttpResponse r = client.execute(post);
//				int status = r.getStatusLine().getStatusCode();
//				Log.d("atf", "status code=" + status);
//				if (status == 200) {
//					HttpEntity e = r.getEntity();
//					data = EntityUtils.toString(e, "UTF-8");
//					
//					
//					if (e != null) {
//					    try {
//					        e.consumeContent();
//					    } catch (IOException ex) {
//					        Log.e("atf", "", ex);
//					    }
//					}
//					
//				}
//			} catch (ClientProtocolException e) {
//				Log.d("atf", e.getMessage());
//				error = "Sorry, try again";
//			} catch (IOException e1) {
//				error = "Sorry, try again";
//				Log.d("atf", e1.getMessage());
//			}
//
//		}
//
//	}

}
