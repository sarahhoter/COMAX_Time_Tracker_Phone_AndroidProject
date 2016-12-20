package com.binasystems.mtimereporter.api.requests;

public class ServerChooser {

	public static final short MOLDOVA = 0;
	public static final short ISRAEL = 1;

	/*
	 * Current color scheme
	 */
	public static short mode = ISRAEL;

	public static String url_moldova = "http://iphonews.binasystems.com/";
	public static String url_israel = "http://iphonews.comax.co.il/";

	public static String getUrl() {
		
		if(mode == MOLDOVA )
			return url_moldova;

		return url_israel;
	}
	
}
