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
package org.pascani.dsl.lib.sca.intents;

import java.util.UUID;

import org.ow2.frascati.tinfi.api.IntentJoinPoint;
import org.pascani.dsl.lib.events.ExceptionEvent;
import org.pascani.dsl.lib.events.InvokeEvent;
import org.pascani.dsl.lib.events.ReturnEvent;
import org.pascani.dsl.lib.events.TimeLapseEvent;

/**
 * @author Miguel Jiménez - Initial contribution and API
 */
public class AllEventsIntentHandler extends AbstractIntentHandler {

	public Object invoke(IntentJoinPoint ijp) throws Throwable {
		UUID transactionId = UUID.randomUUID();
		InvokeEvent invokeEvent = new InvokeEvent(transactionId,
				ijp.getMethod().getDeclaringClass(), ijp.getMethod().getName(),
				ijp.getMethod().getParameterTypes(), ijp.getArguments());
		super.handler.handle(invokeEvent);
		long start = System.nanoTime();
		Object _return = null;
		try {
			_return = ijp.proceed();
		} catch (Throwable cause) {
			ExceptionEvent exceptionEvent = new ExceptionEvent(transactionId,
					new Exception(cause), ijp.getMethod().getDeclaringClass(),
					ijp.getMethod().getName(),
					ijp.getMethod().getParameterTypes(), ijp.getArguments());
			super.handler.handle(exceptionEvent);
			throw new Throwable(cause);
		}
		long end = System.nanoTime();
		TimeLapseEvent timeLapseEvent = new TimeLapseEvent(transactionId, start, end);
		super.handler.handle(timeLapseEvent);
		ReturnEvent returnEvent = new ReturnEvent(transactionId,
				ijp.getMethod().getDeclaringClass(), ijp.getMethod().getName(),
				ijp.getMethod().getParameterTypes(), _return);
		super.handler.handle(returnEvent);
		return _return;
	}

}
