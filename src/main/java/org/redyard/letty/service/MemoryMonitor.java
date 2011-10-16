/*******************************************************************************
 * This file (MemoryMonitor.java) is part of "Letty" project
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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

import org.redyard.letty.logging.LoggingService;

/**
 * @author =Troy=
 * @version 1.0
 * @created Oct 17, 2011
 */
public class MemoryMonitor implements Runnable {

  private static final LoggingService LOG = LoggingService.Instance();

  @Override
  public void run() {
	LOG.StartSection( "Memory Monitor" );
	MemoryUsage heap = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
	MemoryUsage non_heap = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
	LOG.Info( "Heap    (used/max): " + (heap.getUsed() / 1048576) + "/" + (heap.getMax() / 1048576) + " MB" );
	LOG.Info( "NonHeap (used/max): " + (non_heap.getUsed() / 1048576) + "/" + (non_heap.getMax() / 1048576) + " MB" );
	LOG.EndSection();
  }

}
