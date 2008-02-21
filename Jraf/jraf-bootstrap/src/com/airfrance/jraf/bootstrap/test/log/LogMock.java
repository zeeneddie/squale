/*
 * Created on Apr 2, 2004
 */
package com.airfrance.jraf.bootstrap.test.log;

import com.airfrance.jraf.spi.logging.ILogger;

/**
 * <p>Title : LogMock.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class LogMock implements ILogger {

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#debug(java.lang.Object, java.lang.Throwable)
	 */
	public void debug(Object arg0, Throwable arg1) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#debug(java.lang.Object)
	 */
	public void debug(Object arg0) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#error(java.lang.Object, java.lang.Throwable)
	 */
	public void error(Object arg0, Throwable arg1) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#error(java.lang.Object)
	 */
	public void error(Object arg0) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#fatal(java.lang.Object, java.lang.Throwable)
	 */
	public void fatal(Object arg0, Throwable arg1) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#fatal(java.lang.Object)
	 */
	public void fatal(Object arg0) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#info(java.lang.Object, java.lang.Throwable)
	 */
	public void info(Object arg0, Throwable arg1) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#info(java.lang.Object)
	 */
	public void info(Object arg0) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#isDebugEnabled()
	 */
	public boolean isDebugEnabled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#isErrorEnabled()
	 */
	public boolean isErrorEnabled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#isFatalEnabled()
	 */
	public boolean isFatalEnabled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#isInfoEnabled()
	 */
	public boolean isInfoEnabled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#isTraceEnabled()
	 */
	public boolean isTraceEnabled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#isWarnEnabled()
	 */
	public boolean isWarnEnabled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#trace(java.lang.Object, java.lang.Throwable)
	 */
	public void trace(Object arg0, Throwable arg1) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#trace(java.lang.Object)
	 */
	public void trace(Object arg0) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#warn(java.lang.Object, java.lang.Throwable)
	 */
	public void warn(Object arg0, Throwable arg1) {

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.logging.Log#warn(java.lang.Object)
	 */
	public void warn(Object arg0) {

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.logging.ILogger#logCexi(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void logCexi(
		int code,
		String severity,
		String group,
		String sourceComposant,
		String message) {
		// ne fait rien
	}

}
