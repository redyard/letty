/*******************************************************************************
 * This file (ServiceProvider.java) is part of "Letty" project
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

package org.redyard.letty.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.redyard.letty.config.DefaultServiceProviderConfig;
import org.redyard.letty.config.ServiceProviderConfig;
import org.redyard.letty.logging.LoggingService;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 17, 2011
 */
public class ServiceProvider {

  private static final LoggingService LOG = LoggingService.Instance();

  private static boolean initialized = false;
  private static ExecutorService executor;
  private static ScheduledExecutorService scheduler;

  public static synchronized void Init() {
	Init( new DefaultServiceProviderConfig() );
  }

  public static synchronized void Init(ServiceProviderConfig config) {
	LOG.StartSection( "Service Provider" );
	if ( !IsInitialized() ) {
	  int pool_size = config.ExecutorPoolSize();
	  if ( pool_size > 1 ) {
		executor = Executors.newFixedThreadPool( pool_size );
		LOG.Info( "Executor initialized with fixed thread pool [" + pool_size + " threads]" );
	  } else {
		executor = Executors.newSingleThreadExecutor();
		LOG.Info( "Executor initialized with single thread pool" );
	  }
	  pool_size = config.SchedulerPoolSize();
	  if ( pool_size > 1 ) {
		scheduler = Executors.newScheduledThreadPool( pool_size );
		LOG.Info( "Scheduler initialized with fixed thread pool [" + pool_size + " threads]" );
	  } else {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		LOG.Info( "Scheduler initialized with single thread pool" );
	  }
	  initialized = true;
	} else {
	  // FIXME change to LogLevel.ERROR later
	  LOG.Info( "ServiceProvider already initialized" );
	}
	LOG.EndSection();
  }

  public static synchronized void Execute(Runnable service) {
	if ( IsInitialized( true ) ) executor.submit( service );
  }

  public static synchronized void Provide(Runnable service, long delay) {
	if ( IsInitialized( true ) ) scheduler.schedule( service, delay, TimeUnit.SECONDS );
  }

  public static synchronized void ProvideNow(Runnable service, long delay, TimeUnit unit) {
	if ( IsInitialized( true ) ) scheduler.schedule( service, delay, unit );
  }

  public static synchronized void Schedule(Runnable service, long initial_delay, long delay, TimeUnit unit) {
	if ( IsInitialized( true ) ) scheduler.scheduleWithFixedDelay( service, initial_delay, delay, unit );
  }

  public static synchronized void ScheduleNow(Runnable service, long delay, TimeUnit unit) {
	if ( IsInitialized( true ) ) scheduler.scheduleWithFixedDelay( service, 0, delay, unit );
  }

  public static synchronized void ScheduleNow(Runnable service, long delay) {
	if ( IsInitialized( true ) ) scheduler.scheduleWithFixedDelay( service, 0, delay, TimeUnit.SECONDS );
  }

  private static boolean IsInitialized() {
	return initialized;
  }

  private static boolean IsInitialized(boolean with_warning) {
	// FIXME change to LogLevel.ERROR
	if ( !initialized ) LOG.Info( "ServiceProvider is currently not initialized" );
	return initialized;
  }

  private ServiceProvider() {
  }
}
