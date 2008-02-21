package com.airfrance.squalecommon.enterpriselayer.businessobject.config.web;

/**
 * Visiteur de configuration d'affichage afin d'externaliser les traitements
 */
public interface DisplayConfVisitor {
    
    /**
     * @param pBubbleConf la configuration du bubble
     * @param pArgument argument
     * @return objet
     */
    public Object visit(BubbleConfBO pBubbleConf, Object pArgument);

    /**
     * @param pVolumetryConf la configuration de la volumétrie
     * @param pArgument argument
     * @return objet
     */
    public Object visit(VolumetryConfBO pVolumetryConf, Object pArgument);

}
