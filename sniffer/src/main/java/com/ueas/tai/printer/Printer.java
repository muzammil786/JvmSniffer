/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai.printer;

import java.io.IOException;
import java.util.Collection;

public interface Printer {
  void print(Collection collection) throws IOException;
}