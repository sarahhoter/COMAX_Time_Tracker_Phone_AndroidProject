package com.binasystems.mtimereporter.adapter.menu;

public class MenuItem {
	public long id;
	//public int imageRes;
	public int titleRes;
	public String titleString=null;

	
	public MenuItem(){
		
	}
	


	public MenuItem(long id, int title,String titleString) {
		super();
		this.id = id;
	//	this.imageRes = imageRes;
		this.titleRes = title;
		this.titleString = titleString;


	}	
}
