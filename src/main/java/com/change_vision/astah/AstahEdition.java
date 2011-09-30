package com.change_vision.astah;

public enum AstahEdition {
	professional,community,uml,think;
	
	public String getJARName(){
		switch(this){
		case professional :
			return "astah-pro.jar";
		case uml :
			return "astah-uml.jar";
		case community : 
			return "astah-com.jar";
		case think :
			return "astah-think.jar";
		default:
			return null;
		}
	}
}
