/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai.info;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.Method;
import com.sun.jdi.VMDisconnectedException;

import java.util.Objects;
import java.util.stream.Collectors;

public class MethodInfo {

  final Method method;

  public MethodInfo(Method method) {
    this.method = method;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    MethodInfo that = (MethodInfo) obj;
    return Objects.equals(method, that.method);
  }

  @Override
  public int hashCode() {
    return Objects.hash(method);
  }

  @Override
  public String toString() {

    // process parameter names to get only their names. 
    // This is to make it compatible with diffj that reports only parameter names and not their types.
    String parameters = "";
    String returnType = "";

    try {
      if(!method.arguments().isEmpty()) {
        parameters = method.arguments().stream().map(LocalVariable::signature).collect(Collectors.joining());
      }
      returnType = method.returnType().signature();
    } catch (AbsentInformationException | ClassNotLoadedException | VMDisconnectedException e) {
      e.printStackTrace();
    }

    return method.declaringType().name() +
            "." +
            getName() +
            "(" +
            parameters +
            ")" +
            returnType;
  }

  /**
   * Gets the name of the method.
   */
  public String getName() {
    //If it is a constructor returns the class name instead of default name: init.
    /*if (method.isConstructor()) {
      String declaringType = method.declaringType().name();
      return declaringType.substring(declaringType.lastIndexOf('.') + 1);
    }*/
    return method.name();
  }

}