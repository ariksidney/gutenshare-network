package com.group4.core;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

public class IdGenerator {

    private static final EthernetAddress ETHERNET_ADDRESS = EthernetAddress.fromInterface();

    private static final TimeBasedGenerator TIME_BASED_GENERATOR = Generators.timeBasedGenerator(ETHERNET_ADDRESS);

    private IdGenerator() {
    }

    public static UUID timeBasedUUID() {
        return TIME_BASED_GENERATOR.generate();
    }
}
