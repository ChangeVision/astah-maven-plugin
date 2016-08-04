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
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which debug astah with developing plugins.
 *
 * @goal debug
 * @requiresProject false
 */
public class DebugMojo
    extends AbstractMojo
{
    /**
     * Location of astah* install directory.
     * @parameter expression="${safiliaPath}"
     * @required
     */
    private File installDirectory;
    
    /**
     * Edition of astah*.
     * @parameter expression="${safiliaEdition}"
     * @required
     */
    private String edition;

    
    /**
     * Location of build output directory.
     * @parameter expression="${project.build.outputDirectory}"
     * @required
     */
    private File outputDirectory;
    
    /**
     * Debug Port.
     * @parameter expression="${safilia.debug.port}"
     */
    private int port = 44000;
    
    /**
     * Arguments of launching Astah 
     * @parameter expression="${safilia.argLine}"
     */
    private String argLine;
    

    public void execute()
        throws MojoExecutionException
    {
    	AstahEdition edition;
		try {
			edition = AstahEdition.valueOf(this.edition);
		} catch (IllegalArgumentException e) {
			String message = String.format("%s is not supported.", this.edition);
			throw new MojoExecutionException(message);
		}
		File targetPlugin = getTarget();
		PluginPathsBuilder pathBuilder = new PluginPathsBuilder(targetPlugin);

    	Set<String> jvmProp = new LinkedHashSet<String>();
    	jvmProp.add("-Xdebug");
    	jvmProp.add("-Xnoagent");
    	jvmProp.add("-Djava.compiler=NONE");
    	jvmProp.add("-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=" + port);
    	jvmProp.add(pathBuilder.build());
    	if(argLine != null && argLine.isEmpty() == false){
    		jvmProp.add(argLine);
    	}

    	LaunchAstah launch = new LaunchAstah(installDirectory,edition, jvmProp,getLog());
    	launch.execute();

    }
    
	protected File getTarget() {
		return outputDirectory;
	}
}
