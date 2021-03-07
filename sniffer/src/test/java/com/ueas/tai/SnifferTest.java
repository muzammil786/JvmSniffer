/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai;

import com.sun.jdi.VirtualMachine;
import com.ueas.tai.vm.VirtualMachineFactory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SnifferTest {

  @Test
  public void startSniffing() {
    try {
      // run program
      Process process = new JavaProgramRunner()
          .classpath(System.getProperty("user.dir") + "/build/classes/test")
          .program("com.ueas.tai.example.HelloWorld")
          .arguments("2")
          .execute();

      // start sniffing
      Sniffer sniffer = new Sniffer();
      sniffer.setPrintOutputOnExit(false);
      VirtualMachine vm = VirtualMachineFactory.getVirtualMachine("localhost", "9865");
      sniffer.startSniffing(vm);
      process.destroy();
      sniffer.printSniffingOutput();

      // read result
      Path path = Paths.get("build/output/method-trace.log");
      // these methods should be in the trace
      List<String> lines = Files.readAllLines(path);
      String result = String.join(",", lines);
      assertTrue(result.contains("com.ueas.tai.example.HelloWorld.<init>()V"));
      assertTrue(result.contains("com.ueas.tai.example.HelloWorld.<init>(Ljava/lang/String;)V"));
      assertTrue(result.contains("com.ueas.tai.example.HelloWorld.setHelloTo(Ljava/lang/String;)V"));
      assertTrue(result.contains("com.ueas.tai.example.HelloWorld.sayHello()Ljava/lang/String;"));
      assertTrue(result.contains("com.ueas.tai.example.HelloWorld.sayHelloSpecified(Ljava/lang/String;)Ljava/lang/String;"));
      assertTrue(result.contains("com.ueas.tai.example.HelloWorld.sayHelloInFrench()Ljava/lang/String;"));
      assertTrue(result.contains("com.ueas.tai.example.HelloWorld.sayHello(Ljava/lang/String;)Ljava/lang/String;"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}