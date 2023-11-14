package project.utils;

import ch.qos.logback.core.PropertyDefinerBase;

import java.lang.management.ManagementFactory;

public class VmIdPropertyDefiner extends PropertyDefinerBase {
    public String getPropertyValue() {
        return ManagementFactory.getRuntimeMXBean().getName();
    }
}
