package com.change_vision.astah;

import static java.lang.String.format;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class PluginPathsBuilder {

  private static final String SPACE = " ";
  private ASDK sdk = new ASDK();
  private List<File> plugins = new ArrayList<File>();

  public PluginPathsBuilder(File target) {
    if(target == null) throw new IllegalArgumentException("target is null.");
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
      pluginsBuilder.append(createPluginPath(plugin));
      pluginsBuilder.append(SPACE);
    }
    String paths = pluginsBuilder.toString().trim();
    return format("-Dplugin_list=%s",paths);
  }

  private String createPluginPath(File plugin) {
     String path = FilenameUtils.separatorsToUnix(plugin.getAbsolutePath());
    if(plugin.isDirectory()){
      return format("reference:file:///%s/", path);
    } else {
      return format("file:///%s", path);
    }
  }

  private void addBundles() {
    List<File> bundles = sdk.getBundlePlugin();
    for (File bundle : bundles) {
      plugins.add(0,bundle);
    }
  }

}
