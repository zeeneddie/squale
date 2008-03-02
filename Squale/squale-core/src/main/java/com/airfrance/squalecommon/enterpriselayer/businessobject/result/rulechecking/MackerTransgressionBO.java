package com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking;

/**
 * Résultat de transgression de Macker
 * 
 * @hibernate.subclass discriminator-value="MackerTransgression"
 */
public class MackerTransgressionBO
    extends RuleCheckingTransgressionBO
{
    // Vide. Cette classe sert juste à avoir un nom de subclass différent
    // dans la base.
}
