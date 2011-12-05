package com.change_vision.astah;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ASDKTest {
	
	private ASDK sdk;
	private Map<String, String> env;
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void before() throws Exception {
		env = new HashMap<String, String>();
		sdk = new ASDK(env);
		env.put(ASDK.ENVIRONMENT_VALUE_OF_ASDK_HOME, folder.getRoot().getAbsolutePath());
	}

	@Test
	public void ASDK_HOMEが取得できること() {
		String home = sdk.getHome();
		assertThat(home,is(folder.getRoot().getAbsolutePath()));
	}
	
	@Test
	public void bundlesがないとき例外が起きないこと() throws Exception {
		List<File> bundlePlugin = sdk.getBundlePlugin();
		assertThat(bundlePlugin,is(notNullValue()));
		assertThat(bundlePlugin.size(),is(0));
	}
	
	@Test
	public void bundlesがあるときでもプラグインがないときに例外が起きないこと() throws Exception {
		folder.newFolder(ASDK.DEV_BUNDLES_DIR);
		List<File> bundlePlugin = sdk.getBundlePlugin();
		assertThat(bundlePlugin,is(notNullValue()));
		assertThat(bundlePlugin.size(),is(0));
	}
	
	@Test
	public void bundlesがファイルであるときでも例外が起きないこと() throws Exception {
		folder.newFile(ASDK.DEV_BUNDLES_DIR);
		List<File> bundlePlugin = sdk.getBundlePlugin();
		assertThat(bundlePlugin,is(notNullValue()));
		assertThat(bundlePlugin.size(),is(0));
	}
	
	@Test
	public void bundlesの下に一つプラグインがある時に１つ返されること() throws Exception {
		File bundles = createBundlesDir();
		File plugin1 = new File(bundles,"plugin1.jar");
		plugin1.createNewFile();
		List<File> bundlePlugin = sdk.getBundlePlugin();
		assertThat(bundlePlugin,is(notNullValue()));
		assertThat(bundlePlugin.size(),is(1));
	}

	@Test
	public void bundlesの下に2つプラグインがる時に2つ返されること() throws Exception {
		File bundles = createBundlesDir();
		File plugin1 = new File(bundles,"plugin1.jar");
		plugin1.createNewFile();
		File plugin2 = new File(bundles,"plugin2.jar");
		plugin2.createNewFile();
		List<File> bundlePlugin = sdk.getBundlePlugin();
		assertThat(bundlePlugin,is(notNullValue()));
		assertThat(bundlePlugin.size(),is(2));
	}

	@Test
	public void bundlesの下にプラグイン以外があっても正しく返されること() throws Exception {
		File bundles = createBundlesDir();
		File plugin1 = new File(bundles,"plugin1.jar");
		plugin1.createNewFile();
		File txt = new File(bundles,"README.txt");
		txt.createNewFile();
		List<File> bundlePlugin = sdk.getBundlePlugin();
		assertThat(bundlePlugin,is(notNullValue()));
		assertThat(bundlePlugin.size(),is(1));
	}

	private File createBundlesDir() {
		File bundles = folder.newFolder(ASDK.DEV_BUNDLES_DIR);
		return bundles;
	}
	
}
