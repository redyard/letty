/*******************************************************************************
 * This file (Acceptor.java) is part of "Letty" project
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

package org.redyard.letty.network.worker;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.redyard.letty.network.ConnectionFactory;
import org.redyard.letty.network.LettyServer;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 18, 2011
 */
public class Acceptor {

  private final ConnectionFactory factory;
  private final LettyServer server;

  public Acceptor(ConnectionFactory factory, LettyServer server) {
	this.factory = factory;
	this.server = server;
  }
  
  public final void Accept(SelectionKey key) throws IOException {
	ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
    SocketChannel channel = ssc.accept();
    channel.configureBlocking(false);
    factory.Create(channel, server.IODispatcher());
  }
  
}
