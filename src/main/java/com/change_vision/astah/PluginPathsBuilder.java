package com.change_vision.astah;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginPathsBuilder {
	
	private ASDK sdk = new ASDK();
	private List<File> plugins = new ArrayList<File>();

	public PluginPathsBuilder(File target) {
		if(target == null) throw new IllegalArgumentException("target is null.");
		if(target.isDirectory()) throw new IllegalArgumentException("target needs file.");
		if(target.getName().endsWith(".jar") == false)
			throw new IllegalArgumentException("target needs plugin file.");
		plugins.add(target);
	}
	
	PluginPathsBuilder(ASDK sdk,File target){
		this(target);
		this.sdk = sdk;
	}

	public String build() {

		addBundles();

		StringBuilder pluginsBuilder = new StringBuilder();
		for (File plugin : plugins) {
			pluginsBuilder.append("file://");
			pluginsBuilder.append(plugin.getAbsolutePath());
			pluginsBuilder.append(" ");
		}
		String pluginList = pluginsBuilder.toString().trim();

		return "-Dplugin_list=" + pluginList + "" ;
	}
	
	private void addBundles() {
		List<File> bundles = sdk.getBundlePlugin();
		for (File bundle : bundles) {
			plugins.add(bundle);
		}
	}

	

}
