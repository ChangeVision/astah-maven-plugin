package com.change_vision.astah;

public enum AstahEdition {
	safilia;
	
	public String getJARName(){
		switch(this){
        case safilia : 
            return "safilia.jar";
		default:
			return null;
		}
	}
}
