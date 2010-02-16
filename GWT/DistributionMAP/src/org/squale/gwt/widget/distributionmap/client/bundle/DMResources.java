/**
 * 
 */
package org.squale.gwt.widget.distributionmap.client.bundle;

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
