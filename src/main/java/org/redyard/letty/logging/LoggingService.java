/*******************************************************************************
 * This file (LoggingService.java) is part of "Letty" project
 * 
 * Copyright (c) 2010-2011 RedYard Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package org.redyard.letty.logging;

import org.redyard.letty.util.StringUtils;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 16, 2011
 */
public class LoggingService implements Logger {

  private static volatile LoggingService instance;
  private static final int SECTION_LENGTH = 80;
  private static String SECTION_SYMBOL = "=";
  private ConsoleLogger logger; // FIXME add cascade logger support

  public static LoggingService Instance() {
	LoggingService local_instance = instance;
	if ( local_instance == null ) {
	  synchronized ( LoggingService.class ) {
		local_instance = instance;
		if ( local_instance == null ) local_instance = instance = new LoggingService();
	  }
	}
	return local_instance;
  }

  public static LoggingService Init() {
	LoggingService ls = Instance();
	ls.StartSection( "Logging Service" ).Info( "Logging Service initialized" ).EndSection();
	return ls;
  }

  public LoggingService StartSection(String section_name) {
	int length = section_name.length();
	int header_length = SECTION_LENGTH - length - 6;
	int half_header = header_length / 2;
	int i;
	StringBuilder header = new StringBuilder( SECTION_LENGTH );
	for ( i = 0; i < half_header; ++i ) {
	  header.append( SECTION_SYMBOL );
	}
	header.append( " [ " );
	header.append( section_name );
	header.append( " ] " );
	for ( i = 0; i < half_header; ++i ) {
	  header.append( SECTION_SYMBOL );
	}
	int fix;
	for ( i = 0, fix = (SECTION_LENGTH - header.length()); i < fix; ++i ) {
	  header.append( SECTION_SYMBOL );
	}
	Message( header );
	return this;
  }

  public LoggingService EndSection() {
	StringBuilder buffer = new StringBuilder( SECTION_LENGTH );
	for ( int i = 0; i < SECTION_LENGTH; ++i ) {
	  buffer.append( SECTION_SYMBOL );
	}
	Message( buffer.append( StringUtils.EOL ) );
	return this;
  }

  @Override
  public LoggingService Message(Object message) {
	logger.Message( message );
	return this;
  }

  @Override
  public LoggingService Log(LogLevel level, Object message) {
	logger.Log( level, message );
	return this;
  }

  @Override
  public LoggingService Log(LogLevel level, Throwable t) {
	logger.Log( level, t );
	return this;
  }

  @Override
  public LoggingService Debug(Object message) {
	logger.Debug( message );
	return this;
  }

  @Override
  public LoggingService Debug(Throwable t) {
	logger.Debug( t );
	return this;
  }

  @Override
  public LoggingService Info(Object message) {
	logger.Info( message );
	return this;
  }

  @Override
  public LoggingService Info(Throwable t) {
	logger.Info( t );
	return this;
  }

  @Override
  public LoggingService Warning(Object message) {
	logger.Warning( message );
	return this;
  }

  @Override
  public LoggingService Warning(Throwable t) {
	logger.Warning( t );
	return this;
  }

  @Override
  public LoggingService Error(Object message) {
	logger.Error( message );
	return this;
  }

  @Override
  public LoggingService Error(Throwable t) {
	logger.Error( t );
	return this;
  }

  @Override
  public LoggingService Fatal(Object message) {
	logger.Fatal( message );
	return this;
  }

  @Override
  public LoggingService Fatal(Throwable t) {
	logger.Fatal( t );
	return this;
  }

  private LoggingService() {
	logger = new ConsoleLogger();
  }

}
