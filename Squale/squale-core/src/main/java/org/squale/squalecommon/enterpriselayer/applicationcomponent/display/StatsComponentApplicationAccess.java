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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.display;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.squalecommon.datatransfertobject.stats.SetOfStatsDTO;
import org.squale.squalecommon.enterpriselayer.facade.stats.StatsFacade;

/**
 */
public class StatsComponentApplicationAccess
    extends DefaultExecuteComponent
{

    /**
     * Récupération des données statistiques niveau administrateur
     * 
     * @param pDaysForTerminatedAudit le nombre de jours max pour lesquels il doit y avoir au moins un audit réussi pour
     *            que l'application soit active (sert pour les statistiques par application)
     * @param pDaysForAllAudits le nombre de jours défini pour compter les audits
     * @return l'objet regroupant les stats (sert pour les statistiques par application)
     * @throws JrafEnterpriseException en cas d'échec de récupération des données
     */
    public SetOfStatsDTO getStats( Integer pDaysForTerminatedAudit, Integer pDaysForAllAudits )
        throws JrafEnterpriseException
    {
        return StatsFacade.getStats( pDaysForTerminatedAudit.intValue(), pDaysForAllAudits.intValue() );
    }
}
