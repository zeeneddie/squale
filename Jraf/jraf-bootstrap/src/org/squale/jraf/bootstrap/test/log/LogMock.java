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
 * Created on Apr 2, 2004
 */
package org.squale.jraf.bootstrap.test.log;

import org.squale.jraf.spi.logging.ILogger;

/**
 * <p>Title : LogMock.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
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
	 * @see org.squale.jraf.spi.logging.ILogger#logCexi(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
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
