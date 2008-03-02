package com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking;

/**
 * Résultat de transgression de Checkstyle
 * 
 * @hibernate.subclass discriminator-value="CheckstyleTransgression"
 */
public class CheckstyleTransgressionBO
    extends RuleCheckingTransgressionBO
{
    // Vide. Cette classe sert juste à avoir un nom de subclass différent
    // dans la base.
}
