/*******************************************************************************
 * This file (Logger.java) is part of "Letty" project
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

package org.redyard.letty.logging;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 17, 2011
 */
public interface Logger {

  public Logger Message(Object message);

  public Logger Log(LogLevel level, Object message);

  public Logger Log(LogLevel level, Throwable t);

  public Logger Debug(Object message);

  public Logger Debug(Throwable t);
  
  public Logger Info(Object message);

  public Logger Info(Throwable t);
  
  public Logger Warning(Object message);

  public Logger Warning(Throwable t);
  
  public Logger Error(Object message);

  public Logger Error(Throwable t);
  
  public Logger Fatal(Object message);

  public Logger Fatal(Throwable t);

}
