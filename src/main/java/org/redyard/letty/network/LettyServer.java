/*******************************************************************************
 * This file (LettyServer.java) is part of "Letty" project
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

package org.redyard.letty.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.redyard.letty.config.ServerConfig;
import org.redyard.letty.logging.LoggingService;
import org.redyard.letty.network.dispatcher.AcceptDispatcher;
import org.redyard.letty.network.dispatcher.Dispatcher;
import org.redyard.letty.network.dispatcher.IODispatcher;
import org.redyard.letty.network.worker.Acceptor;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 16, 2011
 */
public class LettyServer {

  private static final LoggingService LOG = LoggingService.Instance();

  private final List<SelectionKey> server_key_list = new ArrayList<SelectionKey>();

  private final ServerConfig config;
  private final ConnectionFactory factory;
  private final ExecutorService executor;

  private Dispatcher accept_dispatcher;
  private Dispatcher[] io_dispatchers;
  private int current_io_dispatcher;

  public LettyServer(ServerConfig config, ConnectionFactory factory) {
	this.config = config;
	this.factory = factory;
	int pool = config.ExecutorPoolSize();
	if ( pool > 0 ) {
	  this.executor = Executors.newFixedThreadPool( pool );
	  LOG.Info( "Executor initialized with fixed thread pool (" + pool + " threads)" );
	} else if ( pool < 0 ) {
	  this.executor = Executors.newCachedThreadPool();
	  LOG.Info( "Executor initialized with cached thread pool" );
	} else {
	  this.executor = Executors.newSingleThreadExecutor();
	  LOG.Info( "Executor initialized with single thread pool" );
	}
  }

  public LettyServer Listen() {
	InitDispatchers();
	try {
	  ServerSocketChannel server_channel = ServerSocketChannel.open();
	  server_channel.configureBlocking( false );
	  // Bind the server socket to the specified address and port
	  InetSocketAddress isa;
	  if ( "*".equals( config.Host() ) ) {
		isa = new InetSocketAddress( config.Port() );
	  } else {
		isa = new InetSocketAddress( config.Host(), config.Port() );
	  }
	  server_channel.socket().bind( isa );
	  LOG.Info( config.ServerName() + " listening on " + isa );
	  server_key_list.add( AcceptDispatcher().Register( server_channel, SelectionKey.OP_ACCEPT, new Acceptor( factory, this ) ) );
	} catch ( IOException e ) {
	  LOG.Fatal( config.ServerName() + " initialization fatal error" );
	  throw new RuntimeException( e );
	}
	return this;
  }

  public Dispatcher AcceptDispatcher() {
	return accept_dispatcher;
  }

  public Dispatcher IODispatcher() {
	if ( io_dispatchers == null ) return AcceptDispatcher();
	if ( io_dispatchers.length == 1 ) return io_dispatchers[0];
	if ( current_io_dispatcher > io_dispatchers.length ) current_io_dispatcher = 0;
	return io_dispatchers[current_io_dispatcher++];
  }

  private void InitDispatchers() {
	InitAcceptDispatcher();
	executor.submit( AcceptDispatcher() );
	if ( config.IODispatchersPoolSize() > 0 ) {
	  InitIODispatchers( config.IODispatchersPoolSize() );
	  executor.submit( IODispatcher() );
	}
  }

  private void InitAcceptDispatcher() {
	try {
	  accept_dispatcher = new AcceptDispatcher( "AcceptDispatcher" );
	  LOG.Info( accept_dispatcher + ": ready" );
	} catch ( IOException e ) {
	  LOG.Error( "Failed to initialize AcceptDispatcher" ).Error( e );
	  throw new RuntimeException( "At least one AcceptDispatcher must be initialized" );
	}
  }

  private void InitIODispatchers(int pool_size) {
	int iod_count = 0;
	try {
	  io_dispatchers = new Dispatcher[pool_size];
	  for ( int i = 0; i < pool_size; ++i ) {
		io_dispatchers[i] = new IODispatcher( "IODispatcher-" + i );
		++iod_count;
	  }
	} catch ( IOException e ) {
	  LOG.Error( "Failed to initialize IODispatcher" ).Error( e );
	}
	LOG.Info( "IO Dispatchers: " + iod_count );
  }

}
