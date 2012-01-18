package com.change_vision.astah;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.io.File;
import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PluginPathsBuilderTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test(expected=IllegalArgumentException.class)
	public void targetがnullの時にIAEが投げられること() {
		new PluginPathsBuilder(null);
	}
		
	@Test
	public void bundleにプラグインがない場合でもパスを作成できること() throws Exception {
		PluginPathsBuilder builder = new PluginPathsBuilder(folder.newFile("plugin.jar"));
		String path = builder.build();
		assertThat(path,is(startsWith("-Dplugin_list")));
	}
	
	@Test
	public void targetがディレクトリの時はreferenceを使用すること() throws Exception {
		PluginPathsBuilder builder = new PluginPathsBuilder(folder.newFolder("target","classes"));
		String path = builder.build();
		assertThat(path,is(startsWith("-Dplugin_list")));
		assertThat(path,is(containsString("reference:")));
		assertThat(path,is(endsWith("/")));
	}
	
	@Test
	public void bundleにプラグインがある場合でもパスを作成できること() throws Exception {
		HashMap<String, String> env = new HashMap<String, String>();
		File bundles = folder.newFolder(ASDK.DEV_BUNDLES_DIR);
		File bundle = new File(bundles,"bundle.jar");
		bundle.createNewFile();
		env.put(ASDK.ENVIRONMENT_VALUE_OF_ASDK_HOME, folder.getRoot().getAbsolutePath());

		ASDK sdk = new ASDK(env);
		PluginPathsBuilder builder = new PluginPathsBuilder(sdk,folder.newFile("plugin.jar"));
		String path = builder.build();
		assertThat(path,is(containsString("bundle.jar")));
	}
	
	@Test
	public void パスの区切り文字はスラッシュであること() throws Exception {
		HashMap<String, String> env = new HashMap<String, String>();
		File bundles = folder.newFolder(ASDK.DEV_BUNDLES_DIR);
		File bundle = new File(bundles,"bundle.jar");
		bundle.createNewFile();
		env.put(ASDK.ENVIRONMENT_VALUE_OF_ASDK_HOME, folder.getRoot().getAbsolutePath());

		ASDK sdk = new ASDK(env);
		PluginPathsBuilder builder = new PluginPathsBuilder(sdk,folder.newFile("plugin.jar"));
		String path = builder.build();
		assertThat(path,is(not(containsString("\\"))));
	}

}
