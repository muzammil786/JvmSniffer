package org.jdiscript.util;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class VMProcessAttacher {
  private final String pid;
  private final String timeout;

  public VMProcessAttacher(String pid) {
    this(pid, "0");
  }

  public VMProcessAttacher(String pid, String timeout) {
    this.pid = pid;
    this.timeout = timeout;
  }

  public VirtualMachine attach() throws IOException, IllegalConnectorArgumentsException {
    VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
    List<AttachingConnector> connectors = vmm.attachingConnectors();
    AttachingConnector attachingConnector = null;
    for (AttachingConnector c : connectors) {
      if (c.name().equals("com.sun.jdi.ProcessAttach")) {
        attachingConnector = c;
        break;
      }
    }
    if (attachingConnector == null) {
      throw new RuntimeException("Unable to locate ProcessAttachingConnector");
    }
    Map<String, Argument> cArgs = attachingConnector.defaultArguments();

    cArgs.get("pid").setValue(pid);
    cArgs.get("timeout").setValue(timeout);
    System.out.println("Default args: " + cArgs.toString());
    return attachingConnector.attach(cArgs);
  }
}
