/**********************************************************************.
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2017        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************/

package com.ueas.tai.printer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class FilePrinter implements Printer {

  private final File file;

  public FilePrinter(File file) {
    this.file = file;
  }

  @Override
  public void print(Collection collection) throws IOException {
    FileUtils.writeLines(file, collection);
  }
}