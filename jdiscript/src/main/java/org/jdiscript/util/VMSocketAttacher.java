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

public class VMSocketAttacher {
  private final String host;
  private final String port;
  private final int timeout;

  public VMSocketAttacher(String port) {
    this(null, port);
  }

  public VMSocketAttacher(String port, int timeout) {
    this(null, port, timeout);
  }

  public VMSocketAttacher(String host, String port) {
    this(host, port, 0);
  }

  public VMSocketAttacher(String host, String port, int timeout) {
    this.host = host;
    this.port = port;
    this.timeout = timeout;
  }

  public VirtualMachine safeAttach()
      throws IOException, IllegalConnectorArgumentsException {
    VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
    List<AttachingConnector> connectors = vmm.attachingConnectors();
    AttachingConnector attachingConnector = null;
    for (AttachingConnector c : connectors) {
      if (c.name().equals("com.sun.jdi.SocketAttach")) {
        attachingConnector = c;
        break;
      }
    }
    Map<String, Argument> cArgs = attachingConnector.defaultArguments();
    cArgs.get("port").setValue(port);
    cArgs.get("timeout").setValue(Integer.toString(timeout));
    if (host != null) {
      cArgs.get("hostname").setValue(host);
    }
    final VirtualMachine vm = attachingConnector.attach(cArgs);
    return vm;
  }

  /**
   * Like safeAttach but wraps any checked exceptions in a RuntimeException.
   */
  public VirtualMachine attach() {
    try {
      return safeAttach();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
