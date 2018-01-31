/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai.example;

import java.lang.management.ManagementFactory;

/**
 * HelloWorld example to test method trace sniffing. Different methods are called in a loop and the sniffer should be able to 
 * trace them all.
 */
public class HelloWorld {
  private String helloTo;

  public HelloWorld() {
    this("World");
  }

  public HelloWorld(String helloTo) {
    this.helloTo = helloTo;
  }

  public HelloWorld(String helloTo, String helloAnd) {
    this(helloTo + " and " + helloAnd);
  }

  public String sayHello() {
    return "Hello, " + helloTo;
  }

  public String sayHello(String greetings) {
    return greetings + ", " + helloTo;
  }

  public void setHelloTo(String helloTo) {
    this.helloTo = helloTo;
  }

  private String sayHelloSpecified(String greetings) {
    return greetings + ", " + helloTo;
  }

  public String sayHelloInFrench() {
    return sayHelloSpecified("Bonjour");
  }

  /**
   * Main method.
   * args[0]: integer: How many times the loop should run?
   */
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Pid: " + ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    int limit = Integer.MAX_VALUE;

    if (args.length > 0) {
      try {
        limit = Integer.parseInt(args[0]);
      } catch (Exception ex) {
        System.out.println("Exception occurred while parsing the argument: " + args[0]);
      }
    }
    
    int finalLimit = limit;
    Thread thread = new Thread(() -> {
      System.out.printf("Running loop for " + finalLimit + " times.\n");
      for (int i = 0; i < finalLimit; i++) {
        HelloWorld hello = new HelloWorld();
        hello.setHelloTo("Mary");
        System.out.println(hello.sayHello());
        System.out.println(hello.sayHello("Salam"));
        System.out.println(hello.sayHelloInFrench());
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
    // to keep main running until the thread terminates
    thread.join();
  }

}