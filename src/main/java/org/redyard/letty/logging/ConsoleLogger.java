/*******************************************************************************
 * This file (ConsoleLogger.java) is part of "Letty" project
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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 17, 2011
 */
public class ConsoleLogger implements Logger {

  private final SimpleDateFormat formatter;

  public ConsoleLogger() {
	formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
  }

  @Override
  public Logger Message(Object message) {
	if ( message != null ) {
	  System.out.println( message );
	}
	return this;
  }

  @Override
  public Logger Log(LogLevel level, Object message) {
	Message( "[" + level + "] " + formatter.format( new Date() ) + " - " + message );
	return this;
  }

  @Override
  public Logger Log(LogLevel level, Throwable t) {
	if ( t != null ) t.printStackTrace();
	return this;
  }

  @Override
  public Logger Debug(Object message) {
	Log( LogLevel.DEBUG, message );
	return this;
  }

  @Override
  public Logger Debug(Throwable t) {
	Log( LogLevel.DEBUG, t );
	return this;
  }

  @Override
  public Logger Info(Object message) {
	Log( LogLevel.INFO, message );
	return this;
  }

  @Override
  public Logger Info(Throwable t) {
	Log( LogLevel.INFO, t );
	return this;
  }

  @Override
  public Logger Warning(Object message) {
	Log( LogLevel.WARNING, message );
	return this;
  }

  @Override
  public Logger Warning(Throwable t) {
	Log( LogLevel.WARNING, t );
	return this;
  }

  @Override
  public Logger Error(Object message) {
	Log( LogLevel.ERROR, message );
	return this;
  }

  @Override
  public Logger Error(Throwable t) {
	Log( LogLevel.ERROR, t );
	return this;
  }

  @Override
  public Logger Fatal(Object message) {
	Log( LogLevel.FATAL, message );
	return this;
  }

  @Override
  public Logger Fatal(Throwable t) {
	Log( LogLevel.FATAL, t );
	return this;
  }

}
