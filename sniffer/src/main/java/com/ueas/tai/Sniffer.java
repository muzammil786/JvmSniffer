/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai;

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
    private MethodTracer methodTracer;
    private boolean printOutputOnExit = true;

    /**
     * Starts sniffing on the given virtual machine and finally outputs the method call trace.
     */
    public void startSniffing(VirtualMachine vm) {
        try {
            LOGGER.info("Starting sniffing " + vm.name());
            JDIScript jdiScript = new JDIScript(vm);
            methodTracer = new MethodTracer();

            String classFilter = Configuration.getConfiguration().getProperty("class.filter");
            jdiScript.methodEntryRequest()
                    .addClassFilter(classFilter)
                    .addHandler(methodTracer).enable();
            LOGGER.debug("Class filter added: " + classFilter);

            // to print the info out before termination
            if(isPrintOutputOnExit()) {
                Runtime.getRuntime().addShutdownHook(new Thread(this::printSniffingOutput));
            }

            jdiScript.run();
            LOGGER.info("Sniffer shutting down");
        } catch (Exception exception) {
            LOGGER.error("Error occurred during JVM sniffing ", exception);
        }
    }

    /**
     * Prints method info to output.path/method-trace.log.
     */
    public void printSniffingOutput() {
        LOGGER.debug("Printing sniffer output");
        try {
          /*try {
            vm.dispose();
          } catch(VMDisconnectedException vmdex) {
            LOGGER.warn("Virtual machine could not be disposed. Reason: " + vmdex.getMessage());
          }*/
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
    }

    public boolean isPrintOutputOnExit() {
        return printOutputOnExit;
    }

    public void setPrintOutputOnExit(boolean printOutputOnExit) {
        this.printOutputOnExit = printOutputOnExit;
    }

    /**
     * Main method to run the sniffer.
     */
    public static void main(String[] args) {
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