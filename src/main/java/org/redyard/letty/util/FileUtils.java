/*******************************************************************************
 * This file (FileUtils.java) is part of "Letty" project
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

package org.redyard.letty.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 17, 2011
 */
public class FileUtils {

  public static final String PATH_SEPARATOR = System.getProperty( "file.separator" );

  public static List<File> FileList(File directory, final String[] extensions, boolean recursive) {
	List<File> files = new LinkedList<File>();
	if ( !directory.isDirectory() ) throw new IllegalArgumentException( "Invalid directory" );
	File[] dir_files;
	if ( extensions.length > 0 ) {
	  dir_files = directory.listFiles( new FilenameFilter() {
		public boolean accept(File dir, String name) {
		  if ( new File( dir.getAbsolutePath() + PATH_SEPARATOR + name ).isDirectory() ) return true;
		  for ( String ext : extensions ) {
			if ( name.endsWith( ext ) ) return true;
		  }
		  return false;
		}
	  } );
	} else {
	  dir_files = directory.listFiles();
	}
	for ( File file : dir_files ) {
	  if ( file.isDirectory() && recursive )
		files.addAll( FileList( file, extensions, recursive ) );
	  else
		files.add( file );
	}
	return files;
  }

  public static List<File> FileList(String directory, String[] extensions, boolean recursive) {
	return FileList( new File( directory ), extensions, recursive );
  }

  private FileUtils() {
  }

}
