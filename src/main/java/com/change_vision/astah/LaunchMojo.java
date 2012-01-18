package com.change_vision.astah;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which launch astah with developing plugins.
 * 
 * @goal launch
 * @requiresProject false
 */
public class LaunchMojo extends AbstractMojo {

	/**
	 * Location of astah* install directory.
	 * 
	 * @parameter expression="${astahPath}"
	 * @required
	 */
	private File installDirectory;

	/**
	 * Edition of astah*.
	 * 
	 * @parameter expression="${astahEdition}"
	 * @required
	 */
	private String edition;

	/**
	 * Location of build output directory.
	 * 
	 * @parameter expression="${project.build.outputDirectory}"
	 * @required
	 */
	private File outputDirectory;

	/**
	 * Arguments of launching Astah
	 * 
	 * @parameter expression="${astah.argLine}"
	 */
	private String argLine;

	public void execute() throws MojoExecutionException {
		AstahEdition edition;
		try {
			edition = AstahEdition.valueOf(this.edition);
		} catch (IllegalArgumentException e) {
			String message = String
					.format("%s is not supported.", this.edition);
			throw new MojoExecutionException(message);
		}
		File targetPlugin = getTarget();
		PluginPathsBuilder pathBuilder = new PluginPathsBuilder(targetPlugin);
		Set<String> jvmProp = new HashSet<String>();
		jvmProp.add(pathBuilder.build());
		if (argLine != null && argLine.isEmpty() == false) {
			jvmProp.add(argLine);
		}
		LaunchAstah launch = new LaunchAstah(installDirectory, edition,
				jvmProp, getLog());
		launch.execute();

	}

	protected File getTarget() {
		return outputDirectory;
	}

}
