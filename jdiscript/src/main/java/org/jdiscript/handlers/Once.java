package org.jdiscript.handlers;

import com.sun.jdi.event.Event;

import org.jdiscript.events.DebugEventDispatcher;

public class Once extends BaseEventHandler {
  private DebugEventHandler handler;

  public Once(DebugEventHandler handler) {
    this.handler = handler;
  }

  @Override
  public void unhandledEvent(Event e) {
    DebugEventDispatcher.doFullDispatch(e, handler);
    e.request().disable();
  }
}
