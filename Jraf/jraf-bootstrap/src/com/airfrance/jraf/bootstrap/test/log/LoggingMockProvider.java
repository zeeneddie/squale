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
