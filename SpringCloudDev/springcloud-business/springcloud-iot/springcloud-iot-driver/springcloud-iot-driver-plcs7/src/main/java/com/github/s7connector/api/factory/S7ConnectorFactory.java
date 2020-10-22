package com.github.s7connector.api.factory;

import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.SiemensPLCS;
import com.github.s7connector.impl.S7TCPConnection;

/**
 * S7 connector factory, currently only for TCP connections
 *
 * @author Thomas Rudin
 */
public class S7ConnectorFactory {

    /**
     * TCP Connection builder
     */
    public static class TCPConnectionBuilder {

        private String host;

        private SiemensPLCS plcsType;

        private int rack = 0, slot = 2, port = 102, timeout = 2000;

        TCPConnectionBuilder(SiemensPLCS type) {
            this.plcsType = type;
        }

        /**
         * Builds a connection with given params
         */
        public S7Connector build() {
            return new S7TCPConnection(this.host, this.rack, this.slot, this.port, this.timeout, this.plcsType);
        }

        /**
         * use hostname/ip
         */
        public TCPConnectionBuilder withHost(final String host) {
            this.host = host;
            return this;
        }

        /**
         * use port, default is 102
         */
        public TCPConnectionBuilder withPort(final int port) {
            this.port = port;
            return this;
        }

        /**
         * use rack, default is 0
         */
        public TCPConnectionBuilder withRack(final int rack) {
            this.rack = rack;
            return this;
        }

        /**
         * use slot, default is 2
         */
        public TCPConnectionBuilder withSlot(final int slot) {
            this.slot = slot;
            return this;
        }

        /**
         * use timeout, default is 2000
         */
        public TCPConnectionBuilder withTimeout(final int timeout) {
            this.timeout = timeout;
            return this;
        }

    }

    /**
     * @param type choose a siemens plc type to build a tcp connector.
     * @return returns a new TCP connection builder
     */
    public static TCPConnectionBuilder buildTCPConnector(SiemensPLCS type) {
        return new TCPConnectionBuilder(type);
    }

    public static TCPConnectionBuilder buildTCPConnector() {
        return new TCPConnectionBuilder(SiemensPLCS.SNon200);
    }

}
