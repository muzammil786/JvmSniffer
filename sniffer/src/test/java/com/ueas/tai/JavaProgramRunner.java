/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai;

import org.jdiscript.util.StreamRedirectThread;

import java.io.IOException;

/**
 * Utility to run Java programs.
 */
public class JavaProgramRunner {

  private String classpath = "";
  private String program = "";
  private String arguments = "";

  public JavaProgramRunner classpath(String classpath) {
    this.classpath = classpath;
    return this;
  }

  public JavaProgramRunner program(String program) {
    this.program = program;
    return this;
  }

  public JavaProgramRunner arguments(String arguments) {
    this.arguments = arguments;
    return this;
  }

  /**
   * Executes the java program set in the properties in the debug mode. The program output and errors are written on the 
   * standard output stream.
   */
  public Process execute() throws IOException {
    String[] command = {
        "java",
        "-ea",
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9865",
        "-classpath",
        classpath,
        program,
        arguments
    };
    Process process = Runtime.getRuntime().exec(command);
    new StreamRedirectThread("JavaProgramRunner", process.getErrorStream(), System.err).start();
    new StreamRedirectThread("JavaProgramRunner", process.getInputStream(), System.out).start();
    return process;
  }

}