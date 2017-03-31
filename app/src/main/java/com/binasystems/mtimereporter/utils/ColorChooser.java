package com.binasystems.mtimereporter.utils;


import com.binasystems.mtimereporter.R;

public class ColorChooser {

	/*
	 * Constants
	 */
	public static final short GREEN = 1;
	public static final short GRAY = 2;

	/*
	 * Current color scheme
	 */
	public static short mode = GREEN;

	public static int getColorDark() {

		if (mode == GREEN)
			return R.drawable.list_background_selector_green;
		else
			return R.drawable.list_background_selector_gray;

	}

	public static int getColoLight() {

		if (mode == GREEN)
			return R.drawable.list_background_selector_white;
		else
			return R.drawable.list_background_selector_white_gray;

	}

	public static int getTransparent() {

		if (mode == GREEN)
			return R.drawable.list_background_selector_transparent_green;
		else
			return R.drawable.list_background_selector_transparent_gray;

	}

	public static int getColorDarkSimpleSelect() {

		return R.drawable.list_background_selector_gray;

	}

	public static int getColorLightSimpleSelect() {

		return R.drawable.list_background_selector_white_gray;

	}

	public static int getColorLightDark() {

		return R.drawable.list_background_selector_light_gray;

	}

	public static int getColorLightWhite() {
		
		return R.drawable.list_background_selector_real_white;
		
	}
	
}
