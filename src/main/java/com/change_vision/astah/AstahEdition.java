package com.change_vision.astah;

public enum AstahEdition {
	professional,community,uml,sysml,safilia;
	
	public String getJARName(){
		switch(this){
		case sysml :
			return "astah-sys.jar";
		case professional :
			return "astah-pro.jar";
		case uml :
			return "astah-uml.jar";
		case community : 
			return "astah-community.jar";
        case safilia : 
            return "safilia.jar";
		default:
			return null;
		}
	}
}
