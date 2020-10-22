package com.github.s7connector.exception;

/**
 * The Class S7Exception is an exception related to S7 Communication
 *
 * @author Thomas Rudin
 */
public final class S7Exception extends RuntimeException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -4761415733559374116L;

    /**
     * Instantiates a new s7 exception.
     */
    public S7Exception() {
    }

    /**
     * Instantiates a new s7 exception.
     *
     * @param message the message
     */
    public S7Exception(final String message) {
        super(message);
    }

    /**
     * Instantiates a new s7 exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public S7Exception(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new s7 exception.
     *
     * @param cause the cause
     */
    public S7Exception(final Throwable cause) {
        super(cause);
    }

}
