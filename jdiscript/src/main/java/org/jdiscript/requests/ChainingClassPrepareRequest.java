package org.jdiscript.requests;

import com.sun.jdi.ReferenceType;

import org.jdiscript.handlers.OnClassPrepare;

public interface ChainingClassPrepareRequest extends JDIScriptEventRequest {

  //Chaining EventRequest methods
  ChainingClassPrepareRequest addCountFilter(int count);

  ChainingClassPrepareRequest disable();

  ChainingClassPrepareRequest enable();

  ChainingClassPrepareRequest putProperty(Object key, Object value);

  ChainingClassPrepareRequest setEnabled(boolean val);

  ChainingClassPrepareRequest setSuspendPolicy(int policy);

  //Chaining ClassPrepareRequest methods
  ChainingClassPrepareRequest addClassExclusionFilter(String classPattern);

  ChainingClassPrepareRequest addClassFilter(ReferenceType refType);

  ChainingClassPrepareRequest addClassFilter(String classPattern);

  ChainingClassPrepareRequest addSourceNameFilter(String sourceNamePattern);

  ChainingClassPrepareRequest addHandler(OnClassPrepare handler);
}
