package org.jdiscript.requests;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;

import org.jdiscript.handlers.OnMonitorContendedEnter;

public interface ChainingMonitorContendedEnterRequest extends JDIScriptEventRequest {

  //Chaining EventRequest methods
  ChainingMonitorContendedEnterRequest addCountFilter(int count);

  ChainingMonitorContendedEnterRequest disable();

  ChainingMonitorContendedEnterRequest enable();

  ChainingMonitorContendedEnterRequest putProperty(Object key, Object value);

  ChainingMonitorContendedEnterRequest setEnabled(boolean val);

  ChainingMonitorContendedEnterRequest setSuspendPolicy(int policy);

  //Chaining MonitorContendedEnterRequest methods
  ChainingMonitorContendedEnterRequest addClassExclusionFilter(String classPattern);

  ChainingMonitorContendedEnterRequest addClassFilter(ReferenceType refType);

  ChainingMonitorContendedEnterRequest addClassFilter(String classPattern);

  ChainingMonitorContendedEnterRequest addInstanceFilter(ObjectReference instance);

  ChainingMonitorContendedEnterRequest addThreadFilter(ThreadReference thread);

  ChainingMonitorContendedEnterRequest addHandler(OnMonitorContendedEnter handler);
}
