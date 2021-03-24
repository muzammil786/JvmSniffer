package com.ueas.tai;

import com.sun.jdi.VirtualMachine;
import com.ueas.tai.vm.VirtualMachineFactory;

import java.util.concurrent.TimeUnit;

public abstract class BaseSnifferTest {

    protected void executeSniffer(final int duration) throws InterruptedException {
        Sniffer sniffer = new Sniffer();
        sniffer.setPrintOutputOnExit(false);
        VirtualMachine vm = VirtualMachineFactory.getVirtualMachine("localhost", "9865");
        sniffer.startSniffing(vm);
        TimeUnit.SECONDS.sleep(duration);
        sniffer.printSniffingOutput();
    }
}
