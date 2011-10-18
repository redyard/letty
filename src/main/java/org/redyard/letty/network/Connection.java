/*******************************************************************************
 * This file (Connection.java) is part of "Letty" project
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

import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.redyard.letty.network.dispatcher.Dispatcher;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 17, 2011
 */
public abstract class Connection {

  private final SocketChannel channel;
  private final Dispatcher dispatcher;
  private SelectionKey key;
  private String ip;
  private int port;

  public Connection(SocketChannel channel, Dispatcher dispatcher) throws ClosedChannelException {
	this.channel = channel;
	this.dispatcher = dispatcher;
	this.dispatcher.Register( this.channel, SelectionKey.OP_READ, this );
	if ( channel != null ) {
	  Socket socket = channel.socket();
	  if ( socket != null ) {
		InetAddress address = socket.getInetAddress();
		if ( address != null ) {
		  ip = address.getHostAddress();
		  port = socket.getPort();
		}
	  }
	}
	Connected();
  }

  public abstract void Connected();

  public final Connection SetKey(SelectionKey key) {
	this.key = key;
	return this;
  }

  public final SelectionKey Key() {
	return key;
  }

  public String Ip() {
	return ip;
  }

  public int Port() {
	return port;
  }

}
