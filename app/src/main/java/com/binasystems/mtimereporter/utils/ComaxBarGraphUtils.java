package com.binasystems.mtimereporter.utils;

import android.graphics.Color;
import android.graphics.Paint.Align;


import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

public class ComaxBarGraphUtils {

	public static XYMultipleSeriesRenderer buildBarRenderer(int[] colors, int bkgndColor) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setBarSpacing(1);

		renderer.setMarginsColor(bkgndColor);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);

		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(bkgndColor);

		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setChartValuesSpacing(5);
			r.setChartValuesTextSize(14);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	public static XYMultipleSeriesDataset buildBarDataset(String[] titles,
                                                          List<double[]> values) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}

	public static void setChartSettings(XYMultipleSeriesRenderer renderer,
										String title, String xTitle, String yTitle, double xMin,
										double xMax, double yMin, double yMax, int axesColor,
										int labelsColor) {
		//renderer.setChartTitle(title);
		renderer.setYLabelsAlign(Align.RIGHT);
//		renderer.setXTitle(xTitle);
//		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setMargins(new int[] { 10, 65, 10, 15 });
		renderer.setLabelsColor(labelsColor);
	}
}
