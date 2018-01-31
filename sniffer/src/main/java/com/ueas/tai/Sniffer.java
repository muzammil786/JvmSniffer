/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai;

import static org.jdiscript.util.Utils.println;

import com.sun.jdi.VirtualMachine;
import com.ueas.tai.events.MethodTracer;
import com.ueas.tai.printer.FilePrinter;
import com.ueas.tai.vm.VirtualMachineFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jdiscript.JDIScript;

import java.io.File;
import java.io.IOException;

public class Sniffer {

  private static final Logger LOGGER = LogManager.getLogger(Sniffer.class);

  /**
   * Starts sniffing on the given virtual machine and finally outputs the method call trace.
   */
  public void startSniffing(VirtualMachine vm) {
    try {
      LOGGER.info("Starting sniffing " + vm.name());
      JDIScript jdiScript = new JDIScript(vm);
      MethodTracer methodTracer = new MethodTracer();

      jdiScript.methodEntryRequest()
          .addClassFilter(Configuration.getConfiguration().getProperty("class.filter"))
          .addHandler(methodTracer).enable();

      // to print the info out before termination
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        try {
          vm.dispose();
          String outputPath = Configuration.getConfiguration().getProperty("output.path");
          if (outputPath == null) {
            outputPath = System.getProperty("user.directory") + "/build/output/";
          }
          File file = new File(outputPath + "method-trace.log");
          LOGGER.info("Printing method tracking info to " + file.getAbsolutePath());
          methodTracer.print(new FilePrinter(file));
        } catch (IOException e) {
          LOGGER.error("Exception occurred during trace printout.", e);
        }
      }));

      jdiScript.run();
      println("Shutting down");
    } catch (Exception exception) {
      LOGGER.error("Error occurred during JVM sniffing ", exception);
    }
  }

  /**
   * Main method to run the sniffer.
   */
  public static void main(String[] args) {
    // pid
    //      VirtualMachine vm = VirtualMachineFactory.getVirtualMachine("8524");
    // socket
    try {
      String host = Configuration.getConfiguration().getProperty("host");
      String port = Configuration.getConfiguration().getProperty("port");

      VirtualMachine vm = VirtualMachineFactory.getVirtualMachine(host, port);
      Sniffer sniffer = new Sniffer();
      sniffer.startSniffing(vm);
    } catch (IOException exception) {
      LOGGER.error(exception);
    }
  }
}