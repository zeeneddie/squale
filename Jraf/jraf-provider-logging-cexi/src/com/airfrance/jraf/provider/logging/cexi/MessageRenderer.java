package com.airfrance.jraf.provider.logging.cexi;

import org.apache.log4j.or.ObjectRenderer;

/**
 * <p>Project: JRAF 
 * <p>Module: jraf-provider-logging-cexi
 * <p>Title : StringRenderer.java</p>
 * <p>Description : String renderer</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 * Cette classe implemente l'interface ObjectRenderer de log4j.
 * Elle permet de formater un message de type String, en vue de le 
 * logger pour Cexi.
 */
public class MessageRenderer implements ObjectRenderer {

	/**
	 * 
	 */
	public MessageRenderer() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.or.ObjectRenderer#doRender(java.lang.Object)
	 */
	public String doRender(Object arg0) {
		if( arg0 instanceof Message ) {
			Message mess = (Message) arg0;
			return mess.getCode()
					+" - "+mess.getSeverity()
					+" - "+mess.getGroup()
					+" - "+mess.getSourceComposant()
					+" - "+mess.getMessage();
		} else
			return "Erreur dans le StringRenderer";
	}

}
