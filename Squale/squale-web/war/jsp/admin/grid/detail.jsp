<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<%@ page import="org.squale.squaleweb.resources.WebMessages" %>


<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js">
</script>

<af:page>
	<af:body canvasLeftPageInclude="/jsp/canvas/grid_menu.jsp">
		<af:canvasCenter titleKey="grid_detail.title"
			subTitleKey="grid_detail.subtitle">
			<br />
			<br />
			<bean:message key="grid_detail.details" />
			<br />
			<br />
			<div style="color: #f00"><html:messages id="msg" message="true">
				<bean:write name="msg" />
				<br>
			</html:messages></div>
			<br />

			<af:form action="updateGrid.do" method="POST">
				<logic:iterate id="factor" scope="session" name="gridConfigForm"
					property="factors.list" indexId="factorId">
					<bean:define id="factorName" name="factor" property="name"
						type="String" />
					<af:dropDownPanel titleKey="<%=factorName%>" lazyLoading="false">
						<table class="formulaire">
							<TBODY>
								<tr>
									<af:field key="grid_detail.factor"
										property='<%="factors.list[" + factorId + "].name"%>'
										styleClassLabel="td1" access="READONLY" />
								</tr>
								<tr>
									<af:field key="grid_detail.rule.ponderation"
										property='<%="factors.list[" + factorId + "].ponderation"%>'
										styleClassLabel="td1" />
								</tr>
							</TBODY>
						</table>
						<af:tabbedPane name="<%=factorName%>">
							<logic:iterate id="criterium" name="factor"
								property="criteria.list" indexId="criteriumId">
								<bean:define id="criteriumName" name="criterium" property="name"
									type="String" />
								<af:tab key="<%=criteriumName%>"
									name="<%=factorName + criteriumName%>">
									<table class="formulaire">
										<tbody>
											<tr>
												<af:field key="grid_detail.criterium"
													property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].name"%>'
													styleClassLabel="td1" access="READONLY" />
											</tr>
											<tr>
												<af:field key="grid_detail.rule.ponderation"
													property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].ponderation"%>'
													styleClassLabel="td1" />
											</tr>
										</tbody>
									</table>
									<table class="formulaire">
										<tbody>
											<logic:iterate id="practice" name="criterium"
												property="practices.list" indexId="practiceId">
												<tr>
													<td>
													<table class="formulaire">
														<tr>
															<td><bean:define id="practiceName" name="practice"
																property="name" type="String" /> <af:dropDownPanel
																titleKey="<%=practiceName%>" lazyLoading="false"
																contentClass="gauche">
																<logic:notEmpty name="practice" property="formula">
																	<table>
																		<tr>
																			<af:field key="grid_detail.formula.componentLevel"
																				property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].practices.list[" + practiceId + "].formula.componentLevel"%>' />
																		</tr>
																		<tr>
																			<af:field key="grid_detail.formula.measures"
																				styleClassLabel="right"
																				property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].practices.list[" + practiceId + "].formula.measures"%>' />
																		</tr>
																		<tr>
																			<af:field
																				key="grid_detail.practice.weightingFunction"
																				property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].practices.list[" + practiceId + "].weightingFunction"%>' />
																		</tr>
																		<tr>
																			<af:field key="grid_detail.practice.effort"
																				property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].practices.list[" + practiceId + "].effort"%>' />
																		</tr>
																		<tr>
																			<af:field key="grid_detail.formula.trigger"
																				property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].practices.list[" + practiceId + "].formula.triggerCondition"%>' />
																		</tr>
																			<logic:iterate id="condition" name="practice"
																				property="formula.conditions" indexId="conditionId">
																		<tr>
																				<af:field key="grid_detail.formula.condition" size="60"
																					property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].practices.list[" + practiceId + "].formula.conditions[" + conditionId + "]"%>' />
																			</logic:iterate>
																		</tr>
																	</table>
																</logic:notEmpty>
																<logic:empty name="practice" property="formula">
																	<table>
																		<tr>
																			<af:field key="grid_detail.practice.effort"
																				property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].practices.list[" + practiceId + "].effort"%>' />
																		</tr>
																		<tr>
																			
																			<af:field key="grid_detail.period"
																				property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].practices.list[" + practiceId + "].period"%>' />
																			<%--<af:field key="grid_detail.unit"
																				property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].practices.list[" + practiceId + "].unit"%>' />--%>
																			<td><bean:message key="grid_detail.unit"/>&nbsp
																			<af:select property='<%="factors.list[" + factorId + "].criteria.list[" + criteriumId + "].practices.list[" + practiceId + "].unit"%>' >
																				<af:option value="" key="timelimitation.unit.always"/>
																				<af:option value="D" key="timelimitation.unit.day"/>
																				<af:option value="M" key="timelimitation.unit.month"/>
																				<af:option value="Y" key="timelimitation.unit.year"/>
																			</af:select>
																			</td>
																		</tr>
																	</table>
																</logic:empty>
															</af:dropDownPanel>
														</tr>
													</table>
													</td>
													</td>
												</tr>
											</logic:iterate>
										</tbody>
									</table>
								</af:tab>
							</logic:iterate>
						</af:tabbedPane>
					</af:dropDownPanel>
				</logic:iterate>
				<af:buttonBar>
					<af:button name="modify" callMethod="update" type="form" />
				</af:buttonBar>
			</af:form>

		</af:canvasCenter>
	</af:body>
</af:page>