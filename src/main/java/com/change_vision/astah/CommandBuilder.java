package com.change_vision.astah;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

public class CommandBuilder {

	private Set<String> jvmProp = new HashSet<String>();
	private AstahEdition edition;

	public CommandBuilder(Set<String> jvmProp, AstahEdition edition) {
		if (edition == null)
			throw new IllegalArgumentException("edition is null.");
		if (jvmProp != null) {
			this.jvmProp = jvmProp;
		}
		this.edition = edition;
	}

	public List<String> build() {
		File devProp;
		try {
			devProp = File.createTempFile("dev", ".properties");
			InputStream resourceAsStream = getDevPropertiesResource();
			FileOutputStream outputStream = new FileOutputStream(devProp);
			IOUtils.copy(resourceAsStream, outputStream);
			devProp.deleteOnExit();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} catch (NullPointerException e) {
			throw new IllegalStateException(e);
		}
		List<String> commands = new ArrayList<String>();
		commands.add("java");
		for (String prop : jvmProp) {
			commands.add(prop);
		}
		commands.add("-Dplugin.switch.file=file://"
				+ devProp.getAbsolutePath());
		commands.add("-jar");
		commands.add(edition.getJARName());
		commands.add("-clean");
		return commands;
	}

	protected InputStream getDevPropertiesResource() {
		return CommandBuilder.class.getResourceAsStream("dev.properties");
	}

}
