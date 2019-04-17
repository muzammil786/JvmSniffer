/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai.events;

import com.sun.jdi.Method;
import com.sun.jdi.event.MethodEntryEvent;
import com.ueas.tai.info.MethodInfo;
import com.ueas.tai.printer.Printer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdiscript.handlers.OnMethodEntry;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MethodTracer implements OnMethodEntry {

  private static final Logger LOGGER = LogManager.getLogger(MethodTracer.class);
  // ordered set of method calls
  Set<MethodInfo> methodInfoSet = new LinkedHashSet<>();

  @Override
  public void methodEntry(MethodEntryEvent event) {
    Method method = event.method();
    MethodInfo methodInfo = new MethodInfo(method);
    LOGGER.debug("Call intercepted for " + methodInfo.toString());
    methodInfoSet.add(methodInfo);
  }

  public void print(Printer printer) throws IOException {
    printer.print(methodInfoSet);
  }
}