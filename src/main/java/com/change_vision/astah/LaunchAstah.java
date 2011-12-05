package com.change_vision.astah;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

public class LaunchAstah {

	private File installDirectory;
	private Set<String> jvmProp;
	private Log log;
	private AstahEdition edition;

	public LaunchAstah(File installDirectory, AstahEdition edition,
			Set<String> jvmProp, Log log) {
		this.installDirectory = installDirectory;
		this.edition = edition;
		this.jvmProp = jvmProp;
		this.log = log;
	}

	public void execute() throws MojoExecutionException {

		if (!installDirectory.exists()) {
			throw new MojoExecutionException(
					"Please check astah install folder.");
		}

		File jar = new File(installDirectory, edition.getJARName());
		if (!jar.exists()) {
			throw new MojoExecutionException(
					"Please check astah* install folder.");
		}
		CommandBuilder commandBuilder = new CommandBuilder(jvmProp, edition);
		log.info(new ASDK().getHome());
		List<String> commands = commandBuilder.build();
		for (String command : commands) {
			log.info(command);
		}
		ProcessBuilder builder = new ProcessBuilder(commands);
		builder.directory(installDirectory);
		try {
			log.info("Launching astah*...");
			builder.start();
		} catch (IOException e) {
			throw new MojoExecutionException("Can't run astah.", e);
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
	}

}
