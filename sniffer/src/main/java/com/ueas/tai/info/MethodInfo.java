/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai.info;

import com.sun.jdi.Method;

import java.util.Objects;
import java.util.stream.Collectors;

public class MethodInfo {

  Method method;

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
    String parameters = method.argumentTypeNames().stream()
        .map(s -> s.substring(s.lastIndexOf('.') + 1)).collect(Collectors.joining(","));
    StringBuilder builder = new StringBuilder();
    builder.append(method.declaringType().name())
        .append(".")
        .append(getName())
        .append("(")
        .append(parameters)
        .append(")");

    return builder.toString();
  }

  /**
   * Gets the name of the method. If it is a constructor returns the class name instead of default name: init.
   */
  public String getName() {
    if (method.isConstructor()) {
      String declaringType = method.declaringType().name();
      return declaringType.substring(declaringType.lastIndexOf('.') + 1);
    }
    return method.name();
  }

}