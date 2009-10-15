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
package org.squale.squaleexport.core;

import java.util.List;

import org.squale.squalecommon.datatransfertobject.config.AdminParamsDTO;

/**
 * Exporter interface
 */
public interface IExporter
{

    /**
     * This method do the export. It takes the list of applications to export as entry then it creates the xml file(s)
     * which contains the exported data
     * 
     * @param applications The list of id of the applications to export
     * @param mappingList The list of mapping generic metric / metric in the local squale
     */
    void exportData( List<Long> applications, List<AdminParamsDTO> mappingList );

}
