/**
 * 
 */
package org.squale.gwt.distributionmap.widget.bundle;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author fabrice
 */
public interface DMResources
    extends ClientBundle
{
    @Source( "DistributionMap.css" )
    DMCss css();
    
    @Source("images/spinner.gif")
    ImageResource loading();

}
