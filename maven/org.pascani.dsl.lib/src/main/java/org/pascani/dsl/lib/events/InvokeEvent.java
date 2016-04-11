/*
 * Copyright © 2015 Universidad Icesi
 * 
 * This file is part of the Pascani library.
 * 
 * The Pascani library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * The Pascani library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with The Pascani library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.pascani.dsl.lib.events;

import java.util.Arrays;
import java.util.UUID;

import org.pascani.dsl.lib.Event;

/**
 * Implementation of {@link Event} for raising events each time a method is
 * invoked.
 * 
 * @author Miguel Jiménez - Initial contribution and API
 */
public class InvokeEvent extends Event<Long> {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 8016321382976600130L;

	/**
	 * The class providing the method
	 */
	private final String clazz;

	/**
	 * The invoked method
	 */
	private final String method;

	/**
	 * The formal parameters of the method
	 */
	private final String[] parameters;

	/**
	 * Creates an instance having all of the parameters
	 * 
	 * @param transactionId
	 *            The transaction of which this event makes part
	 * @param clazz
	 *            The class from which the method is member
	 * @param method
	 *            The name of the method
	 * @param parameters
	 *            The formal parameters of the method
	 */
	public InvokeEvent(final UUID transactionId, final String clazz,
			final String method, final String[] parameters) {
		super(transactionId);
		this.clazz = clazz;
		this.method = method;
		this.parameters = parameters;
	}

	@Override public Long value() {
		return this.timestamp;
	}

	public String methodProvider() {
		return this.clazz;
	}

	public String method() {
		return this.method;
	}

	public String[] methodParameters() {
		return this.parameters;
	}

	/**
	 * Returns the string representation of this event for logging purposes.
	 */
	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getCanonicalName() + "\t");
		sb.append(this.transactionId + "\t");
		sb.append(this.identifier + "\t");
		sb.append(this.clazz + "\t");
		sb.append(this.method + "\t");
		sb.append(Arrays.toString(parameters) + "\t");
		sb.append(value());

		return sb.toString();
	}

}