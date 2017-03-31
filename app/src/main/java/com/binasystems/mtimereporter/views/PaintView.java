package com.binasystems.mtimereporter.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.binasystems.mtimereporter.TimeTrackerApplication;

public class PaintView extends View {

	private Paint paint = new Paint();
	private Path path = new Path();
	private Bitmap sign=null;

	public PaintView(Context context, AttributeSet attrs, Bitmap sign) {
		super(context, attrs);
		this.sign=sign;
		
		paint.setAntiAlias(true);
		paint.setStrokeWidth(4f);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		
		
		}

	@Override
	protected void onDraw(Canvas canvas) {

		if(sign!=null){

			canvas.drawBitmap(sign, 0, 0,paint);		
		}
		canvas.drawPath(path, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TimeTrackerApplication.getInstance().setSign(true);
		float eventX = event.getX();
		float eventY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(eventX, eventY);
			return true;
		case MotionEvent.ACTION_MOVE:
			path.lineTo(eventX, eventY);
			break;
		case MotionEvent.ACTION_UP:
			// nothing to do
			break;
		default:
			return false;
		}

		// Schedules a repaint.
		invalidate();
		return true;
	}
}