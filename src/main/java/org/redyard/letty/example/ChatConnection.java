/*******************************************************************************
 * This file (ChatConnection.java) is part of "Letty" project
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

package org.redyard.letty.example;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

import org.redyard.letty.logging.LoggingService;
import org.redyard.letty.network.Connection;
import org.redyard.letty.network.dispatcher.Dispatcher;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 18, 2011
 */
public class ChatConnection extends Connection {

  /**
   * @param SocketChannel channel
   * @param Dispatcher dispatcher
   * @throws ClosedChannelException
   */
  public ChatConnection(SocketChannel channel, Dispatcher dispatcher) throws ClosedChannelException {
	super( channel, dispatcher );
  }

  @Override
  public void Connected() {
	LoggingService.Instance().Debug( "Accept connection from " + Ip() + ":" + Port() );
  }

}
