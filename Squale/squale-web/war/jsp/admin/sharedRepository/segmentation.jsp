<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c"%>
<script type="text/javascript" src="theme/charte_v03_001/js/segmentation.js"></script>
<script type="text/javascript" src="jslib/jquery.js"></script>
<script type="text/javascript" src="jslib/jquery-ui.js"></script>

<af:page>
	<af:body onload="display()">
		<af:canvasCenter titleKey="shared_repository.segmentation.title"> 
			<br/>
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<br/>
					<bean:message key="shared_repository.segmentation.help" />
				<br/>
			</af:dropDownPanel>
			<br/>
			<html:hidden name="sharedRepositorySegmentationForm" property="parentModule" styleId="parentModule"/>
			<div>
				<div class="application">
					<h3 class="segmentation_title">
						<bean:message key="shared_repository.segmentation.application_module" />
					</h3>
					<br/>
					<logic:iterate id="appElt"  name="sharedRepositorySegmentationForm" property="appList" indexId="appIndex" >
						<div>
							<bean:define id="appIdentifier" name="appElt" property="technicalId" type="String"/>
							<img border="0" src="theme/charte_v03_001/img/ico/bleu/icon_sqplus.gif"  style="cursor:pointer; vertical-align: bottom;" id="edit-<%=appIdentifier%>" onclick="edit(<%=appIdentifier %>)"/>
							<img border="0" src="theme/charte_v03_001/img/ico/bleu/icon_sqless.gif"  style="cursor:pointer; display: none; vertical-align: bottom;" id="canceledit-<%=appIdentifier %>" onclick="canceledit(<%=appIdentifier %>)" />
							<logic:equal name="sharedRepositorySegmentationForm" property="appSelected" value='<%=appIdentifier%>'>
								<span class="text_red">
									<bean:write name="appElt" property="name"/>
									<bean:define name="appElt" property="name" id="appName" />
								</span>
							</logic:equal>
							<logic:notEqual name="sharedRepositorySegmentationForm" property="appSelected" value='<%=appIdentifier%>'>
								<html:link href='<%="sharedRepositorySegmentation.do?action=retrieveSegment&appId="+appIdentifier%>'> 
									<bean:write name="appElt" property="name"/>
								</html:link>
							</logic:notEqual>
						</div>
						<div id="moduleform-<%=appIdentifier %>" class="masked" >
							<ul>
								<logic:iterate id="modElt" name="appElt" property="moduleList" indexId="modIndex">
									<li>
										<bean:define id="modIdentifier" name="modElt" property="technicalId" type="String"/>
										<logic:equal name="sharedRepositorySegmentationForm" property="moduleSelected" value='<%=modIdentifier%>'>
											<span class="text_red">
												<bean:write name="modElt" property="name"/>
												<bean:define name="modElt" property="name" id="moduleName"/>
												<bean:define name="appElt" property="name" id="parentModule" />
											</span>
										</logic:equal>
										<logic:notEqual name="sharedRepositorySegmentationForm" property="moduleSelected" value='<%=modIdentifier%>'>
											<html:link href='<%="sharedRepositorySegmentation.do?action=retrieveSegment&parentId="+appIdentifier+"&modId="+modIdentifier%>' >
												<bean:write name="modElt" property="name"/>
											</html:link>
										</logic:notEqual>
									</li>
								</logic:iterate>
							</ul>
						</div>
					</logic:iterate>
					<br/>
				</div>
				<logic:notEmpty name="sharedRepositorySegmentationForm" property="categoryList" >
					<div class="segment">
						<af:form action="/sharedRepositorySegmentation.do">
							<h3 class="segmentation_title">
								<logic:notEmpty name="sharedRepositorySegmentationForm" property="appSelected" >
									<bean:message key="shared_repository.segmentation.application"/>
									<span class="application_reminder">
										<bean:write name="appName"/>
									</span>
								</logic:notEmpty>
								<logic:empty name="sharedRepositorySegmentationForm" property="appSelected" >
									<bean:message key="shared_repository.segmentation.module"/>
									<span class="application_reminder">
										<bean:write name="moduleName"/>
									</span>
									<bean:message key="shared_repository.segmentation.module2"/>
									<span class="application_reminder">
										<bean:write name="parentModule"/>
									</span>
								</logic:empty>
							</h3>
							<br/>
							<nested:iterate name="sharedRepositorySegmentationForm" property="categoryList" id="cat" >
								<div class="height_col">
									<table class="formulaire" >
				 						<thead>
											<tr class="height_col">
												<nested:equal property="deprecated" value="true">
													<th class="segment_deprecated">
														<nested:message property="name"/>
													</th>
												</nested:equal>
												<nested:equal property="deprecated" value="false">
													<th>
														<nested:message property="name"/>
													</th>
												</nested:equal>
											</tr>
										</thead>
									</table>
								</div>
								<div>
									<table>
										<tbody>
											<nested:iterate property="segmentList">
												<tr class="height_col">
													<logic:equal name="cat" property="deprecated" value="true">
														<td class="segment_deprecated">
															<nested:checkbox property="selected" disabled="true"/>
															<nested:message property="name"/>
														</td>
													</logic:equal>
													<logic:equal name="cat" property="deprecated" value="false">
														<nested:equal property="deprecated" value="true">
															<td class="segment_deprecated">
																<nested:checkbox property="selected" disabled="true"/>
																<nested:message property="name"/>
															</td>
														</nested:equal>
														<nested:equal property="deprecated" value="false">
															<td>
																<nested:checkbox property="selected"/>
																<nested:message property="name"/>
															</td>
														</nested:equal>
													</logic:equal>
												</tr>
											</nested:iterate>
										</tbody>
									</table>
								</div>
								<br/>
							</nested:iterate>
							<br/>
							<af:buttonBar>
								<af:button name="valider" toolTipKey="toolTip.valider" callMethod="record"></af:button>
							</af:buttonBar>
						</af:form>
					</div>
				</logic:notEmpty>
			</div>
		</af:canvasCenter>
	</af:body>
</af:page>