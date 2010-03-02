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
package org.squale.sharedrepository.stat;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Module class used for the export
 */
@XStreamAlias( "module" )
public class ModuleStat
{

    /**
     * The computed score for the current reference
     */
    @XStreamImplicit
    private List<DataStat> dataList;

    /**
     * Default constructor
     */
    public ModuleStat()
    {
        dataList = new ArrayList<DataStat>();
    }

    /**
     * Constructor
     * 
     * @param pDatas The list of DataStat linked to the module
     */
    public ModuleStat( List<DataStat> pDatas )
    {
        dataList = pDatas;
    }

    /**
     * Getter method for the attribute datas
     * 
     * @return The list of data linked to the module
     */
    public List<DataStat> getDatas()
    {
        return dataList;
    }

    /**
     * Setter method for the attribute datas
     * 
     * @param pDatas The new list of data linked to the module
     */
    public void setDatas( List<DataStat> pDatas )
    {
        dataList = pDatas;
    }

}
