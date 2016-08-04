package com.change_vision.astah;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ASDK {
	
	private Map<String,String> env = System.getenv();
    static final String DEV_BUNDLES_DIR = "bundles";
	static final String ENVIRONMENT_VALUE_OF_ASDK_HOME = "SSDK_HOME";

	public ASDK(){}
	
	ASDK(Map<String,String> env){
		if(env == null){
			throw new IllegalArgumentException("env is null.");
		}
		this.env = env;
	}
	
	public List<File> getBundlePlugin(){
		ArrayList<File> result = new ArrayList<File>();
		File home = new File(getHome(),DEV_BUNDLES_DIR);
		File[] files = home.listFiles();
		if(files == null) return result;
		for (File file : files) {
			if(file.getName().endsWith(".jar")){
				result.add(file);
			}
		}
		return result;
	}

	public String getHome() {
		return env.get(ENVIRONMENT_VALUE_OF_ASDK_HOME);
	}

}
