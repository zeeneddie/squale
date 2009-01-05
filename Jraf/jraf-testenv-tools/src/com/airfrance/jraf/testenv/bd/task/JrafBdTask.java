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
package com.airfrance.jraf.testenv.bd.task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.URL;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.util.ReflectHelper;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafCommons
 * <p>Title : JrafBdTask.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 * @deprecated Ne pas utiliser cette classe.
 * Elle presente des disfonctionnements. 
 * Elle est conservee a titre d'exemple pour le futur.
 */
public class JrafBdTask {/*extends MatchingTask {

	private String namingStrategy = null;
	private String configurationFile = null;
	private String delimiter = null;
	private boolean drop = false;
	private boolean create = false;
	private boolean all = false;
	private Path compileClasspath;

	/**
	 * 
	 */
	public JrafBdTask() {
		super();
	}
/*
	private Configuration getConfiguration() throws Exception {
		Configuration cfg = new Configuration();
		if (namingStrategy != null)
			cfg.setNamingStrategy(
				(NamingStrategy) ReflectHelper
					.classForName(namingStrategy)
					.newInstance());

		if (configurationFile != null) {

			URL cfgURL = this.getClass().getResource(configurationFile);
			cfg.configure(cfgURL);
		}
		return cfg;

	}

	private SchemaExport getSchemaExport(Configuration cfg)
		throws HibernateException, IOException {
		SchemaExport schemaExport;
		schemaExport = new SchemaExport(cfg);
		schemaExport.setDelimiter(delimiter);
		return schemaExport;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public void setConfig(String configurationFile) {
		System.out.println("file: " + configurationFile);
		this.configurationFile = configurationFile;
	}

	public void setDrop(boolean drop) {
		System.out.println("drop: " + drop);
		this.drop = drop;
	}

	public void setCreate(boolean create) {
		System.out.println("create: " + create);
		this.create = create;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public void setClasspath(Path classpath) {
		if (compileClasspath == null) {
			compileClasspath = classpath;
		} else {
			compileClasspath.append(classpath);
		}
	}

	/** Gets the classpath to be used for this compilation. */
/*	public Path getClasspath() {
		return compileClasspath;
	}

	/**
	 * Adds a path to the classpath.
	 */
/*	public Path createClasspath() {
		if (compileClasspath == null) {
			compileClasspath = new Path(getProject());
		}
		return compileClasspath.createPath();
	}

	/**
	 * Adds a reference to a classpath defined elsewhere.
	 */
/*	public void setClasspathRef(Reference r) {
		createClasspath().setRefid(r);
	}

	/* (non-Javadoc)
	 * @see org.apache.tools.ant.Task#execute()
	 */
/*	public void execute() throws BuildException {
		try {
			Configuration cfg = getConfiguration();
			SchemaExport schemaExport = getSchemaExport(cfg);

			if (drop) {
				// System.out.println("Drop DB");
				schemaExport.drop(false, true);
			}
			if (create) {
				// System.out.println("Create DB");
				schemaExport.create(false, true);
			}
			if (all) {
				// System.out.println("All DB");
				schemaExport.drop(false, true);
				schemaExport.create(false, true);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new BuildException(
				"Schema text failed: " + e.getMessage(),
				e);
		} catch (FileNotFoundException e) {
			throw new BuildException("File not found: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new BuildException("IOException : " + e.getMessage(), e);
		} catch (Exception e) {
			throw new BuildException(e);
		}
	}
*/
}
