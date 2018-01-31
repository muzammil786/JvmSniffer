/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai.vm;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jdiscript.util.VMProcessAttacher;
import org.jdiscript.util.VMSocketAttacher;

import java.io.IOException;

public class VirtualMachineFactory {

  private static final Logger LOGGER = LogManager.getLogger(VirtualMachineFactory.class);

  private VirtualMachineFactory() {
    
  }
  
  /**
   * Gets VirtualMachine based on process id of the JVM running on the local host. The target JVM must be running with
   * -agentlib:jdwp
   */
  public static VirtualMachine getVirtualMachine(String pid) throws IOException, IllegalConnectorArgumentsException {
    LOGGER.info("Attaching debugger to pid: " + pid + " ...");
    VMProcessAttacher vmProcessAttacher = new VMProcessAttacher(pid);
    final VirtualMachine vm = vmProcessAttacher.attach();
    LOGGER.info("Debugger attached to pid: " + pid + " ...");
    return vm;
  }

  /**
   * Gets VirtualMachine based on the port on the given host. The target JVM must be running with
   * -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9865, for the port 9865.
   */
  public static VirtualMachine getVirtualMachine(String hostname, String port) {
    VMSocketAttacher vmSocketAttacher = new VMSocketAttacher(hostname, port);
    final VirtualMachine vm = vmSocketAttacher.attach();
    LOGGER.info("Debugger attached to host: " + hostname + " and port: " + port);
    return vm;
  }

}