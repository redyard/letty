/*******************************************************************************
 * This file (Profiler.java) is part of "Letty" project
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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 17, 2011
 */
public class Profiler {

  private static volatile Profiler instance;
  private static ConcurrentHashMap<Class<?>, Long> time_map;

  public static Profiler Instance() {
	Profiler local_instance = instance;
	if ( local_instance == null ) {
	  synchronized ( Profiler.class ) {
		local_instance = instance;
		if ( local_instance == null ) local_instance = instance = new Profiler();
	  }
	}
	return local_instance;
  }

  public static void Start() {
	Start( Profiler.class );
  }

  public static void Start(Class<?> clazz) {
	time_map.put( clazz, System.currentTimeMillis() );
  }

  public static long ProfilingTime() {
	return ProfilingTime( Profiler.class );
  }

  public static long ProfilingTime(Class<?> clazz) {
	return TimeUnit.MILLISECONDS.toSeconds( System.currentTimeMillis() - time_map.get( clazz ) );
  }

  private Profiler() {
	time_map = new ConcurrentHashMap<Class<?>, Long>();
	Start();
  }

}
