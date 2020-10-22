package com.github.s7connector.api.factory;

import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.S7Serializer;
import com.github.s7connector.impl.serializer.S7SerializerImpl;

/**
 * S7 Serializer factory
 * 
 * @author Thomas Rudin
 *
 */
public class S7SerializerFactory {

	/**
	 * Builds a new serializer with given connector
	 * 
	 * @param connector
	 *            the connector to use
	 * @return a serializer instance
	 */
	public static S7Serializer buildSerializer(final S7Connector connector) {
		return new S7SerializerImpl(connector);
	}

}
