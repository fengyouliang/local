package org.feng.local.jvm.vm;


import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AttachToolMain {
    public static void main(String[] args) {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor virtualMachineDescriptor : list) {
            log.info("{}, {}", virtualMachineDescriptor.id(), virtualMachineDescriptor.displayName());
        }
    }
}
