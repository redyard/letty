/*******************************************************************************
 * This file (SimpleChatServer.java) is part of "Letty" project
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

package org.redyard.letty.example;

import org.redyard.letty.config.ConfigurationService;
import org.redyard.letty.logging.LoggingService;
import org.redyard.letty.network.LettyServer;
import org.redyard.letty.service.MemoryMonitor;
import org.redyard.letty.service.ServiceProvider;
import org.redyard.letty.util.Profiler;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 17, 2011
 */
public class ChatServer {

  public static void main(String... args) {
	Profiler.Instance();

	LoggingService.Init();
	ConfigurationService.Init();
	ServiceProvider.Init();

	LoggingService LOG = LoggingService.Instance();

	LOG.StartSection( "Server" );
	new LettyServer( new ChatServerConfig(), new ChatConnectionFactory() ).Listen();
	LOG.EndSection();

	LOG.StartSection( "Profiling" ).Info( "Server started in " + Profiler.ProfilingTime() + " seconds." ).EndSection();

	ServiceProvider.ScheduleNow( new MemoryMonitor(), 600 );
  }

}
