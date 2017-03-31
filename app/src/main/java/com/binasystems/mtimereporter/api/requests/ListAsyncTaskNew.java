package com.binasystems.mtimereporter.api.requests;

import android.content.Context;
import android.widget.ListView;

/**
 * 
 * @author Bostanica Ion
 * @since 2013-05-21
 * 
 */
public abstract class ListAsyncTaskNew<Result> extends BaseAsyncTask<Result>{

	protected ListView list = null;

	public ListAsyncTaskNew(Context context) {
		super(context);
	}

	public abstract void setLastItem(Object last);
}
