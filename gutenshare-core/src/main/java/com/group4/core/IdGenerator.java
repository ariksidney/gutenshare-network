package com.group4.core;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

/**
 * This class generates unique IDs based on the ethernet address or the current time.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
public class IdGenerator {

    private static final EthernetAddress ETHERNET_ADDRESS = EthernetAddress.fromInterface();

    private static final TimeBasedGenerator TIME_BASED_GENERATOR = Generators.timeBasedGenerator(ETHERNET_ADDRESS);

    private IdGenerator() {
    }

    /**
     * Returns a new ID based on the current time
     * @return ID as String
     */
    public static UUID timeBasedUUID() {
        return TIME_BASED_GENERATOR.generate();
    }
}
