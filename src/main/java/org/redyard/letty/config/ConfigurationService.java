/*******************************************************************************
 * This file (ConfigurationService.java) is part of "Letty" project
 * 
 * Copyright (c) 2010-2011 RedYard Inc.
 *  
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package org.redyard.letty.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.redyard.letty.logging.LoggingService;
import org.redyard.letty.util.FileUtils;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 17, 2011
 */
public class ConfigurationService {

  private static final LoggingService LOG = LoggingService.Instance();

  private static volatile ConfigurationService instance;

  private final HashMap<String, Properties> configs = new HashMap<String, Properties>();

  public static ConfigurationService Instance() {
	ConfigurationService local_instance = instance;
	if ( local_instance == null ) {
	  synchronized ( ConfigurationService.class ) {
		local_instance = instance;
		if ( local_instance == null ) {
		  instance = local_instance = new ConfigurationService();
		}
	  }
	}
	return local_instance;
  }

  public static synchronized ConfigurationService Init() {
	return Init( new File( "config" ), true );
  }

  public static synchronized ConfigurationService Init(String directory) {
	return Init( new File( directory ) );
  }

  public static synchronized ConfigurationService Init(File directory) {
	return Init( directory, true );
  }

  public static synchronized ConfigurationService Init(String directory, boolean recursive) {
	return Init( new File( directory ), recursive );
  }

  public static synchronized ConfigurationService Init(File directory, boolean recursive) {
	LOG.StartSection( "Configuration Service" );
	ConfigurationService cs = Instance();
	int loaded_configs = 0;
	try {
	  loaded_configs = cs.LoadAllFromDirectory( directory, recursive );
	} catch ( Exception e ) {
	  LOG.Info( e );
	}
	LOG.Info( "Total: loaded " + loaded_configs + " configs" ).EndSection();
	return cs;
  }

  public int LoadAllFromDirectory(String directory, boolean recursive) {
	return LoadAllFromDirectory( new File( directory ), recursive );
  }

  public int LoadAllFromDirectory(File directory, boolean recursive) {
	List<File> files = FileUtils.FileList( directory, new String[] { "properties" }, recursive );
	int loaded_configs = 0;
	for ( File file : files ) {
	  try {
		LoadConfig( file );
		++loaded_configs;
		LOG.Info( "Loaded config: " + file.getPath() );
	  } catch ( IOException e ) {
		LOG.Info( e );
	  }
	}
	return loaded_configs;
  }

  public Properties LoadConfig(String config_file) throws IOException {
	return LoadConfig( new File( config_file ) );
  }

  public Properties LoadConfig(File config_file) throws IOException {
	FileInputStream fis = new FileInputStream( config_file );
	Properties config = new Properties();
	try {
	  config.load( fis );
	  configs.put( config_file.getPath(), config );
	  return config;
	} finally {
	  fis.close();
	}
  }

  public Properties Config(String path) {
	if ( !configs.containsKey( path ) ) {
	  throw new IllegalArgumentException( "Config file [ " + path + " ] is not currently loaded" );
	}
	return configs.get( path );
  }

  private ConfigurationService() {
  }
}
