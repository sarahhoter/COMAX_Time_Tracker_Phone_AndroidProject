package com.binasystems.mtimereporter.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import android.content.Context;

public class Formatter {

	public static final SimpleDateFormat DAY_FORMATTER = new SimpleDateFormat("dd/MM/yyyy EEEE");
	public static final SimpleDateFormat WEEK_FORMATTER = new SimpleDateFormat("w");
	public static final SimpleDateFormat MONTH_FORMATTER = new SimpleDateFormat("MMM, yyyy");
	public static final SimpleDateFormat YEAR_FORMATTER = new SimpleDateFormat("yyyy");

	NumberFormat integerFormat;
	NumberFormat numberFormat;	
	NumberFormat percentFormat;
	
	public static Formatter getInstance(Context context){
		return new Formatter(context);
	}
	
	public Formatter(Context context){
		numberFormat = new DecimalFormat();
		numberFormat.setMaximumFractionDigits(0);

		integerFormat = new DecimalFormat();
		integerFormat.setMaximumFractionDigits(2);
		integerFormat.setMinimumFractionDigits(2);
		percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumFractionDigits(2);
		percentFormat.setMinimumFractionDigits(2);
	}
	
	public String formatValue(Double value){		
		if(value == null || value == 0)
			return "";

		return integerFormat.format(value);
	}
	public String formatValueNum(Double value){
		if(value == null )
			return "0";

		return numberFormat.format(value);
	}
	public String formatValueTowDig(Double value){
		if(value == null || value == 0.0)
			return "";
		if(value%10==0)
			return numberFormat.format(value);
		else return integerFormat.format(value);
	}

	public String formatPercent(Double value){		
		if(value == null || value == 0)
			return "";

		return percentFormat.format(value);
	}

	public String formatValue(Long value){
		if(value == null || value == 0)
			return "";

		return integerFormat.format(value);
	}
	public String formatValueNoDigit(Long value){
		if(value == null || value == 0)
			return "";

		return numberFormat.format(value);
	}
	public String formatValueMaxTowDigit(Double value){
		if(value == null || value == 0)
			return "";

		return integerFormat.format(value);
	}
	public String formatValueD(Double value){
		if(value == null || value == 0)
			return "";
		value*=100;
		return String.format("%1$,.2f", value)+"%";
	}

	public String formatValue(String value){
		if(value == null){
			return "";
		}
		
		return value.trim();
	}
}
