/*******************************************************************************
 * This file (Dispatcher.java) is part of "Letty" project
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

package org.redyard.letty.network.dispatcher;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;

import org.redyard.letty.logging.LoggingService;
import org.redyard.letty.network.Connection;
import org.redyard.letty.network.worker.Acceptor;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 17, 2011
 */
public abstract class Dispatcher implements Runnable {

  private static final LoggingService LOG = LoggingService.Instance();

  private final Object lock;
  private final Selector selector;
  private final String name;

  public Dispatcher(String name) throws IOException {
	this.name = name;
	lock = new Object();
	selector = SelectorProvider.provider().openSelector();
  }

  public abstract void Dispatch() throws IOException;

  public String Name() {
	return name;
  }

  public final Selector Selector() {
	return selector;
  }

  public final SelectionKey Register(SelectableChannel channel, int ops, Acceptor acceptor) throws ClosedChannelException {
	synchronized ( lock ) {
	  Selector().wakeup();
	  return channel.register( Selector(), ops, acceptor );
	}
  }

  public final void Register(SelectableChannel channel, int ops, Connection connection) throws ClosedChannelException {
	synchronized ( lock ) {
	  Selector().wakeup();
	  connection.SetKey( channel.register( Selector(), ops, connection ) );
	}
  }

  public final void Accept(SelectionKey key) {
	Acceptor acceptor = (Acceptor) key.attachment();
	if ( acceptor == null ) return;
	try {
	  acceptor.Accept( key );
	} catch ( IOException e ) {
	  LOG.Warning( "Error while accepting connection" ).Warning( e );
	}
  }

  public final void run() {
	while ( true ) {
	  try {
		Dispatch();
		synchronized ( lock ) {
		}
	  } catch ( IOException e ) {
		LOG.Error( this + " error" ).Error( e );
	  }
	}
  }

  @Override
  public final String toString() {
	return Name();
  }
}
