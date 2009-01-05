/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on Apr 6, 2004
 */
package com.airfrance.jraf.bootstrap.test.log;

import com.airfrance.jraf.spi.logging.ILogger;
import com.airfrance.jraf.spi.logging.ILoggingProvider;

/**
 * <p>Title : LoggingMockProvider.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class LoggingMockProvider implements ILoggingProvider {

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.logging.ILoggingProvider#getInstance(java.lang.Class)
	 */
	public ILogger getInstance(Class clazz) {
		return new LogMock();
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.logging.ILoggingProvider#getInstance(java.lang.String)
	 */
	public ILogger getInstance(String clazz) {
		return new LogMock();
	}

}
