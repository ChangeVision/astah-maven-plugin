package com.change_vision.astah;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

public class LaunchAstah {
	
	private File installDirectory;
	private Set<String> jvmProp;
	private Log log;
	private AstahEdition edition;

	public LaunchAstah(File installDirectory,AstahEdition edition, Set<String> jvmProp, Log log){
		this.installDirectory = installDirectory;
		this.edition = edition;
		this.jvmProp = jvmProp;
		this.log = log;
	}
	
	public void execute() throws MojoExecutionException{

        if ( !installDirectory.exists() ) {
        	throw new MojoExecutionException( "Please check astah install folder.");
        }
        
        File jar = new File(installDirectory,edition.getJARName());
        if ( !jar.exists() ) {
        	throw new MojoExecutionException( "Please check astah* install folder.");
        }
        File devProp;
        try {
			devProp = File.createTempFile("dev", ".properties");
			InputStream resourceAsStream = LaunchMojo.class.getResourceAsStream("dev.properties");
			IOUtils.copy(resourceAsStream, new FileOutputStream(devProp));
		} catch (IOException e1) {
			throw new MojoExecutionException("IOException is occurred.",e1);
		}
		List<String> commands = new ArrayList<String>();
        commands.add("java");
        for (String prop : jvmProp) {
			commands.add(prop);
		}
        commands.add("-Dplugin.switch.file=file:///" + devProp.getAbsolutePath());
        commands.add("-jar");
        commands.add(edition.getJARName());
        commands.add("-clean");
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.directory(installDirectory);
        try {
        	log.info("Launching astah*...");
			builder.start();
		} catch (IOException e) {
			throw new MojoExecutionException("Can't run astah.",e);
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		devProp.deleteOnExit();
	}

}
